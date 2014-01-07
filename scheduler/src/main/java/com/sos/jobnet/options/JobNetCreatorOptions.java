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
package com.sos.jobnet.options;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.JSHelper.Options.*;
import com.sos.hibernate.options.HibernateOptions;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.creator.ObjectModelConnector;
import com.sos.scheduler.engine.kernel.scheduler.SchedulerException;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Options to create a jobnet.
 */
@JSOptionClass(name = "JobNetCreatorOptions", description = "JobNetCreatorOptions")
public class JobNetCreatorOptions extends HibernateOptions {
	
	private final static Logger logger = Logger.getLogger(JobNetCreatorOptions.class);

	private static final long serialVersionUID = 2632562825510369271L;
	private final String conClassName = JobNetCreatorOptions.class.getSimpleName();
	
	private ObjectModelConnector liveConnector = null;

	@SuppressWarnings("unused")
	private final String conSVNVersion = "$Id: JobNetCreatorOptionsSuperClass.java 16850 2012-03-21 12:14:23Z ss $";

    // ---------------------------------------------------------------------------------------------------
	/**
	 * The unique id of the jobnet
	 */
	@JSOptionDefinition(name = "JobNetId", description = "", key = "JobNetId", type = "SOSOptionUUID", mandatory = false)
	public SOSOptionUUID JobNetId = new SOSOptionUUID(this, conClassName
			+ ".JobNetId", // HashMap-Key
			"", // Titel
			"", // InitValue
			"", // DefaultValue
			false // isMandatory
	);

