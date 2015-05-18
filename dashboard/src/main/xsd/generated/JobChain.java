//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="job_chain_node.job_chain" type="{}job_chain_node.job_chain"/&gt;
 *             &lt;choice maxOccurs="unbounded"&gt;
 *               &lt;element name="job_chain_node.job_chain" type="{}job_chain_node.job_chain"/&gt;
 *               &lt;element name="job_chain_node.end" type="{}job_chain_node.end"/&gt;
 *             &lt;/choice&gt;
 *           &lt;/sequence&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="file_order_source" maxOccurs="unbounded" minOccurs="0"&gt;
 *               &lt;complexType&gt;
 *                 &lt;complexContent&gt;
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                     &lt;attribute name="directory" use="required" type="{}String" /&gt;
 *                     &lt;attribute name="regex" type="{}String" /&gt;
 *                     &lt;attribute name="delay_after_error" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                     &lt;attribute name="repeat"&gt;
 *                       &lt;simpleType&gt;
 *                         &lt;union&gt;
 *                           &lt;simpleType&gt;
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                               &lt;enumeration value="no"/&gt;
 *                             &lt;/restriction&gt;
 *                           &lt;/simpleType&gt;
 *                           &lt;simpleType&gt;
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *                             &lt;/restriction&gt;
 *                           &lt;/simpleType&gt;
 *                         &lt;/union&gt;
 *                       &lt;/simpleType&gt;
 *                     &lt;/attribute&gt;
 *                     &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                     &lt;attribute name="next_state" type="{}String" /&gt;
 *                   &lt;/restriction&gt;
 *                 &lt;/complexContent&gt;
 *               &lt;/complexType&gt;
 *             &lt;/element&gt;
 *             &lt;choice maxOccurs="unbounded"&gt;
 *               &lt;element name="job_chain_node"&gt;
 *                 &lt;complexType&gt;
 *                   &lt;complexContent&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                       &lt;attribute name="state" use="required" type="{}String" /&gt;
 *                       &lt;attribute name="job" type="{}Path" /&gt;
 *                       &lt;attribute name="next_state" type="{}String" /&gt;
 *                       &lt;attribute name="error_state" type="{}String" /&gt;
 *                       &lt;attribute name="on_error"&gt;
 *                         &lt;simpleType&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                             &lt;enumeration value="setback"/&gt;
 *                             &lt;enumeration value="suspend"/&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/simpleType&gt;
 *                       &lt;/attribute&gt;
 *                       &lt;attribute name="suspend" type="{}Yes_no" /&gt;
 *                       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/complexContent&gt;
 *                 &lt;/complexType&gt;
 *               &lt;/element&gt;
 *               &lt;element name="file_order_sink"&gt;
 *                 &lt;complexType&gt;
 *                   &lt;complexContent&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                       &lt;attribute name="state" use="required" type="{}String" /&gt;
 *                       &lt;attribute name="remove" type="{}Yes_no" /&gt;
 *                       &lt;attribute name="move_to" type="{}File" /&gt;
 *                       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/complexContent&gt;
 *                 &lt;/complexType&gt;
 *               &lt;/element&gt;
 *               &lt;element name="job_chain_node.end" type="{}job_chain_node.end"/&gt;
 *             &lt;/choice&gt;
 *           &lt;/sequence&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{}Name" /&gt;
 *       &lt;attribute name="replace" type="{}Yes_no" /&gt;
 *       &lt;attribute name="visible" type="{}Yes_no_never" /&gt;
 *       &lt;attribute name="orders_recoverable" type="{}Yes_no" /&gt;
 *       &lt;attribute name="distributed" type="{}Yes_no" /&gt;
 *       &lt;attribute name="title" type="{}String" /&gt;
 *       &lt;attribute name="max_orders" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "note",
    "jobChainNodeJobChain",
    "jobChainNodeJobChainOrJobChainNodeEnd",
    "fileOrderSource",
    "jobChainNodeOrFileOrderSinkOrJobChainNodeEnd"
})
@XmlRootElement(name = "job_chain")
public class JobChain {

    protected List<Object> note;
    @XmlElement(name = "job_chain_node.job_chain")
    protected JobChainNodeJobChain jobChainNodeJobChain;
    @XmlElements({
        @XmlElement(name = "job_chain_node.job_chain", type = JobChainNodeJobChain.class),
        @XmlElement(name = "job_chain_node.end", type = JobChainNodeEnd.class)
    })
    protected List<Object> jobChainNodeJobChainOrJobChainNodeEnd;
    @XmlElement(name = "file_order_source")
    protected List<JobChain.FileOrderSource> fileOrderSource;
    @XmlElements({
        @XmlElement(name = "job_chain_node", type = JobChain.JobChainNode.class),
        @XmlElement(name = "file_order_sink", type = JobChain.FileOrderSink.class),
        @XmlElement(name = "job_chain_node.end", type = JobChainNodeEnd.class)
    })
    protected List<Object> jobChainNodeOrFileOrderSinkOrJobChainNodeEnd;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;
    @XmlAttribute(name = "visible")
    protected String visible;
    @XmlAttribute(name = "orders_recoverable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ordersRecoverable;
    @XmlAttribute(name = "distributed")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String distributed;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "max_orders")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxOrders;

