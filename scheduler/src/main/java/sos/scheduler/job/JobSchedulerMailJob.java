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
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import sos.net.SOSMail;
import sos.settings.SOSConnectionSettings;


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
 *
 * @author ghassan.beydoun@sos-berlin.com
 * @since 1.1 2006-03-14
 * 
 * @version $Id: JobSchedulerMailJob.java 15776 2011-11-25 12:26:24Z kb $ 
 * 
 */
public class JobSchedulerMailJob extends JobSchedulerJob {

    protected SOSMail sosMail                       = null;
    protected SOSConnectionSettings sosMailSettings = null;
    protected boolean hasDatabase                   = false;
    protected String tableMails                     = "MAILS";
    protected String tableMailAttachments           = "MAIL_ATTACHMENTS";
    private   String tableMailBouncePatterns        = "MAIL_BOUNCE_PATTERNS";
    private   String tableMailBounces               = "MAIL_BOUNCES";
    private   String tableMailBounceDeliveries      = "MAIL_BOUNCE_DELIVERIES";   
    
    private   String tableMailBouncePatternRetries  = "MAIL_BOUNCE_PATTERN_RETRIES";

    protected String tableMailSettings              = "SETTINGS";
    
    /** Applikationsname f�r eMail-Einstellungen */
    protected String applicationMail                = "email"; 

    /** Sektionsname f�r eMail-Einstellungen */
    protected String sectionMail                    = "mail_server";
    
    /** Applikationsname f�r eMail-Templates in Settings */
    protected String applicationMailTemplates       = "email_templates";
    
    /** Sektionsname f�r eMail-Templates in Settings */
    protected String sectionMailTemplates           = "mail_templates";
    
    /** Applikationsname f�r eMail-Templates der Document Factory in Settings */
    protected String applicationMailTemplatesFactory = "email_templates_factory";
    
    /** Sektionsname f�r eMail-Templates der Document Factory in Settings */
    protected String sectionMailTemplatesFactory    = "mail_templates";
    
    /** Applikationsname f�r eMail-Scripts in Settings */
    protected String applicationMailScripts         = "email";
    
    /** Sektionsname f�r eMail-Scripts in Settings */
    protected String sectionMailScripts             = "mail_start_scripts_factory";
    

    protected boolean hasLocalizedTemplates         = true;
   
    
    /** mail queue directory */
    protected String queueDirectory                 = "";

    
    /** prefix for failed mail files */
    protected String failedPrefix                   = "failed.";

    /** prefix for enqueued mail files */
    protected String queuePrefix                    = "sos.";

    /** pattern for filenames of enqueued mails */
    protected String queuePattern                   = "yyyy-MM-dd.HHmmss.S";
    
    /** regex to retrieve enqueued mail files */
    protected String queuePrefixSpec                = "^(sos.*)(?<!\\~)$";

    /** max. number of trials to dequeue mail (0=unbounded) */
    long maxDeliveryCounter                          = 0;
    
    
    /* simulation settings: overwrites to, cc, bcc */
    protected String mailTo                         = "";
    
    /* simulation settings: store mails to this directory */
    protected String logDirectory                   = "";
    
    /* simulation settings: do not send but store mails to disk */
    protected boolean logOnly                       = false;
    
    
    public static final int STATE_READY = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_CANCEL = 1001;
    public static final int STATE_ERROR = 1002;
    
