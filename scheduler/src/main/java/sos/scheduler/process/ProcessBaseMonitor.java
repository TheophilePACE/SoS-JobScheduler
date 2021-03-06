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
package sos.scheduler.process;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sos.net.SOSMail;
import sos.spooler.Monitor_impl;
import sos.spooler.Variable_set;
import sos.util.SOSSchedulerLogger;
import sos.xml.SOSXMLXPath;

/**
 * <p>ProcessBaseMonitor stellt Methoden f�r Monitor-Scripte zur Verf�gung:</p>
 * <p>prepareConfiguration(): liefert die XML-Konfiguration des Auftrags als Auftragsparameter und stellt ein DOM zur Verf�gung.</p>
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05 
 */

public class ProcessBaseMonitor extends Monitor_impl {

    /** Logging */
    private SOSSchedulerLogger sosLogger = null;
    
    /** Attribut: configuration: Job-Konfiguration im XML-Format */
    private Document configuration = null;
    
    private StringBuffer configurationBuffer = null;
    private String configurationPath = "";
    private String configurationFilename = "";
    
    /** Liste der Parameternamen */
    private Vector orderParameterKeys = null;
    private TreeMap envvars = null;

    
    /**
     * Initialize order configuration
     */
    public void initConfiguration() throws Exception {

        if (spooler_job.order_queue() != null) {
            if (spooler_task.order().params().value("configuration_path") != null && spooler_task.order().params().value("configuration_path").length() > 0)
                this.setConfigurationPath(spooler_task.order().params().value("configuration_path"));

            if (spooler_task.order().params().value("configuration_file") != null && spooler_task.order().params().value("configuration_file").length() > 0)
                this.setConfigurationFilename(spooler_task.order().params().value("configuration_file"));
        }
        
        this.initConfiguration(this.getConfigurationPath(), this.getConfigurationFilename());
    }
    
    
    /**
     * Initialize order configuration
     */
    public void initConfiguration(String configurationPath, String configurationFilename) throws Exception {
        
        if (spooler_job.order_queue() != null) {
            if (spooler_task.order().params().value("configuration_path") != null && spooler_task.order().params().value("configuration_path").length() > 0)
                this.setConfigurationPath(spooler_task.order().params().value("configuration_path"));
        }

        if (configurationFilename.startsWith(".") || configurationFilename.startsWith("/") || configurationFilename.startsWith("\\")
         || configurationFilename.indexOf(":") > -1 || configurationPath == null || configurationPath.length() == 0) {
            this.initConfiguration(configurationFilename);
        } else {
            this.initConfiguration(configurationPath 
                + ( (!configurationPath.endsWith("/") && !configurationPath.endsWith("\\")) ? "/" : "") 
                + configurationFilename);
        }
    }
    
        
    /**
     * Initialize order configuration
     */
    public void initConfiguration(String configurationFilename) throws Exception {

        FileInputStream fis = null;
        
        try { // to retrieve configuration from file
            if (spooler_task.job().order_queue() != null) {

                if (configurationFilename == null || configurationFilename.length() == 0)
                    throw new Exception("no configuration filename was specified");
                
                File configurationFile = new File(configurationFilename); 
                if (!configurationFile.exists()) {
                    throw new Exception("configuration file not found: " + configurationFile.getCanonicalPath());
                } else if (!configurationFile.canRead()) {
                    throw new Exception("configuration file is not accessible: " + configurationFile.getCanonicalPath());                
                }
                
                fis = new FileInputStream(configurationFile);
                BufferedInputStream in = new BufferedInputStream(fis);
                byte inBuffer[] = new byte[1024];
                int inBytesRead;
                this.configurationBuffer = new StringBuffer();
                while ((inBytesRead = in.read(inBuffer)) != -1) {
                    this.configurationBuffer.append(new String(inBuffer, 0, inBytesRead));
                }
                
                spooler_task.order().set_xml_payload(this.configurationBuffer.toString()); 
                spooler_task.order().params().set_var("scheduler_order_configuration_loaded", "true");
                spooler_task.order().params().set_var("configuration_file", configurationFilename);
            }

        } catch (Exception e) {
            this.getLogger().warn("error occurred initializing configuration: " + e.getMessage());
            throw new Exception(this.getLogger().getWarning());
        } finally {
            try { if (fis != null) { fis.close(); fis = null; } } catch (Exception ex) {}
        }
    }

    
    /**
     * Initialize order configuration
     */
    public Document prepareConfiguration() throws Exception {

        String nodeQuery = "";
        String payload = "";
        
        try { // to fetch the order configuration
            this.orderParameterKeys = new Vector();
            
            if (spooler_task.job().order_queue() != null) {

                if (spooler_task.order().xml_payload() == null || spooler_task.order().xml_payload().length() == 0
                        || spooler_task.order().params() == null 
                        || spooler_task.order().params().value("scheduler_order_configuration_loaded") == null
                        || spooler_task.order().params().value("scheduler_order_configuration_loaded").length() == 0 ) 
                    this.initConfiguration();

                if (spooler_task.order().xml_payload() == null) 
                    throw new Exception("monitor:no configuration was specified for this order: " + spooler_task.order().id());

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                docFactory.setNamespaceAware(false);
                docFactory.setValidating(false);
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
                //this.setConfiguration(docBuilder.parse(new ByteArrayInputStream(spooler_task.order().xml_payload().getBytes())));
                payload = spooler_task.order().xml_payload();
                if (!payload.startsWith("<?xml ")) {
                    payload = "<?xml version='1.0' encoding='ISO-8859-1'?>" + payload;
                }
                this.setConfiguration(docBuilder.parse(new ByteArrayInputStream(payload.getBytes())));

                SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(payload));
                NodeList nodeList = null;
                NamedNodeMap nodeMapSettings = null;

                nodeQuery = "//settings/log_level";
                Node nodeSettings = xpath.selectSingleNode(nodeQuery);
                if (nodeSettings != null) {
                    nodeMapSettings = nodeSettings.getAttributes();
                    if (nodeMapSettings != null && nodeMapSettings.getNamedItem("value") != null) {
                	  this.getLogger().debug1("Log Level is: " + nodeMapSettings.getNamedItem("value").getNodeValue());
                	  this.getLogger().setLogLevel(this.logLevel2Int(nodeMapSettings.getNamedItem("value").getNodeValue()));
                    }
                } 

                this.setEnvVars();     
                
                // add attributes from configuration
                // this.getLogger().debug7("adding parameters from configuration: " + spooler_task.order().xml_payload());

                // look up the configuration for the all states
                nodeQuery = "//job_chain[@name='" + spooler_task.order().job_chain().name() + "']/order";
                this.getLogger().debug9("monitor: lookup order for job chain: " + nodeQuery + "/params/param");
                nodeList = xpath.selectNodeList(nodeQuery + "/params/param");
                if (nodeList == null || nodeList.getLength() == 0) {
                    nodeQuery = "//application[@name='" + spooler_task.order().job_chain().name() + "']/order";
                    this.getLogger().debug9("monitor: lookup order for application: " + nodeQuery + "/params/param");
                    nodeList = xpath.selectNodeList(nodeQuery + "/params/param");
                }
                
                for (int i=0; i<nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    String nodeValue = "";
                    String nodeName = "";
                    if (node.getNodeName().equalsIgnoreCase("param")) {
                        NamedNodeMap nodeMap = node.getAttributes();
                        if (nodeMap != null && nodeMap.getNamedItem("name") != null) {
                        	 
                            nodeName = nodeMap.getNamedItem("name").getNodeValue();
                            if (nodeMap.getNamedItem("value") != null) {
                            	nodeValue = nodeMap.getNamedItem("value").getNodeValue();
                            } else {
                                NodeList children = node.getChildNodes();
                                for (int j=0; j<children.getLength(); j++) {
                                    Node item = children.item(j);
                                    switch (item.getNodeType())
                                    {
                                        case Node.TEXT_NODE:
                                        case Node.CDATA_SECTION_NODE:
                                            nodeValue += item.getNodeValue();
                                    }
                                }
                            }
                            this.getLogger().debug1(".. monitor: global configuration parameter [" + nodeName + "]: "  + nodeValue );
                            spooler_task.order().params().set_var(nodeName, nodeValue);
                        }
                    }
                }

                // look up the configuration for the order state
                nodeQuery = "//job_chain[@name='" + spooler_task.order().job_chain().name() + "']/order/process[@state='" + spooler_task.order().state() + "']";
                this.getLogger().debug9("monitor: lookup order node query for job chain: " + nodeQuery + "/params/param");
                nodeList = xpath.selectNodeList(nodeQuery + "/params/param");
                if (nodeList == null || nodeList.getLength() == 0) {
                    nodeQuery = "//application[@name='" + spooler_task.order().job_chain().name() + "']/order/process[@state='" + spooler_task.order().state() + "']";
                    this.getLogger().debug9("monitor: lookup order node query for application: " + nodeQuery + "/params/param");
                    nodeList = xpath.selectNodeList(nodeQuery + "/params/param");
                }

                /* Diesen Block kommentiere ich mal aus, denn das ist ja immer false
                 * if (nodeQuery == null || nodeQuery.length() == 0) {
                     // look up the configuration for the job name
                     this.getLogger().debug9("lookup job node query: //process[@name='" + this.getJobName() + "']");
                     nodeQuery = "//process[@name='" + this.getJobName() + "']";
                 }
                 */

                for (int i=0; i<nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeName().equalsIgnoreCase("param")) {
                        NamedNodeMap nodeMap = node.getAttributes();
                        if (nodeMap != null && nodeMap.getNamedItem("name") != null) { 
                            if (nodeMap.getNamedItem("value") != null) {
                              this.getLogger().debug1(".. monitor: configuration parameter [" + nodeMap.getNamedItem("name").getNodeValue() + "]: "  + nodeMap.getNamedItem("value").getNodeValue());
                              spooler_task.order().params().set_var(nodeMap.getNamedItem("name").getNodeValue(), nodeMap.getNamedItem("value").getNodeValue());
                              this.orderParameterKeys.add(nodeMap.getNamedItem("name").getNodeValue());
                            } else {
                                NodeList children = node.getChildNodes();
                                String nodeValue = "";
                                for (int j=0; j<children.getLength(); j++) {
                                    Node item = children.item(j);
                                    switch (item.getNodeType())
                                    {
                                        case Node.TEXT_NODE:
                                        case Node.CDATA_SECTION_NODE:
                                            nodeValue += item.getNodeValue();
                                    }
                                }
                                this.getLogger().debug1(".. configuration parameter [" + nodeMap.getNamedItem("name").getNodeValue() + "]: "  + nodeValue);
                                spooler_task.order().params().set_var(nodeMap.getNamedItem("name").getNodeValue(), nodeValue);
                            }
                        }
                    }
                }

                String[] parameterNames = spooler_task.order().params().names().split(";");
                for(int i=0; i<parameterNames.length; i++) {
                  String currParam = String.valueOf ( spooler_task.order().params().value(parameterNames[i]) );
                  if ( currParam != null && currParam.matches("^.*\\$\\{.*\\}.*$")) {
                        boolean parameterFound = false;
                        boolean envFound = false;
                        String parameterValue = spooler_task.order().params().value(parameterNames[i]);
                        int trials=0;
                      	while (parameterValue.indexOf("${") != -1 && trials <= 1) {
                      	    this.getLogger().debug1("substitution trials: " + trials + " --> " + parameterValue);
                      	    for(int j=0; j<parameterNames.length; j++) {
                      	        this.getLogger().debug9("parameterNames[j]=" + parameterNames[j] + " -->" + parameterValue.indexOf("${" + parameterNames[j] + "}"));
                        	
                      	        if (!parameterNames[i].equals(parameterNames[j]) && (parameterValue.indexOf("${" + parameterNames[j] + "}") != -1 || parameterValue.indexOf("${basename:" + parameterNames[j] + "}") != -1)) {
                                    if (parameterValue.indexOf("${basename:") != -1) {
                                        parameterValue = myReplaceAll(parameterValue,"\\$\\{basename:" + parameterNames[j] + "\\}", new File(spooler_task.order().params().value(parameterNames[j])).getName().replaceAll("[\\\\]", "\\\\\\\\"));
                                    } else {
                                        parameterValue = myReplaceAll(parameterValue,"\\$\\{" + parameterNames[j] + "\\}", spooler_task.order().params().value(parameterNames[j]).replaceAll("[\\\\]", "\\\\\\\\"));
                                    }
                      	            parameterFound = true;
                      	            trials=0;
                      	        }
                      	    }
                      	    trials++;
                      	}
                        
                       if (this.envvars != null) {
                           Iterator envIterator = this.envvars.keySet().iterator();
                           while(envIterator.hasNext()) {
                              Object envName  = envIterator.next();
                              Object envValue = this.envvars.get(envName.toString());
                              if (parameterValue.indexOf("${" + envName.toString() + "}") != -1) {
                                 parameterValue = myReplaceAll(parameterValue, "\\$\\{" + envName.toString() + "\\}", envValue.toString().replaceAll("[\\\\]", "\\\\\\\\"));
                                 envFound = true;
                              } else if (parameterValue.indexOf("${basename:" + envName.toString() + "}") != -1) {
                                  parameterValue = myReplaceAll(parameterValue, "\\$\\{basename:" + envName.toString() + "\\}", new File(envValue.toString()).getName().replaceAll("[\\\\]", "\\\\\\\\"));
                                  envFound = true;
                              }
                          }
                       }                        
                        
                       if (parameterFound) {
                         this.getLogger().debug3("parameter substitution [" + parameterNames[i] + "]: " + parameterValue);
                         spooler_task.order().params().set_var(parameterNames[i], parameterValue);
                       }
                       
                       if (envFound) {
                         this.getLogger().debug3("environment variable substitution [" + parameterNames[i] + "]: " + parameterValue);
                         spooler_task.order().params().set_var(parameterNames[i], parameterValue);
                       }
                    }
                }
            }
            
