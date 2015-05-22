//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 12:28:08 AM PDT 
//


package org.jobscheduler.converter.tws.scheduler;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for Code_page_encoding.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
@XmlEnum
public enum CodePageEncoding {

    @XmlEnumValue("cp437")
    CP_437("cp437"),
    @XmlEnumValue("cp850")
    CP_850("cp850"),
    @XmlEnumValue("latin1")
    LATIN_1("latin1"),
    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("oem")
    OEM("oem");
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
        throw new IllegalArgumentException(v.toString());
    }

}
