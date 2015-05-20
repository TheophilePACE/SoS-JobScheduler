//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="config" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="base" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="file" use="required" type="{}File" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{}params" minOccurs="0"/>
 *                   &lt;element ref="{}security" minOccurs="0"/>
 *                   &lt;element name="plugins" type="{}Plugins" minOccurs="0"/>
 *                   &lt;element ref="{}cluster" minOccurs="0"/>
 *                   &lt;element ref="{}process_classes" minOccurs="0"/>
 *                   &lt;element name="schedules" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="locks" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;choice>
 *                     &lt;element ref="{}script" minOccurs="0"/>
 *                     &lt;element name="scheduler_script" type="{}scheduler_script" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/choice>
 *                   &lt;choice minOccurs="0">
 *                     &lt;element ref="{}http_server"/>
 *                     &lt;element ref="{}web_services"/>
 *                   &lt;/choice>
 *                   &lt;choice>
 *                     &lt;element ref="{}holidays"/>
 *                     &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/choice>
 *                   &lt;element ref="{}jobs" minOccurs="0"/>
 *                   &lt;element ref="{}job_chains" minOccurs="0"/>
 *                   &lt;element ref="{}commands" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="spooler_id" type="{}Name" />
 *                 &lt;attribute name="port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="ip_address" type="{}String" />
 *                 &lt;attribute name="param" type="{}String" />
 *                 &lt;attribute name="log_dir" type="{}File" />
 *                 &lt;attribute name="priority_max" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="include_path" type="{}File" />
 *                 &lt;attribute name="java_class_path" type="{}String" />
 *                 &lt;attribute name="java_options" type="{}String" />
 *                 &lt;attribute name="main_scheduler" type="{}String" />
 *                 &lt;attribute name="mail_xslt_stylesheet" type="{}File" />
 *                 &lt;attribute name="supervisor" type="{}String" />
 *                 &lt;attribute name="configuration_directory" type="{}File" />
 *                 &lt;attribute name="central_configuration_directory" type="{}File" />
 *                 &lt;attribute name="configuration_add_event" type="{}Path" />
 *                 &lt;attribute name="configuration_modify_event" type="{}Path" />
 *                 &lt;attribute name="configuration_delete_event" type="{}Path" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}commands"/>
 *         &lt;element name="command" type="{}Commands"/>
 *         &lt;element name="answer">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}add_jobs"/>
 *         &lt;element ref="{}add_order"/>
 *         &lt;element name="check_folders" type="{}check_folders"/>
 *         &lt;element ref="{}job.why"/>
 *         &lt;element ref="{}kill_task"/>
 *         &lt;element ref="{}modify_job"/>
 *         &lt;element ref="{}modify_order"/>
 *         &lt;element ref="{}modify_spooler"/>
 *         &lt;element ref="{}register_remote_scheduler"/>
 *         &lt;element ref="{}remove_job_chain"/>
 *         &lt;element ref="{}remove_order"/>
 *         &lt;element name="run_time" type="{}run_time"/>
 *         &lt;element ref="{}show_calendar"/>
 *         &lt;element ref="{}show_history"/>
 *         &lt;element ref="{}show_job"/>
 *         &lt;element ref="{}show_jobs"/>
 *         &lt;element ref="{}show_job_chains"/>
 *         &lt;element ref="{}show_job_chain"/>
 *         &lt;element ref="{}show_order"/>
 *         &lt;element ref="{}s"/>
 *         &lt;element ref="{}show_schedulers"/>
 *         &lt;element ref="{}show_state"/>
 *         &lt;element ref="{}show_task"/>
 *         &lt;element ref="{}service_request"/>
 *         &lt;element ref="{}start_job"/>
 *         &lt;element ref="{}subsystem.show"/>
 *         &lt;element ref="{}terminate"/>
 *         &lt;element ref="{}events.get"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "config",
    "commands",
    "command",
    "answer",
    "addJobs",
    "addOrder",
    "checkFolders",
    "jobWhy",
    "killTask",
    "modifyJob",
    "modifyOrder",
    "modifySpooler",
    "registerRemoteScheduler",
    "removeJobChain",
    "removeOrder",
    "runTime",
    "showCalendar",
    "showHistory",
    "showJob",
    "showJobs",
    "showJobChains",
    "showJobChain",
    "showOrder",
    "s",
    "showSchedulers",
    "showState",
    "showTask",
    "serviceRequest",
    "startJob",
    "subsystemShow",
    "terminate",
    "eventsGet"
})
@XmlRootElement(name = "spooler")
public class Spooler {

