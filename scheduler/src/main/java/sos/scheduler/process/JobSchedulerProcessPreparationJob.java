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
package sos.scheduler.process;


import sos.spooler.Order;

/**
 * <p>JobSchedulerPreparationJob implements the primer job of a job chain that 
 * copies parameters from a configuration to the order payload.</p>
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05
 * @deprecated use sos.scheduler.managed.configuration.JobSchedulerConfigurationPreparationJob 
 */

public class JobSchedulerProcessPreparationJob extends ProcessOrderJob {


    /**
     * Verarbeitung
     * @see sos.spooler.Job_impl#spooler_process()
     */
    public boolean spooler_process() {

        Order order = null;
        String orderId = "(none)";
        
        try {

            try { // to assign a configuration to this order
                if (spooler_job.order_queue() != null) {
                    order = spooler_task.order();
                    orderId = order.id();

                    if (order.params().value("configuration_path") != null && order.params().value("configuration_path").length() > 0) {
                        this.setConfigurationPath(order.params().value("configuration_path"));
                    } else if (spooler_task.params().value("configuration_path") != null && spooler_task.params().value("configuration_path").length() > 0) {
                        this.setConfigurationPath(spooler_task.params().value("configuration_path"));
                    }

                    if (order.params().value("configuration_file") != null && order.params().value("configuration_file").length() > 0) {
                        this.setConfigurationFilename(order.params().value("configuration_file"));
                    } else if (spooler_task.params().value("configuration_file") != null && spooler_task.params().value("configuration_file").length() > 0) {
                        this.setConfigurationFilename(spooler_task.params().value("configuration_file"));
                    }
                }

                // load and assign configuration
                this.initConfiguration();

                // prepare parameters and attributes
                this.prepare();
                
            } catch (Exception e) {
                throw new Exception("error occurred preparing order: " + e.getMessage());
            }
            
            return (spooler_task.job().order_queue() != null) ? true : false;
            
        } catch (Exception e) {
            spooler_log.warn("error occurred processing order [" + orderId + "]: " + e.getMessage());
            return false;
        }
    }
    
}
