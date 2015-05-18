//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour Job.Settings complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Job.Settings"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mail_on_error" type="{}Yes_no" minOccurs="0"/&gt;
 *         &lt;element name="mail_on_warning" type="{}Yes_no" minOccurs="0"/&gt;
 *         &lt;element name="mail_on_success" type="{}Yes_no" minOccurs="0"/&gt;
 *         &lt;element name="mail_on_process" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;union memberTypes=" {}Yes_no {http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *             &lt;/union&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="mail_on_delay_after_error" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *               &lt;enumeration value="all"/&gt;
 *               &lt;enumeration value="first_only"/&gt;
 *               &lt;enumeration value="last_only"/&gt;
 *               &lt;enumeration value="first_and_last_only"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="log_mail_to" type="{}Non_empty_string" minOccurs="0"/&gt;
 *         &lt;element name="log_mail_cc" type="{}Non_empty_string" minOccurs="0"/&gt;
 *         &lt;element name="log_mail_bcc" type="{}Non_empty_string" minOccurs="0"/&gt;
 *         &lt;element name="log_level" type="{}Log_level" minOccurs="0"/&gt;
 *         &lt;element name="history" type="{}Yes_no" minOccurs="0"/&gt;
 *         &lt;element name="history_on_process" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;union memberTypes=" {}Yes_no {http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;/union&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="history_with_log" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *               &lt;enumeration value="yes"/&gt;
 *               &lt;enumeration value="no"/&gt;
 *               &lt;enumeration value="gzip"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Job.Settings", propOrder = {
    "mailOnError",
    "mailOnWarning",
    "mailOnSuccess",
    "mailOnProcess",
    "mailOnDelayAfterError",
    "logMailTo",
    "logMailCc",
    "logMailBcc",
    "logLevel",
    "history",
    "historyOnProcess",
    "historyWithLog"
})
public class JobSettings {

    @XmlElement(name = "mail_on_error")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String mailOnError;
    @XmlElement(name = "mail_on_warning")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String mailOnWarning;
    @XmlElement(name = "mail_on_success")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String mailOnSuccess;
    @XmlElement(name = "mail_on_process")
    protected String mailOnProcess;
    @XmlElement(name = "mail_on_delay_after_error")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String mailOnDelayAfterError;
    @XmlElement(name = "log_mail_to")
    protected String logMailTo;
    @XmlElement(name = "log_mail_cc")
    protected String logMailCc;
    @XmlElement(name = "log_mail_bcc")
    protected String logMailBcc;
    @XmlElement(name = "log_level")
    @XmlSchemaType(name = "NMTOKEN")
    protected LogLevel logLevel;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String history;
    @XmlElement(name = "history_on_process")
    protected String historyOnProcess;
    @XmlElement(name = "history_with_log")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String historyWithLog;

    /**
     * Obtient la valeur de la propriété mailOnError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailOnError() {
        return mailOnError;
    }

    /**
     * Définit la valeur de la propriété mailOnError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailOnError(String value) {
        this.mailOnError = value;
    }

    /**
     * Obtient la valeur de la propriété mailOnWarning.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailOnWarning() {
        return mailOnWarning;
    }

    /**
     * Définit la valeur de la propriété mailOnWarning.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailOnWarning(String value) {
        this.mailOnWarning = value;
    }

    /**
     * Obtient la valeur de la propriété mailOnSuccess.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailOnSuccess() {
        return mailOnSuccess;
    }

    /**
     * Définit la valeur de la propriété mailOnSuccess.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailOnSuccess(String value) {
        this.mailOnSuccess = value;
    }

    /**
     * Obtient la valeur de la propriété mailOnProcess.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailOnProcess() {
        return mailOnProcess;
    }

    /**
     * Définit la valeur de la propriété mailOnProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailOnProcess(String value) {
        this.mailOnProcess = value;
    }

    /**
     * Obtient la valeur de la propriété mailOnDelayAfterError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailOnDelayAfterError() {
        return mailOnDelayAfterError;
    }

    /**
     * Définit la valeur de la propriété mailOnDelayAfterError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailOnDelayAfterError(String value) {
        this.mailOnDelayAfterError = value;
    }

    /**
     * Obtient la valeur de la propriété logMailTo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMailTo() {
        return logMailTo;
    }

    /**
     * Définit la valeur de la propriété logMailTo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMailTo(String value) {
        this.logMailTo = value;
    }

    /**
     * Obtient la valeur de la propriété logMailCc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMailCc() {
        return logMailCc;
    }

    /**
     * Définit la valeur de la propriété logMailCc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMailCc(String value) {
        this.logMailCc = value;
    }

    /**
     * Obtient la valeur de la propriété logMailBcc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogMailBcc() {
        return logMailBcc;
    }

    /**
     * Définit la valeur de la propriété logMailBcc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogMailBcc(String value) {
        this.logMailBcc = value;
    }

    /**
     * Obtient la valeur de la propriété logLevel.
     * 
     * @return
     *     possible object is
     *     {@link LogLevel }
     *     
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Définit la valeur de la propriété logLevel.
     * 
     * @param value
     *     allowed object is
     *     {@link LogLevel }
     *     
     */
    public void setLogLevel(LogLevel value) {
        this.logLevel = value;
    }

    /**
     * Obtient la valeur de la propriété history.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistory() {
        return history;
    }

    /**
     * Définit la valeur de la propriété history.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistory(String value) {
        this.history = value;
    }

    /**
     * Obtient la valeur de la propriété historyOnProcess.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryOnProcess() {
        return historyOnProcess;
    }

    /**
     * Définit la valeur de la propriété historyOnProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryOnProcess(String value) {
        this.historyOnProcess = value;
    }

    /**
     * Obtient la valeur de la propriété historyWithLog.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryWithLog() {
        return historyWithLog;
    }

    /**
     * Définit la valeur de la propriété historyWithLog.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryWithLog(String value) {
        this.historyWithLog = value;
    }

}
