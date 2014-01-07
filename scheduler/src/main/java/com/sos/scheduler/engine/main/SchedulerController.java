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

import com.sos.scheduler.engine.common.time.Time;
import com.sos.scheduler.engine.eventbus.SchedulerEventBus;
import com.sos.scheduler.engine.kernel.settings.Settings;

/** Steuerung für den C++-Scheduler in einem eigenen nebenläufigen Thread. */
public interface SchedulerController {
    /** @throws IllegalStateException, wenn nach {@link #startScheduler(Iterable)}} aufgerufen. */
    void setSettings(Settings o);

    Settings getSettings();

    /** Startet den Scheduler ohne zu warten. */
    void startScheduler(Iterable<String> arguments);

    /** Veranlasst die Beendigung des Schedulers, wartet aufs Ende und schließt alles. */
    void close();

    /** Veranlasst die Beendigung des Schedulers. */
    void terminateScheduler();

    /** Veranlasst die Beendigung des Schedulers nach einem Fehler.
     * Kann aus einem anderen Thread aufgerufen werden und lässt die Warte-Methoden die Exception werfen. */
    void terminateAfterException(Throwable x);

    boolean tryWaitForTermination(Time timeout);

    int exitCode();

    SchedulerEventBus getEventBus();
}
