//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import java.util.ArrayList;
import java.util.List;
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
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="allowed_host" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="host" use="required" type="{}String" /&gt;
 *                 &lt;attribute name="level" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                       &lt;enumeration value="none"/&gt;
 *                       &lt;enumeration value="signal"/&gt;
 *                       &lt;enumeration value="info"/&gt;
 *                       &lt;enumeration value="no_add"/&gt;
 *                       &lt;enumeration value="all"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ignore_unknown_hosts" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "allowedHost"
})
@XmlRootElement(name = "security")
public class Security {

    @XmlElement(name = "allowed_host")
    protected List<Security.AllowedHost> allowedHost;
    @XmlAttribute(name = "ignore_unknown_hosts")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ignoreUnknownHosts;

    /**
     * Gets the value of the allowedHost property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedHost property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedHost().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Security.AllowedHost }
     * 
     * 
     */
    public List<Security.AllowedHost> getAllowedHost() {
        if (allowedHost == null) {
            allowedHost = new ArrayList<Security.AllowedHost>();
        }
        return this.allowedHost;
    }

    /**
     * Obtient la valeur de la propri�t� ignoreUnknownHosts.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIgnoreUnknownHosts() {
        return ignoreUnknownHosts;
    }

    /**
     * D�finit la valeur de la propri�t� ignoreUnknownHosts.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIgnoreUnknownHosts(String value) {
        this.ignoreUnknownHosts = value;
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
     *       &lt;attribute name="host" use="required" type="{}String" /&gt;
     *       &lt;attribute name="level" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
     *             &lt;enumeration value="none"/&gt;
     *             &lt;enumeration value="signal"/&gt;
     *             &lt;enumeration value="info"/&gt;
     *             &lt;enumeration value="no_add"/&gt;
     *             &lt;enumeration value="all"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AllowedHost {

        @XmlAttribute(name = "host", required = true)
        protected String host;
        @XmlAttribute(name = "level", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String level;

        /**
         * Obtient la valeur de la propri�t� host.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHost() {
            return host;
        }

        /**
         * D�finit la valeur de la propri�t� host.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHost(String value) {
            this.host = value;
        }

        /**
         * Obtient la valeur de la propri�t� level.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLevel() {
            return level;
        }

        /**
         * D�finit la valeur de la propri�t� level.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLevel(String value) {
            this.level = value;
        }

    }

}
