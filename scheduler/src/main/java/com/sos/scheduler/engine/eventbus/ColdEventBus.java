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

import com.sos.scheduler.engine.data.event.Event;
import com.sos.scheduler.engine.eventbus.annotated.ColdMethodEventSubscriptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Throwables.propagate;

public class ColdEventBus extends AbstractEventBus implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ColdEventBus.class);

    private final LinkedBlockingQueue<Call> callQueue = new LinkedBlockingQueue<Call>();

    public ColdEventBus() {
        super(ColdMethodEventSubscriptionFactory.singleton);
    }

    @Override public final void publish(Event e) {
        publish(calls(e));
    }

    final void publish(Collection<Call> c) {
        callQueue.addAll(c);
    }

    public final void run() {
        try {
            dispatchEvents(true);
        } catch (InterruptedException ignored) {}
    }

    public final void dispatchEvents() {
        try {
            dispatchEvents(false);
        } catch (InterruptedException e) { throw propagate(e); }    // Kommt nicht vor
    }

    private void dispatchEvents(boolean wait) throws InterruptedException {
        while (true) {
            Call call = wait? callQueue.poll(Long.MAX_VALUE, TimeUnit.DAYS) : callQueue.poll();
            if (call == null) break;
            logger.trace("dispatch "+call);
            dispatchCall(call);
        }
    }
}
