//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour http.authentication complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="http.authentication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="http.users">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="http.user" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" use="required" type="{}Name" />
 *                           &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="scheme">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="basic"/>
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
@XmlType(name = "http.authentication", propOrder = {
    "httpUsers"
})
public class HttpAuthentication {

    @XmlElement(name = "http.users", required = true)
    protected HttpAuthentication.HttpUsers httpUsers;
    @XmlAttribute(name = "scheme")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String scheme;

    /**
     * Obtient la valeur de la propriété httpUsers.
     * 
     * @return
     *     possible object is
     *     {@link HttpAuthentication.HttpUsers }
     *     
     */
    public HttpAuthentication.HttpUsers getHttpUsers() {
        return httpUsers;
    }

    /**
     * Définit la valeur de la propriété httpUsers.
     * 
     * @param value
     *     allowed object is
     *     {@link HttpAuthentication.HttpUsers }
     *     
     */
    public void setHttpUsers(HttpAuthentication.HttpUsers value) {
        this.httpUsers = value;
    }

    /**
     * Obtient la valeur de la propriété scheme.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Définit la valeur de la propriété scheme.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheme(String value) {
        this.scheme = value;
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
     *         &lt;element name="http.user" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="name" use="required" type="{}Name" />
     *                 &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "httpUser"
    })
    public static class HttpUsers {

        @XmlElement(name = "http.user", required = true)
        protected List<HttpAuthentication.HttpUsers.HttpUser> httpUser;

        /**
         * Gets the value of the httpUser property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the httpUser property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHttpUser().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HttpAuthentication.HttpUsers.HttpUser }
         * 
         * 
         */
        public List<HttpAuthentication.HttpUsers.HttpUser> getHttpUser() {
            if (httpUser == null) {
                httpUser = new ArrayList<HttpAuthentication.HttpUsers.HttpUser>();
            }
            return this.httpUser;
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
         *       &lt;attribute name="name" use="required" type="{}Name" />
         *       &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class HttpUser {

            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "password_md5", required = true)
            protected String passwordMd5;

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
             * Obtient la valeur de la propriété passwordMd5.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPasswordMd5() {
                return passwordMd5;
            }

            /**
             * Définit la valeur de la propriété passwordMd5.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPasswordMd5(String value) {
                this.passwordMd5 = value;
            }

        }

    }

}