    protected List<Spooler.Config> config;
    protected Commands commands;
    protected Commands command;
    protected Spooler.Answer answer;
    @XmlElement(name = "add_jobs")
    protected AddJobs addJobs;
    @XmlElement(name = "add_order")
    protected Order addOrder;
    @XmlElement(name = "check_folders")
    protected CheckFolders checkFolders;
    @XmlElement(name = "job.why")
    protected JobWhy jobWhy;
    @XmlElement(name = "kill_task")
    protected KillTask killTask;
    @XmlElement(name = "modify_job")
    protected ModifyJob modifyJob;
    @XmlElement(name = "modify_order")
    protected ModifyOrder modifyOrder;
    @XmlElement(name = "modify_spooler")
    protected ModifySpooler modifySpooler;
    @XmlElement(name = "register_remote_scheduler")
    protected RegisterRemoteScheduler registerRemoteScheduler;
    @XmlElement(name = "remove_job_chain")
    protected RemoveJobChain removeJobChain;
    @XmlElement(name = "remove_order")
    protected RemoveOrder removeOrder;
    @XmlElement(name = "run_time")
    protected RunTime runTime;
    @XmlElement(name = "show_calendar")
    protected ShowCalendar showCalendar;
    @XmlElement(name = "show_history")
    protected ShowHistory showHistory;
    @XmlElement(name = "show_job")
    protected ShowJob showJob;
    @XmlElement(name = "show_jobs")
    protected ShowJobs showJobs;
    @XmlElement(name = "show_job_chains")
    protected ShowJobChains showJobChains;
    @XmlElement(name = "show_job_chain")
    protected ShowJobChain showJobChain;
    @XmlElement(name = "show_order")
    protected ShowOrder showOrder;
    protected ShowState s;
    @XmlElement(name = "show_schedulers")
    protected ShowState showSchedulers;
    @XmlElement(name = "show_state")
    protected ShowState showState;
    @XmlElement(name = "show_task")
    protected ShowTask showTask;
    @XmlElement(name = "service_request")
    protected ServiceRequest serviceRequest;
    @XmlElement(name = "start_job")
    protected StartJob startJob;
    @XmlElement(name = "subsystem.show")
    protected SubsystemShow subsystemShow;
    protected Terminate terminate;
    @XmlElement(name = "events.get")
    protected EventsGet eventsGet;

    /**
     * Gets the value of the config property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the config property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Spooler.Config }
     * 
     * 
     */
    public List<Spooler.Config> getConfig() {
        if (config == null) {
            config = new ArrayList<Spooler.Config>();
        }
        return this.config;
    }

    /**
     * Obtient la valeur de la propriété commands.
     * 
     * @return
     *     possible object is
     *     {@link Commands }
     *     
     */
    public Commands getCommands() {
        return commands;
    }

    /**
     * Définit la valeur de la propriété commands.
     * 
     * @param value
     *     allowed object is
     *     {@link Commands }
     *     
     */
    public void setCommands(Commands value) {
        this.commands = value;
    }

    /**
     * Obtient la valeur de la propriété command.
     * 
     * @return
     *     possible object is
     *     {@link Commands }
     *     
     */
    public Commands getCommand() {
        return command;
    }

    /**
     * Définit la valeur de la propriété command.
     * 
     * @param value
     *     allowed object is
     *     {@link Commands }
     *     
     */
    public void setCommand(Commands value) {
        this.command = value;
    }

    /**
     * Obtient la valeur de la propriété answer.
     * 
     * @return
     *     possible object is
     *     {@link Spooler.Answer }
     *     
     */
    public Spooler.Answer getAnswer() {
        return answer;
    }

    /**
     * Définit la valeur de la propriété answer.
     * 
     * @param value
     *     allowed object is
     *     {@link Spooler.Answer }
     *     
     */
    public void setAnswer(Spooler.Answer value) {
        this.answer = value;
    }

    /**
     * Obtient la valeur de la propriété addJobs.
     * 
     * @return
     *     possible object is
     *     {@link AddJobs }
     *     
     */
    public AddJobs getAddJobs() {
        return addJobs;
    }

    /**
     * Définit la valeur de la propriété addJobs.
     * 
     * @param value
     *     allowed object is
     *     {@link AddJobs }
     *     
     */
    public void setAddJobs(AddJobs value) {
        this.addJobs = value;
    }

    /**
     * Obtient la valeur de la propriété addOrder.
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getAddOrder() {
        return addOrder;
    }

    /**
     * Définit la valeur de la propriété addOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setAddOrder(Order value) {
        this.addOrder = value;
    }

    /**
     * Obtient la valeur de la propriété checkFolders.
     * 
     * @return
     *     possible object is
     *     {@link CheckFolders }
     *     
     */
    public CheckFolders getCheckFolders() {
        return checkFolders;
    }

    /**
     * Définit la valeur de la propriété checkFolders.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckFolders }
     *     
     */
    public void setCheckFolders(CheckFolders value) {
        this.checkFolders = value;
    }

