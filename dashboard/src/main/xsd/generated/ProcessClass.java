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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour process_class complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="process_class"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="name" type="{}Name" /&gt;
 *       &lt;attribute name="max_processes" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="remote_scheduler" type="{}String" /&gt;
 *       &lt;attribute name="spooler_id" type="{}Name" /&gt;
 *       &lt;attribute name="replace" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "process_class")
public class ProcessClass {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "max_processes")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxProcesses;
    @XmlAttribute(name = "remote_scheduler")
    protected String remoteScheduler;
    @XmlAttribute(name = "spooler_id")
    protected String spoolerId;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;

    /**
     * Obtient la valeur de la propri�t� name.
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
     * D�finit la valeur de la propri�t� name.
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
     * Obtient la valeur de la propri�t� maxProcesses.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxProcesses() {
        return maxProcesses;
    }

    /**
     * D�finit la valeur de la propri�t� maxProcesses.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxProcesses(BigInteger value) {
        this.maxProcesses = value;
    }

    /**
     * Obtient la valeur de la propri�t� remoteScheduler.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteScheduler() {
        return remoteScheduler;
    }

    /**
     * D�finit la valeur de la propri�t� remoteScheduler.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteScheduler(String value) {
        this.remoteScheduler = value;
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
     * Obtient la valeur de la propri�t� replace.
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
     * D�finit la valeur de la propri�t� replace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplace(String value) {
        this.replace = value;
    }

}
