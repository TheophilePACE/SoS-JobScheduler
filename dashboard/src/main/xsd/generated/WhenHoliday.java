//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour When_holiday.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="When_holiday"&gt;
 *   &lt;restriction base="{}String"&gt;
 *     &lt;enumeration value="suppress"/&gt;
 *     &lt;enumeration value="ignore_holiday"/&gt;
 *     &lt;enumeration value="previous_non_holiday"/&gt;
 *     &lt;enumeration value="next_non_holiday"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "When_holiday")
@XmlEnum
public enum WhenHoliday {

    @XmlEnumValue("suppress")
    SUPPRESS("suppress"),
    @XmlEnumValue("ignore_holiday")
    IGNORE_HOLIDAY("ignore_holiday"),
    @XmlEnumValue("previous_non_holiday")
    PREVIOUS_NON_HOLIDAY("previous_non_holiday"),
    @XmlEnumValue("next_non_holiday")
    NEXT_NON_HOLIDAY("next_non_holiday");
    private final String value;

    WhenHoliday(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WhenHoliday fromValue(String v) {
        for (WhenHoliday c: WhenHoliday.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