    /**
     * Obtient la valeur de la propriété jobWhy.
     * 
     * @return
     *     possible object is
     *     {@link JobWhy }
     *     
     */
    public JobWhy getJobWhy() {
        return jobWhy;
    }

    /**
     * Définit la valeur de la propriété jobWhy.
     * 
     * @param value
     *     allowed object is
     *     {@link JobWhy }
     *     
     */
    public void setJobWhy(JobWhy value) {
        this.jobWhy = value;
    }

    /**
     * Obtient la valeur de la propriété killTask.
     * 
     * @return
     *     possible object is
     *     {@link KillTask }
     *     
     */
    public KillTask getKillTask() {
        return killTask;
    }

    /**
     * Définit la valeur de la propriété killTask.
     * 
     * @param value
     *     allowed object is
     *     {@link KillTask }
     *     
     */
    public void setKillTask(KillTask value) {
        this.killTask = value;
    }

    /**
     * Obtient la valeur de la propriété modifyJob.
     * 
     * @return
     *     possible object is
     *     {@link ModifyJob }
     *     
     */
    public ModifyJob getModifyJob() {
        return modifyJob;
    }

    /**
     * Définit la valeur de la propriété modifyJob.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyJob }
     *     
     */
    public void setModifyJob(ModifyJob value) {
        this.modifyJob = value;
    }

    /**
     * Obtient la valeur de la propriété modifyOrder.
     * 
     * @return
     *     possible object is
     *     {@link ModifyOrder }
     *     
     */
    public ModifyOrder getModifyOrder() {
        return modifyOrder;
    }

    /**
     * Définit la valeur de la propriété modifyOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyOrder }
     *     
     */
    public void setModifyOrder(ModifyOrder value) {
        this.modifyOrder = value;
    }

    /**
     * Obtient la valeur de la propriété modifySpooler.
     * 
     * @return
     *     possible object is
     *     {@link ModifySpooler }
     *     
     */
    public ModifySpooler getModifySpooler() {
        return modifySpooler;
    }

    /**
     * Définit la valeur de la propriété modifySpooler.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifySpooler }
     *     
     */
    public void setModifySpooler(ModifySpooler value) {
        this.modifySpooler = value;
    }

    /**
     * Obtient la valeur de la propriété registerRemoteScheduler.
     * 
     * @return
     *     possible object is
     *     {@link RegisterRemoteScheduler }
     *     
     */
    public RegisterRemoteScheduler getRegisterRemoteScheduler() {
        return registerRemoteScheduler;
    }

    /**
     * Définit la valeur de la propriété registerRemoteScheduler.
     * 
     * @param value
     *     allowed object is
     *     {@link RegisterRemoteScheduler }
     *     
     */
    public void setRegisterRemoteScheduler(RegisterRemoteScheduler value) {
        this.registerRemoteScheduler = value;
    }

    /**
     * Obtient la valeur de la propriété removeJobChain.
     * 
     * @return
     *     possible object is
     *     {@link RemoveJobChain }
     *     
     */
    public RemoveJobChain getRemoveJobChain() {
        return removeJobChain;
    }

    /**
     * Définit la valeur de la propriété removeJobChain.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoveJobChain }
     *     
     */
    public void setRemoveJobChain(RemoveJobChain value) {
        this.removeJobChain = value;
    }

    /**
     * Obtient la valeur de la propriété removeOrder.
     * 
     * @return
     *     possible object is
     *     {@link RemoveOrder }
     *     
     */
    public RemoveOrder getRemoveOrder() {
        return removeOrder;
    }

    /**
     * Définit la valeur de la propriété removeOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoveOrder }
     *     
     */
    public void setRemoveOrder(RemoveOrder value) {
        this.removeOrder = value;
    }

    /**
     * Obtient la valeur de la propriété runTime.
     * 
     * @return
     *     possible object is
     *     {@link RunTime }
     *     
     */
    public RunTime getRunTime() {
        return runTime;
    }

    /**
     * Définit la valeur de la propriété runTime.
     * 
     * @param value
     *     allowed object is
     *     {@link RunTime }
     *     
     */
    public void setRunTime(RunTime value) {
        this.runTime = value;
    }

    /**
     * Obtient la valeur de la propriété showCalendar.
     * 
     * @return
     *     possible object is
     *     {@link ShowCalendar }
     *     
     */
    public ShowCalendar getShowCalendar() {
        return showCalendar;
    }

    /**
     * Définit la valeur de la propriété showCalendar.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowCalendar }
     *     
     */
    public void setShowCalendar(ShowCalendar value) {
        this.showCalendar = value;
    }

    /**
     * Obtient la valeur de la propriété showHistory.
     * 
     * @return
     *     possible object is
     *     {@link ShowHistory }
     *     
     */
    public ShowHistory getShowHistory() {
        return showHistory;
    }

