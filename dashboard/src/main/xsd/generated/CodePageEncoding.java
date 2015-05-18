//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Code_page_encoding.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Code_page_encoding"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="oem"/&gt;
 *     &lt;enumeration value="cp437"/&gt;
 *     &lt;enumeration value="cp850"/&gt;
 *     &lt;enumeration value="latin1"/&gt;
 *     &lt;enumeration value="none"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Code_page_encoding")
@XmlEnum
public enum CodePageEncoding {

    @XmlEnumValue("oem")
    OEM("oem"),
    @XmlEnumValue("cp437")
    CP_437("cp437"),
    @XmlEnumValue("cp850")
    CP_850("cp850"),
    @XmlEnumValue("latin1")
    LATIN_1("latin1"),
    @XmlEnumValue("none")
    NONE("none");
    private final String value;

    CodePageEncoding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CodePageEncoding fromValue(String v) {
        for (CodePageEncoding c: CodePageEncoding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