	public void setJobNetId(SOSOptionUUID jobNetId) {
		this.JobNetId = jobNetId;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Name of the result parameter (it is always an order parameter).
	 */
	@JSOptionDefinition(name = "JobNetIdParameterName", description = "The name the parameter contains the uuid.", key = "JobNetIdParameterName", type = "SOSOptionString", mandatory = false)
	public SOSOptionString JobNetIdParameterName = new SOSOptionString(this,
			getClass().getSimpleName() + ".JobNetIdParameterName", // HashMap-Key
			"", // Titel
			"", // InitValue
			"", // DefaultValue
			false // isMandatory
	);

	public void setJobNetIdParameterName(
			SOSOptionString jobnet_id_parameter_name) {
		this.JobNetIdParameterName = jobnet_id_parameter_name;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * JobChainName.
	 */
	@JSOptionDefinition(name = "JobChainName", description = "", key = "JobChainName", type = "SOSOptionString", mandatory = true)
	public SOSOptionString JobChainName = new SOSOptionString(this,
			conClassName + ".JobChainName", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setJobChainName(SOSOptionString p_JobChainName) {
		this.JobChainName = p_JobChainName;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * OrderId.
	 */
	@JSOptionDefinition(name = "OrderId", description = "", key = "OrderId", type = "SOSOptionString", mandatory = true)
	public SOSOptionString OrderId = new SOSOptionString(this, conClassName
			+ ".OrderId", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setOrderId(SOSOptionString p_OrderId) {
		this.OrderId = p_OrderId;
	}

    // ---------------------------------------------------------------------------------------------------
    /**
     * Root folder of the job net orders relative to the configuration directory.
     */
    @JSOptionDefinition(name = "JobnetOrderDir", description = "", key = "JobnetOrderDir", type = "SOSOptionString", mandatory = false)
    public SOSOptionString JobnetOrderDir = new SOSOptionString(this,
            conClassName + ".JobnetOrderDir", // HashMap-Key
            "", // Titel
            " ", // InitValue
            " ", // DefaultValue
            false // isMandatory
    );

    public void setJobnetOrderDir(SOSOptionString p_folderName) {
        this.JobnetOrderDir = p_folderName;
    }

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Host
	 */
	@JSOptionDefinition(name = "SCHEDULER_HOST", description = "", key = "SCHEDULER_HOST", type = "SOSOptionHostName", mandatory = true)
	public SOSOptionHostName SCHEDULER_HOST = new SOSOptionHostName(this,
			conClassName + ".SCHEDULER_HOST", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setSCHEDULER_HOST(SOSOptionHostName p_host) {
		this.SCHEDULER_HOST = p_host;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Maximun number of nodes in one job net.
	 */
	@JSOptionDefinition(name = "MaxJobNetNodes", description = "", key = "MaxJobNetNodes", type = "SOSOptionInteger", mandatory = false)
	public SOSOptionInteger MaxJobNetNodes = new SOSOptionInteger(this,
			conClassName + ".MaxJobNetNodes", // HashMap-Key
			"", // Titel
			"500", // InitValue
			"500", // DefaultValue
			false // isMandatory
	);

	public void setMaxJobNetNodes(SOSOptionInteger p_maxJobNetNodes) {
		this.MaxJobNetNodes = p_maxJobNetNodes;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Additional to the order parameter successor a second parameter predecessor can be set. It defines the relationship to
	 * all predecessors of a node in the same manner as in case of the successors.
     * This parameter gives the ability to check the texture of the jobnet. Each successor defined at one node must have a predecessor
     * vice versa.
	 */
	@JSOptionDefinition(name = "CheckJobNetTexture", description = "", key = "CheckJobNetTexture", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean CheckJobNetTexture = new SOSOptionBoolean(this,
			conClassName + ".CheckJobNetTexture", // HashMap-Key
			"", // Titel
			"true", // InitValue
			"true", // DefaultValue
			false // isMandatory
	);

	public SOSOptionBoolean getCheckJobNetTexture() {
		return CheckJobNetTexture;
	}

    // ---------------------------------------------------------------------------------------------------
	@JSOptionDefinition(name = "StartOrder", description = "", key = "StartOrder", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean StartOrder = new SOSOptionBoolean(this,
			conClassName + ".StartOrder", // HashMap-Key
			"", // Titel
			"true", // InitValue
			"true", // DefaultValue
			false // isMandatory
	);

	public void setStartOrder(SOSOptionBoolean p_StartOrder) {
		this.StartOrder = p_StartOrder;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Flag to determine if an order should create and start after buildung the jobnet.
	 */
	@JSOptionDefinition(name = "CheckFrequency", description = "", key = "CheckFrequency", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean CheckFrequency = new SOSOptionBoolean(this,
			conClassName + ".CheckFrequency", // HashMap-Key
			"", // Titel
			"true", // InitValue
			"true", // DefaultValue
			false // isMandatory
	);

	public void setCheckFrequency(SOSOptionBoolean p_CheckFrequency) {
		this.CheckFrequency = p_CheckFrequency;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * \var CreateGraph Flag to determine if a jobnet graph should be create
	 */
	@JSOptionDefinition(name = "CreateGraph", description = "", key = "CreateGraph", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean CreateGraph = new SOSOptionBoolean(this,
			conClassName + ".CreateGraph", // HashMap-Key
			"", // Titel
			"false", // InitValue
			"false", // DefaultValue
			false // isMandatory
	);

	public void setCreateGraph(SOSOptionBoolean p_CreateGraph) {
		this.CreateGraph = p_CreateGraph;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Root folder of the job net graphs.
	 */
	@JSOptionDefinition(name = "GraphDir", description = "", key = "GraphDir", type = "SOSOptionFolderName", mandatory = false)
	public SOSOptionFolderName GraphDir = new SOSOptionFolderName(this,
			conClassName + ".GraphDir", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setGraphDir(SOSOptionFolderName p_folderName) {
		this.GraphDir = p_folderName;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * GraphBuilderJobChain
	 */
	@JSOptionDefinition(name = "GraphBuilderJobChain", description = "", key = "GraphBuilderJobChain", type = "SOSOptionString", mandatory = false)
	public SOSOptionString GraphBuilderJobChain = new SOSOptionString(this,
			conClassName + ".GraphBuilderJobChain", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public SOSOptionString getGraphBuilderJobChain() {
		return GraphBuilderJobChain;
	}

	public void setGraphBuilderJobChain(SOSOptionString jobChain) {
		this.GraphBuilderJobChain = jobChain;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Port
	 */
	@JSOptionDefinition(name = "SCHEDULER_TCP_PORT", description = "", key = "SCHEDULER_TCP_PORT", type = "SOSOptionPortNumber", mandatory = true)
	public SOSOptionPortNumber SCHEDULER_TCP_PORT = new SOSOptionPortNumber(
			this, conClassName + ".SCHEDULER_TCP_PORT", // HashMap-Key
			"", // Titel
			"0", // InitValue
			"0", // DefaultValue
			true // isMandatory
	);

    public void setSCHEDULER_TCP_PORT(SOSOptionPortNumber p_port) {
		this.SCHEDULER_TCP_PORT = p_port;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * SCHEDULER_CONFIGURATION_DIRECTORY : root folder of the job net defintions.
	 */
	@JSOptionDefinition(name = "SCHEDULER_CONFIGURATION_DIRECTORY", description = "", key = "SCHEDULER_CONFIGURATION_DIRECTORY", type = "SOSOptionFolderName", mandatory = false)
	public SOSOptionFolderName SCHEDULER_CONFIGURATION_DIRECTORY = new SOSOptionFolderName(
			this, conClassName + ".SCHEDULER_CONFIGURATION_DIRECTORY", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setSCHEDULER_CONFIGURATION_DIRECTORY(
			SOSOptionFolderName p_liveFolder) {
		this.SCHEDULER_CONFIGURATION_DIRECTORY = p_liveFolder;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * The scheduler id.
	 */
	@JSOptionDefinition(name = "SCHEDULER_ID", description = "", key = "SCHEDULER_ID", type = "SOSOptionString", mandatory = false)
	public SOSOptionString SCHEDULER_ID = new SOSOptionString(this,
			conClassName + ".SCHEDULER_ID", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			true // isMandatory
	);

	public void setSCHEDULER_ID(SOSOptionString p_schedulerId) {
		this.SCHEDULER_ID = p_schedulerId;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * The time horizon for which the jobnet instances will be calculated.
	 */
	@JSOptionDefinition(name = "TimeHorizon", description = "", key = "TimeHorizon", type = "SOSOptionTimeHorizon", mandatory = false)
	public SOSOptionTimeHorizon TimeHorizon = new SOSOptionTimeHorizon(this,
			conClassName + ".TimeHorizon", // HashMap-Key
			"", // Titel
			"1:00:00:00", // InitValue
			"1:00:00:00", // DefaultValue
			false // isMandatory
	);

	public void settime_horizon(SOSOptionTimeHorizon p_time_horizon) {
		this.TimeHorizon = p_time_horizon;
	}

    // ---------------------------------------------------------------------------------------------------
	/**
	 * A parameter to restart an existing jobnet instance (JobNetId has to be set).
	 */
	@JSOptionDefinition(name = "StartOption", description = "", key = "RestartOption", type = "SOSOptionStartJobnet", mandatory = false)
	public SOSOptionStartJobnet StartOption = new SOSOptionStartJobnet(this, conClassName
			+ ".StartOption", // HashMap-Key
			"", // Titel
			"start", // InitValue
			"start", // DefaultValue
			false // isMandatory
	);

	public void setStartOption(SOSOptionStartJobnet startOption) {
		this.StartOption = startOption;
	}

    // ---------------------------------------------------------------------------------------------------
    /**
     * \var uuid_jobnet_identifier :
     */
    @JSOptionDefinition(name = "uuid_jobnet_identifier", description = "", key = "uuid_jobnet_identifier", type = "SOSOptionUUID", mandatory = false)
    public SOSOptionUUID uuid_jobnet_identifier = new SOSOptionUUID(this, conClassName + ".uuid_jobnet_identifier", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setuuid_jobnet_identifier (SOSOptionUUID p_uuid_jobnet_identifier) { 
        this.uuid_jobnet_identifier = p_uuid_jobnet_identifier;
    }

    // ---------------------------------------------------------------------------------------------------
	/**
	 * Transfertype: tcp (via object model), udp (via object model), direct (via spooler.execute_xml)
	 */
	@JSOptionDefinition(name = "TransferType", description = "", key = "TransferType", type = "TransferType", mandatory = false)
	public SOSOptionStartType TransferType = new SOSOptionStartType(this,
			conClassName + ".TransferType", // HashMap-Key
			"", // Titel
			"tcp", // InitValue
			"tcp", // DefaultValue
			false // isMandatory
	);

	public void setTransferType(SOSOptionStartType startType) {
		this.TransferType = startType;
	}

	
	public ObjectModelConnector getLiveConnector() {
		if (liveConnector == null) {
			try {
				if (SCHEDULER_HOST.IsEmpty() || SCHEDULER_TCP_PORT.IsEmpty() || SCHEDULER_CONFIGURATION_DIRECTORY.IsEmpty())
					throw new JobNetException("Could not establish the connection to the live folder - SCHEDULER_HOST, SCHEDULER_TCP_PORT or SCHEDULER_CONFIGURATION_DIRECTORY is missing.");
                SOSOptionFolderName configDir = SCHEDULER_CONFIGURATION_DIRECTORY;
                if(!JobnetOrderDir.Value().isEmpty()) configDir.Value(configDir.Value() + "/" + JobnetOrderDir.Value());
				liveConnector = ObjectModelConnector.getInstance(SCHEDULER_HOST, SCHEDULER_TCP_PORT, configDir);
				// liveConnector = ObjectModelConnector.getInstance(SCHEDULER_HOST, SCHEDULER_TCP_PORT, SCHEDULER_CONFIGURATION_DIRECTORY);
			} catch (Exception e) {
				logger.error("Error connecting live folder: " + e.getMessage());
				throw new SchedulerException("Error connecting live folder.", e);
			}
		}
		return liveConnector;
	}
    // ---------------------------------------------------------------------------------------------------
    
	public JobNetCreatorOptions() {
		objParentClass = this.getClass();
	} // public JobNetCreatorOptionsSuperClass

	public JobNetCreatorOptions(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public JobNetCreatorOptionsSuperClass

	//

	public JobNetCreatorOptions(HashMap<String, String> JSSettings)
			throws Exception {
		this();
		this.setAllOptions(JSSettings);
	} // public JobNetCreatorOptionsSuperClass (HashMap JSSettings)

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