    /**
     * Définit la valeur de la propriété showHistory.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowHistory }
     *     
     */
    public void setShowHistory(ShowHistory value) {
        this.showHistory = value;
    }

    /**
     * Obtient la valeur de la propriété showJob.
     * 
     * @return
     *     possible object is
     *     {@link ShowJob }
     *     
     */
    public ShowJob getShowJob() {
        return showJob;
    }

    /**
     * Définit la valeur de la propriété showJob.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowJob }
     *     
     */
    public void setShowJob(ShowJob value) {
        this.showJob = value;
    }

    /**
     * Obtient la valeur de la propriété showJobs.
     * 
     * @return
     *     possible object is
     *     {@link ShowJobs }
     *     
     */
    public ShowJobs getShowJobs() {
        return showJobs;
    }

    /**
     * Définit la valeur de la propriété showJobs.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowJobs }
     *     
     */
    public void setShowJobs(ShowJobs value) {
        this.showJobs = value;
    }

    /**
     * Obtient la valeur de la propriété showJobChains.
     * 
     * @return
     *     possible object is
     *     {@link ShowJobChains }
     *     
     */
    public ShowJobChains getShowJobChains() {
        return showJobChains;
    }

    /**
     * Définit la valeur de la propriété showJobChains.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowJobChains }
     *     
     */
    public void setShowJobChains(ShowJobChains value) {
        this.showJobChains = value;
    }

    /**
     * Obtient la valeur de la propriété showJobChain.
     * 
     * @return
     *     possible object is
     *     {@link ShowJobChain }
     *     
     */
    public ShowJobChain getShowJobChain() {
        return showJobChain;
    }

    /**
     * Définit la valeur de la propriété showJobChain.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowJobChain }
     *     
     */
    public void setShowJobChain(ShowJobChain value) {
        this.showJobChain = value;
    }

    /**
     * Obtient la valeur de la propriété showOrder.
     * 
     * @return
     *     possible object is
     *     {@link ShowOrder }
     *     
     */
    public ShowOrder getShowOrder() {
        return showOrder;
    }

    /**
     * Définit la valeur de la propriété showOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowOrder }
     *     
     */
    public void setShowOrder(ShowOrder value) {
        this.showOrder = value;
    }

    /**
     * Obtient la valeur de la propriété s.
     * 
     * @return
     *     possible object is
     *     {@link ShowState }
     *     
     */
    public ShowState getS() {
        return s;
    }

    /**
     * Définit la valeur de la propriété s.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowState }
     *     
     */
    public void setS(ShowState value) {
        this.s = value;
    }

    /**
     * Obtient la valeur de la propriété showSchedulers.
     * 
     * @return
     *     possible object is
     *     {@link ShowState }
     *     
     */
    public ShowState getShowSchedulers() {
        return showSchedulers;
    }

    /**
     * Définit la valeur de la propriété showSchedulers.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowState }
     *     
     */
    public void setShowSchedulers(ShowState value) {
        this.showSchedulers = value;
    }

    /**
     * Obtient la valeur de la propriété showState.
     * 
     * @return
     *     possible object is
     *     {@link ShowState }
     *     
     */
    public ShowState getShowState() {
        return showState;
    }

    /**
     * Définit la valeur de la propriété showState.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowState }
     *     
     */
    public void setShowState(ShowState value) {
        this.showState = value;
    }

    /**
     * Obtient la valeur de la propriété showTask.
     * 
     * @return
     *     possible object is
     *     {@link ShowTask }
     *     
     */
    public ShowTask getShowTask() {
        return showTask;
    }

    /**
     * Définit la valeur de la propriété showTask.
     * 
     * @param value
     *     allowed object is
     *     {@link ShowTask }
     *     
     */
    public void setShowTask(ShowTask value) {
        this.showTask = value;
    }

    /**
     * Obtient la valeur de la propriété serviceRequest.
     * 
     * @return
     *     possible object is
     *     {@link ServiceRequest }
     *     
     */
    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    /**
     * Définit la valeur de la propriété serviceRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceRequest }
     *     
     */
    public void setServiceRequest(ServiceRequest value) {
        this.serviceRequest = value;
    }

    /**
     * Obtient la valeur de la propriété startJob.
     * 
     * @return
     *     possible object is
     *     {@link StartJob }
     *     
     */
    public StartJob getStartJob() {
        return startJob;
    }

    /**
     * Définit la valeur de la propriété startJob.
     * 
     * @param value
     *     allowed object is
     *     {@link StartJob }
     *     
     */
    public void setStartJob(StartJob value) {
        this.startJob = value;
    }

    /**
     * Obtient la valeur de la propriété subsystemShow.
     * 
     * @return
     *     possible object is
     *     {@link SubsystemShow }
     *     
     */
    public SubsystemShow getSubsystemShow() {
        return subsystemShow;
    }

