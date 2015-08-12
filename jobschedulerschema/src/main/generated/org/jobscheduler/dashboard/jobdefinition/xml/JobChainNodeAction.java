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
 * <p>Classe Java pour Job_chain_node.Action.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Job_chain_node.Action">
 *   &lt;restriction base="{}String">
 *     &lt;enumeration value="process"/>
 *     &lt;enumeration value="stop"/>
 *     &lt;enumeration value="next_state"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Job_chain_node.Action")
@XmlEnum
public enum JobChainNodeAction {

    @XmlEnumValue("process")
    PROCESS("process"),
    @XmlEnumValue("stop")
    STOP("stop"),
    @XmlEnumValue("next_state")
    NEXT_STATE("next_state");
    private final String value;

    JobChainNodeAction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static JobChainNodeAction fromValue(String v) {
        for (JobChainNodeAction c: JobChainNodeAction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
