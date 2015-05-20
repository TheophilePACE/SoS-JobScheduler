//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
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
 *         &lt;element name="settings" type="{}Job.Settings" minOccurs="0"/>
 *         &lt;element name="description" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}include"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="lock.use" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="lock" use="required" type="{}String" />
 *                 &lt;attribute name="exclusive" type="{}Yes_no" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}params" minOccurs="0"/>
 *         &lt;element name="environment" type="{}environment" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}script"/>
 *           &lt;element name="process">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="environment" type="{}environment" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;attribute name="file" use="required" type="{}File" />
 *                   &lt;attribute name="param" type="{}String" />
 *                   &lt;attribute name="log_file" type="{}File" />
 *                   &lt;attribute name="ignore_error" type="{}Yes_no" />
 *                   &lt;attribute name="ignore_signal" type="{}Yes_no" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="monitor" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}script"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" type="{}Name" />
 *                 &lt;attribute name="ordering" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="start_when_directory_changed" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="directory" use="required" type="{}File" />
 *                 &lt;attribute name="regex" type="{}String" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="delay_after_error" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="error_count" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                 &lt;attribute name="delay" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{}String">
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="delay_order_after_setback" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="setback_count" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                 &lt;attribute name="delay" type="{}Duration" />
 *                 &lt;attribute name="is_maximum" type="{}Yes_no" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="run_time" type="{}run_time" minOccurs="0"/>
 *         &lt;element name="commands" type="{}Commands" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="visible" type="{}Yes_no_never" />
 *       &lt;attribute name="priority" type="{}Process_priority" />
 *       &lt;attribute name="temporary" type="{}Yes_no" />
 *       &lt;attribute name="spooler_id" type="{}Name" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="log_append" type="{}Yes_no" />
 *       &lt;attribute name="order" type="{}Yes_no" />
 *       &lt;attribute name="separate_process" type="{}Yes_no" />
 *       &lt;attribute name="tasks" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="min_tasks" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="timeout" type="{}Duration" />
 *       &lt;attribute name="idle_timeout" type="{}Duration_or_never" />
 *       &lt;attribute name="force_idle_timeout" type="{}Yes_no" />
 *       &lt;attribute name="process_class" type="{}Path" />
 *       &lt;attribute name="mail_xslt_stylesheet" type="{}File" />
 *       &lt;attribute name="java_options" type="{}String" />
 *       &lt;attribute name="stop_on_error" type="{}Yes_no" />
 *       &lt;attribute name="ignore_signals">
 *         &lt;simpleType>
 *           &lt;union>
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                 &lt;enumeration value="all"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *             &lt;simpleType>
 *               &lt;list itemType="{}Unix_signal" />
 *             &lt;/simpleType>
 *           &lt;/union>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="replace" type="{}Yes_no" />
 *       &lt;attribute name="warn_if_longer_than" type="{}Duration_or_percentage" />
 *       &lt;attribute name="warn_if_shorter_than" type="{}Duration_or_percentage" />
 *       &lt;attribute name="enabled" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "settings",
    "description",
    "lockUse",
    "params",
    "environment",
    "script",
    "process",
    "monitor",
    "startWhenDirectoryChanged",
    "delayAfterError",
    "delayOrderAfterSetback",
    "runTime",
    "commands"
})
@XmlRootElement(name = "job")
public class Job {