    /**
     * Initialisation
     */
    public boolean spooler_init() {

        try {
            if (!super.spooler_init()) return false;        

            this.setQueueDirectory(spooler_log.mail().queue_dir());            
            
            try { // to process job parameters
                if (spooler_task != null) {
                    
                    if (spooler_task.params().var("db") != null) {
                        if (spooler_task.params().var("db").length() > 0) {
                            if (spooler_task.params().var("db").equalsIgnoreCase("true")
                                || spooler_task.params().var("db").equalsIgnoreCase("yes")
                                || spooler_task.params().var("db").equals("1")) {
                                    this.setHasDatabase(true);
                                    spooler_log.debug6(".. job parameter [db]: " + this.hasDatabase());
                                }
                        }
                    }

                    if (spooler_task.params().var("table_mails") != null) {
                        if (spooler_task.params().var("table_mails").length() > 0) {
                            this.setTableMails(spooler_task.params().var("table_mails"));
                            spooler_log.debug6(".. job parameter [table_mails]: " + this.getTableMails());
                        }
                    }

                    if (spooler_task.params().var("table_mail_attachments") != null) {
                        if (spooler_task.params().var("table_mail_attachments").length() > 0) {
                            this.setTableMailAttachments(spooler_task.params().var("table_mail_attachments"));
                            spooler_log.debug6(".. job parameter [table_mail_attachments]: " + this.getTableMailAttachments());
                        }
                    }

                    if (spooler_task.params().var("table_settings") != null) {
                        if (spooler_task.params().var("table_settingss").length() > 0) {
                            this.setTableMailSettings(spooler_task.params().var("table_settings"));
                            spooler_log.debug6(".. job parameter [table_settings]: " + this.getTableMailSettings());
                        }
                    }

                    if (spooler_task.params().var("application_mail") != null) {
                        if (spooler_task.params().var("application_mail").length() > 0) {
                            this.setApplicationMail(spooler_task.params().var("application_mail"));
                            spooler_log.debug6(".. job parameter [application_mail]: " + this.getApplicationMail());
                        }
                    }
                
                    if (spooler_task.params().var("section_mail") != null) {
                        if (spooler_task.params().var("section_mail").length() > 0) {
                            this.setSectionMail(spooler_task.params().var("section_mail"));
                            spooler_log.debug6(".. job parameter [section_mail]: " + this.getSectionMail());
                        }
                    }

                    if (spooler_task.params().var("application_mail_templates") != null) {
                        if (spooler_task.params().var("application_mail_templates").length() > 0) {
                            this.setApplicationMailTemplates(spooler_task.params().var("application_mail_templates"));
                            spooler_log.debug6(".. job parameter [application_mail_templates]: " + this.getApplicationMailTemplates());
                        }
                    }

                    if (spooler_task.params().var("section_mail_templates") != null) {
                        if (spooler_task.params().var("section_mail_templates").length() > 0) {
                            this.setSectionMailTemplates(spooler_task.params().var("section_mail_templates"));
                            spooler_log.debug6(".. job parameter [section_mail_templates]: " + this.getSectionMailTemplates());
                        }
                    }

                    
                    if (spooler_task.params().var("application_mail_templates_factory") != null) {
                        if (spooler_task.params().var("application_mail_templates_factory").length() > 0) {
                            this.setApplicationMailTemplatesFactory(spooler_task.params().var("application_mail_templates_factory"));
                            spooler_log.debug6(".. job parameter [application_mail_templates_factory]: " + this.getApplicationMailTemplatesFactory());
                        }
                    }

                    if (spooler_task.params().var("section_mail_templates_factory") != null) {
                        if (spooler_task.params().var("section_mail_templates_factory").length() > 0) {
                            this.setSectionMailTemplatesFactory(spooler_task.params().var("section_mail_templates_factory"));
                            spooler_log.debug6(".. job parameter [section_mail_templates_factory]: " + this.getSectionMailTemplatesFactory());
                        }
                    }

                    
                    if (spooler_task.params().var("mail_to") != null) {
                        if (spooler_task.params().var("mail_to").length() > 0) {
                            this.setMailTo(spooler_task.params().var("mail_to"));
                            spooler_log.debug6(".. job parameter [mail_to]: " + this.getMailTo());
                        }
                    }

                    
                    if (spooler_task.params().var("queue_prefix") != null) {
                        if (spooler_task.params().var("queue_prefix").length() > 0) {
                            this.setQueuePrefix(spooler_task.params().var("queue_prefix"));
                            spooler_log.debug6(".. job parameter [queue_prefix]: " + this.getQueuePrefix());
                        }
                    }

                    if (spooler_task.params().var("queue_prefix_spec") != null) {
                        if (spooler_task.params().var("queue_prefix_spec").length() > 0) {
                            this.setQueuePrefixSpec(spooler_task.params().var("queue_prefix_spec"));
                            spooler_log.debug6(".. job parameter [queue_prefix_spec]: " + this.getQueuePrefixSpec());
                        }
                    }

                    if (spooler_task.params().var("queue_directory") != null) {
                        if (spooler_task.params().var("queue_directory").length() > 0) {
                            this.setQueueDirectory(spooler_task.params().var("queue_directory"));
                            spooler_log.debug6(".. job parameter [queue_directory]: " + this.getQueueDirectory());
                        }
                    }

                    if (spooler_task.params().var("log_directory") != null) {
                        if (spooler_task.params().var("log_directory").length() > 0) {
                            this.setLogDirectory(spooler_task.params().var("log_directory"));
                            spooler_log.debug6(".. job parameter [log_directory]: " + this.getLogDirectory());
                        }
                    }

                    if (spooler_task.params().var("log_only") != null) {
                        if (spooler_task.params().var("log_only").length() > 0) {
                            this.setLogOnly((spooler_task.params().var("log_only").equals("1") 
                                    || spooler_task.params().var("log_only").equalsIgnoreCase("true")
                                    || spooler_task.params().var("log_only").equalsIgnoreCase("yes")) ? true : false);
                            spooler_log.debug6(".. job parameter [log_only]: " + this.isLogOnly());
                        }
                    }

                    if (spooler_task.params().var("max_delivery") != null) {
                        if (spooler_task.params().var("max_delivery").length() > 0) {
                            this.setMaxDeliveryCounter(Long.parseLong(spooler_task.params().var("max_delivery")));
                            spooler_log.debug6(".. job parameter [max_delivery]: " + this.getMaxDeliveryCounter());
                        }
                    }
                }
            }
            catch (Exception e) {
                throw (new Exception("an error occurred processing job parameters: " + e.getMessage()));
            }

            try { // to create settings instance if a connection was given
                if (this.getConnection() != null && this.hasDatabase()) {
                    this.sosMailSettings = new
                    SOSConnectionSettings( this.getConnection(),    // connection instance
                                      this.getTableMailSettings(),  // settings table
                                      this.getApplicationMail(),    // eMail application in settings
                                      this.getSectionMail(),        // eMail section in settings
                                      this.getLogger()              // logger instance
                    );
                
                    if (this.sosMailSettings.getSectionEntry("mail_to") != null && this.sosMailSettings.getSectionEntry("mail_to").length() > 0) {
                        this.setMailTo(this.sosMailSettings.getSectionEntry("mail_to"));
                        spooler_log.debug6(".. job settings [mail_to]: " + this.getMailTo());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("mail_queue_prefix") != null && this.sosMailSettings.getSectionEntry("mail_queue_prefix").length() > 0) {
                        this.setQueuePrefix(this.sosMailSettings.getSectionEntry("mail_queue_prefix"));
                        spooler_log.debug6(".. job settings [mail_queue_prefix]: " + this.getQueuePrefix());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("queue_prefix") != null && this.sosMailSettings.getSectionEntry("queue_prefix").length() > 0) {
                        this.setQueuePrefix(this.sosMailSettings.getSectionEntry("queue_prefix"));
                        spooler_log.debug6(".. job settings [queue_prefix]: " + this.getQueuePrefix());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("mail_queue_prefix_spec") != null && this.sosMailSettings.getSectionEntry("mail_queue_prefix_spec").length() > 0) {
                        this.setQueuePrefixSpec(this.sosMailSettings.getSectionEntry("mail_queue_prefix_spec"));
                        spooler_log.debug6(".. job settings [mail_queue_prefix_spec]: " + this.getQueuePrefixSpec());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("queue_prefix_spec") != null && this.sosMailSettings.getSectionEntry("queue_prefix_spec").length() > 0) {
                        this.setQueuePrefixSpec(this.sosMailSettings.getSectionEntry("queue_prefix_spec"));
                        spooler_log.debug6(".. job settings [queue_prefix_spec]: " + this.getQueuePrefix());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("mail_queue_directory") != null && this.sosMailSettings.getSectionEntry("mail_queue_directory").length() > 0) {
                        this.setQueueDirectory(this.sosMailSettings.getSectionEntry("mail_queue_directory"));
                        spooler_log.debug6(".. job settings [mail_queue_directory]: " + this.getQueueDirectory());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("queue_directory") != null && this.sosMailSettings.getSectionEntry("queue_directory").length() > 0) {
                        this.setQueueDirectory(this.sosMailSettings.getSectionEntry("queue_directory"));
                        spooler_log.debug6(".. job settings [queue_directory]: " + this.getQueueDirectory());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("mail_log_directory") != null && this.sosMailSettings.getSectionEntry("mail_log_directory").length() > 0) {
                        this.setLogDirectory(this.sosMailSettings.getSectionEntry("mail_log_directory"));
                        spooler_log.debug6(".. job settings [mail_log_directory]: " + this.getLogDirectory());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("log_directory") != null && this.sosMailSettings.getSectionEntry("log_directory").length() > 0) {
                        this.setLogDirectory(this.sosMailSettings.getSectionEntry("log_directory"));
                        spooler_log.debug6(".. job settings [log_directory]: " + this.getLogDirectory());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("mail_log_only") != null && this.sosMailSettings.getSectionEntry("mail_log_only").length() > 0) {
                        this.setLogOnly((this.sosMailSettings.getSectionEntry("mail_log_only").equals("1") 
                            || this.sosMailSettings.getSectionEntry("mail_log_only").equalsIgnoreCase("true")
                            || this.sosMailSettings.getSectionEntry("mail_log_only").equalsIgnoreCase("yes")) ? true : false);
                        spooler_log.debug6(".. job settings [mail_log_only]: " + this.isLogOnly());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("log_only") != null && this.sosMailSettings.getSectionEntry("log_only").length() > 0) {
                        this.setLogOnly((this.sosMailSettings.getSectionEntry("log_only").equals("1") 
                            || this.sosMailSettings.getSectionEntry("log_only").equalsIgnoreCase("true")
                            || this.sosMailSettings.getSectionEntry("log_only").equalsIgnoreCase("yes")) ? true : false);
                        spooler_log.debug6(".. job settings [log_only]: " + this.isLogOnly());
                    }
                
                    if (this.sosMailSettings.getSectionEntry("max_delivery") != null && this.sosMailSettings.getSectionEntry("max_delivery").length() > 0) {
                        this.setMaxDeliveryCounter(Long.parseLong(this.sosMailSettings.getSectionEntry("max_delivery")));
                        spooler_log.debug6(".. job settings [max_delivery]: " + this.getMaxDeliveryCounter());
                    }
                }
                
            } catch (Exception e) {
                throw new Exception("could not process settings: " + e.getMessage());
            }
                
                
            return true;
        } catch (Exception e){
            spooler_log.warn("failed to initialize job: " + e.getMessage());
            return false;
        }
    }
    
	

