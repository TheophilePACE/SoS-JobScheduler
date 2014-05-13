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
package com.sos.scheduler.engine.eventbus.annotated;

import com.sos.scheduler.engine.eventbus.EventHandlerAnnotated;
import com.sos.scheduler.engine.eventbus.EventSource;
import com.sos.scheduler.engine.eventbus.EventSourceEvent;
import com.sos.scheduler.engine.data.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Eine {@link com.sos.scheduler.engine.eventbus.EventSubscription} für eine mit
 * @{@link com.sos.scheduler.engine.eventbus.HotEventHandler} annotierte Methode mit einem
 * Parameter für das {@link Event} und einem zweiten Parameter für {@link EventSource}
 * mit dem auslösenden Objekt. Das auslösende Objekt (zum Beispiel eine Order) ist nur in einem
 * @{@code HotEventHandler} gültig, weshalb es nicht ins {@code Event} aufgenommen wird. */
public class EventSourceMethodEventSubscription extends MethodEventSubscription {
    private final Class<? extends EventSource> eventSourceClass;

    EventSourceMethodEventSubscription(EventHandlerAnnotated annotatedObject, Method method) {
        super(annotatedObject, method);
        this.eventSourceClass = checkedParameterClass(method, 1, EventSource.class);
        checkMethodParameterCount(method, 1, 2);
    }

    public final boolean eventSourceMatches(EventSourceEvent e) {
        return eventSourceClass.isAssignableFrom(e.getEventSource().getClass());
    }

    @Override protected final void invokeHandler(Event event) throws InvocationTargetException, IllegalAccessException {
        EventSourceEvent e = (EventSourceEvent)event;
        getMethod().invoke(getAnnotatedObject(), e.getEvent(), e.getEventSource());
    }

    @Override public String toString() {
        return getMethod().getDeclaringClass().getSimpleName() +"."+ getMethod().getName() +
                "("+ getEventClass().getSimpleName() +","+ eventSourceClass.getSimpleName() +")";
    }
}