    /**
     * Définit la valeur de la propriété subsystemShow.
     * 
     * @param value
     *     allowed object is
     *     {@link SubsystemShow }
     *     
     */
    public void setSubsystemShow(SubsystemShow value) {
        this.subsystemShow = value;
    }

    /**
     * Obtient la valeur de la propriété terminate.
     * 
     * @return
     *     possible object is
     *     {@link Terminate }
     *     
     */
    public Terminate getTerminate() {
        return terminate;
    }

    /**
     * Définit la valeur de la propriété terminate.
     * 
     * @param value
     *     allowed object is
     *     {@link Terminate }
     *     
     */
    public void setTerminate(Terminate value) {
        this.terminate = value;
    }

    /**
     * Obtient la valeur de la propriété eventsGet.
     * 
     * @return
     *     possible object is
     *     {@link EventsGet }
     *     
     */
    public EventsGet getEventsGet() {
        return eventsGet;
    }

    /**
     * Définit la valeur de la propriété eventsGet.
     * 
     * @param value
     *     allowed object is
     *     {@link EventsGet }
     *     
     */
    public void setEventsGet(EventsGet value) {
        this.eventsGet = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Answer {


    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="base" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="file" use="required" type="{}File" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{}params" minOccurs="0"/>
     *         &lt;element ref="{}security" minOccurs="0"/>
     *         &lt;element name="plugins" type="{}Plugins" minOccurs="0"/>
     *         &lt;element ref="{}cluster" minOccurs="0"/>
     *         &lt;element ref="{}process_classes" minOccurs="0"/>
     *         &lt;element name="schedules" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="locks" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element ref="{}script" minOccurs="0"/>
     *           &lt;element name="scheduler_script" type="{}scheduler_script" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;/choice>
     *         &lt;choice minOccurs="0">
     *           &lt;element ref="{}http_server"/>
     *           &lt;element ref="{}web_services"/>
     *         &lt;/choice>
     *         &lt;choice>
     *           &lt;element ref="{}holidays"/>
     *           &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;/choice>
     *         &lt;element ref="{}jobs" minOccurs="0"/>
     *         &lt;element ref="{}job_chains" minOccurs="0"/>
     *         &lt;element ref="{}commands" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="spooler_id" type="{}Name" />
     *       &lt;attribute name="port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="ip_address" type="{}String" />
     *       &lt;attribute name="param" type="{}String" />
     *       &lt;attribute name="log_dir" type="{}File" />
     *       &lt;attribute name="priority_max" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="include_path" type="{}File" />
     *       &lt;attribute name="java_class_path" type="{}String" />
     *       &lt;attribute name="java_options" type="{}String" />
     *       &lt;attribute name="main_scheduler" type="{}String" />
     *       &lt;attribute name="mail_xslt_stylesheet" type="{}File" />
     *       &lt;attribute name="supervisor" type="{}String" />
     *       &lt;attribute name="configuration_directory" type="{}File" />
     *       &lt;attribute name="central_configuration_directory" type="{}File" />
     *       &lt;attribute name="configuration_add_event" type="{}Path" />
     *       &lt;attribute name="configuration_modify_event" type="{}Path" />
     *       &lt;attribute name="configuration_delete_event" type="{}Path" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "base",
        "params",
        "security",
        "plugins",
        "cluster",
        "processClasses",
        "schedules",
        "locks",
        "script",
        "schedulerScript",
        "httpServer",
        "webServices",
        "holidays",
        "holiday",
        "jobs",
        "jobChains",
        "commands"
    })
    public static class Config {

        protected List<Spooler.Config.Base> base;
        protected Params params;
        protected Security security;
        protected Plugins plugins;
        protected Cluster cluster;
        @XmlElement(name = "process_classes")
        protected ProcessClasses processClasses;
        protected Spooler.Config.Schedules schedules;
        protected Spooler.Config.Locks locks;
        protected Script script;
        @XmlElement(name = "scheduler_script")
        protected List<SchedulerScript> schedulerScript;
        @XmlElement(name = "http_server")
        protected HttpServer httpServer;
        @XmlElement(name = "web_services")
        protected WebServices webServices;
        protected Holidays holidays;
        protected List<Holiday> holiday;
        protected Jobs jobs;
        @XmlElement(name = "job_chains")
        protected JobChains jobChains;
        protected Commands commands;
        @XmlAttribute(name = "spooler_id")
        protected String spoolerId;
        @XmlAttribute(name = "port")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger port;
        @XmlAttribute(name = "tcp_port")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger tcpPort;
        @XmlAttribute(name = "udp_port")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger udpPort;
        @XmlAttribute(name = "ip_address")
        protected String ipAddress;
        @XmlAttribute(name = "param")
        protected String param;
        @XmlAttribute(name = "log_dir")
        protected String logDir;
        @XmlAttribute(name = "priority_max")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger priorityMax;
        @XmlAttribute(name = "include_path")
        protected String includePath;
        @XmlAttribute(name = "java_class_path")
        protected String javaClassPath;
        @XmlAttribute(name = "java_options")
        protected String javaOptions;
        @XmlAttribute(name = "main_scheduler")
        protected String mainScheduler;
        @XmlAttribute(name = "mail_xslt_stylesheet")
        protected String mailXsltStylesheet;
        @XmlAttribute(name = "supervisor")
        protected String supervisor;
        @XmlAttribute(name = "configuration_directory")
        protected String configurationDirectory;
        @XmlAttribute(name = "central_configuration_directory")
        protected String centralConfigurationDirectory;
        @XmlAttribute(name = "configuration_add_event")
        protected String configurationAddEvent;
        @XmlAttribute(name = "configuration_modify_event")
        protected String configurationModifyEvent;
        @XmlAttribute(name = "configuration_delete_event")
        protected String configurationDeleteEvent;

