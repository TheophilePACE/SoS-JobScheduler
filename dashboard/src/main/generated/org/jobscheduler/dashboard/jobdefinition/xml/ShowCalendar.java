//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="from" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="limit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="what" type="{}What" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "show_calendar")
public class ShowCalendar {

    @XmlAttribute(name = "from")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar from;
    @XmlAttribute(name = "before")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar before;
    @XmlAttribute(name = "limit")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger limit;
    @XmlAttribute(name = "what")
    protected String what;

    /**
     * Obtient la valeur de la propriété from.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Définit la valeur de la propriété from.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    /**
     * Obtient la valeur de la propriété before.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBefore() {
        return before;
    }

    /**
     * Définit la valeur de la propriété before.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBefore(XMLGregorianCalendar value) {
        this.before = value;
    }

    /**
     * Obtient la valeur de la propriété limit.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLimit() {
        return limit;
    }

    /**
     * Définit la valeur de la propriété limit.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLimit(BigInteger value) {
        this.limit = value;
    }

    /**
     * Obtient la valeur de la propriété what.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhat() {
        return what;
    }

    /**
     * Définit la valeur de la propriété what.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhat(String value) {
        this.what = value;
    }

}
