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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.*;


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
 *       &lt;attribute name="language" type="{}Name" />
 *       &lt;attribute name="use_engine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="task"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="com_class" type="{}String" />
 *       &lt;attribute name="filename" type="{}File" />
 *       &lt;attribute name="java_class" type="{}String" />
 *       &lt;attribute name="recompile" type="{}Yes_no" />
 *       &lt;attribute name="encoding" type="{}Code_page_encoding" />
 *       &lt;attribute name="java_class_path" type="{}String" />
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
@XmlRootElement(name = "script")
public class Script {

    @XmlElementRef(name = "include", type = Include.class, required = false)
    @XmlMixed
    protected List<Object> content;
    @XmlAttribute(name = "language")
    protected String language;
    @XmlAttribute(name = "use_engine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String useEngine;
    @XmlAttribute(name = "com_class")
    protected String comClass;
    @XmlAttribute(name = "filename")
    protected String filename;
    @XmlAttribute(name = "java_class")
    protected String javaClass;
    @XmlAttribute(name = "recompile")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String recompile;
    @XmlAttribute(name = "encoding")
    protected CodePageEncoding encoding;
    @XmlAttribute(name = "java_class_path")
    protected String javaClassPath;

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

    /**
     * Obtient la valeur de la propriété language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Définit la valeur de la propriété language.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Obtient la valeur de la propriété useEngine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseEngine() {
        return useEngine;
    }

    /**
     * Définit la valeur de la propriété useEngine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseEngine(String value) {
        this.useEngine = value;
    }

    /**
     * Obtient la valeur de la propriété comClass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComClass() {
        return comClass;
    }

    /**
     * Définit la valeur de la propriété comClass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComClass(String value) {
        this.comClass = value;
    }

    /**
     * Obtient la valeur de la propriété filename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Définit la valeur de la propriété filename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Obtient la valeur de la propriété javaClass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaClass() {
        return javaClass;
    }

    /**
     * Définit la valeur de la propriété javaClass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaClass(String value) {
        this.javaClass = value;
    }

    /**
     * Obtient la valeur de la propriété recompile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecompile() {
        return recompile;
    }

    /**
     * Définit la valeur de la propriété recompile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecompile(String value) {
        this.recompile = value;
    }

    /**
     * Obtient la valeur de la propriété encoding.
     * 
     * @return
     *     possible object is
     *     {@link CodePageEncoding }
     *     
     */
    public CodePageEncoding getEncoding() {
        return encoding;
    }

    /**
     * Définit la valeur de la propriété encoding.
     * 
     * @param value
     *     allowed object is
     *     {@link CodePageEncoding }
     *     
     */
    public void setEncoding(CodePageEncoding value) {
        this.encoding = value;
    }

    /**
     * Obtient la valeur de la propriété javaClassPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaClassPath() {
        return javaClassPath;
    }

    /**
     * Définit la valeur de la propriété javaClassPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaClassPath(String value) {
        this.javaClassPath = value;
    }

}
