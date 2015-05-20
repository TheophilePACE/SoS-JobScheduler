//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="all_schedulers" type="{}Yes_no" /&gt;
 *       &lt;attribute name="restart" type="{}Yes_no" /&gt;
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="continue_exclusive_operation" type="{}Yes_no" /&gt;
 *       &lt;attribute name="cluster_member_id" type="{}String" /&gt;
 *       &lt;attribute name="delete_dead_entry" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "terminate")
public class Terminate {

    @XmlAttribute(name = "all_schedulers")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String allSchedulers;
    @XmlAttribute(name = "restart")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String restart;
    @XmlAttribute(name = "timeout")
    protected BigInteger timeout;
    @XmlAttribute(name = "continue_exclusive_operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String continueExclusiveOperation;
    @XmlAttribute(name = "cluster_member_id")
    protected String clusterMemberId;
    @XmlAttribute(name = "delete_dead_entry")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deleteDeadEntry;

    /**
     * Obtient la valeur de la propri�t� allSchedulers.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllSchedulers() {
        return allSchedulers;
    }

    /**
     * D�finit la valeur de la propri�t� allSchedulers.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllSchedulers(String value) {
        this.allSchedulers = value;
    }

    /**
     * Obtient la valeur de la propri�t� restart.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestart() {
        return restart;
    }

    /**
     * D�finit la valeur de la propri�t� restart.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestart(String value) {
        this.restart = value;
    }

    /**
     * Obtient la valeur de la propri�t� timeout.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimeout() {
        return timeout;
    }

    /**
     * D�finit la valeur de la propri�t� timeout.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimeout(BigInteger value) {
        this.timeout = value;
    }

    /**
     * Obtient la valeur de la propri�t� continueExclusiveOperation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinueExclusiveOperation() {
        return continueExclusiveOperation;
    }

    /**
     * D�finit la valeur de la propri�t� continueExclusiveOperation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinueExclusiveOperation(String value) {
        this.continueExclusiveOperation = value;
    }

    /**
     * Obtient la valeur de la propri�t� clusterMemberId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterMemberId() {
        return clusterMemberId;
    }

    /**
     * D�finit la valeur de la propri�t� clusterMemberId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterMemberId(String value) {
        this.clusterMemberId = value;
    }

    /**
     * Obtient la valeur de la propri�t� deleteDeadEntry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteDeadEntry() {
        return deleteDeadEntry;
    }

    /**
     * D�finit la valeur de la propri�t� deleteDeadEntry.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteDeadEntry(String value) {
        this.deleteDeadEntry = value;
    }

}
