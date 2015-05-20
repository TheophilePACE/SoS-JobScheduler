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
 * <p>Classe Java pour Unix_signal_name.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Unix_signal_name"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="SIGHUP"/&gt;
 *     &lt;enumeration value="SIGINT"/&gt;
 *     &lt;enumeration value="SIGQUIT"/&gt;
 *     &lt;enumeration value="SIGILL"/&gt;
 *     &lt;enumeration value="SIGTRAP"/&gt;
 *     &lt;enumeration value="SIGABRT"/&gt;
 *     &lt;enumeration value="SIGIOT"/&gt;
 *     &lt;enumeration value="SIGBUS"/&gt;
 *     &lt;enumeration value="SIGFPE"/&gt;
 *     &lt;enumeration value="SIGKILL"/&gt;
 *     &lt;enumeration value="SIGUSR1"/&gt;
 *     &lt;enumeration value="SIGSEGV"/&gt;
 *     &lt;enumeration value="SIGUSR2"/&gt;
 *     &lt;enumeration value="SIGPIPE"/&gt;
 *     &lt;enumeration value="SIGALRM"/&gt;
 *     &lt;enumeration value="SIGTERM"/&gt;
 *     &lt;enumeration value="SIGSTKFLT"/&gt;
 *     &lt;enumeration value="SIGCHLD"/&gt;
 *     &lt;enumeration value="SIGCONT"/&gt;
 *     &lt;enumeration value="SIGSTOP"/&gt;
 *     &lt;enumeration value="SIGTSTP"/&gt;
 *     &lt;enumeration value="SIGTTIN"/&gt;
 *     &lt;enumeration value="SIGTTOU"/&gt;
 *     &lt;enumeration value="SIGURG"/&gt;
 *     &lt;enumeration value="SIGXCPU"/&gt;
 *     &lt;enumeration value="SIGXFSZ"/&gt;
 *     &lt;enumeration value="SIGVTALRM"/&gt;
 *     &lt;enumeration value="SIGPROF"/&gt;
 *     &lt;enumeration value="SIGWINCH"/&gt;
 *     &lt;enumeration value="SIGPOLL"/&gt;
 *     &lt;enumeration value="SIGIO"/&gt;
 *     &lt;enumeration value="SIGPWR"/&gt;
 *     &lt;enumeration value="SIGSYS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Unix_signal_name")
@XmlEnum
public enum UnixSignalName {

    SIGHUP("SIGHUP"),
    SIGINT("SIGINT"),
    SIGQUIT("SIGQUIT"),
    SIGILL("SIGILL"),
    SIGTRAP("SIGTRAP"),
    SIGABRT("SIGABRT"),
    SIGIOT("SIGIOT"),
    SIGBUS("SIGBUS"),
    SIGFPE("SIGFPE"),
    SIGKILL("SIGKILL"),
    @XmlEnumValue("SIGUSR1")
    SIGUSR_1("SIGUSR1"),
    SIGSEGV("SIGSEGV"),
    @XmlEnumValue("SIGUSR2")
    SIGUSR_2("SIGUSR2"),
    SIGPIPE("SIGPIPE"),
    SIGALRM("SIGALRM"),
    SIGTERM("SIGTERM"),
    SIGSTKFLT("SIGSTKFLT"),
    SIGCHLD("SIGCHLD"),
    SIGCONT("SIGCONT"),
    SIGSTOP("SIGSTOP"),
    SIGTSTP("SIGTSTP"),
    SIGTTIN("SIGTTIN"),
    SIGTTOU("SIGTTOU"),
    SIGURG("SIGURG"),
    SIGXCPU("SIGXCPU"),
    SIGXFSZ("SIGXFSZ"),
    SIGVTALRM("SIGVTALRM"),
    SIGPROF("SIGPROF"),
    SIGWINCH("SIGWINCH"),
    SIGPOLL("SIGPOLL"),
    SIGIO("SIGIO"),
    SIGPWR("SIGPWR"),
    SIGSYS("SIGSYS");
    private final String value;

    UnixSignalName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnixSignalName fromValue(String v) {
        for (UnixSignalName c: UnixSignalName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
