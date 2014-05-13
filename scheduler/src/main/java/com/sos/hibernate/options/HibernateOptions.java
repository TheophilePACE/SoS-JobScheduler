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
package com.sos.hibernate.options;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.JSHelper.Options.SOSOptionBoolean;
import com.sos.JSHelper.Options.SOSOptionDBDriver;
import com.sos.JSHelper.Options.SOSOptionInFileName;
import com.sos.JSHelper.Options.SOSOptionJdbcUrl;
import com.sos.JSHelper.Options.SOSOptionPassword;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.hibernate.interfaces.IHibernateOptions;
import com.sos.JSHelper.Archiver.IJSArchiverOptions;


/**
 * \class 		JobNetOptionsSuperClass - title
 *
 * \brief 
 * An Options-Super-Class with all Options. This Class will be extended by the "real" Options-class (\see JobNetOptions.
 * The "real" Option class will hold all the things, which are normaly overwritten at a new generation
 * of the super-class.
 *
 *

 *
 * see \see C:\Dokumente und Einstellungen\Uwe Risse\Lokale Einstellungen\Temp\scheduler_editor-3285541815265253193.html for (more) details.
 * 
 * \verbatim ;
 * mechanicaly created by C:\Dokumente und Einstellungen\Uwe Risse\Eigene Dateien\sos-berlin.com\jobscheduler.1.3.9\scheduler_139\config\JOETemplates\java\xsl\JSJobDoc2JSOptionSuperClass.xsl from http://www.sos-berlin.com at 20120224145540 
 * \endverbatim
 * \section OptionsTable Tabelle der vorhandenen Optionen
 * 
 * Tabelle mit allen Optionen
 * 
 * MethodName
 * Title
 * Setting
 * Description
 * IsMandatory
 * DataType
 * InitialValue
 * TestValue
 * 
 * 
 *
 * \section TestData Eine Hilfe zum Erzeugen einer HashMap mit Testdaten
 *
 * Die folgenden Methode kann verwendet werden, um f�r einen Test eine HashMap
 * mit sinnvollen Werten f�r die einzelnen Optionen zu erzeugen.
 *
 * \verbatim
 private HashMap <String, String> SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM) {
	pobjHM.put ("		JobNetOptionsSuperClass.auth_file", "test");  // This parameter specifies the path and name of a user's pr
		return pobjHM;
  }  //  private void SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM)
 * \endverbatim
 */
@JSOptionClass(name = "JobNetOptionsSuperClass", description = "JobNetOptionsSuperClass")
public class HibernateOptions extends JSOptionsClass implements IHibernateOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5713555021974034071L;
	private final String	conClassName	= "JSHibernateOptions";
	@SuppressWarnings("unused")
	private static Logger	logger			= Logger.getLogger(HibernateOptions.class);

