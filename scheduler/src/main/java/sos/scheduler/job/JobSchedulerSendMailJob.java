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
package sos.scheduler.job;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import sos.net.SOSMailOrder;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.util.SOSDate;
import sos.textprocessor.SOSDocumentFactoryTextProcessor;
import sos.textprocessor.SOSPlainTextProcessor;
import sos.textprocessor.SOSTextProcessor;


/**
 * process mails
 * 
 * job parameters:
 * table_mails
 * table_mail_attachments
 * table_settings
 * application_mail
 * section_mail
 * application_mail_templates
 * section_mail_templates
 * mail_to
 * queue_directory
 * log_directory
 * log_only
 * 
 * order parameters:
 * id order identification
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-01-03 
 */
public class JobSchedulerSendMailJob extends JobSchedulerMailJob {

    protected ArrayList mailOrders                     = null;
    
    protected Iterator mailOrderIterator            = null;

    protected String encoding                       = "7bit";
    protected String charset                        = "iso-8859-1";

    protected String attachmentEncoding             = "Base64";
    protected String attachmentCharset              = "iso-8859-1";
    
    protected SOSTextProcessor mailTextProcessor                                = null;
    
    protected SOSPlainTextProcessor mailPlainTextProcessor                      = null;
    
    protected SOSDocumentFactoryTextProcessor mailDocumentFactoryTextProcessor  = null;

    protected boolean hasDatabase                   = true;

    
    