        /**
         * Gets the value of the base property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the base property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBase().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Spooler.Config.Base }
         * 
         * 
         */
        public List<Spooler.Config.Base> getBase() {
            if (base == null) {
                base = new ArrayList<Spooler.Config.Base>();
            }
            return this.base;
        }

        /**
         * Obtient la valeur de la propriété params.
         * 
         * @return
         *     possible object is
         *     {@link Params }
         *     
         */
        public Params getParams() {
            return params;
        }

        /**
         * Définit la valeur de la propriété params.
         * 
         * @param value
         *     allowed object is
         *     {@link Params }
         *     
         */
        public void setParams(Params value) {
            this.params = value;
        }

        /**
         * Obtient la valeur de la propriété security.
         * 
         * @return
         *     possible object is
         *     {@link Security }
         *     
         */
        public Security getSecurity() {
            return security;
        }

        /**
         * Définit la valeur de la propriété security.
         * 
         * @param value
         *     allowed object is
         *     {@link Security }
         *     
         */
        public void setSecurity(Security value) {
            this.security = value;
        }

        /**
         * Obtient la valeur de la propriété plugins.
         * 
         * @return
         *     possible object is
         *     {@link Plugins }
         *     
         */
        public Plugins getPlugins() {
            return plugins;
        }

        /**
         * Définit la valeur de la propriété plugins.
         * 
         * @param value
         *     allowed object is
         *     {@link Plugins }
         *     
         */
        public void setPlugins(Plugins value) {
            this.plugins = value;
        }

        /**
         * Obtient la valeur de la propriété cluster.
         * 
         * @return
         *     possible object is
         *     {@link Cluster }
         *     
         */
        public Cluster getCluster() {
            return cluster;
        }

        /**
         * Définit la valeur de la propriété cluster.
         * 
         * @param value
         *     allowed object is
         *     {@link Cluster }
         *     
         */
        public void setCluster(Cluster value) {
            this.cluster = value;
        }

        /**
         * Obtient la valeur de la propriété processClasses.
         * 
         * @return
         *     possible object is
         *     {@link ProcessClasses }
         *     
         */
        public ProcessClasses getProcessClasses() {
            return processClasses;
        }

        /**
         * Définit la valeur de la propriété processClasses.
         * 
         * @param value
         *     allowed object is
         *     {@link ProcessClasses }
         *     
         */
        public void setProcessClasses(ProcessClasses value) {
            this.processClasses = value;
        }

        /**
         * Obtient la valeur de la propriété schedules.
         * 
         * @return
         *     possible object is
         *     {@link Spooler.Config.Schedules }
         *     
         */
        public Spooler.Config.Schedules getSchedules() {
            return schedules;
        }

        /**
         * Définit la valeur de la propriété schedules.
         * 
         * @param value
         *     allowed object is
         *     {@link Spooler.Config.Schedules }
         *     
         */
        public void setSchedules(Spooler.Config.Schedules value) {
            this.schedules = value;
        }

        /**
         * Obtient la valeur de la propriété locks.
         * 
         * @return
         *     possible object is
         *     {@link Spooler.Config.Locks }
         *     
         */
        public Spooler.Config.Locks getLocks() {
            return locks;
        }

        /**
         * Définit la valeur de la propriété locks.
         * 
         * @param value
         *     allowed object is
         *     {@link Spooler.Config.Locks }
         *     
         */
        public void setLocks(Spooler.Config.Locks value) {
            this.locks = value;
        }

        /**
         * Obtient la valeur de la propriété script.
         * 
         * @return
         *     possible object is
         *     {@link Script }
         *     
         */
        public Script getScript() {
            return script;
        }

