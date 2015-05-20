//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import java.io.Serializable;
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


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element ref="{}include"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="language" type="{}Name" /&gt;
 *       &lt;attribute name="use_engine"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="task"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="com_class" type="{}String" /&gt;
 *       &lt;attribute name="filename" type="{}File" /&gt;
 *       &lt;attribute name="java_class" type="{}String" /&gt;
 *       &lt;attribute name="recompile" type="{}Yes_no" /&gt;
 *       &lt;attribute name="encoding" type="{}Code_page_encoding" /&gt;
 *       &lt;attribute name="java_class_path" type="{}String" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
    protected List<Serializable> content;
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
     * {@link Include }
     * {@link String }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Obtient la valeur de la propri�t� language.
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
     * D�finit la valeur de la propri�t� language.
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
     * Obtient la valeur de la propri�t� useEngine.
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
     * D�finit la valeur de la propri�t� useEngine.
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
     * Obtient la valeur de la propri�t� comClass.
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
     * D�finit la valeur de la propri�t� comClass.
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
     * Obtient la valeur de la propri�t� filename.
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
     * D�finit la valeur de la propri�t� filename.
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
     * Obtient la valeur de la propri�t� javaClass.
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
     * D�finit la valeur de la propri�t� javaClass.
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
     * Obtient la valeur de la propri�t� recompile.
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
     * D�finit la valeur de la propri�t� recompile.
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
     * Obtient la valeur de la propri�t� encoding.
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
     * D�finit la valeur de la propri�t� encoding.
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
     * Obtient la valeur de la propri�t� javaClassPath.
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
     * D�finit la valeur de la propri�t� javaClassPath.
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
