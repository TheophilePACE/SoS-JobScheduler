//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

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
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="config" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="base" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="file" use="required" type="{}File" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element ref="{}params" minOccurs="0"/&gt;
 *                   &lt;element ref="{}security" minOccurs="0"/&gt;
 *                   &lt;element name="plugins" type="{}Plugins" minOccurs="0"/&gt;
 *                   &lt;element ref="{}cluster" minOccurs="0"/&gt;
 *                   &lt;element ref="{}process_classes" minOccurs="0"/&gt;
 *                   &lt;element name="schedules" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="locks" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;choice&gt;
 *                     &lt;element ref="{}script" minOccurs="0"/&gt;
 *                     &lt;element name="scheduler_script" type="{}scheduler_script" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;/choice&gt;
 *                   &lt;choice minOccurs="0"&gt;
 *                     &lt;element ref="{}http_server"/&gt;
 *                     &lt;element ref="{}web_services"/&gt;
 *                   &lt;/choice&gt;
 *                   &lt;choice&gt;
 *                     &lt;element ref="{}holidays"/&gt;
 *                     &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;/choice&gt;
 *                   &lt;element ref="{}jobs" minOccurs="0"/&gt;
 *                   &lt;element ref="{}job_chains" minOccurs="0"/&gt;
 *                   &lt;element ref="{}commands" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="spooler_id" type="{}Name" /&gt;
 *                 &lt;attribute name="port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="ip_address" type="{}String" /&gt;
 *                 &lt;attribute name="param" type="{}String" /&gt;
 *                 &lt;attribute name="log_dir" type="{}File" /&gt;
 *                 &lt;attribute name="priority_max" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="include_path" type="{}File" /&gt;
 *                 &lt;attribute name="java_class_path" type="{}String" /&gt;
 *                 &lt;attribute name="java_options" type="{}String" /&gt;
 *                 &lt;attribute name="main_scheduler" type="{}String" /&gt;
 *                 &lt;attribute name="mail_xslt_stylesheet" type="{}File" /&gt;
 *                 &lt;attribute name="supervisor" type="{}String" /&gt;
 *                 &lt;attribute name="configuration_directory" type="{}File" /&gt;
 *                 &lt;attribute name="central_configuration_directory" type="{}File" /&gt;
 *                 &lt;attribute name="configuration_add_event" type="{}Path" /&gt;
 *                 &lt;attribute name="configuration_modify_event" type="{}Path" /&gt;
 *                 &lt;attribute name="configuration_delete_event" type="{}Path" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{}commands"/&gt;
 *         &lt;element name="command" type="{}Commands"/&gt;
 *         &lt;element name="answer"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{}add_jobs"/&gt;
 *         &lt;element ref="{}add_order"/&gt;
 *         &lt;element name="check_folders" type="{}check_folders"/&gt;
 *         &lt;element ref="{}job.why"/&gt;
 *         &lt;element ref="{}kill_task"/&gt;
 *         &lt;element ref="{}modify_job"/&gt;
 *         &lt;element ref="{}modify_order"/&gt;
 *         &lt;element ref="{}modify_spooler"/&gt;
 *         &lt;element ref="{}register_remote_scheduler"/&gt;
 *         &lt;element ref="{}remove_job_chain"/&gt;
 *         &lt;element ref="{}remove_order"/&gt;
 *         &lt;element name="run_time" type="{}run_time"/&gt;
 *         &lt;element ref="{}show_calendar"/&gt;
 *         &lt;element ref="{}show_history"/&gt;
 *         &lt;element ref="{}show_job"/&gt;
 *         &lt;element ref="{}show_jobs"/&gt;
 *         &lt;element ref="{}show_job_chains"/&gt;
 *         &lt;element ref="{}show_job_chain"/&gt;
 *         &lt;element ref="{}show_order"/&gt;
 *         &lt;element ref="{}s"/&gt;
 *         &lt;element ref="{}show_schedulers"/&gt;
 *         &lt;element ref="{}show_state"/&gt;
 *         &lt;element ref="{}show_task"/&gt;
 *         &lt;element ref="{}service_request"/&gt;
 *         &lt;element ref="{}start_job"/&gt;
 *         &lt;element ref="{}subsystem.show"/&gt;
 *         &lt;element ref="{}terminate"/&gt;
 *         &lt;element ref="{}events.get"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Obtient la valeur de la propri�t� commands.
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
     * D�finit la valeur de la propri�t� commands.
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
     * Obtient la valeur de la propri�t� command.
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
     * D�finit la valeur de la propri�t� command.
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
     * Obtient la valeur de la propri�t� answer.
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
     * D�finit la valeur de la propri�t� answer.
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
     * Obtient la valeur de la propri�t� addJobs.
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
     * D�finit la valeur de la propri�t� addJobs.
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
     * Obtient la valeur de la propri�t� addOrder.
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
     * D�finit la valeur de la propri�t� addOrder.
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
     * Obtient la valeur de la propri�t� checkFolders.
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
     * D�finit la valeur de la propri�t� checkFolders.
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
     * Obtient la valeur de la propri�t� jobWhy.
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
     * D�finit la valeur de la propri�t� jobWhy.
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
     * Obtient la valeur de la propri�t� killTask.
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
     * D�finit la valeur de la propri�t� killTask.
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
     * Obtient la valeur de la propri�t� modifyJob.
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
     * D�finit la valeur de la propri�t� modifyJob.
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
     * Obtient la valeur de la propri�t� modifyOrder.
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
     * D�finit la valeur de la propri�t� modifyOrder.
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
     * Obtient la valeur de la propri�t� modifySpooler.
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
     * D�finit la valeur de la propri�t� modifySpooler.
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
     * Obtient la valeur de la propri�t� registerRemoteScheduler.
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
     * D�finit la valeur de la propri�t� registerRemoteScheduler.
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
     * Obtient la valeur de la propri�t� removeJobChain.
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
     * D�finit la valeur de la propri�t� removeJobChain.
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
     * Obtient la valeur de la propri�t� removeOrder.
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
     * D�finit la valeur de la propri�t� removeOrder.
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
     * Obtient la valeur de la propri�t� runTime.
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
     * D�finit la valeur de la propri�t� runTime.
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
     * Obtient la valeur de la propri�t� showCalendar.
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
     * D�finit la valeur de la propri�t� showCalendar.
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
     * Obtient la valeur de la propri�t� showHistory.
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
     * D�finit la valeur de la propri�t� showHistory.
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
     * Obtient la valeur de la propri�t� showJob.
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
     * D�finit la valeur de la propri�t� showJob.
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
     * Obtient la valeur de la propri�t� showJobs.
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
     * D�finit la valeur de la propri�t� showJobs.
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
     * Obtient la valeur de la propri�t� showJobChains.
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
     * D�finit la valeur de la propri�t� showJobChains.
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
     * Obtient la valeur de la propri�t� showJobChain.
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
     * D�finit la valeur de la propri�t� showJobChain.
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
     * Obtient la valeur de la propri�t� showOrder.
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
     * D�finit la valeur de la propri�t� showOrder.
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
     * Obtient la valeur de la propri�t� s.
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
     * D�finit la valeur de la propri�t� s.
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
     * Obtient la valeur de la propri�t� showSchedulers.
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
     * D�finit la valeur de la propri�t� showSchedulers.
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
     * Obtient la valeur de la propri�t� showState.
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
     * D�finit la valeur de la propri�t� showState.
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
     * Obtient la valeur de la propri�t� showTask.
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
     * D�finit la valeur de la propri�t� showTask.
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
     * Obtient la valeur de la propri�t� serviceRequest.
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
     * D�finit la valeur de la propri�t� serviceRequest.
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
     * Obtient la valeur de la propri�t� startJob.
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
     * D�finit la valeur de la propri�t� startJob.
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
     * Obtient la valeur de la propri�t� subsystemShow.
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
     * D�finit la valeur de la propri�t� subsystemShow.
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
     * Obtient la valeur de la propri�t� terminate.
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
     * D�finit la valeur de la propri�t� terminate.
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
     * Obtient la valeur de la propri�t� eventsGet.
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
     * D�finit la valeur de la propri�t� eventsGet.
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
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
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
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="base" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="file" use="required" type="{}File" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element ref="{}params" minOccurs="0"/&gt;
     *         &lt;element ref="{}security" minOccurs="0"/&gt;
     *         &lt;element name="plugins" type="{}Plugins" minOccurs="0"/&gt;
     *         &lt;element ref="{}cluster" minOccurs="0"/&gt;
     *         &lt;element ref="{}process_classes" minOccurs="0"/&gt;
     *         &lt;element name="schedules" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="locks" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;choice&gt;
     *           &lt;element ref="{}script" minOccurs="0"/&gt;
     *           &lt;element name="scheduler_script" type="{}scheduler_script" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;/choice&gt;
     *         &lt;choice minOccurs="0"&gt;
     *           &lt;element ref="{}http_server"/&gt;
     *           &lt;element ref="{}web_services"/&gt;
     *         &lt;/choice&gt;
     *         &lt;choice&gt;
     *           &lt;element ref="{}holidays"/&gt;
     *           &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;/choice&gt;
     *         &lt;element ref="{}jobs" minOccurs="0"/&gt;
     *         &lt;element ref="{}job_chains" minOccurs="0"/&gt;
     *         &lt;element ref="{}commands" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="spooler_id" type="{}Name" /&gt;
     *       &lt;attribute name="port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="ip_address" type="{}String" /&gt;
     *       &lt;attribute name="param" type="{}String" /&gt;
     *       &lt;attribute name="log_dir" type="{}File" /&gt;
     *       &lt;attribute name="priority_max" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="include_path" type="{}File" /&gt;
     *       &lt;attribute name="java_class_path" type="{}String" /&gt;
     *       &lt;attribute name="java_options" type="{}String" /&gt;
     *       &lt;attribute name="main_scheduler" type="{}String" /&gt;
     *       &lt;attribute name="mail_xslt_stylesheet" type="{}File" /&gt;
     *       &lt;attribute name="supervisor" type="{}String" /&gt;
     *       &lt;attribute name="configuration_directory" type="{}File" /&gt;
     *       &lt;attribute name="central_configuration_directory" type="{}File" /&gt;
     *       &lt;attribute name="configuration_add_event" type="{}Path" /&gt;
     *       &lt;attribute name="configuration_modify_event" type="{}Path" /&gt;
     *       &lt;attribute name="configuration_delete_event" type="{}Path" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
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
         * Obtient la valeur de la propri�t� params.
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
         * D�finit la valeur de la propri�t� params.
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
         * Obtient la valeur de la propri�t� security.
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
         * D�finit la valeur de la propri�t� security.
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
         * Obtient la valeur de la propri�t� plugins.
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
         * D�finit la valeur de la propri�t� plugins.
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
         * Obtient la valeur de la propri�t� cluster.
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
         * D�finit la valeur de la propri�t� cluster.
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
         * Obtient la valeur de la propri�t� processClasses.
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
         * D�finit la valeur de la propri�t� processClasses.
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
         * Obtient la valeur de la propri�t� schedules.
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
         * D�finit la valeur de la propri�t� schedules.
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
         * Obtient la valeur de la propri�t� locks.
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
         * D�finit la valeur de la propri�t� locks.
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
         * Obtient la valeur de la propri�t� script.
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
         * D�finit la valeur de la propri�t� script.
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
         * Obtient la valeur de la propri�t� httpServer.
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
         * D�finit la valeur de la propri�t� httpServer.
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
         * Obtient la valeur de la propri�t� webServices.
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
         * D�finit la valeur de la propri�t� webServices.
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
         * Obtient la valeur de la propri�t� holidays.
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
         * D�finit la valeur de la propri�t� holidays.
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
         * Obtient la valeur de la propri�t� jobs.
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
         * D�finit la valeur de la propri�t� jobs.
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
         * Obtient la valeur de la propri�t� jobChains.
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
         * D�finit la valeur de la propri�t� jobChains.
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
         * Obtient la valeur de la propri�t� commands.
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
         * D�finit la valeur de la propri�t� commands.
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
         * Obtient la valeur de la propri�t� spoolerId.
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
         * D�finit la valeur de la propri�t� spoolerId.
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
         * Obtient la valeur de la propri�t� port.
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
         * D�finit la valeur de la propri�t� port.
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
         * Obtient la valeur de la propri�t� tcpPort.
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
         * D�finit la valeur de la propri�t� tcpPort.
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
         * Obtient la valeur de la propri�t� udpPort.
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
         * D�finit la valeur de la propri�t� udpPort.
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
         * Obtient la valeur de la propri�t� ipAddress.
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
         * D�finit la valeur de la propri�t� ipAddress.
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
         * Obtient la valeur de la propri�t� param.
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
         * D�finit la valeur de la propri�t� param.
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
         * Obtient la valeur de la propri�t� logDir.
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
         * D�finit la valeur de la propri�t� logDir.
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
         * Obtient la valeur de la propri�t� priorityMax.
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
         * D�finit la valeur de la propri�t� priorityMax.
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
         * Obtient la valeur de la propri�t� includePath.
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
         * D�finit la valeur de la propri�t� includePath.
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
         * Obtient la valeur de la propri�t� javaClassPath.
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
         * D�finit la valeur de la propri�t� javaClassPath.
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
         * Obtient la valeur de la propri�t� javaOptions.
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
         * D�finit la valeur de la propri�t� javaOptions.
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
         * Obtient la valeur de la propri�t� mainScheduler.
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
         * D�finit la valeur de la propri�t� mainScheduler.
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
         * Obtient la valeur de la propri�t� mailXsltStylesheet.
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
         * D�finit la valeur de la propri�t� mailXsltStylesheet.
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
         * Obtient la valeur de la propri�t� supervisor.
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
         * D�finit la valeur de la propri�t� supervisor.
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
         * Obtient la valeur de la propri�t� configurationDirectory.
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
         * D�finit la valeur de la propri�t� configurationDirectory.
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
         * Obtient la valeur de la propri�t� centralConfigurationDirectory.
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
         * D�finit la valeur de la propri�t� centralConfigurationDirectory.
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
         * Obtient la valeur de la propri�t� configurationAddEvent.
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
         * D�finit la valeur de la propri�t� configurationAddEvent.
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
         * Obtient la valeur de la propri�t� configurationModifyEvent.
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
         * D�finit la valeur de la propri�t� configurationModifyEvent.
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
         * Obtient la valeur de la propri�t� configurationDeleteEvent.
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
         * D�finit la valeur de la propri�t� configurationDeleteEvent.
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
         * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="file" use="required" type="{}File" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
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
             * Obtient la valeur de la propri�t� file.
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
             * D�finit la valeur de la propri�t� file.
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
         * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="lock" type="{}lock" maxOccurs="unbounded" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
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
         * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="schedule" type="{}run_time" maxOccurs="unbounded" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
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
