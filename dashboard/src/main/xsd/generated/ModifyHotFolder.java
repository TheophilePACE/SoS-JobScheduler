//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour modify_hot_folder complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="modify_hot_folder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{}job"/&gt;
 *         &lt;element ref="{}job_chain"/&gt;
 *         &lt;element ref="{}lock"/&gt;
 *         &lt;element ref="{}order"/&gt;
 *         &lt;element ref="{}process_class"/&gt;
 *         &lt;element ref="{}schedule"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="folder" type="{}Path" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modify_hot_folder", propOrder = {
    "job",
    "jobChain",
    "lock",
    "order",
    "processClass",
    "schedule"
})
public class ModifyHotFolder {

    protected Job job;
    @XmlElement(name = "job_chain")
    protected JobChain jobChain;
    protected Lock lock;
    protected Order order;
    @XmlElement(name = "process_class")
    protected ProcessClass processClass;
    protected RunTime schedule;
    @XmlAttribute(name = "folder")
    protected String folder;

    /**
     * Obtient la valeur de la propri�t� job.
     * 
     * @return
     *     possible object is
     *     {@link Job }
     *     
     */
    public Job getJob() {
        return job;
    }

    /**
     * D�finit la valeur de la propri�t� job.
     * 
     * @param value
     *     allowed object is
     *     {@link Job }
     *     
     */
    public void setJob(Job value) {
        this.job = value;
    }

    /**
     * Obtient la valeur de la propri�t� jobChain.
     * 
     * @return
     *     possible object is
     *     {@link JobChain }
     *     
     */
    public JobChain getJobChain() {
        return jobChain;
    }

    /**
     * D�finit la valeur de la propri�t� jobChain.
     * 
     * @param value
     *     allowed object is
     *     {@link JobChain }
     *     
     */
    public void setJobChain(JobChain value) {
        this.jobChain = value;
    }

    /**
     * Obtient la valeur de la propri�t� lock.
     * 
     * @return
     *     possible object is
     *     {@link Lock }
     *     
     */
    public Lock getLock() {
        return lock;
    }

    /**
     * D�finit la valeur de la propri�t� lock.
     * 
     * @param value
     *     allowed object is
     *     {@link Lock }
     *     
     */
    public void setLock(Lock value) {
        this.lock = value;
    }

    /**
     * Obtient la valeur de la propri�t� order.
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getOrder() {
        return order;
    }

    /**
     * D�finit la valeur de la propri�t� order.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setOrder(Order value) {
        this.order = value;
    }

    /**
     * Obtient la valeur de la propri�t� processClass.
     * 
     * @return
     *     possible object is
     *     {@link ProcessClass }
     *     
     */
    public ProcessClass getProcessClass() {
        return processClass;
    }

    /**
     * D�finit la valeur de la propri�t� processClass.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessClass }
     *     
     */
    public void setProcessClass(ProcessClass value) {
        this.processClass = value;
    }

    /**
     * Obtient la valeur de la propri�t� schedule.
     * 
     * @return
     *     possible object is
     *     {@link RunTime }
     *     
     */
    public RunTime getSchedule() {
        return schedule;
    }

    /**
     * D�finit la valeur de la propri�t� schedule.
     * 
     * @param value
     *     allowed object is
     *     {@link RunTime }
     *     
     */
    public void setSchedule(RunTime value) {
        this.schedule = value;
    }

    /**
     * Obtient la valeur de la propri�t� folder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolder() {
        return folder;
    }

    /**
     * D�finit la valeur de la propri�t� folder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolder(String value) {
        this.folder = value;
    }

}
