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
package com.sos.scheduler.engine.main;

import com.google.common.collect.ImmutableList;
import com.sos.scheduler.engine.common.sync.ThrowableMailbox;
import com.sos.scheduler.engine.common.time.Stopwatch;
import com.sos.scheduler.engine.common.time.Time;
import com.sos.scheduler.engine.cplusplus.runtime.CppProxyInvalidatedException;
import com.sos.scheduler.engine.eventbus.SchedulerEventBus;
import com.sos.scheduler.engine.kernel.Scheduler;
import com.sos.scheduler.engine.kernel.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.google.common.base.Preconditions.checkState;

/** Steuert den {@link SchedulerThread}. */
public class SchedulerThreadController implements SchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerThreadController.class);
    private static final Time terminationTimeout = Time.of(5);

    private final String name;
    private final SchedulerEventBus eventBus = new SchedulerEventBus();
    private final Settings settings = new Settings();
    private boolean isStarted = false;
    private final ThrowableMailbox<Throwable> throwableMailbox = new ThrowableMailbox<Throwable>();
    private final SchedulerThreadControllerBridge controllerBridge = new SchedulerThreadControllerBridge(this, eventBus);
    private final SchedulerThread thread = new SchedulerThread(controllerBridge);

    public SchedulerThreadController(String name) {
        this.name = name;
    }

    @Override public final void setSettings(Settings o) {
        checkIsNotStarted();
        settings.setAll(o);
    }

    public final void loadModule(File cppModuleFile) {
        thread.loadModule(cppModuleFile);
    }

    @Override public final void startScheduler(Iterable<String> args) {
        checkIsNotStarted();
        controllerBridge.start();
        thread.startThread(ImmutableList.copyOf(args));
        isStarted = true;
    }

    @Override public final void close() {
        Stopwatch stopwatch = new Stopwatch();
        terminateScheduler();
        if (!tryJoinThread(terminationTimeout)) {
            logger.warn("Still waiting for JobScheduler termination ("+terminationTimeout+") ...");
            tryJoinThread(Time.eternal);
            logger.info("JobScheduler has been terminated after "+stopwatch);
        }
        controllerBridge.close();
        eventBus.dispatchEvents();  // Thread-sicher, weil der Scheduler-Thread beendet ist, also selbst kein dispatchEvents() mehr aufrufen kann.
        throwableMailbox.throwUncheckedIfSet();
    }

    public final Scheduler waitUntilSchedulerState(SchedulerState s) {
        try {
            checkIsStarted();
            Scheduler scheduler = controllerBridge.waitUntilSchedulerState(s);
            throwableMailbox.throwUncheckedIfSet();
            return scheduler;
        }
        catch (InterruptedException x) { throw new RuntimeException(x); }
    }

    @Override public final void terminateAfterException(Throwable t) {
        throwableMailbox.setIfFirst(t);
        terminateScheduler();
    }

    final void setThrowable(Throwable t) {
        throwableMailbox.setIfFirst(t);
    }

    @Override public final void terminateScheduler() {
        try {
            controllerBridge.terminate();
        } catch (CppProxyInvalidatedException x) {
            logger.debug("controllerBridge.terminate()", x);
        }
    }

    @Override public final boolean tryWaitForTermination(Time timeout) {
        checkIsStarted();
        boolean result = tryJoinThread(timeout);
        throwableMailbox.throwUncheckedIfSet();
        return result;
    }

    private boolean tryJoinThread(Time timeout) {
        try {
            if (timeout == Time.eternal)  thread.join();
            else timeout.unit.timedJoin(thread, timeout.value);
        }
        catch (InterruptedException x) { throw new RuntimeException(x); }
        return !thread.isAlive();
    }

    public void checkIsNotStarted() {
        checkState(!isStarted, "Scheduler has already been started");
    }

    private void checkIsStarted() {
        checkState(isStarted, "Scheduler has not been started");
    }

    public final boolean isStarted() {
        return isStarted;
    }

    @Override public final int exitCode() {
        return thread.exitCode();
    }

    @Override public final SchedulerEventBus getEventBus() {
        return eventBus;
    }

    public final Settings getSettings() {
        return settings;
    }

    public String getName() {
        return name;
    }
}
