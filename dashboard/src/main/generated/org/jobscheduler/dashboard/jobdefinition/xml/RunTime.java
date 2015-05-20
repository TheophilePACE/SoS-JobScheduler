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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour run_time complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="run_time">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="at" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="at" type="{}Loose_date_time" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="date" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="date" use="required" type="{}String" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="weekdays" type="{}weekdays" minOccurs="0"/>
 *         &lt;element name="monthdays" type="{}monthdays" minOccurs="0"/>
 *         &lt;element name="ultimos" type="{}ultimos" minOccurs="0"/>
 *         &lt;element name="month" maxOccurs="12" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="weekdays" type="{}weekdays"/>
 *                   &lt;element name="monthdays" type="{}monthdays"/>
 *                   &lt;element name="ultimos" type="{}ultimos"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="month">
 *                   &lt;simpleType>
 *                     &lt;list itemType="{}Month_name" />
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{}holidays"/>
 *           &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="schedule" type="{}String" />
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="substitute" type="{}Path" />
 *       &lt;attribute name="valid_from" type="{}Date_time" />
 *       &lt;attribute name="valid_to" type="{}Date_time" />
 *       &lt;attribute name="single_start" type="{}Time_of_day" />
 *       &lt;attribute name="begin" type="{}Time_of_day" />
 *       &lt;attribute name="end" type="{}Time_of_day" />
 *       &lt;attribute name="let_run" type="{}Yes_no" />
 *       &lt;attribute name="repeat" type="{}Duration" />
 *       &lt;attribute name="when_holiday" type="{}When_holiday" />
 *       &lt;attribute name="once" type="{}Yes_no" />
 *       &lt;attribute name="start_time_function" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "run_time", propOrder = {
    "period",
    "at",
    "date",
    "weekdays",
    "monthdays",
    "ultimos",
    "month",
    "holidays",
    "holiday"
})
public class RunTime {

