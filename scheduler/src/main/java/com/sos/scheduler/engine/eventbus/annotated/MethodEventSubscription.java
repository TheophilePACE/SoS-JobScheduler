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
import com.sos.scheduler.engine.eventbus.EventSubscription;
import com.sos.scheduler.engine.data.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Throwables.propagate;

/** Eine {@link EventSubscription} für eine mit @{@link com.sos.scheduler.engine.eventbus.EventHandler} oder
 * @{@link com.sos.scheduler.engine.eventbus.HotEventHandler} annotierte Methode. */
abstract class MethodEventSubscription implements EventSubscription {
    private final Class<? extends Event> eventClass;
    private final EventHandlerAnnotated annotatedObject;
    private final Method method;

    MethodEventSubscription(EventHandlerAnnotated o, Method method) {
        this.eventClass = checkedParameterClass(method, 0, Event.class);
        this.annotatedObject = o;
        checkReturnType(method);
        this.method = method;
        method.setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    static <T, U extends T> Class<U> checkedParameterClass(Method m, int i, Class<T> c) {
        Class<?>[] t = m.getParameterTypes();
        if (t.length < i)
            throw new IllegalArgumentException("Method "+m+" must have "+i+" parameters");
        Class<?> p = t[i];
        if (!c.isAssignableFrom(p))
            throw new IllegalArgumentException("Method "+m+" must have parameters #"+ (i+1) +" of a subtype of "+ c.getName());
        return (Class<U>)p;
    }

    static void checkMethodParameterCount(Method m, int min, int max) {
        int n = m.getParameterTypes().length;
        if (!(n >= min && n <= max))
            throw new IllegalArgumentException("Method "+m+" must have "+(min == max? min : min+" through "+max)+" arguments");
    }

    private static void checkReturnType(Method m) {
        Class<?> c = m.getReturnType();
        if (!isVoid(c))
            throw new IllegalArgumentException("Method "+m+" has an unexpected return type");
    }

    private static boolean isVoid(Class<?> c) {
        return c == Void.class || c.getName().equals("void");
    }

    @Override public final void handleEvent(Event event) {
        try {
            invokeHandler(event);
        } catch (IllegalArgumentException x) { throw new Error("Method "+ getMethod() +" rejected argument '"+event+"'", x);
        } catch (IllegalAccessException x) { throw new Error("Method "+ getMethod() +" is inaccessible: " +event, x);
        } catch (InvocationTargetException x) {
            throw propagate(x.getCause() == null? x : x.getCause());
        }
    }

    protected abstract void invokeHandler(Event event) throws InvocationTargetException, IllegalAccessException;

    @Override public final Class<? extends Event> getEventClass() {
        return eventClass;
    }

    final EventHandlerAnnotated getAnnotatedObject() {
        return annotatedObject;
    }

    final Method getMethod() {
        return method;
    }
}
