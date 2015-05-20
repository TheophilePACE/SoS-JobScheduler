//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Commands complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Commands"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element ref="{}add_jobs"/&gt;
 *         &lt;element ref="{}add_order"/&gt;
 *         &lt;element name="check_folders" type="{}check_folders"/&gt;
 *         &lt;element ref="{}kill_task"/&gt;
 *         &lt;element ref="{}licence.use"/&gt;
 *         &lt;element name="lock" type="{}lock"/&gt;
 *         &lt;element name="lock.remove" type="{}lock.remove"/&gt;
 *         &lt;element ref="{}modify_job"/&gt;
 *         &lt;element ref="{}modify_hot_folder"/&gt;
 *         &lt;element ref="{}modify_order"/&gt;
 *         &lt;element ref="{}modify_spooler"/&gt;
 *         &lt;element ref="{}job.why"/&gt;
 *         &lt;element ref="{}job_chain.modify"/&gt;
 *         &lt;element ref="{}job_chain_node.modify"/&gt;
 *         &lt;element name="order" type="{}order"/&gt;
 *         &lt;element ref="{}plugin.command"/&gt;
 *         &lt;element name="process_class" type="{}process_class"/&gt;
 *         &lt;element name="process_class.remove" type="{}process_class.remove"/&gt;
 *         &lt;element ref="{}register_remote_scheduler"/&gt;
 *         &lt;element ref="{}remove_job_chain"/&gt;
 *         &lt;element ref="{}remove_order"/&gt;
 *         &lt;element name="run_time" type="{}run_time"/&gt;
 *         &lt;element ref="{}scheduler_log.log_categories.reset"/&gt;
 *         &lt;element ref="{}scheduler_log.log_categories.set"/&gt;
 *         &lt;element ref="{}scheduler_log.log_categories.show"/&gt;
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
 *         &lt;element ref="{}remote_scheduler.start_remote_task"/&gt;
 *         &lt;element ref="{}remote_scheduler.remote_task.close"/&gt;
 *         &lt;element ref="{}terminate"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="on_exit_code"&gt;
 *         &lt;simpleType&gt;
 *           &lt;union&gt;
 *             &lt;simpleType&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                 &lt;enumeration value="success"/&gt;
 *                 &lt;enumeration value="error"/&gt;
 *                 &lt;enumeration value="signal"/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/simpleType&gt;
 *             &lt;simpleType&gt;
 *               &lt;list&gt;
 *                 &lt;simpleType&gt;
 *                   &lt;union memberTypes=" {http://www.w3.org/2001/XMLSchema}integer {}Unix_signal_name"&gt;
 *                   &lt;/union&gt;
 *                 &lt;/simpleType&gt;
 *               &lt;/list&gt;
 *             &lt;/simpleType&gt;
 *           &lt;/union&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Commands", propOrder = {
    "addJobsOrAddOrderOrCheckFolders"
})
public class Commands {