    protected List<Period> period;
    protected List<RunTime.At> at;
    protected List<RunTime.Date> date;
    protected Weekdays weekdays;
    protected Monthdays monthdays;
    protected Ultimos ultimos;
    protected List<RunTime.Month> month;
    protected Holidays holidays;
    protected List<Holiday> holiday;
    @XmlAttribute(name = "schedule")
    protected String schedule;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "substitute")
    protected String substitute;
    @XmlAttribute(name = "valid_from")
    protected String validFrom;
    @XmlAttribute(name = "valid_to")
    protected String validTo;
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
    @XmlAttribute(name = "when_holiday")
    protected WhenHoliday whenHoliday;
    @XmlAttribute(name = "once")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String once;
    @XmlAttribute(name = "start_time_function")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String startTimeFunction;

    /**
     * Gets the value of the period property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the period property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Period }
     * 
     * 
     */
    public List<Period> getPeriod() {
        if (period == null) {
            period = new ArrayList<Period>();
        }
        return this.period;
    }

    /**
     * Gets the value of the at property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the at property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.At }
     * 
     * 
     */
    public List<RunTime.At> getAt() {
        if (at == null) {
            at = new ArrayList<RunTime.At>();
        }
        return this.at;
    }

    /**
     * Gets the value of the date property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the date property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.Date }
     * 
     * 
     */
    public List<RunTime.Date> getDate() {
        if (date == null) {
            date = new ArrayList<RunTime.Date>();
        }
        return this.date;
    }

    /**
     * Obtient la valeur de la propriété weekdays.
     * 
     * @return
     *     possible object is
     *     {@link Weekdays }
     *     
     */
    public Weekdays getWeekdays() {
        return weekdays;
    }

    /**
     * Définit la valeur de la propriété weekdays.
     * 
     * @param value
     *     allowed object is
     *     {@link Weekdays }
     *     
     */
    public void setWeekdays(Weekdays value) {
        this.weekdays = value;
    }

    /**
     * Obtient la valeur de la propriété monthdays.
     * 
     * @return
     *     possible object is
     *     {@link Monthdays }
     *     
     */
    public Monthdays getMonthdays() {
        return monthdays;
    }

    /**
     * Définit la valeur de la propriété monthdays.
     * 
     * @param value
     *     allowed object is
     *     {@link Monthdays }
     *     
     */
    public void setMonthdays(Monthdays value) {
        this.monthdays = value;
    }

    /**
     * Obtient la valeur de la propriété ultimos.
     * 
     * @return
     *     possible object is
     *     {@link Ultimos }
     *     
     */
    public Ultimos getUltimos() {
        return ultimos;
    }

    /**
     * Définit la valeur de la propriété ultimos.
     * 
     * @param value
     *     allowed object is
     *     {@link Ultimos }
     *     
     */
    public void setUltimos(Ultimos value) {
        this.ultimos = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the month property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonth().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RunTime.Month }
     * 
     * 
     */
    public List<RunTime.Month> getMonth() {
        if (month == null) {
            month = new ArrayList<RunTime.Month>();
        }
        return this.month;
    }

    /**
     * Obtient la valeur de la propriété holidays.
     * 
     * @return
     *     possible object is
     *     {@link Holidays }
     *     
     */
    public Holidays getHolidays() {
        return holidays;
    }

    /**
     * Définit la valeur de la propriété holidays.
     * 
     * @param value
     *     allowed object is
     *     {@link Holidays }
     *     
     */
    public void setHolidays(Holidays value) {
        this.holidays = value;
    }

    /**
     * Gets the value of the holiday property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the holiday property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoliday().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Holiday }
     * 
     * 
     */
    public List<Holiday> getHoliday() {
        if (holiday == null) {
            holiday = new ArrayList<Holiday>();
        }
        return this.holiday;
    }

    /**
     * Obtient la valeur de la propriété schedule.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Définit la valeur de la propriété schedule.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedule(String value) {
        this.schedule = value;
    }

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
     * Obtient la valeur de la propriété title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit la valeur de la propriété title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtient la valeur de la propriété substitute.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubstitute() {
        return substitute;
    }

    /**
     * Définit la valeur de la propriété substitute.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubstitute(String value) {
        this.substitute = value;
    }

    /**
     * Obtient la valeur de la propriété validFrom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Définit la valeur de la propriété validFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFrom(String value) {
        this.validFrom = value;
    }

    /**
     * Obtient la valeur de la propriété validTo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Définit la valeur de la propriété validTo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

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
     * Obtient la valeur de la propriété once.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnce() {
        return once;
    }

    /**
     * Définit la valeur de la propriété once.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnce(String value) {
        this.once = value;
    }

    /**
     * Obtient la valeur de la propriété startTimeFunction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTimeFunction() {
        return startTimeFunction;
    }

    /**
     * Définit la valeur de la propriété startTimeFunction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTimeFunction(String value) {
        this.startTimeFunction = value;
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
     *       &lt;attribute name="at" type="{}Loose_date_time" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class At {

        @XmlAttribute(name = "at")
        protected String at;

        /**
         * Obtient la valeur de la propriété at.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAt() {
            return at;
        }

        /**
         * Définit la valeur de la propriété at.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAt(String value) {
            this.at = value;
        }

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
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="date" use="required" type="{}String" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "period"
    })
    public static class Date {

        protected List<Period> period;
        @XmlAttribute(name = "date", required = true)
        protected String date;

        /**
         * Gets the value of the period property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the period property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Period }
         * 
         * 
         */
        public List<Period> getPeriod() {
            if (period == null) {
                period = new ArrayList<Period>();
            }
            return this.period;
        }

        /**
         * Obtient la valeur de la propriété date.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDate() {
            return date;
        }

        /**
         * Définit la valeur de la propriété date.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDate(String value) {
            this.date = value;
        }

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
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="weekdays" type="{}weekdays"/>
     *         &lt;element name="monthdays" type="{}monthdays"/>
     *         &lt;element name="ultimos" type="{}ultimos"/>
     *       &lt;/choice>
     *       &lt;attribute name="month">
     *         &lt;simpleType>
     *           &lt;list itemType="{}Month_name" />
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
    @XmlType(name = "", propOrder = {
        "periodOrWeekdaysOrMonthdays"
    })
    public static class Month {

        @XmlElements({
            @XmlElement(name = "period", type = Period.class),
            @XmlElement(name = "weekdays", type = Weekdays.class),
            @XmlElement(name = "monthdays", type = Monthdays.class),
            @XmlElement(name = "ultimos", type = Ultimos.class)
        })
        protected List<Object> periodOrWeekdaysOrMonthdays;
        @XmlAttribute(name = "month")
        protected List<String> month;

        /**
         * Gets the value of the periodOrWeekdaysOrMonthdays property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the periodOrWeekdaysOrMonthdays property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriodOrWeekdaysOrMonthdays().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Period }
         * {@link Weekdays }
         * {@link Monthdays }
         * {@link Ultimos }
         * 
         * 
         */
        public List<Object> getPeriodOrWeekdaysOrMonthdays() {
            if (periodOrWeekdaysOrMonthdays == null) {
                periodOrWeekdaysOrMonthdays = new ArrayList<Object>();
            }
            return this.periodOrWeekdaysOrMonthdays;
        }

        /**
         * Gets the value of the month property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the month property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMonth().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getMonth() {
            if (month == null) {
                month = new ArrayList<String>();
            }
            return this.month;
        }

    }

}
