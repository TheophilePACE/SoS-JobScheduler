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
package com.sos.jobnet.graph;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.JSHelper.Options.SOSOptionFolderName;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.JSHelper.Options.SOSOptionTime;
import com.sos.JSHelper.Options.SOSOptionUUID;
import com.sos.hibernate.options.HibernateOptions;

import java.util.HashMap;

/**
 * JobNetGraphBuilderOptions - creates a graph for a given jobnet instance
 */
@JSOptionClass(name = "JobNetGraphBuilderOptions", description = "JobNetGraphBuilderOptions")
public class JobNetGraphBuilderOptions extends HibernateOptions {

	private static final long serialVersionUID = -7201337187084469930L;

	private final String conClassName = JobNetGraphBuilderOptions.class
			.getSimpleName();

	@SuppressWarnings("unused")
	private final String conSVNVersion = "$Id: JobNetCreatorOptionsSuperClass.java 16850 2012-03-21 12:14:23Z ss $";

	public JobNetGraphBuilderOptions() {
		objParentClass = this.getClass();
	}

	public JobNetGraphBuilderOptions(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	}

	public JobNetGraphBuilderOptions(HashMap<String, String> JSSettings)
			throws Exception {
		this();
		this.setAllOptions(JSSettings);
	}

	/**
	 * \var uuid_jobnet_identifier :
	 */
	@JSOptionDefinition(name = "uuid_jobnet_identifier", description = "", key = "uuid_jobnet_identifier", type = "SOSOptionUUID", mandatory = false)
	public SOSOptionUUID uuid_jobnet_identifier = new SOSOptionUUID(this,
			conClassName + ".uuid_jobnet_identifier", // HashMap-Key
			"", // Titel
			"", // InitValue
			"", // DefaultValue
			false // isMandatory
	);

	public SOSOptionUUID getuuid_jobnet_identifier() {
		return uuid_jobnet_identifier;
	}

	public void setuuid_jobnet_identifier(SOSOptionUUID p_uuid_jobnet_identifier) {
		this.uuid_jobnet_identifier = p_uuid_jobnet_identifier;
	}

	/**
	 * root folder of the job net graphs.
	 */
	@JSOptionDefinition(name = "GraphDir", description = "", key = "GraphDir", type = "SOSOptionFolderName", mandatory = false)
	public SOSOptionFolderName GraphDir = new SOSOptionFolderName(this,
			conClassName + ".GraphDir", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public SOSOptionFolderName getGraphDir() {
		return GraphDir;
	}

	public void setGraphDir(SOSOptionFolderName p_folderName) {
		this.GraphDir = p_folderName;
	}

   /**
     * root folder of the job net graphs.
     */
    @JSOptionDefinition(name = "graphType", description = "", key = "graphType", type = "SOSOptionString", mandatory = false)
    public SOSOptionString graphType = new SOSOptionString(this,
            conClassName + ".graphType", // HashMap-Key
            "", // Titel
            "svg", // InitValue
            "svg", // DefaultValue
            false // isMandatory
    );

    public SOSOptionString getGraphType() {
        return graphType;
    }

    public void setGraphType(SOSOptionString p_graphType) {
        this.graphType = p_graphType;
    }

	/**
	 * the data folder of the JobScheduler installation
	 */
	@JSOptionDefinition(name = "SCHEDULER_DIRECTORY", description = "", key = "SCHEDULER_DIRECTORY", type = "SOSOptionFolderName", mandatory = false)
	public SOSOptionFolderName SCHEDULER_DIRECTORY = new SOSOptionFolderName(
			this, conClassName + ".SCHEDULER_DIRECTORY", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public SOSOptionFolderName getSCHEDULER_DIRECTORY() {
		return SCHEDULER_DIRECTORY;
	}

	public void setSCHEDULER_DIRECTORY(SOSOptionFolderName dataFolder) {
		this.SCHEDULER_DIRECTORY = dataFolder;
	}

    /**
     * size of the graph (corresponds with the size attribute of the dot language)
     */
    @JSOptionDefinition(name = "graphSize", description = "", key = "graphSize", type = "SOSOptionString", mandatory = false)
    public SOSOptionString graphSize = new SOSOptionString(this,
            conClassName + ".graphSize", // HashMap-Key
            "", // Titel
            "", // InitValue
            "", // DefaultValue
            false // isMandatory
    );

    public void setGraphSize(SOSOptionString p_graphSize) {
        this.graphSize = p_graphSize;
    }

    /**
     * ratio of the graph (corresponds with the ratio attribute of the dot language)
     */
    @JSOptionDefinition(name = "graphRatio", description = "", key = "graphRatio", type = "SOSOptionString", mandatory = false)
    public SOSOptionString graphRatio = new SOSOptionString(this,
            conClassName + ".graphRatio", // HashMap-Key
            "", // Titel
            "", // InitValue
            "", // DefaultValue
            false // isMandatory
    );

    public void setGraphRatio(SOSOptionString p_graphRatio) {
        this.graphRatio = p_graphRatio;
    }

	/**
	 * The interval for creating a new graph (in seconds)
	 */
	@JSOptionDefinition(name = "check_interval", description = "", key = "check_interval", type = "SOSOptionTime", mandatory = false)
	public SOSOptionTime check_interval = new SOSOptionTime(this, conClassName
			+ ".check_interval", // HashMap-Key
			"", // Titel
			"00:00:30", // InitValue
			"00:00:30", // DefaultValue
			false // isMandatory
	);

	public SOSOptionTime getcheck_interval() {
		return check_interval;
	}

	public void setcheck_interval(SOSOptionTime p_check_interval) {
		this.check_interval = p_check_interval;
	}

	/**
	 * \brief setAllOptions - uebernimmt die OptionenWerte aus der HashMap
	 * 
	 * \see JSOptionsClass::getOrderRecord
	 * 
	 * @param pobjJSSettings
	 * @throws Exception
	 */
	public void setAllOptions(HashMap<String, String> pobjJSSettings)
			throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setAllOptions";
		flgSetAllOptions = true;
		objSettings = pobjJSSettings;
		super.Settings(objSettings);
		super.setAllOptions(pobjJSSettings);
		flgSetAllOptions = false;
	} // public void setAllOptions (HashMap <String, String> JSSettings)

	/**
	 * \brief CheckMandatory - prueft alle Muss-Optionen auf Werte
	 * 
	 * \details
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 *             - wird ausgeloest, wenn eine mandatory-Option keinen Wert hat
	 */
	@Override
	public void CheckMandatory() throws JSExceptionMandatoryOptionMissing //
			, Exception {
		try {
			super.CheckMandatory();
		} catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()

	/**
	 * 
	 * \brief CommandLineArgs - uebernehmen der Options/Settings aus der
	 * Kommandozeile
	 * 
	 * \details Die in der Kommandozeile beim Starten der Applikation
	 * angegebenen Parameter werden hier in die HashMap uebertragen und danach
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
} // public class JobNetCreatorOptionsSuperClass