    /**
     * Cleanup
     */
    public void spooler_exit() {
        
        super.spooler_exit();
    }

    
    public File getMailFile(String path) throws Exception {

        Date d = new Date();
        StringBuffer bb = new StringBuffer();
        SimpleDateFormat s =  new SimpleDateFormat(this.getQueuePattern());
        
        if (!path.endsWith("/") && !path.endsWith("\\") ) path += "/";
        
        FieldPosition fp = new FieldPosition(0);
        StringBuffer b =  s.format(d, bb, fp);
        String lastGeneratedFilename = path + this.getQueuePrefix() + b + ".email";

        File f = new File(lastGeneratedFilename);
        while (f.exists()){
            b =  s.format(d, bb, fp);
            lastGeneratedFilename = path + this.getQueuePrefix() + b + ".email";
            f = new File(lastGeneratedFilename);
        }
        return f;
    }
    
    
    /**
     * @return Returns the applicationMail.
     */
    public String getApplicationMail() {
        return applicationMail;
    }


    /**
     * @param applicationMail The applicationMail to set.
     */
    public void setApplicationMail(String applicationMail) {
        this.applicationMail = applicationMail;
    }


    /**
     * @return Returns the applicationMailTemplates.
     */
    public String getApplicationMailTemplates() {
        return applicationMailTemplates;
    }


