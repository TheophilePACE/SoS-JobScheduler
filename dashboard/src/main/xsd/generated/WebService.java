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
 * <p>Classe Java pour web_service complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="web_service"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}params" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{}Name" /&gt;
 *       &lt;attribute name="url_path" use="required" type="{}Url_path" /&gt;
 *       &lt;attribute name="debug" type="{}Yes_no" /&gt;
 *       &lt;attribute name="request_xslt_stylesheet" type="{}File" /&gt;
 *       &lt;attribute name="response_xslt_stylesheet" type="{}File" /&gt;
 *       &lt;attribute name="forward_xslt_stylesheet" type="{}File" /&gt;
 *       &lt;attribute name="job_chain" type="{}Path" /&gt;
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "web_service", propOrder = {
    "params"
})
public class WebService {

    protected Params params;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "url_path", required = true)
    protected String urlPath;
    @XmlAttribute(name = "debug")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String debug;
    @XmlAttribute(name = "request_xslt_stylesheet")
    protected String requestXsltStylesheet;
    @XmlAttribute(name = "response_xslt_stylesheet")
    protected String responseXsltStylesheet;
    @XmlAttribute(name = "forward_xslt_stylesheet")
    protected String forwardXsltStylesheet;
    @XmlAttribute(name = "job_chain")
    protected String jobChain;
    @XmlAttribute(name = "timeout")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger timeout;

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
     * Obtient la valeur de la propri�t� urlPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPath() {
        return urlPath;
    }

    /**
     * D�finit la valeur de la propri�t� urlPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPath(String value) {
        this.urlPath = value;
    }

    /**
     * Obtient la valeur de la propri�t� debug.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebug() {
        return debug;
    }

    /**
     * D�finit la valeur de la propri�t� debug.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebug(String value) {
        this.debug = value;
    }

    /**
     * Obtient la valeur de la propri�t� requestXsltStylesheet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestXsltStylesheet() {
        return requestXsltStylesheet;
    }

    /**
     * D�finit la valeur de la propri�t� requestXsltStylesheet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestXsltStylesheet(String value) {
        this.requestXsltStylesheet = value;
    }

    /**
     * Obtient la valeur de la propri�t� responseXsltStylesheet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseXsltStylesheet() {
        return responseXsltStylesheet;
    }

    /**
     * D�finit la valeur de la propri�t� responseXsltStylesheet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseXsltStylesheet(String value) {
        this.responseXsltStylesheet = value;
    }

    /**
     * Obtient la valeur de la propri�t� forwardXsltStylesheet.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForwardXsltStylesheet() {
        return forwardXsltStylesheet;
    }

    /**
     * D�finit la valeur de la propri�t� forwardXsltStylesheet.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForwardXsltStylesheet(String value) {
        this.forwardXsltStylesheet = value;
    }

    /**
     * Obtient la valeur de la propri�t� jobChain.
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
     * D�finit la valeur de la propri�t� jobChain.
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

}
