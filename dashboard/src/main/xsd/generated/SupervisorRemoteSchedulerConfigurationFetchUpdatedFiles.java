//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.04.28 à 05:45:56 AM EDT 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="configuration.file" type="{}configuration.file"/&gt;
 *           &lt;element name="configuration.directory" type="{}configuration.directory"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configurationFileOrConfigurationDirectory"
})
@XmlRootElement(name = "supervisor.remote_scheduler.configuration.fetch_updated_files")
public class SupervisorRemoteSchedulerConfigurationFetchUpdatedFiles {

    @XmlElements({
        @XmlElement(name = "configuration.file", type = ConfigurationFile.class),
        @XmlElement(name = "configuration.directory", type = ConfigurationDirectory.class)
    })
    protected List<Object> configurationFileOrConfigurationDirectory;

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

}
