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

package sos.scheduler.webservice;


import sos.spooler.Job_impl;
import sos.spooler.Subprocess;
import sos.spooler.Variable_set;


public class JobSchedulerWebServiceShellJob extends Job_impl {

    
    public boolean spooler_process() {

        try {
            String commandline = "";
            Variable_set parameters = null;;
            Subprocess subprocess = spooler_task.create_subprocess();
            
            if (spooler_task.params().value("shell_command") != null && spooler_task.params().value("shell_command").length() > 0) {
                commandline = spooler_task.params().value("shell_command");
            } else {
                throw new Exception( "no value for shell command was given for parameter [shell_command]" );
            }

            /* these are samples for shell commands:
            if ( System.getProperty("os.name").toLowerCase().startsWith("win") ) {
              // Windows: the command to be executed, modify this to an executable script for your platform
              commandline = "cmd.exe /V:ON /C " + spooler.directory() + "samples/shell.webservice.command/webservice_shell.cmd";
            } else {
              // Unix: the command to be executed, modify this to an executable script for your platform
              commandline = spooler.directory() + "samples/shell.webservice.command/webservice_shell.sh";
            }
            */

            // retrieve parameters from order payload (<job order="yes"/>) or from task parameters
            if ( spooler_job.order_queue() != null ) {
              parameters = spooler_task.order().params();
            } else {
              parameters = spooler_task.params();
            }

            if (parameters != null) {
              String[] parameter_names = parameters.names().split(";");
              for(int i=0; i<parameter_names.length; i++) {
                if ( parameter_names[i].equalsIgnoreCase("shell_command") ) continue;
                // .. either append parameter names and values to the comamnd line (this needs more checking for vulnerabilities)
                //  spooler_log.debug3( "parameter " + parameter_names[i] + "=" + parameters.value(parameter_names[i]) );
                //  commandline += " " + parameter_names[i] + "=\"" + parameters.value(parameter_names[i]) + "\"";

                // .. or move parameters to environment variables which is more safe
                subprocess.set_environment( parameter_names[i], parameters.value(parameter_names[i]) );
                // .. and for this sample append parameter names to the command line
                commandline += " \"" + parameter_names[i] + "\"";
              }
            }

            spooler_log.info( ".. executing command line: " + commandline );
            subprocess.start( commandline );

            // wait until the subprocess terminates, timeout is restricted by <job timeout="..."/> or subprocess.wait_for_termination(60)
            subprocess.wait_for_termination();
            if (subprocess.terminated()) {
              spooler_log.info( "exit code=" + subprocess.exit_code() );
              spooler_log.info( "termination signal=" + subprocess.termination_signal() );
            }

            return !(spooler_job.order_queue() == null);
        } catch (Exception e) {
            spooler_log.warn("error occurred processing web service request: " + e.getMessage());
            return false;
        }
    }

}
