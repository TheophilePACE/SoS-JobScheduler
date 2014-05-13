/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.scheduler.engine.eventbus;

import com.google.common.collect.*;
import com.sos.scheduler.engine.eventbus.annotated.EventSourceMethodEventSubscription;
import com.sos.scheduler.engine.eventbus.annotated.MethodEventSubscriptionFactory;
import com.sos.scheduler.engine.data.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public abstract class AbstractEventBus implements EventBus {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEventBus.class);

    private final Multimap<Class<? extends Event>, EventSubscription> subscribers = HashMultimap.create();
    private final AnnotatedHandlerFinder handlerFinder;
    private final Multimap<EventHandlerAnnotated,EventSubscription> annotatedEventSubscriberMap = HashMultimap.create();
    private final Map<Class<? extends Event>, ImmutableSet<Class<? extends Event>>> cachedSuperEventClasses = newHashMap();

    protected AbstractEventBus(MethodEventSubscriptionFactory factory) {
        this.handlerFinder = new AnnotatedHandlerFinder(factory);
    }

    @Override public final void registerAnnotated(EventHandlerAnnotated o) {
        unregisterAnnotated(o);
        Iterable<EventSubscription> subscribers = handlerFinder.handlers(o);
        annotatedEventSubscriberMap.putAll(o, subscribers);
        registerAll(subscribers);
    }

    @Override public final void unregisterAnnotated(EventHandlerAnnotated o) {
        unregisterAll(annotatedEventSubscriberMap.get(o));
        annotatedEventSubscriberMap.removeAll(o);
    }

    private void registerAll(Iterable<EventSubscription> subscribers) {
        for (EventSubscription s: subscribers)
            register(s);
    }

    private void unregisterAll(Iterable<EventSubscription> subscribers) {
        for (EventSubscription s: subscribers)
            unregister(s);
    }

    public final synchronized void register(EventSubscription s) {
        subscribers.put(s.getEventClass(), s);
    }

    public final synchronized void unregister(EventSubscription s) {
        subscribers.remove(s.getEventClass(), s);
    }

    protected final synchronized ImmutableCollection<Call> calls(Event e) {
        Class<? extends Event> realEventClass = (e instanceof EventSourceEvent? ((EventSourceEvent)e).getEvent() : e).getClass();
        ImmutableList.Builder<Call> result = null;
        for (Class<? extends Event> c: allSuperEventClasses(realEventClass)) {
            for (EventSubscription s: subscribers.get(c)) {
                // matches() sollte nicht in einer Schleife aufgerufen werden. Das kann langsam werden. Stattdessen sollte die EventSource-Klasse direkt in einer Map abgelegt werden.
                if (matches(s, e)) {
                    if (result == null) result = ImmutableList.builder();
                    result.add(new Call(e, s));
                }
            }
        }
        if (result == null) return ImmutableList.of();
        else return result.build();
    }

    private static boolean matches(EventSubscription s, Event e) {
        return !(s instanceof EventSourceMethodEventSubscription) ||
                e instanceof EventSourceEvent &&
                        ((EventSourceMethodEventSubscription)s).eventSourceMatches((EventSourceEvent) e);
    }

    public final boolean publishNow(Event e) {
        boolean published = false;
        for (Call call: calls(e)) {
            dispatchCall(call);
            published = true;
        }
        return published;
    }

    protected final boolean dispatchCall(Call call) {
        try {
            call.apply();
            return true;
        } catch (Throwable t) {   // Der C++-Code soll wirklich keine Exception bekommen.
            if (t instanceof Error && t.getClass().getSimpleName().equals("TestError"))
                logger.debug("{}", call, t);
            else
                logger.error("{}", call, t);
            //Löst ein rekursives Event aus: log().error(s+": "+x);
            if (call.getEvent().getClass() == EventHandlerFailedEvent.class) {
                return false;
            } else
                return publishNow(new EventHandlerFailedEvent(call, t));
        }
    }

    private ImmutableSet<Class<? extends Event>> allSuperEventClasses(Class<? extends Event> eventClass) {
        ImmutableSet<Class<? extends Event>> result = cachedSuperEventClasses.get(eventClass);
        if (result == null) {
            Set<Class<? extends Event>> classes = newHashSet();
            collectAllSuperEventClasses(classes, eventClass);
            result = ImmutableSet.copyOf(classes);
            cachedSuperEventClasses.put(eventClass, result);
        }
        return result;
    }

    private static void collectAllSuperEventClasses(Set<Class<? extends Event>> result, Class<?> clas) {
        if (clas != null && Event.class.isAssignableFrom(clas)) {
            @SuppressWarnings("unchecked") Class<? extends Event> eventClass = (Class<? extends Event>)clas;
            if (!result.contains(eventClass)) {
                result.add(eventClass);
                collectAllSuperEventClasses(result, clas.getSuperclass());
                for (Class<?> c: clas.getInterfaces())
                    collectAllSuperEventClasses(result, c);
            }
        }
    }
}
