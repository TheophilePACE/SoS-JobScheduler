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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="http.authentication" type="{}http.authentication"/&gt;
 *         &lt;element name="web_service" type="{}web_service"/&gt;
 *         &lt;element name="http_directory" type="{}http_directory"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "httpAuthenticationOrWebServiceOrHttpDirectory"
})
@XmlRootElement(name = "http_server")
public class HttpServer {

    @XmlElements({
        @XmlElement(name = "http.authentication", type = HttpAuthentication.class),
        @XmlElement(name = "web_service", type = WebService.class),
        @XmlElement(name = "http_directory", type = HttpDirectory.class)
    })
    protected List<Object> httpAuthenticationOrWebServiceOrHttpDirectory;

    /**
     * Gets the value of the httpAuthenticationOrWebServiceOrHttpDirectory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the httpAuthenticationOrWebServiceOrHttpDirectory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHttpAuthenticationOrWebServiceOrHttpDirectory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HttpAuthentication }
     * {@link WebService }
     * {@link HttpDirectory }
     * 
     * 
     */
    public List<Object> getHttpAuthenticationOrWebServiceOrHttpDirectory() {
        if (httpAuthenticationOrWebServiceOrHttpDirectory == null) {
            httpAuthenticationOrWebServiceOrHttpDirectory = new ArrayList<Object>();
        }
        return this.httpAuthenticationOrWebServiceOrHttpDirectory;
    }

}