    @XmlElementRefs({
        @XmlElementRef(name = "check_folders", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "licence.use", type = LicenceUse.class, required = false),
        @XmlElementRef(name = "lock.remove", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "show_history", type = ShowHistory.class, required = false),
        @XmlElementRef(name = "show_job_chain", type = ShowJobChain.class, required = false),
        @XmlElementRef(name = "lock", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "job_chain_node.modify", type = JobChainNodeModify.class, required = false),
        @XmlElementRef(name = "subsystem.show", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "order", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "modify_spooler", type = ModifySpooler.class, required = false),
        @XmlElementRef(name = "terminate", type = Terminate.class, required = false),
        @XmlElementRef(name = "show_state", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "start_job", type = StartJob.class, required = false),
        @XmlElementRef(name = "show_order", type = ShowOrder.class, required = false),
        @XmlElementRef(name = "modify_hot_folder", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "modify_order", type = ModifyOrder.class, required = false),
        @XmlElementRef(name = "show_job_chains", type = ShowJobChains.class, required = false),
        @XmlElementRef(name = "add_order", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "remove_order", type = RemoveOrder.class, required = false),
        @XmlElementRef(name = "show_calendar", type = ShowCalendar.class, required = false),
        @XmlElementRef(name = "scheduler_log.log_categories.set", type = SchedulerLogLogCategoriesSet.class, required = false),
        @XmlElementRef(name = "service_request", type = ServiceRequest.class, required = false),
        @XmlElementRef(name = "show_jobs", type = ShowJobs.class, required = false),
        @XmlElementRef(name = "show_schedulers", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "job.why", type = JobWhy.class, required = false),
        @XmlElementRef(name = "plugin.command", type = PluginCommand.class, required = false),
        @XmlElementRef(name = "show_task", type = ShowTask.class, required = false),
        @XmlElementRef(name = "register_remote_scheduler", type = RegisterRemoteScheduler.class, required = false),
        @XmlElementRef(name = "s", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "scheduler_log.log_categories.show", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "modify_job", type = ModifyJob.class, required = false),
        @XmlElementRef(name = "process_class", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "run_time", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "kill_task", type = KillTask.class, required = false),
        @XmlElementRef(name = "show_job", type = ShowJob.class, required = false),
        @XmlElementRef(name = "remote_scheduler.start_remote_task", type = RemoteSchedulerStartRemoteTask.class, required = false),
        @XmlElementRef(name = "remove_job_chain", type = RemoveJobChain.class, required = false),
        @XmlElementRef(name = "scheduler_log.log_categories.reset", type = SchedulerLogLogCategoriesReset.class, required = false),
        @XmlElementRef(name = "remote_scheduler.remote_task.close", type = RemoteSchedulerRemoteTaskClose.class, required = false),
        @XmlElementRef(name = "job_chain.modify", type = JobChainModify.class, required = false),
        @XmlElementRef(name = "add_jobs", type = AddJobs.class, required = false),
        @XmlElementRef(name = "process_class.remove", type = JAXBElement.class, required = false)
    })
    protected List<Object> addJobsOrAddOrderOrCheckFolders;
    @XmlAttribute(name = "on_exit_code")
    protected List<String> onExitCode;

    /**
     * Gets the value of the addJobsOrAddOrderOrCheckFolders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addJobsOrAddOrderOrCheckFolders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddJobsOrAddOrderOrCheckFolders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CheckFolders }{@code >}
     * {@link LicenceUse }
     * {@link JAXBElement }{@code <}{@link LockRemove }{@code >}
     * {@link ShowHistory }
     * {@link ShowJobChain }
     * {@link JAXBElement }{@code <}{@link Lock }{@code >}
     * {@link JobChainNodeModify }
     * {@link JAXBElement }{@code <}{@link SubsystemShow }{@code >}
     * {@link JAXBElement }{@code <}{@link Order }{@code >}
     * {@link ModifySpooler }
     * {@link Terminate }
     * {@link JAXBElement }{@code <}{@link ShowState }{@code >}
     * {@link StartJob }
     * {@link ShowOrder }
     * {@link JAXBElement }{@code <}{@link ModifyHotFolder }{@code >}
     * {@link ModifyOrder }
     * {@link ShowJobChains }
     * {@link JAXBElement }{@code <}{@link Order }{@code >}
     * {@link RemoveOrder }
     * {@link ShowCalendar }
     * {@link SchedulerLogLogCategoriesSet }
     * {@link ServiceRequest }
     * {@link ShowJobs }
     * {@link JAXBElement }{@code <}{@link ShowState }{@code >}
     * {@link JobWhy }
     * {@link PluginCommand }
     * {@link ShowTask }
     * {@link RegisterRemoteScheduler }
     * {@link JAXBElement }{@code <}{@link ShowState }{@code >}
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * {@link ModifyJob }
     * {@link JAXBElement }{@code <}{@link ProcessClass }{@code >}
     * {@link JAXBElement }{@code <}{@link RunTime }{@code >}
     * {@link KillTask }
     * {@link ShowJob }
     * {@link RemoteSchedulerStartRemoteTask }
     * {@link RemoveJobChain }
     * {@link SchedulerLogLogCategoriesReset }
     * {@link RemoteSchedulerRemoteTaskClose }
     * {@link JobChainModify }
     * {@link AddJobs }
     * {@link JAXBElement }{@code <}{@link ProcessClassRemove }{@code >}
     * 
     * 
     */
    public List<Object> getAddJobsOrAddOrderOrCheckFolders() {
        if (addJobsOrAddOrderOrCheckFolders == null) {
            addJobsOrAddOrderOrCheckFolders = new ArrayList<Object>();
        }
        return this.addJobsOrAddOrderOrCheckFolders;
    }

    /**
     * Gets the value of the onExitCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the onExitCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOnExitCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOnExitCode() {
        if (onExitCode == null) {
            onExitCode = new ArrayList<String>();
        }
        return this.onExitCode;
    }

}
