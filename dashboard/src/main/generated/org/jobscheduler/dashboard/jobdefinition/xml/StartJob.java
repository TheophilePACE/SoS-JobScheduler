//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}params" minOccurs="0"/>
 *         &lt;element name="environment" type="{}environment" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="job" use="required" type="{}Path" />
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="at" type="{}Date_time_with_now_or_period" />
 *       &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="web_service" type="{}Name" />
 *       &lt;attribute name="force" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Obtient la valeur de la propriété environment.
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
     * Définit la valeur de la propriété environment.
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
     * Obtient la valeur de la propriété at.
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
     * Définit la valeur de la propriété at.
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
     * Obtient la valeur de la propriété after.
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
     * Définit la valeur de la propriété after.
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
     * Obtient la valeur de la propriété webService.
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
     * Définit la valeur de la propriété webService.
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
     * Obtient la valeur de la propriété force.
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
     * Définit la valeur de la propriété force.
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
