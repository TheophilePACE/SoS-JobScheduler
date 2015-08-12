//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence>
 *         &lt;element name="run_time" type="{}run_time" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="job" use="required" type="{}Path" />
 *       &lt;attribute name="cmd">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="stop"/>
 *             &lt;enumeration value="unstop"/>
 *             &lt;enumeration value="start"/>
 *             &lt;enumeration value="wake"/>
 *             &lt;enumeration value="wake_when_in_period"/>
 *             &lt;enumeration value="end"/>
 *             &lt;enumeration value="suspend"/>
 *             &lt;enumeration value="continue"/>
 *             &lt;enumeration value="reread"/>
 *             &lt;enumeration value="remove"/>
 *             &lt;enumeration value="enable"/>
 *             &lt;enumeration value="disable"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "runTime"
})
@XmlRootElement(name = "modify_job")
public class ModifyJob {

    @XmlElement(name = "run_time")
    protected RunTime runTime;
    @XmlAttribute(name = "job", required = true)
    protected String job;
    @XmlAttribute(name = "cmd")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String cmd;

    /**
     * Obtient la valeur de la propriété runTime.
     * 
     * @return
     *     possible object is
     *     {@link RunTime }
     *     
     */
    public RunTime getRunTime() {
        return runTime;
    }

    /**
     * Définit la valeur de la propriété runTime.
     * 
     * @param value
     *     allowed object is
     *     {@link RunTime }
     *     
     */
    public void setRunTime(RunTime value) {
        this.runTime = value;
    }

    /**
     * Obtient la valeur de la propriété job.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJob() {
        return job;
    }

    /**
     * Définit la valeur de la propriété job.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJob(String value) {
        this.job = value;
    }

    /**
     * Obtient la valeur de la propriété cmd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Définit la valeur de la propriété cmd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmd(String value) {
        this.cmd = value;
    }

}
