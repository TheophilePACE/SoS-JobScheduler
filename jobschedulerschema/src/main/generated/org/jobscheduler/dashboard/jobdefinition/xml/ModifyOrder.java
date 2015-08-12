//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}params" minOccurs="0"/>
 *         &lt;element name="xml_payload" type="{}Xml_payload" minOccurs="0"/>
 *         &lt;element name="run_time" type="{}run_time" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="job_chain" use="required" type="{}Path" />
 *       &lt;attribute name="order" use="required" type="{}Order_id" />
 *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="state" type="{}String" />
 *       &lt;attribute name="setback" type="{}No" />
 *       &lt;attribute name="suspended" type="{}Yes_no" />
 *       &lt;attribute name="at" type="{}Date_time_with_now" />
 *       &lt;attribute name="end_state" type="{}String" />
 *       &lt;attribute name="action">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="reset"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
    "xmlPayload",
    "runTime"
})
@XmlRootElement(name = "modify_order")
public class ModifyOrder {

    protected Params params;
    @XmlElement(name = "xml_payload")
    protected XmlPayload xmlPayload;
    @XmlElement(name = "run_time")
    protected RunTime runTime;
    @XmlAttribute(name = "job_chain", required = true)
    protected String jobChain;
    @XmlAttribute(name = "order", required = true)
    protected String order;
    @XmlAttribute(name = "priority")
    protected BigInteger priority;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "setback")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String setback;
    @XmlAttribute(name = "suspended")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String suspended;
    @XmlAttribute(name = "at")
    protected String at;
    @XmlAttribute(name = "end_state")
    protected String endState;
    @XmlAttribute(name = "action")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String action;

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
     * Obtient la valeur de la propriété xmlPayload.
     * 
     * @return
     *     possible object is
     *     {@link XmlPayload }
     *     
     */
    public XmlPayload getXmlPayload() {
        return xmlPayload;
    }

    /**
     * Définit la valeur de la propriété xmlPayload.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPayload }
     *     
     */
    public void setXmlPayload(XmlPayload value) {
        this.xmlPayload = value;
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
     * Obtient la valeur de la propriété jobChain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobChain() {
        return jobChain;
    }

    /**
     * Définit la valeur de la propriété jobChain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobChain(String value) {
        this.jobChain = value;
    }

    /**
     * Obtient la valeur de la propriété order.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrder() {
        return order;
    }

    /**
     * Définit la valeur de la propriété order.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrder(String value) {
        this.order = value;
    }

    /**
     * Obtient la valeur de la propriété priority.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPriority() {
        return priority;
    }

    /**
     * Définit la valeur de la propriété priority.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPriority(BigInteger value) {
        this.priority = value;
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
     * Obtient la valeur de la propriété setback.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetback() {
        return setback;
    }

    /**
     * Définit la valeur de la propriété setback.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetback(String value) {
        this.setback = value;
    }

    /**
     * Obtient la valeur de la propriété suspended.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuspended() {
        return suspended;
    }

    /**
     * Définit la valeur de la propriété suspended.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuspended(String value) {
        this.suspended = value;
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
     * Obtient la valeur de la propriété endState.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndState() {
        return endState;
    }

    /**
     * Définit la valeur de la propriété endState.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndState(String value) {
        this.endState = value;
    }

    /**
     * Obtient la valeur de la propriété action.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Définit la valeur de la propriété action.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

}
