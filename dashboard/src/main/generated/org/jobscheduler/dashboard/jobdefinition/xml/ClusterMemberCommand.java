//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 08:38:39 AM EDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="terminate">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="restart" type="{}Yes_no" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Obtient la valeur de la propriété terminate.
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
     * Définit la valeur de la propriété terminate.
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
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="restart" type="{}Yes_no" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
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
         * Obtient la valeur de la propriété timeout.
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
         * Définit la valeur de la propriété timeout.
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
         * Obtient la valeur de la propriété restart.
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
         * Définit la valeur de la propriété restart.
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
