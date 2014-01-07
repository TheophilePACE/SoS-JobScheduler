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
import com.sos.scheduler.engine.data.folder.FileBasedActivatedEvent;
import com.sos.scheduler.engine.data.folder.FileBasedRemovedEvent;
import com.sos.scheduler.engine.data.job.TaskClosedEvent;
import com.sos.scheduler.engine.data.job.TaskEndedEvent;
import com.sos.scheduler.engine.data.job.TaskStartedEvent;
import com.sos.scheduler.engine.data.log.LogEvent;
import com.sos.scheduler.engine.data.log.SchedulerLogLevel;
import com.sos.scheduler.engine.data.order.*;
import com.sos.scheduler.engine.eventbus.EventSource;
import com.sos.scheduler.engine.kernel.folder.FileBased;
import com.sos.scheduler.engine.kernel.job.Task;
import com.sos.scheduler.engine.kernel.order.Order;

@ForCpp
public final class CppEventFactory {
    
    static Event newInstance(CppEventCode cppEventCode, EventSource o) {
        switch (cppEventCode) {
            case fileBasedActivatedEvent:
                return new FileBasedActivatedEvent(((FileBased)o).getTypedPath());

            case fileBasedRemovedEvent:
                return new FileBasedRemovedEvent(((FileBased)o).getTypedPath());

            case taskStartedEvent: {
                Task task = (Task)o;
                return new TaskStartedEvent(task.getId(), task.getJob().getPath());
            }

            case taskEndedEvent: {
                Task task = (Task)o;
                return new TaskEndedEvent(task.getId(), task.getJob().getPath());
            }

            case taskClosedEvent: {
                Task task = (Task)o;
                return new TaskClosedEvent(task.getId(), task.getJob().getPath());
            }

            case orderTouchedEvent:
                return new OrderTouchedEvent(((Order)o).getKey());

            case orderFinishedEvent:
                return new OrderFinishedEvent(((Order)o).getKey());

            case orderSuspendedEvent:
                return new OrderSuspendedEvent(((Order)o).getKey());

            case orderResumedEvent:
                return new OrderResumedEvent(((Order)o).getKey());

            case orderStepStartedEvent: {
                Order order = (Order)o;
                return new OrderStepStartedEvent(order.getKey(), order.getState());
            }
        }
        throw new RuntimeException("Not implemented cppEventCode="+cppEventCode);
    }

    @ForCpp private static AbstractEvent newLogEvent(int cppLevel, String message) {
        return LogEvent.of(SchedulerLogLevel.ofCpp(cppLevel), message);
    }

    @ForCpp private static AbstractEvent newOrderStateChangedEvent(String jobChainPath, String orderId, String previousState) {
        return new OrderStateChangedEvent(OrderKey.of(jobChainPath, orderId), new OrderState(previousState));
    }

    @ForCpp private static AbstractEvent newOrderStepEndedEvent(String jobChainPath, String orderId, int orderStateTransitionCpp) {
        return new OrderStepEndedEvent(OrderKey.of(jobChainPath, orderId), OrderStateTransition.ofCppCode(orderStateTransitionCpp));
    }

    private CppEventFactory() {}
}
