//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allowed_host" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="host" use="required" type="{}String" />
 *                 &lt;attribute name="level" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                       &lt;enumeration value="none"/>
 *                       &lt;enumeration value="signal"/>
 *                       &lt;enumeration value="info"/>
 *                       &lt;enumeration value="no_add"/>
 *                       &lt;enumeration value="all"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ignore_unknown_hosts" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Obtient la valeur de la propriété ignoreUnknownHosts.
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
     * Définit la valeur de la propriété ignoreUnknownHosts.
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
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="host" use="required" type="{}String" />
     *       &lt;attribute name="level" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *             &lt;enumeration value="none"/>
     *             &lt;enumeration value="signal"/>
     *             &lt;enumeration value="info"/>
     *             &lt;enumeration value="no_add"/>
     *             &lt;enumeration value="all"/>
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
    public static class AllowedHost {

        @XmlAttribute(name = "host", required = true)
        protected String host;
        @XmlAttribute(name = "level", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String level;

        /**
         * Obtient la valeur de la propriété host.
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
         * Définit la valeur de la propriété host.
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
         * Obtient la valeur de la propriété level.
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
         * Définit la valeur de la propriété level.
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
