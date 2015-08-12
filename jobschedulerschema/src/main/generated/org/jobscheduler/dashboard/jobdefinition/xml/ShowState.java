//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Show_state complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Show_state">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="what" type="{}String" />
 *       &lt;attribute name="subsystems" type="{}String" />
 *       &lt;attribute name="max_orders" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="max_order_history" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="max_task_history" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="path" type="{}Path" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Show_state")
public class ShowState {

    @XmlAttribute(name = "what")
    protected String what;
    @XmlAttribute(name = "subsystems")
    protected String subsystems;
    @XmlAttribute(name = "max_orders")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxOrders;
    @XmlAttribute(name = "max_order_history")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxOrderHistory;
    @XmlAttribute(name = "max_task_history")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxTaskHistory;
    @XmlAttribute(name = "path")
    protected String path;

    /**
     * Obtient la valeur de la propriété what.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhat() {
        return what;
    }

    /**
     * Définit la valeur de la propriété what.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhat(String value) {
        this.what = value;
    }

    /**
     * Obtient la valeur de la propriété subsystems.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubsystems() {
        return subsystems;
    }

    /**
     * Définit la valeur de la propriété subsystems.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubsystems(String value) {
        this.subsystems = value;
    }

    /**
     * Obtient la valeur de la propriété maxOrders.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOrders() {
        return maxOrders;
    }

    /**
     * Définit la valeur de la propriété maxOrders.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOrders(BigInteger value) {
        this.maxOrders = value;
    }

    /**
     * Obtient la valeur de la propriété maxOrderHistory.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOrderHistory() {
        return maxOrderHistory;
    }

    /**
     * Définit la valeur de la propriété maxOrderHistory.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOrderHistory(BigInteger value) {
        this.maxOrderHistory = value;
    }

    /**
     * Obtient la valeur de la propriété maxTaskHistory.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxTaskHistory() {
        return maxTaskHistory;
    }

    /**
     * Définit la valeur de la propriété maxTaskHistory.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxTaskHistory(BigInteger value) {
        this.maxTaskHistory = value;
    }

    /**
     * Obtient la valeur de la propriété path.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Définit la valeur de la propriété path.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

}
