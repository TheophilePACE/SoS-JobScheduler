//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour monthdays complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="monthdays"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence maxOccurs="unbounded"&gt;
 *         &lt;choice&gt;
 *           &lt;element name="day" maxOccurs="unbounded"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="day" use="required"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;list&gt;
 *                         &lt;simpleType&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *                             &lt;maxInclusive value="31"/&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/simpleType&gt;
 *                       &lt;/list&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/attribute&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *           &lt;element name="weekday" maxOccurs="unbounded"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="day" use="required"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;list itemType="{}Weekday" /&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/attribute&gt;
 *                   &lt;attribute name="which" use="required"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;union&gt;
 *                         &lt;simpleType&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *                             &lt;maxInclusive value="4"/&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/simpleType&gt;
 *                         &lt;simpleType&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}negativeInteger"&gt;
 *                             &lt;minInclusive value="-4"/&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/simpleType&gt;
 *                       &lt;/union&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/attribute&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monthdays", propOrder = {
    "dayOrWeekday"
})
public class Monthdays {

    @XmlElements({
        @XmlElement(name = "day", type = Monthdays.Day.class),
        @XmlElement(name = "weekday", type = Monthdays.Weekday.class)
    })
    protected List<Object> dayOrWeekday;

    /**
     * Gets the value of the dayOrWeekday property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dayOrWeekday property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDayOrWeekday().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Monthdays.Day }
     * {@link Monthdays.Weekday }
     * 
     * 
     */
    public List<Object> getDayOrWeekday() {
        if (dayOrWeekday == null) {
            dayOrWeekday = new ArrayList<Object>();
        }
        return this.dayOrWeekday;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="day" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;list&gt;
     *             &lt;simpleType&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
     *                 &lt;maxInclusive value="31"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/simpleType&gt;
     *           &lt;/list&gt;
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
    @XmlType(name = "", propOrder = {
        "period"
    })
    public static class Day {

        protected List<Period> period;
        @XmlAttribute(name = "day", required = true)
        protected List<Integer> day;

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
         * Gets the value of the day property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the day property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDay().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getDay() {
            if (day == null) {
                day = new ArrayList<Integer>();
            }
            return this.day;
        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="day" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;list itemType="{}Weekday" /&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="which" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;union&gt;
     *             &lt;simpleType&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
     *                 &lt;maxInclusive value="4"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/simpleType&gt;
     *             &lt;simpleType&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}negativeInteger"&gt;
     *                 &lt;minInclusive value="-4"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/simpleType&gt;
     *           &lt;/union&gt;
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
    @XmlType(name = "", propOrder = {
        "period"
    })
    public static class Weekday {

        protected List<Period> period;
        @XmlAttribute(name = "day", required = true)
        protected List<String> day;
        @XmlAttribute(name = "which", required = true)
        protected String which;

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
         * Gets the value of the day property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the day property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDay().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDay() {
            if (day == null) {
                day = new ArrayList<String>();
            }
            return this.day;
        }

        /**
         * Obtient la valeur de la propriété which.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWhich() {
            return which;
        }

        /**
         * Définit la valeur de la propriété which.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWhich(String value) {
            this.which = value;
        }

    }

}
