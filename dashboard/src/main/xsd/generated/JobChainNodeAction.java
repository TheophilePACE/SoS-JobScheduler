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
 * <p>Classe Java pour Job_chain_node.Action.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Job_chain_node.Action"&gt;
 *   &lt;restriction base="{}String"&gt;
 *     &lt;enumeration value="process"/&gt;
 *     &lt;enumeration value="stop"/&gt;
 *     &lt;enumeration value="next_state"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