/**
 * \var hibernate_connection_autocommit : 
 * 
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_autocommit", 
    description = "", 
    key = "hibernate_connection_autocommit", 
    type = "SOSOptionBoolean", 
    mandatory = false)
    
    public SOSOptionBoolean hibernate_connection_autocommit = new SOSOptionBoolean(this, conClassName + ".hibernate_connection_autocommit", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_autocommit()
 */
    @Override
	public SOSOptionBoolean  gethibernate_connection_autocommit() {
        return hibernate_connection_autocommit;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_autocommit(com.sos.JSHelper.Options.SOSOptionBoolean)
 */
    @Override
	public void sethibernate_connection_autocommit (SOSOptionBoolean p_hibernate_connection_autocommit) { 
        this.hibernate_connection_autocommit = p_hibernate_connection_autocommit;
    }

                        

/**
 * \var hibernate_connection_config_file : 
 * Hibernate configuration file of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_config_file", 
    description = "", 
    key = "hibernate_connection_config_file", 
    type = "SOSOptionInFileName", 
    mandatory = false)
    
    public SOSOptionInFileName hibernate_connection_config_file = new SOSOptionInFileName(this, conClassName + ".hibernate_connection_config_file", // HashMap-Key
                                                                "", // Titel
                                                                "config/hibernate.cfg.xml", // InitValue
                                                                "config/hibernate.cfg.xml", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_config_file()
 */
    @Override
	public SOSOptionInFileName  gethibernate_connection_config_file() {
        return hibernate_connection_config_file;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_config_file(com.sos.JSHelper.Options.SOSOptionInFileName)
 */
    @Override
	public void sethibernate_connection_config_file (SOSOptionInFileName p_hibernate_connection_config_file) { 
        this.hibernate_connection_config_file = p_hibernate_connection_config_file;
    }

                        

/**
 * \var hibernate_connection_driver_class : 
 * Class of JBDC driver of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_driver_class", 
    description = "", 
    key = "hibernate_connection_driver_class", 
    type = "SOSOptionDBDriver", 
    mandatory = false)
    
    public SOSOptionDBDriver hibernate_connection_driver_class = new SOSOptionDBDriver(this, conClassName + ".hibernate_connection_driver_class", // HashMap-Key
                                                                "", // Titel
                                                                "oracle.jdbc.driver.OracleDriver", // InitValue
                                                                "oracle.jdbc.driver.OracleDriver", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_driver_class()
 */
    @Override
	public SOSOptionDBDriver  gethibernate_connection_driver_class() {
        return hibernate_connection_driver_class;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_driver_class(com.sos.JSHelper.Options.SOSOptionDBDriver)
 */
    @Override
	public void sethibernate_connection_driver_class (SOSOptionDBDriver p_hibernate_connection_driver_class) { 
        this.hibernate_connection_driver_class = p_hibernate_connection_driver_class;
    }

                        

/**
 * \var hibernate_connection_password : 
 * Password of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_password", 
    description = "", 
    key = "hibernate_connection_password", 
    type = "SOSOptionPassword", 
    mandatory = false)
    
    public SOSOptionPassword hibernate_connection_password = new SOSOptionPassword(this, conClassName + ".hibernate_connection_password", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_password()
 */
    @Override
	public SOSOptionPassword  gethibernate_connection_password() {
        return hibernate_connection_password;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_password(com.sos.JSHelper.Options.SOSOptionPassword)
 */
    @Override
	public void sethibernate_connection_password (SOSOptionPassword p_hibernate_connection_password) { 
        this.hibernate_connection_password = p_hibernate_connection_password;
    }

                        

/**
 * \var hibernate_connection_url : 
 * JDBC URL of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_url", 
    description = "", 
    key = "hibernate_connection_url", 
    type = "SOSOptionJdbcUrl", 
    mandatory = false)
    
    public SOSOptionJdbcUrl hibernate_connection_url = new SOSOptionJdbcUrl(this, conClassName + ".hibernate_connection_url", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_url()
 */
    @Override
	public SOSOptionJdbcUrl  gethibernate_connection_url() {
        return hibernate_connection_url;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_url(com.sos.JSHelper.Options.SOSOptionJdbcUrl)
 */
    @Override
	public void sethibernate_connection_url (SOSOptionJdbcUrl p_hibernate_connection_url) { 
        this.hibernate_connection_url = p_hibernate_connection_url;
    }

                        

/**
 * \var hibernate_connection_username : 
 * User of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_connection_username", 
    description = "", 
    key = "hibernate_connection_username", 
    type = "SOSOptionString", 
    mandatory = false)
    
    public SOSOptionString hibernate_connection_username = new SOSOptionString(this, conClassName + ".hibernate_connection_username", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_connection_username()
 */
    @Override
	public SOSOptionString  gethibernate_connection_username() {
        return hibernate_connection_username;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_connection_username(com.sos.JSHelper.Options.SOSOptionString)
 */
    @Override
	public void sethibernate_connection_username (SOSOptionString p_hibernate_connection_username) { 
        this.hibernate_connection_username = p_hibernate_connection_username;
    }

                        

/**
 * \var hibernate_dialect : 
 * Hibernate dialect of the database connection
 *
 */
    @JSOptionDefinition(name = "hibernate_dialect", 
    description = "", 
    key = "hibernate_dialect", 
    type = "SOSOptionString", 
    mandatory = false)
    
    public SOSOptionString hibernate_dialect = new SOSOptionString(this, conClassName + ".hibernate_dialect", // HashMap-Key
                                                                "", // Titel
                                                                "org.hibernate.dialect.Oracle10gDialect", // InitValue
                                                                "org.hibernate.dialect.Oracle10gDialect", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_dialect()
 */
    @Override
	public SOSOptionString  gethibernate_dialect() {
        return hibernate_dialect;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_dialect(com.sos.JSHelper.Options.SOSOptionString)
 */
    @Override
	public void sethibernate_dialect (SOSOptionString p_hibernate_dialect) { 
        this.hibernate_dialect = p_hibernate_dialect;
    }

                        

/**
 * \var hibernate_format_sql : 
 * 
 *
 */
    @JSOptionDefinition(name = "hibernate_format_sql", 
    description = "", 
    key = "hibernate_format_sql", 
    type = "SOSOptionBoolean", 
    mandatory = false)
    
    public SOSOptionBoolean hibernate_format_sql = new SOSOptionBoolean(this, conClassName + ".hibernate_format_sql", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_format_sql()
 */
    @Override
	public SOSOptionBoolean  gethibernate_format_sql() {
        return hibernate_format_sql;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_format_sql(com.sos.JSHelper.Options.SOSOptionBoolean)
 */
    @Override
	public void sethibernate_format_sql (SOSOptionBoolean p_hibernate_format_sql) { 
        this.hibernate_format_sql = p_hibernate_format_sql;
    }

                        

/**
 * \var hibernate_show_sql : 
 * 
 *
 */
    @JSOptionDefinition(name = "hibernate_show_sql", 
    description = "", 
    key = "hibernate_show_sql", 
    type = "SOSOptionBoolean", 
    mandatory = false)
    
    public SOSOptionBoolean hibernate_show_sql = new SOSOptionBoolean(this, conClassName + ".hibernate_show_sql", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#gethibernate_show_sql()
 */
    @Override
	public SOSOptionBoolean  gethibernate_show_sql() {
        return hibernate_show_sql;
    }

/* (non-Javadoc)
 * @see com.sos.jobnet.options.ISOSJSHibernateOptions#sethibernate_show_sql(com.sos.JSHelper.Options.SOSOptionBoolean)
 */
    @Override
	public void sethibernate_show_sql (SOSOptionBoolean p_hibernate_show_sql) { 
        this.hibernate_show_sql = p_hibernate_show_sql;
    }

                        

public HibernateOptions() {
		objParentClass = this.getClass();
	} // public JobNetOptionsSuperClass    
        
	//
	public HibernateOptions(HashMap<String, String> JSSettings) throws Exception {
		this();
		this.setAllOptions(JSSettings);
	} // public JobNetOptionsSuperClass (HashMap JSSettings)

	/**
	 * \brief getAllOptionsAsString - liefert die Werte und Beschreibung aller
	 * Optionen als String
	 *
	 * \details
	 * 
	 * \see toString 
	 * \see toOut
	 */
	private String getAllOptionsAsString() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAllOptionsAsString";
		String strT = conClassName + "\n";
		final StringBuffer strBuffer = new StringBuffer();
		// strT += IterateAllDataElementsByAnnotation(objParentClass, this,
		// JSOptionsClass.IterationTypes.toString, strBuffer);
		// strT += IterateAllDataElementsByAnnotation(objParentClass, this, 13,
		// strBuffer);
		strT += this.toString(); // fix
		//
		return strT;
	} // private String getAllOptionsAsString ()

	/**
	 * \brief setAllOptions - �bernimmt die OptionenWerte aus der HashMap
	 *
	 * \details In der als Parameter anzugebenden HashMap sind Schl�ssel (Name)
	 * und Wert der jeweiligen Option als Paar angegeben. Ein Beispiel f�r den
	 * Aufbau einer solchen HashMap findet sich in der Beschreibung dieser
	 * Klasse (\ref TestData "setJobSchedulerSSHJobOptions"). In dieser Routine
	 * werden die Schl�ssel analysiert und, falls gefunden, werden die
	 * dazugeh�rigen Werte den Properties dieser Klasse zugewiesen.
	 *
	 * Nicht bekannte Schl�ssel werden ignoriert.
	 *
	 * \see JSOptionsClass::getItem
	 *
	 * @param pobjJSSettings
	 * @throws Exception
	 */
	public void setAllOptions(HashMap<String, String> pobjJSSettings) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setAllOptions";
		flgSetAllOptions = true;
		objSettings = pobjJSSettings;
		super.Settings(objSettings);
		super.setAllOptions(pobjJSSettings);
		flgSetAllOptions = false;
	} // public void setAllOptions (HashMap <String, String> JSSettings)

	/**
	 * \brief CheckMandatory - pr�ft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgel�st, wenn eine mandatory-Option keinen Wert hat
	 */
	@Override
	public void CheckMandatory() throws JSExceptionMandatoryOptionMissing //
			, Exception {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()

	/**
	 *
	 * \brief CommandLineArgs - �bernehmen der Options/Settings aus der
	 * Kommandozeile
	 *
	 * \details Die in der Kommandozeile beim Starten der Applikation
	 * angegebenen Parameter werden hier in die HashMap �bertragen und danach
	 * den Optionen als Wert zugewiesen.
	 *
	 * \return void
	 *
	 * @param pstrArgs
	 * @throws Exception
	 */
	@Override
	public void CommandLineArgs(String[] pstrArgs) throws Exception {
		super.CommandLineArgs(pstrArgs);
		this.setAllOptions(super.objSettings);
	}
} // public class JobNetOptionsSuperClass