    protected JobSettings settings;
    protected Job.Description description;
    @XmlElement(name = "lock.use")
    protected List<Job.LockUse> lockUse;
    protected Params params;
    protected Environment environment;
    protected Script script;
    protected Job.Process process;
    protected List<Job.Monitor> monitor;
    @XmlElement(name = "start_when_directory_changed")
    protected List<Job.StartWhenDirectoryChanged> startWhenDirectoryChanged;
    @XmlElement(name = "delay_after_error")
    protected List<Job.DelayAfterError> delayAfterError;
    @XmlElement(name = "delay_order_after_setback")
    protected List<Job.DelayOrderAfterSetback> delayOrderAfterSetback;
    @XmlElement(name = "run_time")
    protected RunTime runTime;
    protected List<Commands> commands;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "visible")
    protected String visible;
    @XmlAttribute(name = "priority")
    protected List<String> priority;
    @XmlAttribute(name = "temporary")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String temporary;
    @XmlAttribute(name = "spooler_id")
    protected String spoolerId;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "log_append")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String logAppend;
    @XmlAttribute(name = "order")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String order;
    @XmlAttribute(name = "separate_process")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String separateProcess;
    @XmlAttribute(name = "tasks")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger tasks;
    @XmlAttribute(name = "min_tasks")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger minTasks;
    @XmlAttribute(name = "timeout")
    protected String timeout;
    @XmlAttribute(name = "idle_timeout")
    protected String idleTimeout;
    @XmlAttribute(name = "force_idle_timeout")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String forceIdleTimeout;
    @XmlAttribute(name = "process_class")
    protected String processClass;
    @XmlAttribute(name = "mail_xslt_stylesheet")
    protected String mailXsltStylesheet;
    @XmlAttribute(name = "java_options")
    protected String javaOptions;
    @XmlAttribute(name = "stop_on_error")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String stopOnError;
    @XmlAttribute(name = "ignore_signals")
    protected List<String> ignoreSignals;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;
    @XmlAttribute(name = "warn_if_longer_than")
    protected String warnIfLongerThan;
    @XmlAttribute(name = "warn_if_shorter_than")
    protected String warnIfShorterThan;
    @XmlAttribute(name = "enabled")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String enabled;

    /**
     * Obtient la valeur de la propriété settings.
     * 
     * @return
     *     possible object is
     *     {@link JobSettings }
     *     
     */
    public JobSettings getSettings() {
        return settings;
    }

    /**
     * Définit la valeur de la propriété settings.
     * 
     * @param value
     *     allowed object is
     *     {@link JobSettings }
     *     
     */
    public void setSettings(JobSettings value) {
        this.settings = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link Job.Description }
     *     
     */
    public Job.Description getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link Job.Description }
     *     
     */
    public void setDescription(Job.Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the lockUse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lockUse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLockUse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job.LockUse }
     * 
     * 
     */
    public List<Job.LockUse> getLockUse() {
        if (lockUse == null) {
            lockUse = new ArrayList<Job.LockUse>();
        }
        return this.lockUse;
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
     * Obtient la valeur de la propriété script.
     * 
     * @return
     *     possible object is
     *     {@link Script }
     *     
     */
    public Script getScript() {
        return script;
    }

    /**
     * Définit la valeur de la propriété script.
     * 
     * @param value
     *     allowed object is
     *     {@link Script }
     *     
     */
    public void setScript(Script value) {
        this.script = value;
    }

    /**
     * Obtient la valeur de la propriété process.
     * 
     * @return
     *     possible object is
     *     {@link Job.Process }
     *     
     */
    public Job.Process getProcess() {
        return process;
    }

    /**
     * Définit la valeur de la propriété process.
     * 
     * @param value
     *     allowed object is
     *     {@link Job.Process }
     *     
     */
    public void setProcess(Job.Process value) {
        this.process = value;
    }

    /**
     * Gets the value of the monitor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job.Monitor }
     * 
     * 
     */
    public List<Job.Monitor> getMonitor() {
        if (monitor == null) {
            monitor = new ArrayList<Job.Monitor>();
        }
        return this.monitor;
    }

    /**
     * Gets the value of the startWhenDirectoryChanged property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the startWhenDirectoryChanged property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStartWhenDirectoryChanged().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job.StartWhenDirectoryChanged }
     * 
     * 
     */
    public List<Job.StartWhenDirectoryChanged> getStartWhenDirectoryChanged() {
        if (startWhenDirectoryChanged == null) {
            startWhenDirectoryChanged = new ArrayList<Job.StartWhenDirectoryChanged>();
        }
        return this.startWhenDirectoryChanged;
    }

    /**
     * Gets the value of the delayAfterError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delayAfterError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelayAfterError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job.DelayAfterError }
     * 
     * 
     */
    public List<Job.DelayAfterError> getDelayAfterError() {
        if (delayAfterError == null) {
            delayAfterError = new ArrayList<Job.DelayAfterError>();
        }
        return this.delayAfterError;
    }

    /**
     * Gets the value of the delayOrderAfterSetback property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delayOrderAfterSetback property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelayOrderAfterSetback().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job.DelayOrderAfterSetback }
     * 
     * 
     */
    public List<Job.DelayOrderAfterSetback> getDelayOrderAfterSetback() {
        if (delayOrderAfterSetback == null) {
            delayOrderAfterSetback = new ArrayList<Job.DelayOrderAfterSetback>();
        }
        return this.delayOrderAfterSetback;
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
     * Gets the value of the commands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Commands }
     * 
     * 
     */
    public List<Commands> getCommands() {
        if (commands == null) {
            commands = new ArrayList<Commands>();
        }
        return this.commands;
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
     * Gets the value of the priority property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priority property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriority().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPriority() {
        if (priority == null) {
            priority = new ArrayList<String>();
        }
        return this.priority;
    }

    /**
     * Obtient la valeur de la propriété temporary.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporary() {
        return temporary;
    }

    /**
     * Définit la valeur de la propriété temporary.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporary(String value) {
        this.temporary = value;
    }

    /**
     * Obtient la valeur de la propriété spoolerId.
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
     * Définit la valeur de la propriété spoolerId.
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
     * Obtient la valeur de la propriété logAppend.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogAppend() {
        return logAppend;
    }

    /**
     * Définit la valeur de la propriété logAppend.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogAppend(String value) {
        this.logAppend = value;
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
     * Obtient la valeur de la propriété separateProcess.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeparateProcess() {
        return separateProcess;
    }

    /**
     * Définit la valeur de la propriété separateProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeparateProcess(String value) {
        this.separateProcess = value;
    }

    /**
     * Obtient la valeur de la propriété tasks.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTasks() {
        return tasks;
    }

    /**
     * Définit la valeur de la propriété tasks.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTasks(BigInteger value) {
        this.tasks = value;
    }

    /**
     * Obtient la valeur de la propriété minTasks.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinTasks() {
        return minTasks;
    }

    /**
     * Définit la valeur de la propriété minTasks.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinTasks(BigInteger value) {
        this.minTasks = value;
    }

    /**
     * Obtient la valeur de la propriété timeout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeout() {
        return timeout;
    }

    /**
     * Définit la valeur de la propriété timeout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeout(String value) {
        this.timeout = value;
    }

    /**
     * Obtient la valeur de la propriété idleTimeout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdleTimeout() {
        return idleTimeout;
    }

    /**
     * Définit la valeur de la propriété idleTimeout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdleTimeout(String value) {
        this.idleTimeout = value;
    }

    /**
     * Obtient la valeur de la propriété forceIdleTimeout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForceIdleTimeout() {
        return forceIdleTimeout;
    }

    /**
     * Définit la valeur de la propriété forceIdleTimeout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForceIdleTimeout(String value) {
        this.forceIdleTimeout = value;
    }

    /**
     * Obtient la valeur de la propriété processClass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessClass() {
        return processClass;
    }

    /**
     * Définit la valeur de la propriété processClass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessClass(String value) {
        this.processClass = value;
    }

    /**
     * Obtient la valeur de la propriété mailXsltStylesheet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailXsltStylesheet() {
        return mailXsltStylesheet;
    }

    /**
     * Définit la valeur de la propriété mailXsltStylesheet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailXsltStylesheet(String value) {
        this.mailXsltStylesheet = value;
    }

    /**
     * Obtient la valeur de la propriété javaOptions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaOptions() {
        return javaOptions;
    }

    /**
     * Définit la valeur de la propriété javaOptions.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaOptions(String value) {
        this.javaOptions = value;
    }

    /**
     * Obtient la valeur de la propriété stopOnError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStopOnError() {
        return stopOnError;
    }

    /**
     * Définit la valeur de la propriété stopOnError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopOnError(String value) {
        this.stopOnError = value;
    }

    /**
     * Gets the value of the ignoreSignals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ignoreSignals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIgnoreSignals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIgnoreSignals() {
        if (ignoreSignals == null) {
            ignoreSignals = new ArrayList<String>();
        }
        return this.ignoreSignals;
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
     * Obtient la valeur de la propriété warnIfLongerThan.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarnIfLongerThan() {
        return warnIfLongerThan;
    }

    /**
     * Définit la valeur de la propriété warnIfLongerThan.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarnIfLongerThan(String value) {
        this.warnIfLongerThan = value;
    }

    /**
     * Obtient la valeur de la propriété warnIfShorterThan.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarnIfShorterThan() {
        return warnIfShorterThan;
    }

    /**
     * Définit la valeur de la propriété warnIfShorterThan.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarnIfShorterThan(String value) {
        this.warnIfShorterThan = value;
    }

    /**
     * Obtient la valeur de la propriété enabled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * Définit la valeur de la propriété enabled.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnabled(String value) {
        this.enabled = value;
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
     *       &lt;attribute name="error_count" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="delay" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{}String">
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
    @XmlType(name = "")
    public static class DelayAfterError {

        @XmlAttribute(name = "error_count", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger errorCount;
        @XmlAttribute(name = "delay", required = true)
        protected String delay;

        /**
         * Obtient la valeur de la propriété errorCount.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getErrorCount() {
            return errorCount;
        }

        /**
         * Définit la valeur de la propriété errorCount.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setErrorCount(BigInteger value) {
            this.errorCount = value;
        }

        /**
         * Obtient la valeur de la propriété delay.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDelay() {
            return delay;
        }

        /**
         * Définit la valeur de la propriété delay.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDelay(String value) {
            this.delay = value;
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
     *       &lt;attribute name="setback_count" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="delay" type="{}Duration" />
     *       &lt;attribute name="is_maximum" type="{}Yes_no" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DelayOrderAfterSetback {

        @XmlAttribute(name = "setback_count", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger setbackCount;
        @XmlAttribute(name = "delay")
        protected String delay;
        @XmlAttribute(name = "is_maximum")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String isMaximum;

        /**
         * Obtient la valeur de la propriété setbackCount.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSetbackCount() {
            return setbackCount;
        }

        /**
         * Définit la valeur de la propriété setbackCount.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSetbackCount(BigInteger value) {
            this.setbackCount = value;
        }

        /**
         * Obtient la valeur de la propriété delay.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDelay() {
            return delay;
        }

        /**
         * Définit la valeur de la propriété delay.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDelay(String value) {
            this.delay = value;
        }

        /**
         * Obtient la valeur de la propriété isMaximum.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsMaximum() {
            return isMaximum;
        }

        /**
         * Définit la valeur de la propriété isMaximum.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsMaximum(String value) {
            this.isMaximum = value;
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
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}include"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class Description {

        @XmlElementRef(name = "include", type = Include.class, required = false)
        @XmlMixed
        protected List<Object> content;

        /**
         * Gets the value of the content property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the content property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * {@link Include }
         * 
         * 
         */
        public List<Object> getContent() {
            if (content == null) {
                content = new ArrayList<Object>();
            }
            return this.content;
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
     *       &lt;attribute name="lock" use="required" type="{}String" />
     *       &lt;attribute name="exclusive" type="{}Yes_no" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class LockUse {

        @XmlAttribute(name = "lock", required = true)
        protected String lock;
        @XmlAttribute(name = "exclusive")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String exclusive;

        /**
         * Obtient la valeur de la propriété lock.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLock() {
            return lock;
        }

        /**
         * Définit la valeur de la propriété lock.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLock(String value) {
            this.lock = value;
        }

        /**
         * Obtient la valeur de la propriété exclusive.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExclusive() {
            return exclusive;
        }

        /**
         * Définit la valeur de la propriété exclusive.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExclusive(String value) {
            this.exclusive = value;
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
     *         &lt;element ref="{}script"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" type="{}Name" />
     *       &lt;attribute name="ordering" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "script"
    })
    public static class Monitor {

        @XmlElement(required = true)
        protected Script script;
        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "ordering")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger ordering;

        /**
         * Obtient la valeur de la propriété script.
         * 
         * @return
         *     possible object is
         *     {@link Script }
         *     
         */
        public Script getScript() {
            return script;
        }

        /**
         * Définit la valeur de la propriété script.
         * 
         * @param value
         *     allowed object is
         *     {@link Script }
         *     
         */
        public void setScript(Script value) {
            this.script = value;
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
         * Obtient la valeur de la propriété ordering.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getOrdering() {
            return ordering;
        }

        /**
         * Définit la valeur de la propriété ordering.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setOrdering(BigInteger value) {
            this.ordering = value;
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
     *       &lt;/sequence>
     *       &lt;attribute name="file" use="required" type="{}File" />
     *       &lt;attribute name="param" type="{}String" />
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
        "environment"
    })
    public static class Process {

        protected Environment environment;
        @XmlAttribute(name = "file", required = true)
        protected String file;
        @XmlAttribute(name = "param")
        protected String param;
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


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="directory" use="required" type="{}File" />
     *       &lt;attribute name="regex" type="{}String" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class StartWhenDirectoryChanged {

        @XmlAttribute(name = "directory", required = true)
        protected String directory;
        @XmlAttribute(name = "regex")
        protected String regex;

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

    }

}
