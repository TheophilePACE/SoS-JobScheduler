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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Show_state complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Show_state"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="what" type="{}String" /&gt;
 *       &lt;attribute name="subsystems" type="{}String" /&gt;
 *       &lt;attribute name="max_orders" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="max_order_history" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="max_task_history" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="path" type="{}Path" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Obtient la valeur de la propri�t� what.
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
     * D�finit la valeur de la propri�t� what.
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
     * Obtient la valeur de la propri�t� subsystems.
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
     * D�finit la valeur de la propri�t� subsystems.
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
     * Obtient la valeur de la propri�t� maxOrders.
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
     * D�finit la valeur de la propri�t� maxOrders.
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
     * Obtient la valeur de la propri�t� maxOrderHistory.
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
     * D�finit la valeur de la propri�t� maxOrderHistory.
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
     * Obtient la valeur de la propri�t� maxTaskHistory.
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
     * D�finit la valeur de la propri�t� maxTaskHistory.
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
     * Obtient la valeur de la propri�t� path.
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
     * D�finit la valeur de la propri�t� path.
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
