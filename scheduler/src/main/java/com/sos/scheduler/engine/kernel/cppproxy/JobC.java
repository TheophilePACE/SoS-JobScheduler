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
package com.sos.scheduler.engine.kernel.cppproxy;

import com.sos.scheduler.engine.cplusplus.runtime.CppProxyWithSister;
import com.sos.scheduler.engine.cplusplus.runtime.Sister;
import com.sos.scheduler.engine.cplusplus.runtime.SisterType;
import com.sos.scheduler.engine.cplusplus.runtime.annotation.CppClass;
import com.sos.scheduler.engine.data.job.TaskPersistent;
import com.sos.scheduler.engine.kernel.job.Job;
import com.sos.scheduler.engine.kernel.scheduler.HasInjector;

@CppClass(clas="sos::scheduler::Job", directory="scheduler", include="spooler.h")
public interface JobC extends CppProxyWithSister<Job> {
    SisterType<Job, JobC> sisterType = new SisterType<Job, JobC>() {
        public Job sister(JobC proxy, Sister context) {
            return new Job(proxy, ((HasInjector)context).injector());
        }
    };

    String file_based_state_name();
    void set_force_file_reread();
    boolean is_file_based_reread();
    String name();
    String path();
    String source_xml();
    String description();
    Prefix_logC log();
    String state_name();
    void set_state_cmd(String cmd);
    boolean is_permanently_stopped();
    long next_start_time_millis();
    void enqueue_task(TaskPersistent o);
}
