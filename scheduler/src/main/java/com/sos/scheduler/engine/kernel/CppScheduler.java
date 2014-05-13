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
package com.sos.scheduler.engine.kernel;

import com.google.common.collect.ImmutableList;
import com.sos.scheduler.engine.cplusplus.runtime.CppProxy;
import com.sos.scheduler.engine.main.SchedulerControllerBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Iterables.toArray;

/** Schnittstelle zum Start des C++-Teils des JobScheduler über JNI. */
public class CppScheduler {
    private static final Logger logger = LoggerFactory.getLogger(CppScheduler.class);
    private static final AtomicReference<CppScheduler> onlyInstance = new AtomicReference<CppScheduler>();
    private String name = null;

    public static void loadModuleFromPath() {
        System.loadLibrary("scheduler");
    }

    public final void loadModule(File moduleFile) {
        try {
            logger.trace("Load "+moduleFile+", java.library.path="+System.getProperty("java.library.path"));
            System.load(moduleFile.getPath());
            logger.trace(moduleFile.getName() + " loaded");
        } catch (Throwable t) {
            logger.error("Load "+moduleFile+": "+t);
            logger.error("java.library.path=" + System.getProperty("java.library.path"));
            throw propagate(t);
        }
    }

    public final int run(ImmutableList<String> arguments, String argumentLine, SchedulerControllerBridge controllerBridge) {
        name = controllerBridge.getName();
        boolean ok  = onlyInstance.compareAndSet(null, this);
        if (!ok)
            throw new RuntimeException("Running "+ onlyInstance.get() +" hinders start of "+this);
        try {
            return run2(arguments, argumentLine, controllerBridge);
        }
        finally {
            onlyInstance.set(null);
        }
    }

    private int run2(ImmutableList<String> arguments, String argumentLine, SchedulerControllerBridge controllerBridge) {
        CppProxy.threadLock.requireUnlocked();
        CppProxy.threadLock.lock();
        if (logger.isTraceEnabled()) logger.trace(this +" starts");
        try {
            return runNative(toArray(arguments, String.class), argumentLine, controllerBridge);
        }
        finally {
            if (logger.isTraceEnabled()) logger.trace(this +" has ended");
            CppProxy.threadLock.unlock();
        }
    }

    @Override public String toString() {
        return "JobScheduler C++ Engine '"+name+"'";
    }

    private native int runNative(String[] arguments, String argumentLine, SchedulerControllerBridge controllerBridge);
}
