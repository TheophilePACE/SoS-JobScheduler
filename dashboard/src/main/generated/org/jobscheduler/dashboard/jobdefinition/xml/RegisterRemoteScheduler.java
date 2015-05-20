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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence>
 *         &lt;element name="ERROR" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{}String" />
 *       &lt;attribute name="scheduler_id" type="{}String" />
 *       &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="is_cluster_member" type="{}String" />
 *       &lt;attribute name="logoff" type="{}Yes_no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "error"
})
@XmlRootElement(name = "register_remote_scheduler")
public class RegisterRemoteScheduler {

    @XmlElement(name = "ERROR")
    protected RegisterRemoteScheduler.ERROR error;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "scheduler_id")
    protected String schedulerId;
    @XmlAttribute(name = "tcp_port")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger tcpPort;
    @XmlAttribute(name = "udp_port")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger udpPort;
    @XmlAttribute(name = "is_cluster_member")
    protected String isClusterMember;
    @XmlAttribute(name = "logoff")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String logoff;

    /**
     * Obtient la valeur de la propriété error.
     * 
     * @return
     *     possible object is
     *     {@link RegisterRemoteScheduler.ERROR }
     *     
     */
    public RegisterRemoteScheduler.ERROR getERROR() {
        return error;
    }

    /**
     * Définit la valeur de la propriété error.
     * 
     * @param value
     *     allowed object is
     *     {@link RegisterRemoteScheduler.ERROR }
     *     
     */
    public void setERROR(RegisterRemoteScheduler.ERROR value) {
        this.error = value;
    }

    /**
     * Obtient la valeur de la propriété version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Définit la valeur de la propriété version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtient la valeur de la propriété schedulerId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedulerId() {
        return schedulerId;
    }

    /**
     * Définit la valeur de la propriété schedulerId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedulerId(String value) {
        this.schedulerId = value;
    }

    /**
     * Obtient la valeur de la propriété tcpPort.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTcpPort() {
        return tcpPort;
    }

    /**
     * Définit la valeur de la propriété tcpPort.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTcpPort(BigInteger value) {
        this.tcpPort = value;
    }

    /**
     * Obtient la valeur de la propriété udpPort.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUdpPort() {
        return udpPort;
    }

    /**
     * Définit la valeur de la propriété udpPort.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUdpPort(BigInteger value) {
        this.udpPort = value;
    }

    /**
     * Obtient la valeur de la propriété isClusterMember.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsClusterMember() {
        return isClusterMember;
    }

    /**
     * Définit la valeur de la propriété isClusterMember.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsClusterMember(String value) {
        this.isClusterMember = value;
    }

    /**
     * Obtient la valeur de la propriété logoff.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoff() {
        return logoff;
    }

    /**
     * Définit la valeur de la propriété logoff.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoff(String value) {
        this.logoff = value;
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
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ERROR {


    }

}