    /**
     * @param applicationMailTemplates The applicationMailTemplates to set.
     */
    public void setApplicationMailTemplates(String applicationMailTemplates) {
        this.applicationMailTemplates = applicationMailTemplates;
    }


    /**
     * @return Returns the sectionMail.
     */
    public String getSectionMail() {
        return sectionMail;
    }


    /**
     * @param sectionMail The sectionMail to set.
     */
    public void setSectionMail(String sectionMail) {
        this.sectionMail = sectionMail;
    }


    /**
     * @return Returns the sectionMailTemplates.
     */
    public String getSectionMailTemplates() {
        return sectionMailTemplates;
    }


    /**
     * @param sectionMailTemplates The sectionMailTemplates to set.
     */
    public void setSectionMailTemplates(String sectionMailTemplates) {
        this.sectionMailTemplates = sectionMailTemplates;
    }


    /**
     * @return Returns the tableMailAttachments.
     */
    public String getTableMailAttachments() {
        return tableMailAttachments;
    }


    /**
     * @param tableMailAttachments The tableMailAttachments to set.
     */
    public void setTableMailAttachments(String tableMailAttachments) {
        this.tableMailAttachments = tableMailAttachments;
    }


    /**
     * @return Returns the tableMails.
     */
    public String getTableMails() {
        return tableMails;
    }


