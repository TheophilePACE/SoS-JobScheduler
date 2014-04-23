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
import com.sos.JSHelper.Options.*;
import com.sos.hibernate.interfaces.IHibernateOptions;
import com.sos.hibernate.options.HibernateOptions;

import java.util.HashMap;

/**
 * Options to control a jobnet order.
 */
@JSOptionClass(name = "JobNetOptions", description = "JobNetOptions")
public class JobNetOptions  extends HibernateOptions implements IHibernateOptions{

	private static final long	serialVersionUID	= 2628303960726088794L;
	private final String	conClassName	= "JobNetOptionsSuperClass";

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Interval to check preconditions in seconds.
     */
    @JSOptionDefinition(name = "check_interval", description = "", key = "check_interval", type = "SOSOptionTime", mandatory = false)
    
    public SOSOptionTime check_interval = new SOSOptionTime(this, conClassName + ".check_interval", // HashMap-Key
                                                                "", // Titel
                                                                "00:00:30", // InitValue
                                                                "00:00:30", // DefaultValue
                                                                false // isMandatory
                    );

    public void setcheck_interval (SOSOptionTime p_check_interval) {
        this.check_interval = p_check_interval;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Delay_start_dispatcher :
     */
    @JSOptionDefinition(name = "delay_start_dispatcher", description = "", key = "delay_start_dispatcher", type = "SOSOptionTime", mandatory = false)
    
    public SOSOptionTime delay_start_dispatcher = new SOSOptionTime(this, conClassName + ".delay_start_dispatcher", // HashMap-Key
                                                                "", // Titel
                                                                "00:00:03", // InitValue
                                                                "00:00:03", // DefaultValue
                                                                false // isMandatory
                    );

    public void setdelay_start_dispatcher (SOSOptionTime p_delay_start_dispatcher) {
        this.delay_start_dispatcher = p_delay_start_dispatcher;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Flag to indicate the bootstarp order of the jobnet.
     */
    @JSOptionDefinition(name = "is_bootstrap_order", description = "", key = "is_bootstrap_order", type = "SOSOptionBoolean", mandatory = false)
    
    public SOSOptionBoolean is_bootstrap_order = new SOSOptionBoolean(this, conClassName + ".is_bootstrap_order", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

    public void setis_bootstrap_order (SOSOptionBoolean p_is_bootstrap_order) {
        this.is_bootstrap_order = p_is_bootstrap_order;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Name of the job net
     */
    @JSOptionDefinition(name = "jobnet", description = "", key = "jobnet", type = "SOSOptionString", mandatory = false)
    
    public SOSOptionString jobnet = new SOSOptionString(this, conClassName + ".jobnet", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                true // isMandatory
                    );

    public void setjobnet (SOSOptionString p_jobnet) {
        this.jobnet = p_jobnet;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Maximum number of checks.
     */
    @JSOptionDefinition(name = "max_number_of_checks", description = "", key = "max_number_of_checks", type = "SOSOptionInteger", mandatory = false)
    
    public SOSOptionInteger max_number_of_checks = new SOSOptionInteger(this, conClassName + ".max_number_of_checks", // HashMap-Key
                                                                "", // Titel
                                                                "-1", // InitValue
                                                                "-1", // DefaultValue
                                                                false // isMandatory
                    );

    public void setmax_number_of_checks (SOSOptionInteger p_max_number_of_checks) {
        this.max_number_of_checks = p_max_number_of_checks;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Name of the job net node.
     */
    @JSOptionDefinition(name = "node", description = "", key = "node", type = "SOSOptionString", mandatory = false)

    public SOSOptionString node = new SOSOptionString(this, conClassName + ".node", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setnode (SOSOptionString p_node) {
        this.node = p_node;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * List of comma separated predesessor_nodes (order names).
     */
    @JSOptionDefinition(name = "predesessor_nodes", description = "", key = "predesessor_nodes", type = "SOSOptionString", mandatory = false)
    
    public SOSOptionString predesessor_nodes = new SOSOptionString(this, conClassName + ".predesessor_nodes", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setpredesessor_nodes (SOSOptionString p_predesessor_nodes) {
        this.predesessor_nodes = p_predesessor_nodes;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * ID of the JobScheduler (Cluster)
     */
    @JSOptionDefinition(name = "scheduler_id", description = "", key = "scheduler_id", type = "SOSOptionSchedulerID", mandatory = false)
    
    public SOSOptionSchedulerID scheduler_id = new SOSOptionSchedulerID(this, conClassName + ".scheduler_id", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setscheduler_id (SOSOptionSchedulerID p_scheduler_id) {
        this.scheduler_id = p_scheduler_id;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * \var script_to_execute :
     */
    @JSOptionDefinition(name = "script_to_execute", description = "", key = "script_to_execute", type = "SOSOptionScript", mandatory = false)
    
    public SOSOptionScript script_to_execute = new SOSOptionScript(this, conClassName + ".script_to_execute", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setscript_to_execute (SOSOptionScript p_script_to_execute) {
        this.script_to_execute = p_script_to_execute;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * \var skipDispatcher :
     */
    @JSOptionDefinition(name = "skipDispatcher", description = "", key = "skipDispatcher", type = "SOSOptionBoolean", mandatory = false)
    
    public SOSOptionBoolean skipDispatcher = new SOSOptionBoolean(this, conClassName + ".skipDispatcher", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

    public void setskipDispatcher (SOSOptionBoolean p_skipDispatcher) {
        this.skipDispatcher = p_skipDispatcher;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * \var skipRunner :
     */
    @JSOptionDefinition(name = "skipRunner", description = "", key = "skipRunner", type = "SOSOptionBoolean", mandatory = false)
    
    public SOSOptionBoolean skipRunner = new SOSOptionBoolean(this, conClassName + ".skipRunner", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

    public void setskipRunner (SOSOptionBoolean p_skipRunner) {
        this.skipRunner = p_skipRunner;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * \var skipWaiter :
     */
    @JSOptionDefinition(name = "skipWaiter", description = "", key = "skipWaiter", type = "SOSOptionBoolean", mandatory = false)
    
    public SOSOptionBoolean skipWaiter = new SOSOptionBoolean(this, conClassName + ".skipWaiter", // HashMap-Key
                                                                "", // Titel
                                                                "false", // InitValue
                                                                "false", // DefaultValue
                                                                false // isMandatory
                    );

    public void setskipWaiter (SOSOptionBoolean p_skipWaiter) {
        this.skipWaiter = p_skipWaiter;
    }

    // --------------------------------------------------------------------------------------------------------------
    /**
     * A comma separated list of successor nodes 8order names).
     */
    @JSOptionDefinition(name = "successor_nodes", description = "", key = "successor_nodes", type = "SOSOptionString", mandatory = false)
    
    public SOSOptionString successor_nodes = new SOSOptionString(this, conClassName + ".successor_nodes", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setsuccessor_nodes (SOSOptionString p_successor_nodes) {
        this.successor_nodes = p_successor_nodes;
    }

    // --------------------------------------------------------------------------------------------------------------
    @JSOptionDefinition(name = "wait_for_events", description = "", key = "wait_for_events", type = "SOSOptionString", mandatory = false)
    
    public SOSOptionString wait_for_events = new SOSOptionString(this, conClassName + ".wait_for_events", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setwait_for_events (SOSOptionString events) { 
        this.wait_for_events = events;
    }

    // --------------------------------------------------------------------------------------------------------------
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

	public void setGraphBuilderJobChain(SOSOptionString jobChain) {
		this.GraphBuilderJobChain = jobChain;
	}

    // --------------------------------------------------------------------------------------------------------------
    /**
     * The unique jobnet id.
     */
    @JSOptionDefinition(name = "uuid_jobnet_identifier", 
    description = "", 
    key = "uuid_jobnet_identifier", 
    type = "SOSOptionUUID", 
    mandatory = false)
    
    public SOSOptionUUID uuid_jobnet_identifier = new SOSOptionUUID(this, conClassName + ".uuid_jobnet_identifier", // HashMap-Key
                                                                "", // Titel
                                                                "", // InitValue
                                                                "", // DefaultValue
                                                                false // isMandatory
                    );

    public void setuuid_jobnet_identifier (SOSOptionUUID p_uuid_jobnet_identifier) {
        this.uuid_jobnet_identifier = p_uuid_jobnet_identifier;
    }


    // --------------------------------------------------------------------------------------------------------------
	/**
	 * A list of task comma separated list of task errors that will be ignored. Ranges of errors can be specified with
     * the pattern "from-to", e.g. 2-4 means the errors 2, 3 and 4 will be ignored.
	 */
	@JSOptionDefinition(name = "valid_errors", description = "", key = "valid_errors", type = "SOSOptionExitCodeList", mandatory = false)
	public SOSOptionExitCodeList valid_errors = new SOSOptionExitCodeList(this,
			conClassName + ".valid_errors", // HashMap-Key
			"", // Titel
			" ", // InitValue
			" ", // DefaultValue
			false // isMandatory
	);

	public void setvalid_errors(SOSOptionExitCodeList valid_errors) {
		this.valid_errors = valid_errors;
	}

    // --------------------------------------------------------------------------------------------------------------
    /**
     * Parameter to set the order state in case of valid errors.
     */
    @JSOptionDefinition(name = "RestartOption", description = "", key = "RestartOption", type = "SOSOptionRestartJobnetNode", mandatory = false)

    public SOSOptionRestartJobnetNode restart_option = new SOSOptionRestartJobnetNode(this, conClassName + ".RestartOption", // HashMap-Key
            "", // Titel
            "none", // InitValue
            "none", // DefaultValue
            false // isMandatory
    );

    public void setrestart_option (SOSOptionRestartJobnetNode option) {
        this.restart_option = option;
    }


    // --------------------------------------------------------------------------------------------------------------


    public JobNetOptions() {
		objParentClass = this.getClass();
	} // public JobNetOptionsSuperClass    
        
	//
	public JobNetOptions(HashMap<String, String> JSSettings) throws Exception {
		this();
		this.setAllOptions(JSSettings);
	} // public JobNetOptionsSuperClass (HashMap JSSettings)


	/**
	 * \brief setAllOptions - uebernimmt die OptionenWerte aus der HashMap
	 *
	 * \details In der als Parameter anzugebenden HashMap sind Schluessel (Name)
	 * und Wert der jeweiligen Option als Paar angegeben. Ein Beispiel fuer den
	 * Aufbau einer solchen HashMap findet sich in der Beschreibung dieser
	 * Klasse (\ref TestData "setJobSchedulerSSHJobOptions"). In dieser Routine
	 * werden die Schluessel analysiert und, falls gefunden, werden die
	 * dazugehoerigen Werte den Properties dieser Klasse zugewiesen.
	 *
	 * Nicht bekannte Schluessel werden ignoriert.
	 *
	 * \see JSOptionsClass::getOrderRecord
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
	 * \brief CheckMandatory - prueft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgeloest, wenn eine mandatory-Option keinen Wert hat
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
	 * \brief CommandLineArgs - uebernehmen der Options/Settings aus der Kommandozeile
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
} // public class JobNetOptionsSuperClass
