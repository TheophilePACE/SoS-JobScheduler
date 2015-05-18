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
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence&gt;
 *         &lt;element ref="{}params" minOccurs="0"/&gt;
 *         &lt;element name="environment" type="{}environment" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="job" use="required" type="{}Path" /&gt;
 *       &lt;attribute name="name" type="{}Name" /&gt;
 *       &lt;attribute name="at" type="{}Date_time_with_now_or_period" /&gt;
 *       &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="web_service" type="{}Name" /&gt;
 *       &lt;attribute name="force" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "params",
    "environment"
})
@XmlRootElement(name = "start_job")
public class StartJob {

    protected Params params;
    protected Environment environment;
    @XmlAttribute(name = "job", required = true)
    protected String job;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "at")
    protected String at;
    @XmlAttribute(name = "after")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger after;
    @XmlAttribute(name = "web_service")
    protected String webService;
    @XmlAttribute(name = "force")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String force;

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
     * Obtient la valeur de la propri�t� environment.
     * 
     * @return
     *     possible object is
     *     {@link Environment }
     *     
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * D�finit la valeur de la propri�t� environment.
     * 
     * @param value
     *     allowed object is
     *     {@link Environment }
     *     
     */
    public void setEnvironment(Environment value) {
        this.environment = value;
    }

    /**
     * Obtient la valeur de la propri�t� job.
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
     * D�finit la valeur de la propri�t� job.
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
     * Obtient la valeur de la propri�t� at.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAt() {
        return at;
    }

    /**
     * D�finit la valeur de la propri�t� at.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAt(String value) {
        this.at = value;
    }

    /**
     * Obtient la valeur de la propri�t� after.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAfter() {
        return after;
    }

    /**
     * D�finit la valeur de la propri�t� after.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAfter(BigInteger value) {
        this.after = value;
    }

    /**
     * Obtient la valeur de la propri�t� webService.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebService() {
        return webService;
    }

    /**
     * D�finit la valeur de la propri�t� webService.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebService(String value) {
        this.webService = value;
    }

    /**
     * Obtient la valeur de la propri�t� force.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForce() {
        return force;
    }

    /**
     * D�finit la valeur de la propri�t� force.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForce(String value) {
        this.force = value;
    }

}