    /**
     * @param tableMails The tableMails to set.
     */
    public void setTableMails(String tableMails) {
        this.tableMails = tableMails;
    }


    /**
     * @return Returns the tableMailSettings.
     */
    public String getTableMailSettings() {
        return tableMailSettings;
    }


    /**
     * @param tableMailSettings The tableMailSettings to set.
     */
    public void setTableMailSettings(String tableMailSettings) {
        this.tableMailSettings = tableMailSettings;
    }


    /**
     * @return Returns the mailTo.
     */
    public String getMailTo() {
        return mailTo;
    }


    /**
     * @param mailTo The mailTo to set.
     */
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }


    /**
     * @return Returns the logDirectory.
     */
    public String getLogDirectory() {
        return logDirectory;
    }


    /**
     * @param logDirectory The logDirectory to set.
     */
    public void setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
    }


    /**
     * @return Returns the logOnly.
     */
    public boolean isLogOnly() {
        return logOnly;
    }


    /**
     * @param logOnly The logOnly to set.
     */
    public void setLogOnly(boolean logOnly) {
        this.logOnly = logOnly;
    }



    /**
     * @return Returns the queueDirectory.
     */
    public String getQueueDirectory() {
        return queueDirectory;
    }



    /**
     * @param queueDirectory The queueDirectory to set.
     */
    public void setQueueDirectory(String queueDirectory) {
        this.queueDirectory = queueDirectory;
    }



    /**
     * @return Returns the queuePrefix.
     */
    public String getQueuePrefix() {
        return queuePrefix;
    }



    /**
     * @param queuePrefix The queuePrefix to set.
     */
    public void setQueuePrefix(String queuePrefix) {
        this.queuePrefix = queuePrefix;
    }



    /**
     * @return Returns the queuePrefixSpec.
     */
    public String getQueuePrefixSpec() {
        return queuePrefixSpec;
    }



    /**
     * @param queuePrefixSpec The queuePrefixSpec to set.
     */
    public void setQueuePrefixSpec(String queuePrefixSpec) {
        this.queuePrefixSpec = queuePrefixSpec;
    }



    /**
     * @return Returns the maxDeliveryCounter.
     */
    public long getMaxDeliveryCounter() {
        return maxDeliveryCounter;
    }



    /**
     * @param maxDeliveryCounter The maxDeliveryCounter to set.
     */
    public void setMaxDeliveryCounter(long maxDeliveryCounter) {
        this.maxDeliveryCounter = maxDeliveryCounter;
    }



    /**
     * @return Returns the failedPrefix.
     */
    public String getFailedPrefix() {
        return failedPrefix;
    }



    /**
     * @param failedPrefix The failedPrefix to set.
     */
    public void setFailedPrefix(String failedPrefix) {
        this.failedPrefix = failedPrefix;
    }



    /**
     * @return Returns the queuePattern.
     */
    public String getQueuePattern() {
        return queuePattern;
    }



    /**
     * @param queuePattern The queuePattern to set.
     */
    public void setQueuePattern(String queuePattern) {
        this.queuePattern = queuePattern;
    }



    /**
     * @return Returns the applicationMailTemplatesFactory.
     */
    public String getApplicationMailTemplatesFactory() {
        return applicationMailTemplatesFactory;
    }



    /**
     * @param applicationMailTemplatesFactory The applicationMailTemplatesFactory to set.
     */
    public void setApplicationMailTemplatesFactory(String applicationMailTemplatesFactory) {
        this.applicationMailTemplatesFactory = applicationMailTemplatesFactory;
    }



    /**
     * @return Returns the sectionMailTemplatesFactory.
     */
    public String getSectionMailTemplatesFactory() {
        return sectionMailTemplatesFactory;
    }



    /**
     * @param sectionMailTemplatesFactory The sectionMailTemplatesFactory to set.
     */
    public void setSectionMailTemplatesFactory(String sectionMailTemplatesFactory) {
        this.sectionMailTemplatesFactory = sectionMailTemplatesFactory;
    }



    /**
     * @return Returns the hasLocalizedTemplates.
     */
    public boolean hasLocalizedTemplates() {
        return hasLocalizedTemplates;
    }



    /**
     * @param hasLocalizedTemplates The hasLocalizedTemplates to set.
     */
    public void setHasLocalizedTemplates(boolean hasLocalizedTemplates) {
        this.hasLocalizedTemplates = hasLocalizedTemplates;
    }



    /**
     * @return Returns the applicationMailScripts.
     */
    public String getApplicationMailScripts() {
        return applicationMailScripts;
    }



    /**
     * @param applicationMailScripts The applicationMailScripts to set.
     */
    public void setApplicationMailScripts(String applicationMailScripts) {
        this.applicationMailScripts = applicationMailScripts;
    }



    /**
     * @return Returns the sectionMailScripts.
     */
    public String getSectionMailScripts() {
        return sectionMailScripts;
    }


    
    /**
     * @param sectionMailScripts The sectionMailScripts to set.
     */
    public void setSectionMailScripts(String sectionMailScripts) {
        this.sectionMailScripts = sectionMailScripts;
    }



    /**
     * @return Returns the hasDatabase.
     */
    public boolean hasDatabase() {
        return hasDatabase;
    }



    /**
     * @param hasDatabase The hasDatabase to set.
     */
    public void setHasDatabase(boolean hasDatabase) {
        this.hasDatabase = hasDatabase;
    }



    /**
     * @return Returns the tableMailBouncePatterns.
     */
    public String getTableMailBouncePatterns() {
        return tableMailBouncePatterns;
    }



    /**
     * @param tableMailBouncePatterns The tableMailBouncePatterns to set.
     */
    public void setTableMailBouncePatterns(String tableMailBouncePatterns) {
        this.tableMailBouncePatterns = tableMailBouncePatterns;
    }



    /**
     * @return Returns the tableMailBounces.
     */
    public String getTableMailBounces() {
        return tableMailBounces;
    }



    /**
     * @param tableMailBounces The tableMailBounces to set.
     */
    public void setTableMailBounces(String tableMailBounces) {
        this.tableMailBounces = tableMailBounces;
    }



    /**
     * @return Returns the tableMailBounceDeliveries.
     */
    public String getTableMailBounceDeliveries() {
        return tableMailBounceDeliveries;
    }



    /**
     * @param tableMailBounceDeliveries The tableMailBounceDeliveries to set.
     */
    public void setTableMailBounceDeliveries(String tableMailBounceDeliveries) {
        this.tableMailBounceDeliveries = tableMailBounceDeliveries;
    }



    /**
     * @return Returns the tableMailBouncePatternRetries.
     */
    public String getTableMailBouncePatternRetries() {
        return tableMailBouncePatternRetries;
    }



    /**
     * @param tableMailBouncePatternRetries The tableMailBouncePatternRetries to set.
     */
    public void setTableMailBouncePatternRetries(
            String tableMailBouncePatternRetries) {
        this.tableMailBouncePatternRetries = tableMailBouncePatternRetries;
    }
}
