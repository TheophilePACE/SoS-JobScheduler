//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.04.28 � 05:45:56 AM EDT 
//


package generated;

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
 *       &lt;attribute name="process_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="kill" type="{}Yes_no" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "remote_scheduler.remote_task.close")
public class RemoteSchedulerRemoteTaskClose {

    @XmlAttribute(name = "process_id", required = true)
    protected long processId;
    @XmlAttribute(name = "kill")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String kill;

    /**
     * Obtient la valeur de la propri�t� processId.
     * 
     */
    public long getProcessId() {
        return processId;
    }

    /**
     * D�finit la valeur de la propri�t� processId.
     * 
     */
    public void setProcessId(long value) {
        this.processId = value;
    }

    /**
     * Obtient la valeur de la propri�t� kill.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKill() {
        return kill;
    }

    /**
     * D�finit la valeur de la propri�t� kill.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKill(String value) {
        this.kill = value;
    }

}