    /**
     * initialization 
     */
    public boolean spooler_init() {

        if (!super.spooler_init()) return false;        
        return true;
    }
    
    
    /**
     * select mails to process for classic job mode 
     */
    public boolean spooler_open() {
        
        try {
            // to select mails for classic job mode
            if (spooler_task.job().order_queue() == null) {
                this.mailOrders = this.getConnection().getArrayValue("SELECT \"ID\" FROM " + this.getTableMails() 
                    + " WHERE \"STATUS\"=" + STATE_READY + " AND (\"TARGETED\" IS NULL OR \"TARGETED\"<=%now) ORDER BY \"ID\" ASC");

                this.mailOrderIterator = this.mailOrders.iterator();
                return this.mailOrderIterator.hasNext();
            } else {
                return true;
            }
            
        } catch (Exception e){
            spooler_log.warn("failed to retrieve mail orders: " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (this.getConnection() != null) this.getConnection().rollback();
            } 
            catch (Exception ex) {} // gracefully ignore this error to preserve the original exception
        }
    }
    
    
    /**
     * process single mail 
     */
    public boolean spooler_process() {

        Order order = null;
        Variable_set orderData = null;
        
        int mailOrderId = 0;
        boolean rc = true;
        
        try {
        	mailOrderId = Integer.parseInt( this.mailOrderIterator.next().toString());
        	SOSMailOrder mailOrder = new SOSMailOrder(this.sosMailSettings, this.getConnection());
        	if (this.getLogger() != null) mailOrder.setSOSLogger(this.getLogger());
        	//  job scheduler sets mail host if no default was specified by settings
            if (mailOrder.getHost() == null || mailOrder.getHost().length() == 0) {
                if (!spooler_log.mail().smtp().equalsIgnoreCase("-queue")) {
                    mailOrder.setHost(spooler_log.mail().smtp());
                } else {
                    throw new Exception("no SMTP host was configured, global settings contain smtp=-queue");
                }
            }
            	
            
            // job scheduler sets mail queue directory if no default was specified by parameters or settings
            if (mailOrder.getQueueDir() == null || mailOrder.getQueueDir().length() == 0)
            	mailOrder.setQueueDir(this.getQueueDirectory());

            // set queue prefix "sos" to enalbe dequeueing by JobSchedulerMailDequeueJob
            mailOrder.setQueuePraefix(this.getQueuePrefix());
            
            // classic or order driven
            if (spooler_task.job().order_queue() == null) {                                
                mailOrder.load(mailOrderId);
                rc = this.mailOrderIterator.hasNext();
                
            } else {
                order = spooler_task.order();
                orderData = (Variable_set) spooler_task.order().payload();
                
                if ( orderData.var("id") == null || orderData.var("id").toString().length() == 0){
                    mailOrder.initOrder();
                    // TODO:Parameter dokumentieren
                    if(orderData.var("mail_from")!=null && orderData.var("mail_from").length()>0){
                    	mailOrder.setFrom(orderData.var("mail_from"));
                    }
                    if(orderData.var("mail_to")!=null && orderData.var("mail_to").length()>0){
                    	mailOrder.addRecipient(orderData.var("mail_to"));
                    }
                    if(orderData.var("mail_cc")!=null && orderData.var("mail_cc").length()>0){
                    	mailOrder.addCC(orderData.var("mail_cc"));
                    }
                    if(orderData.var("mail_bcc")!=null && orderData.var("mail_bcc").length()>0){
                    	mailOrder.addBCC(orderData.var("mail_bcc"));
                    }
                    if(orderData.var("mail_subject")!=null && orderData.var("mail_subject").length()>0){
                    	mailOrder.setSubject(orderData.var("mail_subject"));
                    }
                    if(orderData.var("mail_body")!=null && orderData.var("mail_body").length()>0){
                    	mailOrder.setBody(orderData.var("mail_body"));
                    }
                    if(orderData.var("mail_attachment")!=null && orderData.var("mail_attachment").length()>0){
                    	mailOrder.addAttachment(orderData.var("mail_attachment"));
                    }
                } else{
                try{
                	mailOrder.load(mailOrderId); 
                }catch (Exception e){
                    if (this.getLogger() != null) this.getLogger().info("failed to load order [" + orderData.var("id")+"]: " + e.getMessage());
                    return false;
                }
                }
                // setback this order for future target date
                if (mailOrder.getTargeted() != null) {
                    Date currentDate = SOSDate.getTime();
                    Date targetDate = mailOrder.getTargeted();
                    if (targetDate.after(currentDate)) {
                        Calendar target = Calendar.getInstance();
                        target.setTime(targetDate);
                        spooler_task.job().set_delay_order_after_setback(1, (target.getTimeInMillis()-System.currentTimeMillis())/1000);
                        spooler_task.order().setback();
                        if (this.getLogger() != null) this.getLogger().info("order is set back for target date: " + mailOrder.getTargeted().toString());
                        return false;
                    }
                }
            }

            
            try { // to check status: allow all status values except STATE_SUCCESS and STATE_CANCEL                    
                    switch (mailOrder.getStatus()) {
                        case STATE_SUCCESS: throw new Exception("mail order has already been successfully processed");
                        case STATE_CANCEL:  throw new Exception("mail order has already been cancelled"); 
                    }
                
            } catch (Exception ex) {
                if (this.getLogger() != null) this.getLogger().info("mail status [" + mailOrder.getStatus() + "] not applicable for order [" + mailOrder.getId() + "]: " + ex.getMessage());
                // return value for classic and order driven processing
                if (spooler_task.job().order_queue() == null) {
                    return this.mailOrderIterator.hasNext();
                } else {
                    return false;
                }
            }            
           
            try {
                File mailFile  = null;
                String message = "";

                // clear recipients for test purposes
                if (this.getMailTo() != null && this.getMailTo().length() > 0) {
                    if (this.getLogger() != null) this.getLogger().info("mail recipients [" + mailOrder.getRecipientsAsString() + "] are replaced by settings: " + this.getMailTo());
                    mailOrder.clearRecipients();
                    mailOrder.addRecipient(this.getMailTo());
                }

                if (this.getLogDirectory() != null && this.getLogDirectory().length() > 0) {
                    mailFile = this.getMailFile(this.getLogDirectory());
                    mailOrder.dumpMessageToFile(mailFile, true);
                }
                mailOrder.setModifiedBy(spooler_task.job().name());
                mailOrder.setJobId(spooler_task.id());
                if (this.isLogOnly()) {
                    message = "mail was NOT sent but stored to file: " + mailFile.getAbsolutePath();
                    if (this.getLogger() != null) this.getLogger().info(message);
                    message = (message.length() > 250 ? message.substring(message.length()-250) : message);
                    message = message.replaceAll("'", "''");
                } else mailOrder.send() ;

                if (this.getLogger() != null) this.getLogger().info("mail was " + (this.isLogOnly() ? "processed" : "sent") + " for order " + mailOrderId + " to: " + mailOrder.getRecipientsAsString());
                

                if (this.getLogger() != null) this.getLogger().debug3("mail was sent with headers: " + mailOrder.dumpHeaders());
            } catch (Exception ex) {
                throw new Exception("mail was NOT sent for order " + mailOrderId + ": " + ex.getMessage());
            }


            // return value for classic and order driven processing
            if (spooler_task.job().order_queue() == null) {
                return this.mailOrderIterator.hasNext();
            } else {
                return rc;
            }
        }    
        catch (Exception e) {
            if (spooler_task.job().order_queue() != null) {
                spooler_log.warn("error occurred processing mail [" + ((order != null) ? "job chain: " + order.job_chain().name() + ", order: " + order.id() : "(none)") + "]: " + e.getMessage());
            } else { 
                spooler_log.warn("error occurred processing mail: " + e.getMessage());
            }            
            
            // restart this order driven task in case of errors to make the task log available by mail
            if (spooler_task.job().order_queue() != null) {            
                spooler_task.end();
                return false;
            } else {
                return this.mailOrderIterator.hasNext();
            }
        } 
        finally {
            try {
                if (this.getConnection() != null) this.getConnection().rollback();
            } 
            catch (Exception ex) {} // gracefully ignore this error to preserve the original exception
        }
	}


	public boolean hasDatabase() {

		return true;
	}  
    
    

}
