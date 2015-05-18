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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="heart_beat_timeout" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="heart_beat_own_timeout">
 *         &lt;simpleType>
 *           &lt;union memberTypes=" {http://www.w3.org/2001/XMLSchema}positiveInteger">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                 &lt;enumeration value="never"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/union>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="heart_beat_warn_timeout" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
