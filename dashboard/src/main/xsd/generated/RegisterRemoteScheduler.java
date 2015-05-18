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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence&gt;
 *         &lt;element name="ERROR" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{}String" /&gt;
 *       &lt;attribute name="scheduler_id" type="{}String" /&gt;
 *       &lt;attribute name="tcp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="is_cluster_member" type="{}String" /&gt;
 *       &lt;attribute name="logoff" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Obtient la valeur de la propri�t� error.
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
     * D�finit la valeur de la propri�t� error.
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
     * Obtient la valeur de la propri�t� version.
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
     * D�finit la valeur de la propri�t� version.
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
     * Obtient la valeur de la propri�t� schedulerId.
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
     * D�finit la valeur de la propri�t� schedulerId.
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
     * Obtient la valeur de la propri�t� tcpPort.
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
     * D�finit la valeur de la propri�t� tcpPort.
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
     * Obtient la valeur de la propri�t� udpPort.
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
     * D�finit la valeur de la propri�t� udpPort.
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
     * Obtient la valeur de la propri�t� isClusterMember.
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
     * D�finit la valeur de la propri�t� isClusterMember.
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
     * Obtient la valeur de la propri�t� logoff.
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
     * D�finit la valeur de la propri�t� logoff.
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
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ERROR {


    }

}
