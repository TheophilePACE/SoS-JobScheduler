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
 * <p>Classe Java pour Job_chain_node.Action.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
