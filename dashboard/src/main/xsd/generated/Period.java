//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="single_start" type="{}Time_of_day" /&gt;
 *       &lt;attribute name="begin" type="{}Time_of_day" /&gt;
 *       &lt;attribute name="end" type="{}Time_of_day" /&gt;
 *       &lt;attribute name="let_run" type="{}Yes_no" /&gt;
 *       &lt;attribute name="repeat" type="{}Duration" /&gt;
 *       &lt;attribute name="absolute_repeat" type="{}Duration" /&gt;
 *       &lt;attribute name="when_holiday" type="{}When_holiday" /&gt;
 *       &lt;attribute name="start_once" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "period")
public class Period {

    @XmlAttribute(name = "single_start")
    protected String singleStart;
    @XmlAttribute(name = "begin")
    protected String begin;
    @XmlAttribute(name = "end")
    protected String end;
    @XmlAttribute(name = "let_run")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String letRun;
    @XmlAttribute(name = "repeat")
    protected String repeat;
    @XmlAttribute(name = "absolute_repeat")
    protected String absoluteRepeat;
    @XmlAttribute(name = "when_holiday")
    protected WhenHoliday whenHoliday;
    @XmlAttribute(name = "start_once")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String startOnce;

    /**
     * Obtient la valeur de la propri�t� singleStart.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSingleStart() {
        return singleStart;
    }

    /**
     * D�finit la valeur de la propri�t� singleStart.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSingleStart(String value) {
        this.singleStart = value;
    }

    /**
     * Obtient la valeur de la propri�t� begin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegin() {
        return begin;
    }

    /**
     * D�finit la valeur de la propri�t� begin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegin(String value) {
        this.begin = value;
    }

    /**
     * Obtient la valeur de la propri�t� end.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * D�finit la valeur de la propri�t� end.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Obtient la valeur de la propri�t� letRun.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetRun() {
        return letRun;
    }

    /**
     * D�finit la valeur de la propri�t� letRun.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetRun(String value) {
        this.letRun = value;
    }

    /**
     * Obtient la valeur de la propri�t� repeat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepeat() {
        return repeat;
    }

    /**
     * D�finit la valeur de la propri�t� repeat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepeat(String value) {
        this.repeat = value;
    }

    /**
     * Obtient la valeur de la propri�t� absoluteRepeat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbsoluteRepeat() {
        return absoluteRepeat;
    }

    /**
     * D�finit la valeur de la propri�t� absoluteRepeat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbsoluteRepeat(String value) {
        this.absoluteRepeat = value;
    }

    /**
     * Obtient la valeur de la propri�t� whenHoliday.
     * 
     * @return
     *     possible object is
     *     {@link WhenHoliday }
     *     
     */
    public WhenHoliday getWhenHoliday() {
        return whenHoliday;
    }

    /**
     * D�finit la valeur de la propri�t� whenHoliday.
     * 
     * @param value
     *     allowed object is
     *     {@link WhenHoliday }
     *     
     */
    public void setWhenHoliday(WhenHoliday value) {
        this.whenHoliday = value;
    }

    /**
     * Obtient la valeur de la propri�t� startOnce.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartOnce() {
        return startOnce;
    }

    /**
     * D�finit la valeur de la propri�t� startOnce.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartOnce(String value) {
        this.startOnce = value;
    }

}