    /**
     * Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getNote() {
        if (note == null) {
            note = new ArrayList<Object>();
        }
        return this.note;
    }

    /**
     * Obtient la valeur de la propriété jobChainNodeJobChain.
     * 
     * @return
     *     possible object is
     *     {@link JobChainNodeJobChain }
     *     
     */
    public JobChainNodeJobChain getJobChainNodeJobChain() {
        return jobChainNodeJobChain;
    }

    /**
     * Définit la valeur de la propriété jobChainNodeJobChain.
     * 
     * @param value
     *     allowed object is
     *     {@link JobChainNodeJobChain }
     *     
     */
    public void setJobChainNodeJobChain(JobChainNodeJobChain value) {
        this.jobChainNodeJobChain = value;
    }

    /**
     * Gets the value of the jobChainNodeJobChainOrJobChainNodeEnd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobChainNodeJobChainOrJobChainNodeEnd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobChainNodeJobChainOrJobChainNodeEnd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChainNodeJobChain }
     * {@link JobChainNodeEnd }
     * 
     * 
     */
    public List<Object> getJobChainNodeJobChainOrJobChainNodeEnd() {
        if (jobChainNodeJobChainOrJobChainNodeEnd == null) {
            jobChainNodeJobChainOrJobChainNodeEnd = new ArrayList<Object>();
        }
        return this.jobChainNodeJobChainOrJobChainNodeEnd;
    }

    /**
     * Gets the value of the fileOrderSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileOrderSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileOrderSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChain.FileOrderSource }
     * 
     * 
     */
    public List<JobChain.FileOrderSource> getFileOrderSource() {
        if (fileOrderSource == null) {
            fileOrderSource = new ArrayList<JobChain.FileOrderSource>();
        }
        return this.fileOrderSource;
    }

