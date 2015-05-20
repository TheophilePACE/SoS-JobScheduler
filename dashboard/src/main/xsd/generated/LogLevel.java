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
 * <p>Classe Java pour Log_level.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Log_level"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="error"/&gt;
 *     &lt;enumeration value="warn"/&gt;
 *     &lt;enumeration value="info"/&gt;
 *     &lt;enumeration value="debug"/&gt;
 *     &lt;enumeration value="debug1"/&gt;
 *     &lt;enumeration value="debug2"/&gt;
 *     &lt;enumeration value="debug3"/&gt;
 *     &lt;enumeration value="debug4"/&gt;
 *     &lt;enumeration value="debug5"/&gt;
 *     &lt;enumeration value="debug6"/&gt;
 *     &lt;enumeration value="debug7"/&gt;
 *     &lt;enumeration value="debug8"/&gt;
 *     &lt;enumeration value="debug9"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Log_level")
@XmlEnum
public enum LogLevel {

    @XmlEnumValue("error")
    ERROR("error"),
    @XmlEnumValue("warn")
    WARN("warn"),
    @XmlEnumValue("info")
    INFO("info"),
    @XmlEnumValue("debug")
    DEBUG("debug"),
    @XmlEnumValue("debug1")
    DEBUG_1("debug1"),
    @XmlEnumValue("debug2")
    DEBUG_2("debug2"),
    @XmlEnumValue("debug3")
    DEBUG_3("debug3"),
    @XmlEnumValue("debug4")
    DEBUG_4("debug4"),
    @XmlEnumValue("debug5")
    DEBUG_5("debug5"),
    @XmlEnumValue("debug6")
    DEBUG_6("debug6"),
    @XmlEnumValue("debug7")
    DEBUG_7("debug7"),
    @XmlEnumValue("debug8")
    DEBUG_8("debug8"),
    @XmlEnumValue("debug9")
    DEBUG_9("debug9");
    private final String value;

    LogLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LogLevel fromValue(String v) {
        for (LogLevel c: LogLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
