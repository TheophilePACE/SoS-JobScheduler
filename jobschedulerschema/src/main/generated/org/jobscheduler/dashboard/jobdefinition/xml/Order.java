//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * <p>Classe Java pour order complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="order">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}extensions" minOccurs="0"/>
 *         &lt;element ref="{}params" minOccurs="0"/>
 *         &lt;element name="run_time" type="{}run_time" minOccurs="0"/>
 *         &lt;element name="payload" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any processContents='skip'/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="xml_payload" type="{}Xml_payload" minOccurs="0"/>
 *         &lt;element name="process" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="environment" type="{}environment" minOccurs="0"/>
 *                   &lt;element ref="{}params"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="file" use="required" type="{}File" />
 *                 &lt;attribute name="param" type="{}String" />
 *                 &lt;attribute name="state" type="{}String" />
 *                 &lt;attribute name="log_file" type="{}File" />
 *                 &lt;attribute name="ignore_error" type="{}Yes_no" />
 *                 &lt;attribute name="ignore_signal" type="{}Yes_no" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="job_chain" type="{}Path" />
 *       &lt;attribute name="id" type="{}Order_id" />
 *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="state" type="{}String" />
 *       &lt;attribute name="end_state" type="{}String" />
 *       &lt;attribute name="web_service" type="{}Name" />
 *       &lt;attribute name="replace" type="{}Yes_no" />
 *       &lt;attribute name="at" type="{}Date_time_with_now" />
 *       &lt;attribute name="suspended" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "order", propOrder = {
    "extensions",
    "params",
    "runTime",
    "payload",
    "xmlPayload",
    "process"
})
public class Order {

    protected Extensions extensions;
    protected Params params;
    @XmlElement(name = "run_time")
    protected RunTime runTime;
    protected Order.Payload payload;
    @XmlElement(name = "xml_payload")
    protected XmlPayload xmlPayload;
    protected List<Order.Process> process;
    @XmlAttribute(name = "job_chain")
    protected String jobChain;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "priority")
    protected BigInteger priority;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "end_state")
    protected String endState;
    @XmlAttribute(name = "web_service")
    protected String webService;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;
    @XmlAttribute(name = "at")
    protected String at;
    @XmlAttribute(name = "suspended")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String suspended;

    /**
     * Obtient la valeur de la propriété extensions.
     * 
     * @return
     *     possible object is
     *     {@link Extensions }
     *     
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * Définit la valeur de la propriété extensions.
     * 
     * @param value
     *     allowed object is
     *     {@link Extensions }
     *     
     */
    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

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
     * Obtient la valeur de la propriété payload.
     * 
     * @return
     *     possible object is
     *     {@link Order.Payload }
     *     
     */
    public Order.Payload getPayload() {
        return payload;
    }

    /**
     * Définit la valeur de la propriété payload.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Payload }
     *     
     */
    public void setPayload(Order.Payload value) {
        this.payload = value;
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
     * Gets the value of the process property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the process property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Order.Process }
     * 
     * 
     */
    public List<Order.Process> getProcess() {
        if (process == null) {
            process = new ArrayList<Order.Process>();
        }
        return this.process;
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
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;any processContents='skip'/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class Payload {

        @XmlAnyElement
        protected Element any;

        /**
         * Obtient la valeur de la propriété any.
         * 
         * @return
         *     possible object is
         *     {@link Element }
         *     
         */
        public Element getAny() {
            return any;
        }

        /**
         * Définit la valeur de la propriété any.
         * 
         * @param value
         *     allowed object is
         *     {@link Element }
         *     
         */
        public void setAny(Element value) {
            this.any = value;
        }

    }


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
     *         &lt;element name="environment" type="{}environment" minOccurs="0"/>
     *         &lt;element ref="{}params"/>
     *       &lt;/sequence>
     *       &lt;attribute name="file" use="required" type="{}File" />
     *       &lt;attribute name="param" type="{}String" />
     *       &lt;attribute name="state" type="{}String" />
     *       &lt;attribute name="log_file" type="{}File" />
     *       &lt;attribute name="ignore_error" type="{}Yes_no" />
     *       &lt;attribute name="ignore_signal" type="{}Yes_no" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "environment",
        "params"
    })
    public static class Process {

        protected Environment environment;
        @XmlElement(required = true)
        protected Params params;
        @XmlAttribute(name = "file", required = true)
        protected String file;
        @XmlAttribute(name = "param")
        protected String param;
        @XmlAttribute(name = "state")
        protected String state;
        @XmlAttribute(name = "log_file")
        protected String logFile;
        @XmlAttribute(name = "ignore_error")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String ignoreError;
        @XmlAttribute(name = "ignore_signal")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String ignoreSignal;

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
         * Obtient la valeur de la propriété file.
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
         * Définit la valeur de la propriété file.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFile(String value) {
            this.file = value;
        }

        /**
         * Obtient la valeur de la propriété param.
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
         * Définit la valeur de la propriété param.
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
         * Obtient la valeur de la propriété logFile.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLogFile() {
            return logFile;
        }

        /**
         * Définit la valeur de la propriété logFile.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLogFile(String value) {
            this.logFile = value;
        }

        /**
         * Obtient la valeur de la propriété ignoreError.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIgnoreError() {
            return ignoreError;
        }

        /**
         * Définit la valeur de la propriété ignoreError.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIgnoreError(String value) {
            this.ignoreError = value;
        }

        /**
         * Obtient la valeur de la propriété ignoreSignal.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIgnoreSignal() {
            return ignoreSignal;
        }

        /**
         * Définit la valeur de la propriété ignoreSignal.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIgnoreSignal(String value) {
            this.ignoreSignal = value;
        }

    }

}
