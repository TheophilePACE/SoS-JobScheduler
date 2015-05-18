//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="heart_beat_timeout" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="heart_beat_own_timeout"&gt;
 *         &lt;simpleType&gt;
 *           &lt;union memberTypes=" {http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *             &lt;simpleType&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                 &lt;enumeration value="never"/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/simpleType&gt;
 *           &lt;/union&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="heart_beat_warn_timeout" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "cluster")
public class Cluster {

    @XmlAttribute(name = "heart_beat_timeout")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger heartBeatTimeout;
    @XmlAttribute(name = "heart_beat_own_timeout")
    protected String heartBeatOwnTimeout;
    @XmlAttribute(name = "heart_beat_warn_timeout")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger heartBeatWarnTimeout;

    /**
     * Obtient la valeur de la propriété heartBeatTimeout.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    /**
     * Définit la valeur de la propriété heartBeatTimeout.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHeartBeatTimeout(BigInteger value) {
        this.heartBeatTimeout = value;
    }

    /**
     * Obtient la valeur de la propriété heartBeatOwnTimeout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeartBeatOwnTimeout() {
        return heartBeatOwnTimeout;
    }

    /**
     * Définit la valeur de la propriété heartBeatOwnTimeout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeartBeatOwnTimeout(String value) {
        this.heartBeatOwnTimeout = value;
    }

    /**
     * Obtient la valeur de la propriété heartBeatWarnTimeout.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHeartBeatWarnTimeout() {
        return heartBeatWarnTimeout;
    }

    /**
     * Définit la valeur de la propriété heartBeatWarnTimeout.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHeartBeatWarnTimeout(BigInteger value) {
        this.heartBeatWarnTimeout = value;
    }

}