            return this.getConfiguration();
        } catch (Exception e) {
            this.getLogger().warn("Monitor: error occurred preparing configuration: " + e.getMessage());
            throw new Exception(this.getLogger().getWarning());
        }
    }
    

    /**
     * cleanup order parameters
     */
    public void cleanupConfiguration() throws Exception {

        try {
            if (this.orderParameterKeys != null) {
                Variable_set resultParameters = spooler.create_variable_set();
                String[] parameterNames = spooler_task.order().params().names().split(";");
                for(int i=0; i<parameterNames.length; i++) {
                    if (!this.orderParameterKeys.contains(parameterNames[i])) {
                        resultParameters.set_var(parameterNames[i], spooler_task.order().params().value(parameterNames[i]));
                    }
                }
                spooler_task.order().set_params(resultParameters);
            }
        } catch (Exception e) {
            throw new Exception("error occurred in monitor on cleanup: " + e.getMessage());
        }
    }
        
    
    /**
     * sendet Mail mit den Einstellungen des Job Schedulers
     * 
     * @param recipient
     * @param subject
     * @param body
     * @throws Exception
     */
    public void sendMail(String recipient, String recipientCC, String recipientBCC, String subject, String body) throws Exception {
        
        try {
            SOSMail sosMail = new SOSMail(spooler_log.mail().smtp());

            sosMail.setQueueDir(spooler_log.mail().queue_dir());
            sosMail.setFrom(spooler_log.mail().from());
            sosMail.setContentType("text/plain");
            sosMail.setEncoding("Base64");
                
            String recipients[] = recipient.split(",");
            for(int i=0; i<recipients.length; i++) {
                if (i==0) sosMail.setReplyTo(recipients[i].trim()); 
                sosMail.addRecipient(recipients[i].trim());
            }

            String recipientsCC[] = recipientCC.split(",");
            for(int i=0; i<recipientsCC.length; i++) {
                sosMail.addCC(recipientsCC[i].trim());
            }

            String recipientsBCC[] = recipientBCC.split(",");
            for(int i=0; i<recipientsBCC.length; i++) {
                sosMail.addBCC(recipientsBCC[i].trim());
            }

            sosMail.setSubject(subject);
            sosMail.setBody(body);
            sosMail.setSOSLogger(this.getLogger());
        
            this.getLogger().info("sending mail: \n" + sosMail.dumpMessageAsString());
            
            if (!sosMail.send()){
                this.getLogger().warn("mail server is unavailable, mail for recipient [" + recipient + "] is queued in local directory [" + sosMail.getQueueDir() + "]:" + sosMail.getLastError());
            }
        
            sosMail.clearRecipients();
        } catch (Exception e) {
            throw new Exception("error occurred in monitor sending mai: " + e.getMessage());
        }
    }
    
    
    /**
     * @return Returns the sosLogger.
     */
    public SOSSchedulerLogger getLogger() {
        return sosLogger;
    }
    
    
    /**
     * @param sosLogger
     *            The sosLogger to set.
     */
    public void setLogger(SOSSchedulerLogger sosLogger) {
        this.sosLogger = sosLogger;
    }

    
    /**
     * @return Returns the configuration.
     */
    public Document getConfiguration() {
        return configuration;
    }


    /**
     * @param configuration The configuration to set.
     */
    public void setConfiguration(Document configuration) {
        this.configuration = configuration;
    }

    /**
     * @return Returns the configurationPath.
     */
    public String getConfigurationPath() {
        
        return configurationPath;
    }


    /**
     * @param configurationPath The configurationPath to set.
     */
    public void setConfigurationPath(String configurationPath) {
        
        this.configurationPath = configurationPath;
    }

    /**
     * @return Returns the configurationFilename.
     */
    public String getConfigurationFilename() {
        
        return configurationFilename;
    }


    /**
     * @param configurationFilename The configurationFilename to set.
     */
    public void setConfigurationFilename(String configurationFilename) {
        
        this.configurationFilename = configurationFilename;
    }
    
    
    private void setEnvVars() throws Exception {
      
      String OS = System.getProperty("os.name").toLowerCase();
      boolean win = false;
      this.envvars = new TreeMap();
    
      if ( (OS.indexOf("nt") > -1)
        || (OS.indexOf("windows") > -1)) {
          win=true;
      }
    
      Variable_set env = spooler_task.create_subprocess().env();
      this.getLogger().debug9("environment variable names: " + env.names());
      StringTokenizer t = new StringTokenizer(env.names(), ";");
      while (t.hasMoreTokens()) {
          String envname = t.nextToken();
          if (envname != null) {
              String envvalue = env.value(envname);
              if (win) {
                  this.getLogger().debug9("set environment variable: " + envname.toUpperCase() + "=" + envvalue);
                  this.envvars.put(envname.toUpperCase(), envvalue);
              } else {
                  this.getLogger().debug9("set environment variable: " + envname + "=" + envvalue);
                  this.envvars.put(envname, envvalue);
              }
          }
       }
    }

    
    private int logLevel2Int(String l) {
    	HashMap levels = new HashMap();
    	if (l == null) {
    		return this.getLogger().getLogLevel();
    	} else {
    	   levels.put("info", "10");
    	   levels.put("warn", "11");
    	   levels.put("error", "12");
    	   levels.put("debug1", "1");
    	   levels.put("debug2", "2");
    	   levels.put("debug3", "3");
    	   levels.put("debug4", "4");
    	   levels.put("debug5", "5");
    	   levels.put("debug6", "6");
    	   levels.put("debug7", "7");
    	   levels.put("debug8", "8");
    	   levels.put("debug9", "9");
    	
    	   if (levels.get(l) != null) {
    	       return Integer.parseInt(levels.get(l).toString());
    	   } else {
    	       return  this.getLogger().getLogLevel();
    	   }
    	}
    }
    
    
   	public static String myReplaceAll(String source, String what, String replacement) {
        
  		String newReplacement = replacement.replaceAll("\\$", "\\\\\\$");
  		newReplacement = newReplacement.replaceAll("\"", "");
  		return source.replaceAll(what, newReplacement);
  	}

}