        /**
         * Définit la valeur de la propriété script.
         * 
         * @param value
         *     allowed object is
         *     {@link Script }
         *     
         */
        public void setScript(Script value) {
            this.script = value;
        }

        /**
         * Gets the value of the schedulerScript property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the schedulerScript property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSchedulerScript().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SchedulerScript }
         * 
         * 
         */
        public List<SchedulerScript> getSchedulerScript() {
            if (schedulerScript == null) {
                schedulerScript = new ArrayList<SchedulerScript>();
            }
            return this.schedulerScript;
        }

        /**
         * Obtient la valeur de la propriété httpServer.
         * 
         * @return
         *     possible object is
         *     {@link HttpServer }
         *     
         */
        public HttpServer getHttpServer() {
            return httpServer;
        }

        /**
         * Définit la valeur de la propriété httpServer.
         * 
         * @param value
         *     allowed object is
         *     {@link HttpServer }
         *     
         */
        public void setHttpServer(HttpServer value) {
            this.httpServer = value;
        }

        /**
         * Obtient la valeur de la propriété webServices.
         * 
         * @return
         *     possible object is
         *     {@link WebServices }
         *     
         */
        public WebServices getWebServices() {
            return webServices;
        }

        /**
         * Définit la valeur de la propriété webServices.
         * 
         * @param value
         *     allowed object is
         *     {@link WebServices }
         *     
         */
        public void setWebServices(WebServices value) {
            this.webServices = value;
        }

        /**
         * Obtient la valeur de la propriété holidays.
         * 
         * @return
         *     possible object is
         *     {@link Holidays }
         *     
         */
        public Holidays getHolidays() {
            return holidays;
        }

        /**
         * Définit la valeur de la propriété holidays.
         * 
         * @param value
         *     allowed object is
         *     {@link Holidays }
         *     
         */
        public void setHolidays(Holidays value) {
            this.holidays = value;
        }

        /**
         * Gets the value of the holiday property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the holiday property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHoliday().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Holiday }
         * 
         * 
         */
        public List<Holiday> getHoliday() {
            if (holiday == null) {
                holiday = new ArrayList<Holiday>();
            }
            return this.holiday;
        }

        /**
         * Obtient la valeur de la propriété jobs.
         * 
         * @return
         *     possible object is
         *     {@link Jobs }
         *     
         */
        public Jobs getJobs() {
            return jobs;
        }

        /**
         * Définit la valeur de la propriété jobs.
         * 
         * @param value
         *     allowed object is
         *     {@link Jobs }
         *     
         */
        public void setJobs(Jobs value) {
            this.jobs = value;
        }

        /**
         * Obtient la valeur de la propriété jobChains.
         * 
         * @return
         *     possible object is
         *     {@link JobChains }
         *     
         */
        public JobChains getJobChains() {
            return jobChains;
        }

        /**
         * Définit la valeur de la propriété jobChains.
         * 
         * @param value
         *     allowed object is
         *     {@link JobChains }
         *     
         */
        public void setJobChains(JobChains value) {
            this.jobChains = value;
        }

        /**
         * Obtient la valeur de la propriété commands.
         * 
         * @return
         *     possible object is
         *     {@link Commands }
         *     
         */
        public Commands getCommands() {
            return commands;
        }

        /**
         * Définit la valeur de la propriété commands.
         * 
         * @param value
         *     allowed object is
         *     {@link Commands }
         *     
         */
        public void setCommands(Commands value) {
            this.commands = value;
        }

