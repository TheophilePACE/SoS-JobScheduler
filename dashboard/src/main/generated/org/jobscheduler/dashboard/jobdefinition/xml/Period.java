//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="single_start" type="{}Time_of_day" />
 *       &lt;attribute name="begin" type="{}Time_of_day" />
 *       &lt;attribute name="end" type="{}Time_of_day" />
 *       &lt;attribute name="let_run" type="{}Yes_no" />
 *       &lt;attribute name="repeat" type="{}Duration" />
 *       &lt;attribute name="absolute_repeat" type="{}Duration" />
 *       &lt;attribute name="when_holiday" type="{}When_holiday" />
 *       &lt;attribute name="start_once" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Obtient la valeur de la propriété singleStart.
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
     * Définit la valeur de la propriété singleStart.
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
     * Obtient la valeur de la propriété begin.
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
     * Définit la valeur de la propriété begin.
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
     * Obtient la valeur de la propriété end.
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
     * Définit la valeur de la propriété end.
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
     * Obtient la valeur de la propriété letRun.
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
     * Définit la valeur de la propriété letRun.
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
     * Obtient la valeur de la propriété repeat.
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
     * Définit la valeur de la propriété repeat.
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
     * Obtient la valeur de la propriété absoluteRepeat.
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
     * Définit la valeur de la propriété absoluteRepeat.
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
     * Obtient la valeur de la propriété whenHoliday.
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
     * Définit la valeur de la propriété whenHoliday.
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
     * Obtient la valeur de la propriété startOnce.
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
     * Définit la valeur de la propriété startOnce.
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
