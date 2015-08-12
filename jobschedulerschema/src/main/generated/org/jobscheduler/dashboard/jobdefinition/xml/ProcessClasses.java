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
 *         &lt;element ref="{}extensions" minOccurs="0"/>
 *         &lt;element name="process_class" type="{}process_class" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ignore" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extensions",
    "processClass"
})
@XmlRootElement(name = "process_classes")
public class ProcessClasses {

    protected Extensions extensions;
    @XmlElement(name = "process_class")
    protected List<ProcessClass> processClass;
    @XmlAttribute(name = "ignore")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ignore;

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
     * Gets the value of the processClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessClass }
     * 
     * 
     */
    public List<ProcessClass> getProcessClass() {
        if (processClass == null) {
            processClass = new ArrayList<ProcessClass>();
        }
        return this.processClass;
    }

    /**
     * Obtient la valeur de la propriété ignore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIgnore() {
        return ignore;
    }

    /**
     * Définit la valeur de la propriété ignore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIgnore(String value) {
        this.ignore = value;
    }

}
