//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="web_service" type="{}web_service" minOccurs="0"/&gt;
 *         &lt;element name="content"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;any/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="url" type="{}String" /&gt;
 *       &lt;attribute name="url_path" type="{}String" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "webService",
    "content"
})
@XmlRootElement(name = "service_request")
public class ServiceRequest {

    @XmlElement(name = "web_service")
    protected WebService webService;
    @XmlElement(required = true)
    protected ServiceRequest.Content content;
    @XmlAttribute(name = "url")
    protected String url;
    @XmlAttribute(name = "url_path")
    protected String urlPath;

    /**
     * Obtient la valeur de la propri�t� webService.
     * 
     * @return
     *     possible object is
     *     {@link WebService }
     *     
     */
    public WebService getWebService() {
        return webService;
    }

    /**
     * D�finit la valeur de la propri�t� webService.
     * 
     * @param value
     *     allowed object is
     *     {@link WebService }
     *     
     */
    public void setWebService(WebService value) {
        this.webService = value;
    }

    /**
     * Obtient la valeur de la propri�t� content.
     * 
     * @return
     *     possible object is
     *     {@link ServiceRequest.Content }
     *     
     */
    public ServiceRequest.Content getContent() {
        return content;
    }

    /**
     * D�finit la valeur de la propri�t� content.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceRequest.Content }
     *     
     */
    public void setContent(ServiceRequest.Content value) {
        this.content = value;
    }

    /**
     * Obtient la valeur de la propri�t� url.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * D�finit la valeur de la propri�t� url.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;any/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class Content {

        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Obtient la valeur de la propri�t� any.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getAny() {
            return any;
        }

        /**
         * D�finit la valeur de la propri�t� any.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}
