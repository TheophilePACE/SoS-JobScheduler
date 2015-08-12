//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Code_page_encoding.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Code_page_encoding">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="oem"/>
 *     &lt;enumeration value="cp437"/>
 *     &lt;enumeration value="cp850"/>
 *     &lt;enumeration value="latin1"/>
 *     &lt;enumeration value="none"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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
