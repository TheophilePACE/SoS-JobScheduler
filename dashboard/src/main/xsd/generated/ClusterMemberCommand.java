//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="terminate"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *                 &lt;attribute name="restart" type="{}Yes_no" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "terminate"
})
@XmlRootElement(name = "cluster_member_command")
public class ClusterMemberCommand {

    protected ClusterMemberCommand.Terminate terminate;

    /**
     * Obtient la valeur de la propri�t� terminate.
     * 
     * @return
     *     possible object is
     *     {@link ClusterMemberCommand.Terminate }
     *     
     */
    public ClusterMemberCommand.Terminate getTerminate() {
        return terminate;
    }

    /**
     * D�finit la valeur de la propri�t� terminate.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterMemberCommand.Terminate }
     *     
     */
    public void setTerminate(ClusterMemberCommand.Terminate value) {
        this.terminate = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
     *       &lt;attribute name="restart" type="{}Yes_no" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Terminate {

        @XmlAttribute(name = "timeout")
        protected BigInteger timeout;
        @XmlAttribute(name = "restart")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String restart;

        /**
         * Obtient la valeur de la propri�t� timeout.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTimeout() {
            return timeout;
        }

        /**
         * D�finit la valeur de la propri�t� timeout.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTimeout(BigInteger value) {
            this.timeout = value;
        }

        /**
         * Obtient la valeur de la propri�t� restart.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRestart() {
            return restart;
        }

        /**
         * D�finit la valeur de la propri�t� restart.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRestart(String value) {
            this.restart = value;
        }

    }

}
