//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="configuration.file" type="{}configuration.file"/>
 *           &lt;element name="configuration.directory" type="{}configuration.directory"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{}String" />
 *       &lt;attribute name="scheduler_id" use="required" type="{}String" />
 *       &lt;attribute name="cluster_member_id" type="{}String" />
 *       &lt;attribute name="ip" type="{}String" />
 *       &lt;attribute name="udp_port" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="interval" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configurationFileOrConfigurationDirectory"
})
@XmlRootElement(name = "supervisor.configuration.fetch")
public class SupervisorConfigurationFetch {

    @XmlElements({
        @XmlElement(name = "configuration.file", type = ConfigurationFile.class),
        @XmlElement(name = "configuration.directory", type = ConfigurationDirectory.class)
    })
    protected List<Object> configurationFileOrConfigurationDirectory;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "scheduler_id", required = true)
    protected String schedulerId;
    @XmlAttribute(name = "cluster_member_id")
    protected String clusterMemberId;
    @XmlAttribute(name = "ip")
    protected String ip;
    @XmlAttribute(name = "udp_port")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger udpPort;
    @XmlAttribute(name = "interval", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger interval;

    /**
     * Gets the value of the configurationFileOrConfigurationDirectory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configurationFileOrConfigurationDirectory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigurationFileOrConfigurationDirectory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigurationFile }
     * {@link ConfigurationDirectory }
     * 
     * 
     */
    public List<Object> getConfigurationFileOrConfigurationDirectory() {
        if (configurationFileOrConfigurationDirectory == null) {
            configurationFileOrConfigurationDirectory = new ArrayList<Object>();
        }
        return this.configurationFileOrConfigurationDirectory;
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
     * Obtient la valeur de la propriété clusterMemberId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterMemberId() {
        return clusterMemberId;
    }

    /**
     * Définit la valeur de la propriété clusterMemberId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterMemberId(String value) {
        this.clusterMemberId = value;
    }

    /**
     * Obtient la valeur de la propriété ip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp() {
        return ip;
    }

    /**
     * Définit la valeur de la propriété ip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp(String value) {
        this.ip = value;
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
     * Obtient la valeur de la propriété interval.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInterval() {
        return interval;
    }

    /**
     * Définit la valeur de la propriété interval.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInterval(BigInteger value) {
        this.interval = value;
    }

}
