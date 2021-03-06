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
package sos.scheduler.file;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.JSHelper.Options.SOSOptionFileName;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		JobSchedulerFolderTreeOptionsSuperClass - check wether a file exist
 *
 * \brief 
 * An Options-Super-Class with all Options. This Class will be extended by the "real" Options-class (\see JobSchedulerFolderTreeOptions.
 * The "real" Option class will hold all the things, which are normaly overwritten at a new generation
 * of the super-class.
 *
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerFolderTree.xml for (more) details.
 * 
 * \verbatim ;
 * mechanicaly created by  from http://www.sos-berlin.com at 20110805104732 
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
	pobjHM.put ("		JobSchedulerFolderTreeOptionsSuperClass.auth_file", "test");  // This parameter specifies the path and name of a user's pr
		return pobjHM;
  }  //  private void SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM)
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com.sos.scheduler.messages", defaultLocale = "en")
@JSOptionClass(name = "JobSchedulerFolderTreeOptionsSuperClass", description = "JobSchedulerFolderTreeOptionsSuperClass")
public class JobSchedulerFolderTreeOptionsSuperClass extends JSOptionsClass {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1785419532839028016L;
	private final String		conClassName		= "JobSchedulerFolderTreeOptionsSuperClass";
	@SuppressWarnings("unused")
	private static Logger		logger				= Logger.getLogger(JobSchedulerFolderTreeOptionsSuperClass.class);
	private final String		conSVNVersion		= "$Id: JobSchedulerFolderTreeOptionsSuperClass.java 15153 2011-09-14 11:59:34Z kb $";

	/**
	 * \var file_path : This parameter is used alternatively to the parame
	 * This parameter is used alternatively to the parameter file_spec to specify a single file for transfer. When receiving files the following applies: This parameter accepts the absolute name and path of file at the FTP/SFTP server that should be transferred. The file name has to include both name and path of the file at the FTP/SFTP server. The file will be stored unter its name in the directory that is specified by the parameter local_dir. The following parameters are ignored should this parameter be used: file_spec and remote_dir. When sending files the following applies: This parameter accepts the absolute name and path of file that should be transferred. An absolute path has to be specified. The file will be stored under its name in the directory at the FTP/SFTP server that has been specified by the parameter remote_dir. The following parameters are ignored should this parameter be used: file_spec and local_dir.
	 *
	 */
	@JSOptionDefinition(name = "file_path", description = "This parameter is used alternatively to the parame", key = "file_path", type = "SOSOptionFileName", mandatory = false)
	public SOSOptionFileName	file_path			= new SOSOptionFileName(this, conClassName + ".file_path", // HashMap-Key
															"This parameter is used alternatively to the parame", // Titel
															" ", // InitValue
															" ", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getfile_path : This parameter is used alternatively to the parame
	 * 
	 * \details
	 * This parameter is used alternatively to the parameter file_spec to specify a single file for transfer. When receiving files the following applies: This parameter accepts the absolute name and path of file at the FTP/SFTP server that should be transferred. The file name has to include both name and path of the file at the FTP/SFTP server. The file will be stored unter its name in the directory that is specified by the parameter local_dir. The following parameters are ignored should this parameter be used: file_spec and remote_dir. When sending files the following applies: This parameter accepts the absolute name and path of file that should be transferred. An absolute path has to be specified. The file will be stored under its name in the directory at the FTP/SFTP server that has been specified by the parameter remote_dir. The following parameters are ignored should this parameter be used: file_spec and local_dir.
	 *
	 * \return This parameter is used alternatively to the parame
	 *
	 */
	public SOSOptionFileName getfile_path() {
		return file_path;
	}

	/**
	 * \brief setfile_path : This parameter is used alternatively to the parame
	 * 
	 * \details
	 * This parameter is used alternatively to the parameter file_spec to specify a single file for transfer. When receiving files the following applies: This parameter accepts the absolute name and path of file at the FTP/SFTP server that should be transferred. The file name has to include both name and path of the file at the FTP/SFTP server. The file will be stored unter its name in the directory that is specified by the parameter local_dir. The following parameters are ignored should this parameter be used: file_spec and remote_dir. When sending files the following applies: This parameter accepts the absolute name and path of file that should be transferred. An absolute path has to be specified. The file will be stored under its name in the directory at the FTP/SFTP server that has been specified by the parameter remote_dir. The following parameters are ignored should this parameter be used: file_spec and local_dir.
	 *
	 * @param file_path : This parameter is used alternatively to the parame
	 */
	public void setfile_path(SOSOptionFileName p_file_path) {
		this.file_path = p_file_path;
	}

	public JobSchedulerFolderTreeOptionsSuperClass() {
		objParentClass = this.getClass();
	} // public JobSchedulerFolderTreeOptionsSuperClass

	public JobSchedulerFolderTreeOptionsSuperClass(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public JobSchedulerFolderTreeOptionsSuperClass

	//

	public JobSchedulerFolderTreeOptionsSuperClass(HashMap<String, String> JSSettings) throws Exception {
		this();
		this.setAllOptions(JSSettings);
	} // public JobSchedulerFolderTreeOptionsSuperClass (HashMap JSSettings)

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
} // public class JobSchedulerFolderTreeOptionsSuperClass