        /**
         * Obtient la valeur de la propriété spoolerId.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpoolerId() {
            return spoolerId;
        }

        /**
         * Définit la valeur de la propriété spoolerId.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpoolerId(String value) {
            this.spoolerId = value;
        }

        /**
         * Obtient la valeur de la propriété port.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPort() {
            return port;
        }

        /**
         * Définit la valeur de la propriété port.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPort(BigInteger value) {
            this.port = value;
        }

        /**
         * Obtient la valeur de la propriété tcpPort.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTcpPort() {
            return tcpPort;
        }

        /**
         * Définit la valeur de la propriété tcpPort.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTcpPort(BigInteger value) {
            this.tcpPort = value;
        }

        /**
         * Obtient la valeur de la propriété udpPort.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getUdpPort() {
            return udpPort;
        }

        /**
         * Définit la valeur de la propriété udpPort.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setUdpPort(BigInteger value) {
            this.udpPort = value;
        }

        /**
         * Obtient la valeur de la propriété ipAddress.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIpAddress() {
            return ipAddress;
        }

        /**
         * Définit la valeur de la propriété ipAddress.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIpAddress(String value) {
            this.ipAddress = value;
        }

        /**
         * Obtient la valeur de la propriété param.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParam() {
            return param;
        }

        /**
         * Définit la valeur de la propriété param.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParam(String value) {
            this.param = value;
        }

        /**
         * Obtient la valeur de la propriété logDir.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLogDir() {
            return logDir;
        }

        /**
         * Définit la valeur de la propriété logDir.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLogDir(String value) {
            this.logDir = value;
        }

        /**
         * Obtient la valeur de la propriété priorityMax.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPriorityMax() {
            return priorityMax;
        }

        /**
         * Définit la valeur de la propriété priorityMax.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPriorityMax(BigInteger value) {
            this.priorityMax = value;
        }

        /**
         * Obtient la valeur de la propriété includePath.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIncludePath() {
            return includePath;
        }

        /**
         * Définit la valeur de la propriété includePath.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIncludePath(String value) {
            this.includePath = value;
        }

        /**
         * Obtient la valeur de la propriété javaClassPath.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJavaClassPath() {
            return javaClassPath;
        }

        /**
         * Définit la valeur de la propriété javaClassPath.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJavaClassPath(String value) {
            this.javaClassPath = value;
        }

        /**
         * Obtient la valeur de la propriété javaOptions.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJavaOptions() {
            return javaOptions;
        }

        /**
         * Définit la valeur de la propriété javaOptions.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJavaOptions(String value) {
            this.javaOptions = value;
        }

        /**
         * Obtient la valeur de la propriété mainScheduler.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMainScheduler() {
            return mainScheduler;
        }

        /**
         * Définit la valeur de la propriété mainScheduler.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMainScheduler(String value) {
            this.mainScheduler = value;
        }

        /**
         * Obtient la valeur de la propriété mailXsltStylesheet.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMailXsltStylesheet() {
            return mailXsltStylesheet;
        }

        /**
         * Définit la valeur de la propriété mailXsltStylesheet.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMailXsltStylesheet(String value) {
            this.mailXsltStylesheet = value;
        }

        /**
         * Obtient la valeur de la propriété supervisor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupervisor() {
            return supervisor;
        }

        /**
         * Définit la valeur de la propriété supervisor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupervisor(String value) {
            this.supervisor = value;
        }

        /**
         * Obtient la valeur de la propriété configurationDirectory.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConfigurationDirectory() {
            return configurationDirectory;
        }

        /**
         * Définit la valeur de la propriété configurationDirectory.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConfigurationDirectory(String value) {
            this.configurationDirectory = value;
        }

        /**
         * Obtient la valeur de la propriété centralConfigurationDirectory.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCentralConfigurationDirectory() {
            return centralConfigurationDirectory;
        }

        /**
         * Définit la valeur de la propriété centralConfigurationDirectory.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCentralConfigurationDirectory(String value) {
            this.centralConfigurationDirectory = value;
        }

        /**
         * Obtient la valeur de la propriété configurationAddEvent.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConfigurationAddEvent() {
            return configurationAddEvent;
        }

        /**
         * Définit la valeur de la propriété configurationAddEvent.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConfigurationAddEvent(String value) {
            this.configurationAddEvent = value;
        }

        /**
         * Obtient la valeur de la propriété configurationModifyEvent.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConfigurationModifyEvent() {
            return configurationModifyEvent;
        }

        /**
         * Définit la valeur de la propriété configurationModifyEvent.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConfigurationModifyEvent(String value) {
            this.configurationModifyEvent = value;
        }

        /**
         * Obtient la valeur de la propriété configurationDeleteEvent.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConfigurationDeleteEvent() {
            return configurationDeleteEvent;
        }

        /**
         * Définit la valeur de la propriété configurationDeleteEvent.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConfigurationDeleteEvent(String value) {
            this.configurationDeleteEvent = value;
        }


        /**
         * <p>Classe Java pour anonymous complex type.
         * 
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="file" use="required" type="{}File" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Base {

            @XmlAttribute(name = "file", required = true)
            protected String file;

            /**
             * Obtient la valeur de la propriété file.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFile() {
                return file;
            }

            /**
             * Définit la valeur de la propriété file.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFile(String value) {
                this.file = value;
            }

        }


        /**
         * <p>Classe Java pour anonymous complex type.
         * 
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "lock"
        })
        public static class Locks {

            protected List<Lock> lock;

            /**
             * Gets the value of the lock property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the lock property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLock().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Lock }
             * 
             * 
             */
            public List<Lock> getLock() {
                if (lock == null) {
                    lock = new ArrayList<Lock>();
                }
                return this.lock;
            }

        }


        /**
         * <p>Classe Java pour anonymous complex type.
         * 
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "schedule"
        })
        public static class Schedules {

            protected List<RunTime> schedule;

            /**
             * Gets the value of the schedule property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the schedule property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSchedule().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RunTime }
             * 
             * 
             */
            public List<RunTime> getSchedule() {
                if (schedule == null) {
                    schedule = new ArrayList<RunTime>();
                }
                return this.schedule;
            }

        }

    }

}