    /**
     * Gets the value of the jobChainNodeOrFileOrderSinkOrJobChainNodeEnd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobChainNodeOrFileOrderSinkOrJobChainNodeEnd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChain.JobChainNode }
     * {@link JobChain.FileOrderSink }
     * {@link JobChainNodeEnd }
     * 
     * 
     */
    public List<Object> getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd() {
        if (jobChainNodeOrFileOrderSinkOrJobChainNodeEnd == null) {
            jobChainNodeOrFileOrderSinkOrJobChainNodeEnd = new ArrayList<Object>();
        }
        return this.jobChainNodeOrFileOrderSinkOrJobChainNodeEnd;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propriété replace.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplace() {
        return replace;
    }

    /**
     * Définit la valeur de la propriété replace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplace(String value) {
        this.replace = value;
    }

    /**
     * Obtient la valeur de la propriété visible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisible() {
        return visible;
    }

    /**
     * Définit la valeur de la propriété visible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisible(String value) {
        this.visible = value;
    }

    /**
     * Obtient la valeur de la propriété ordersRecoverable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdersRecoverable() {
        return ordersRecoverable;
    }

    /**
     * Définit la valeur de la propriété ordersRecoverable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdersRecoverable(String value) {
        this.ordersRecoverable = value;
    }

    /**
     * Obtient la valeur de la propriété distributed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributed() {
        return distributed;
    }

    /**
     * Définit la valeur de la propriété distributed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributed(String value) {
        this.distributed = value;
    }

    /**
     * Obtient la valeur de la propriété title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit la valeur de la propriété title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtient la valeur de la propriété maxOrders.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOrders() {
        return maxOrders;
    }

    /**
     * Définit la valeur de la propriété maxOrders.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOrders(BigInteger value) {
        this.maxOrders = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="state" use="required" type="{}String" /&gt;
     *       &lt;attribute name="remove" type="{}Yes_no" /&gt;
     *       &lt;attribute name="move_to" type="{}File" /&gt;
     *       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FileOrderSink {

        @XmlAttribute(name = "state", required = true)
        protected String state;
        @XmlAttribute(name = "remove")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String remove;
        @XmlAttribute(name = "move_to")
        protected String moveTo;
        @XmlAttribute(name = "delay")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger delay;

        /**
         * Obtient la valeur de la propriété state.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Définit la valeur de la propriété state.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Obtient la valeur de la propriété remove.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemove() {
            return remove;
        }

        /**
         * Définit la valeur de la propriété remove.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemove(String value) {
            this.remove = value;
        }

        /**
         * Obtient la valeur de la propriété moveTo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMoveTo() {
            return moveTo;
        }

        /**
         * Définit la valeur de la propriété moveTo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMoveTo(String value) {
            this.moveTo = value;
        }

        /**
         * Obtient la valeur de la propriété delay.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelay() {
            return delay;
        }

        /**
         * Définit la valeur de la propriété delay.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelay(BigInteger value) {
            this.delay = value;
        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="directory" use="required" type="{}String" /&gt;
     *       &lt;attribute name="regex" type="{}String" /&gt;
     *       &lt;attribute name="delay_after_error" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *       &lt;attribute name="repeat"&gt;
     *         &lt;simpleType&gt;
     *           &lt;union&gt;
     *             &lt;simpleType&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
     *                 &lt;enumeration value="no"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/simpleType&gt;
     *             &lt;simpleType&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
     *               &lt;/restriction&gt;
     *             &lt;/simpleType&gt;
     *           &lt;/union&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *       &lt;attribute name="next_state" type="{}String" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FileOrderSource {

        @XmlAttribute(name = "directory", required = true)
        protected String directory;
        @XmlAttribute(name = "regex")
        protected String regex;
        @XmlAttribute(name = "delay_after_error")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger delayAfterError;
        @XmlAttribute(name = "repeat")
        protected String repeat;
        @XmlAttribute(name = "max")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger max;
        @XmlAttribute(name = "next_state")
        protected String nextState;

        /**
         * Obtient la valeur de la propriété directory.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDirectory() {
            return directory;
        }

        /**
         * Définit la valeur de la propriété directory.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDirectory(String value) {
            this.directory = value;
        }

        /**
         * Obtient la valeur de la propriété regex.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegex() {
            return regex;
        }

        /**
         * Définit la valeur de la propriété regex.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegex(String value) {
            this.regex = value;
        }

        /**
         * Obtient la valeur de la propriété delayAfterError.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelayAfterError() {
            return delayAfterError;
        }

        /**
         * Définit la valeur de la propriété delayAfterError.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelayAfterError(BigInteger value) {
            this.delayAfterError = value;
        }

        /**
         * Obtient la valeur de la propriété repeat.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRepeat() {
            return repeat;
        }

        /**
         * Définit la valeur de la propriété repeat.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRepeat(String value) {
            this.repeat = value;
        }

        /**
         * Obtient la valeur de la propriété max.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMax() {
            return max;
        }

        /**
         * Définit la valeur de la propriété max.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMax(BigInteger value) {
            this.max = value;
        }

        /**
         * Obtient la valeur de la propriété nextState.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNextState() {
            return nextState;
        }

        /**
         * Définit la valeur de la propriété nextState.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNextState(String value) {
            this.nextState = value;
        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="state" use="required" type="{}String" /&gt;
     *       &lt;attribute name="job" type="{}Path" /&gt;
     *       &lt;attribute name="next_state" type="{}String" /&gt;
     *       &lt;attribute name="error_state" type="{}String" /&gt;
     *       &lt;attribute name="on_error"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
     *             &lt;enumeration value="setback"/&gt;
     *             &lt;enumeration value="suspend"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="suspend" type="{}Yes_no" /&gt;
     *       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class JobChainNode {

        @XmlAttribute(name = "state", required = true)
        protected String state;
        @XmlAttribute(name = "job")
        protected String job;
        @XmlAttribute(name = "next_state")
        protected String nextState;
        @XmlAttribute(name = "error_state")
        protected String errorState;
        @XmlAttribute(name = "on_error")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String onError;
        @XmlAttribute(name = "suspend")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String suspend;
        @XmlAttribute(name = "delay")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger delay;

        /**
         * Obtient la valeur de la propriété state.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Définit la valeur de la propriété state.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Obtient la valeur de la propriété job.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJob() {
            return job;
        }

        /**
         * Définit la valeur de la propriété job.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJob(String value) {
            this.job = value;
        }

        /**
         * Obtient la valeur de la propriété nextState.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNextState() {
            return nextState;
        }

        /**
         * Définit la valeur de la propriété nextState.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNextState(String value) {
            this.nextState = value;
        }

        /**
         * Obtient la valeur de la propriété errorState.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrorState() {
            return errorState;
        }

        /**
         * Définit la valeur de la propriété errorState.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrorState(String value) {
            this.errorState = value;
        }

        /**
         * Obtient la valeur de la propriété onError.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOnError() {
            return onError;
        }

        /**
         * Définit la valeur de la propriété onError.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOnError(String value) {
            this.onError = value;
        }

        /**
         * Obtient la valeur de la propriété suspend.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuspend() {
            return suspend;
        }

        /**
         * Définit la valeur de la propriété suspend.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuspend(String value) {
            this.suspend = value;
        }

        /**
         * Obtient la valeur de la propriété delay.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelay() {
            return delay;
        }

        /**
         * Définit la valeur de la propriété delay.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelay(BigInteger value) {
            this.delay = value;
        }

    }

}
