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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour http.authentication complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="http.authentication"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="http.users"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="http.user" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="name" use="required" type="{}Name" /&gt;
 *                           &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="scheme"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="basic"/&gt;
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
     * Obtient la valeur de la propri�t� httpUsers.
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
     * D�finit la valeur de la propri�t� httpUsers.
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
     * Obtient la valeur de la propri�t� scheme.
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
     * D�finit la valeur de la propri�t� scheme.
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
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="http.user" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="name" use="required" type="{}Name" /&gt;
     *                 &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
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
         * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="name" use="required" type="{}Name" /&gt;
         *       &lt;attribute name="password_md5" use="required" type="{}Md5_checksum" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
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
             * Obtient la valeur de la propri�t� passwordMd5.
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
             * D�finit la valeur de la propri�t� passwordMd5.
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
