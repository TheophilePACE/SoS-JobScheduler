//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété allSchedulers.
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
     * Définit la valeur de la propriété allSchedulers.
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
     * Obtient la valeur de la propriété restart.
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
     * Définit la valeur de la propriété restart.
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
     * Obtient la valeur de la propriété timeout.
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
     * Définit la valeur de la propriété timeout.
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
     * Obtient la valeur de la propriété continueExclusiveOperation.
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
     * Définit la valeur de la propriété continueExclusiveOperation.
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
     * Obtient la valeur de la propriété clusterMemberId.
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
     * Définit la valeur de la propriété clusterMemberId.
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
     * Obtient la valeur de la propriété deleteDeadEntry.
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
     * Définit la valeur de la propriété deleteDeadEntry.
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
