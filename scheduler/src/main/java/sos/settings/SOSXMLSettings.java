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
package sos.settings;

import java.util.Properties;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;

import sos.util.SOSClassUtil;
import sos.util.SOSLogger;
import sos.util.SOSString;


/**
 * <p>
 * Title: SOSXMLSettings.java
 * </p>
 * <p>
 * Description: Diese Klasse liest eine XML-Konfigurationsdatei aus.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SOS GmbH
 * </p>
 * 
 * @resource sos.util.jar; sos.xml.XMLPath; org.w3c.dom
 * @author Robert Ehrlich
 */
public class SOSXMLSettings extends SOSSettings {

	/** Name der Anwendung */
    protected String application = "";

    /** stellt applications mit sections und entries in der XML-Datei dar */
	private LinkedHashMap applications = new LinkedHashMap();
	
	/** Feldnamen in Kleinschreibung (default) */
	protected boolean lowerCase = true;

	/**
	 * Konstruktor
	 * 
	 * @param source
	 *            Name der Datenquelle
	 * 
	 * @throws java.lang.Exception
	 */
	public SOSXMLSettings(String source) throws Exception {

		super(source);
		this.load();
	}

	/**
	 * Konstruktor
	 * 
	 * @param source
	 *            Name der Datenquelle
	 * @param logger
	 *            Das Logger-Objekt
	 * 
	 * @throws java.lang.Exception
	 */
	public SOSXMLSettings(String source, SOSLogger logger) throws Exception {

		super(source, logger);
		this.load();
	}

	
	/**
	 * Konstruktor
	 * 
	 * @param source
	 *            Name der Datenquelle
	 * @param application
	 *            Name der Anwendung
	 * 
	 * @throws java.lang.Exception
	 */
	public SOSXMLSettings(String source, String application)
			throws Exception {

		super(source);
		
		this.application = application;
		this.load();
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param source
	 *            Name der Datenquelle
	 * @param application
	 *            Name der Anwendung
	 * @param logger
	 *            Das Logger-Objekt
	 * 
	 * @throws java.lang.Exception
	 */
	public SOSXMLSettings(String source, String application, SOSLogger logger)
			throws Exception {

		super(source,logger);
		
		this.application = application;
		this.load();
	}
	
	 /**
     * @param source
     *            Name der Datenquelle
     * @param application
     *            Name der Anwendung
     * @param section
     *            Name der Sektion
     * @throws Exception
     */
    public SOSXMLSettings(String source,
            String application, String section)
            throws Exception {

        super(source, section);
        this.application = application;
        this.load();
    }
	
    /**
     * @param source
     *            Name der Datenquelle
     * @param application
     *            Name der Anwendung
     * @param section
     *            Name der Sektion
     * @param logger
     *            Das Logger-Objekt
     * @throws Exception
     */
    public SOSXMLSettings(String source,
            String application, String section,SOSLogger logger)
            throws Exception {

        super(source, section,logger);
        this.application = application;
        this.load();
    }
	
	
	
	

	/**
	 * L�dt die XML-Datei in den Speicher
	 * 
	 * @throws Exception
	 */
	private void load() throws Exception {

		LinkedHashMap	sections				= null;
		Properties 		entries					= null;
		String 			xpathQueryAttributes 	= "attribute::name != '' and not (attribute::disabled = 'yes')";
		
		try {
			sos.xml.SOSXMLXPath 	xpath 					= null;
			org.w3c.dom.NodeList 	nodeListApplications 	= null;
						
			this.applications 		= new LinkedHashMap();
			xpath 					= new sos.xml.SOSXMLXPath(this.source);
			nodeListApplications	= xpath.selectNodeList("/settings/application["+xpathQueryAttributes+"]");
			
			for (int i = 0; i < nodeListApplications.getLength(); i++) {
				org.w3c.dom.Node 	nodeApplication 	= nodeListApplications.item(i);
				org.w3c.dom.Element elementApplication 	= (org.w3c.dom.Element)nodeApplication;
				String 				applicationName 	= elementApplication.getAttribute("name");
									sections			= new LinkedHashMap();				
				
				org.w3c.dom.NodeList nodeListSections 	= xpath
							.selectNodeList(nodeApplication,
									"./sections/section["+xpathQueryAttributes+"]");
				
				for (int j = 0; j < nodeListSections.getLength(); j++) {
					org.w3c.dom.Node 	nodeSection 	= nodeListSections.item(j);
					org.w3c.dom.Element elementSection 	= (org.w3c.dom.Element) nodeSection;
					String 	sectionName 	= elementSection.getAttribute("name");
							entries			= new Properties();					

					org.w3c.dom.NodeList nodeListEntries = xpath
								.selectNodeList(nodeSection,
										"./entries/entry["+xpathQueryAttributes+"]");
					for (int e = 0; e < nodeListEntries.getLength(); e++) {
						org.w3c.dom.Node 	nodeEntry 		= nodeListEntries.item(e);
						org.w3c.dom.Element elementEntry 	= (org.w3c.dom.Element) nodeEntry;
						String 				entryName 		= elementEntry.getAttribute("name");
						String 				value 			= xpath.selectSingleNodeValue(elementEntry, "./value");
						// value nicht gefunden - kein Fehler ?
						entries.put(entryName,(value == null) ? "" : value.trim());
					}
					
					sections.put(sectionName,entries);
				}
				
				this.applications.put(applicationName,sections);
			}
			
			if (logger != null)
				logger.debug3(SOSClassUtil.getMethodName() + ": xml [" + source
						+ "] successfully loaded.");
		} 
		catch (NoClassDefFoundError e) {
             throw new Exception("Class not found  : "
                     + e.getMessage());
		}
		catch (Exception e) {
			throw (new Exception(SOSClassUtil.getMethodName() + ": "
					+ e.toString()));
		}
	}

	
	/**
     * Liefert alle Eintr�ge einer Sektion zur�ck.
     * 
     * @param String
     *            application Name der Anwendung
     * @param String
     *            section Name der Sektion
     * @return Properties Objekt, das alle Eintr�ge der Sektion darstellt.
     * @exception Exception
     * @see #getSection( String )
     */
    public Properties getSection(String application, String section)
            throws Exception {
    	
        Properties entries = new Properties();
        
        if (SOSString.isEmpty(application))
            throw (new Exception(SOSClassUtil.getMethodName()
                    + ": application has no value!"));

        if (SOSString.isEmpty(section))
            throw (new Exception(SOSClassUtil.getMethodName()
                    + ": section has no value!"));

        if(logger != null)logger.debug6("calling " + SOSClassUtil.getMethodName()+" : application = " + application+" section = "+section );
        
        if(this.applications != null && this.applications.containsKey(application)){
        	LinkedHashMap sections = (LinkedHashMap)this.applications.get(application);
        	if(sections.containsKey(section)){
        		entries = (Properties)sections.get(section);
            }
        }
        
        
       
    return entries;
    }
	
    
    /**
     * Liefert alle Eintr�ge einer Sektion zur�ck.
     * 
     * @param String
     *            section Name der Sektion
     * @return Properties Objekt, das alle Eintr�ge der Sektion darstellt.
     * @exception Exception
     * @see #getSection( String, String )
     */
    public Properties getSection(String section) throws Exception {

    	return getSection(this.application, section);
    }

    /**
     * Liefert alle Eintr�ge einer Sektion zur�ck.
     * 
     * @return Properties Objekt, das alle Eintr�ge der Sektion darstellt.
     * @exception Exception
     * @see #getSection( String, String )
     */
    public Properties getSection() throws Exception {
       
        return getSection(application, section);
    }
    
    
    /**
     * Liefert alle Sektionen einer Anwendung zur�ck
     * 
     * @param String
     *            application Name der Anwendung
     * @return ArrayList die alle Sektionen beinhaltet
     * @throws java.lang.Exception
     */
    public ArrayList getSections(String application) throws Exception {

        ArrayList sections = new ArrayList();
        if (SOSString.isEmpty(application))
                throw (new Exception(SOSClassUtil.getMethodName()
                        + ": application has no value!"));

        if(logger != null)logger.debug6("calling " + SOSClassUtil.getMethodName()+" : application = "+application);
        
        if(this.applications != null && this.applications.containsKey(application)){
        	LinkedHashMap appSections = (LinkedHashMap)this.applications.get(application);
        	
        	Iterator it = appSections.entrySet().iterator(); 
        	while (it.hasNext()) {
        		Map.Entry entry = (Map.Entry)it.next();
        		sections.add((String)entry.getKey());
        	} 
        }
      return sections;
    }
    
    /**
     * Liefert alle Sektionen einer Anwendung zur�ck
     * 
     * @return ArrayList die alle Sektionen beinhaltet
     * @throws java.lang.Exception
     */
    public ArrayList getSections() throws Exception {
    
        return this.getSections(this.application);
    }
    
    /**
     * Liefert den Wert eines Eintrages zur�ck.
     * 
     * @param entry
     *            Name des Eintrages
     * @return String der Wert eines Eintrages
     * @throws java.lang.Exception
     * @see #getSectionEntry( String, String, String )
     */
    public String getSectionEntry(String entry) throws Exception {

        return this.getSectionEntry(this.application, this.section, entry);
    }
    
    /**
     * Liefert den Wert eines Eintrages zur�ck.
     * 
     * @param String
     *            section Name der Sektion
     * @param entry
     *            Name des Eintrages
     * @return String der Wert eines Eintrages
     * @throws java.lang.Exception
     * @see #getSectionEntry( String, String, String )
     */
    public String getSectionEntry(String section,String entry) throws Exception {

        return this.getSectionEntry(this.application, section, entry);
    }
    
    /**
     * Liefert den Wert eines Eintrages zur�ck.
     * 
     * @param String
     *            application Name der Anwendung
     * @param String
     *            section Name der Sektion
     * @param entry
     *            Name des Eintrages
     * @return String der Wert eines Eintrages
     * @throws java.lang.Exception
     * @see #getSectionEntry( String )
     */
    public String getSectionEntry(String application, String section,
            String entry) throws Exception {

    	String entryValue = "";
        try {
            if (SOSString.isEmpty(application))
                throw (new Exception(SOSClassUtil.getMethodName()
                        + ": application has no value!"));

            if (SOSString.isEmpty(section))
                throw (new Exception(SOSClassUtil.getMethodName()
                        + ": section has no value!"));
            if (SOSString.isEmpty(entry))
                throw (new Exception(SOSClassUtil.getMethodName()
                        + ": entry has no value!"));
            
            if(logger != null)logger.debug6("calling " + SOSClassUtil.getMethodName()+" : application = "+application+" section = "+section+" entry = "+entry);
            
            if(this.applications != null && this.applications.containsKey(application)){
            	LinkedHashMap sections = (LinkedHashMap)this.applications.get(application);
            	if(sections.containsKey(section)){
            		Properties entries = (Properties)sections.get(section);
            		if(entries.containsKey(entry)){
            			entryValue = entries.getProperty(entry);
            		}
                }
            }
                        
        return  entryValue;
            
        } catch (Exception e) {
            throw (new Exception(SOSClassUtil.getMethodName() + ": "
                    + e.toString()));
        }
    }

	/**
	 * Aktiviert die Kleinschreibung f�r Feldnamen
	 * 
	 * @see #setKeysToUpperCase
	 */
	public void setKeysToLowerCase() throws Exception {
		if (logger != null)
			logger.debug3("calling " + SOSClassUtil.getMethodName());
		if (logger != null)
			logger.debug3(".. now keys set to lower case.");
		lowerCase = true;
	}

	/**
	 * Aktiviert die Grossschreibung f�r Feldnamen
	 * 
	 * @see #setKeysToLowerCase
	 */
	public void setKeysToUpperCase() throws Exception {
		if (logger != null)
			logger.debug3("calling " + SOSClassUtil.getMethodName());
		if (logger != null)
			logger.debug3(".. now keys set to upper case.");
		lowerCase = false;
	}

	/**
	 * Liefert den eingegebenen String in Kleinschreibung als default.
	 * 
	 * @param key
	 * @return String
	 * @throws java.lang.Exception
	 * @see #setKeysToLowerCase
	 * @see #setKeysToUpperCase
	 */
	protected String normalizeKey(String key) throws Exception {
		try {
			if (SOSString.isEmpty(key))
				throw new Exception(SOSClassUtil.getMethodName()
						+ ": invalid key.");
			if (this.getIgnoreCase())
				return key;
			if (this.lowerCase)
				return key.toLowerCase();
			else
				return key.toUpperCase();

		} catch (Exception e) {
			throw new Exception("error occurred in "
					+ SOSClassUtil.getMethodName() + ": " + e);
		}
	}

	/**
	 * Setzt den Schalter f�r die Ber�cksichtigung von
	 * Gro�/Kleinschreibung
	 * 
	 * @param ignoreCase
	 */
	public void setIgnoreCase(boolean ignoreCase) {

		this.ignoreCase = ignoreCase;
	}

	/**
	 * liefert den Schalter f�r die Ber�cksichtigung von
	 * Gro�/Kleinschreibung
	 * 
	 * @param ignoreCase
	 */
	public boolean getIgnoreCase() {

		return this.ignoreCase;
	}
		

}
