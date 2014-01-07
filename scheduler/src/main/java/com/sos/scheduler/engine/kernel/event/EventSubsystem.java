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
package com.sos.scheduler.engine.kernel.event;

import com.sos.scheduler.engine.cplusplus.runtime.annotation.ForCpp;
import com.sos.scheduler.engine.data.event.AbstractEvent;
import com.sos.scheduler.engine.data.event.Event;
import com.sos.scheduler.engine.eventbus.EventSource;
import com.sos.scheduler.engine.eventbus.SchedulerEventBus;
import com.sos.scheduler.engine.kernel.scheduler.Subsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sos.scheduler.engine.eventbus.HasUnmodifiableDelegates.tryUnmodifiableEventSource;

@ForCpp
@Singleton
public final class EventSubsystem implements Subsystem {
    private static final Logger logger = LoggerFactory.getLogger(EventSubsystem.class);

    private final SchedulerEventBus eventBus;

    @Inject private EventSubsystem(SchedulerEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /** @param e {@link AbstractEvent} statt {@link com.sos.scheduler.engine.data.event.Event}, weil C++/Java-Generator die Interface-Hierarchie nicht berücksichtig. */
    @ForCpp private void report(AbstractEvent e) {
        eventBus.publish(e);
    }

    /** @param e {@link AbstractEvent} statt {@link com.sos.scheduler.engine.data.event.Event}, weil C++/Java-Generator die Interface-Hierarchie nicht berücksichtig.
     * @param eventSource {@link Object} statt {@link EventSource}, weil C++/Java-Generator die Interface-Hierarchie nicht berücksichtig. */
    @ForCpp private void report(AbstractEvent e, Object eventSource) {
        EventSource o = (EventSource)eventSource;
        eventBus.publish(e, o);
    }

    @ForCpp private void checkNumberOfEventCodes(int count) {
        checkArgument(count == CppEventCode.values().length, "C++-Event_code does not match CppEventCode");
    }

    @ForCpp private void reportEventClass(int cppEventCode, Object eventSource) {
        try {
            EventSource o = (EventSource)eventSource;
            Event e = CppEventFactory.newInstance(CppEventCode.values()[cppEventCode], o);
            eventBus.publish(e, tryUnmodifiableEventSource(e, o));
        } catch (Exception x) {
            logger.error("EventSubsystem.reportEventClass("+cppEventCode+"):", x);
        }
    }

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
