//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.17 alle 11:24:50 AM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nffg" type="{http://www.example.org/nfvInfo}nffgType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="in" type="{http://www.example.org/nfvInfo}inType"/&gt;
 *         &lt;element name="catalog" type="{http://www.example.org/nfvInfo}catalogType"/&gt;
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
    "nffg",
    "in",
    "catalog"
})
@XmlRootElement(name = "networkProvider")
public class NetworkProvider {

    protected List<NffgType> nffg;
    @XmlElement(required = true)
    protected InType in;
    @XmlElement(required = true)
    protected CatalogType catalog;

    /**
     * Gets the value of the nffg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nffg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNffg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NffgType }
     * 
     * 
     */
    public List<NffgType> getNffg() {
        if (nffg == null) {
            nffg = new ArrayList<NffgType>();
        }
        return this.nffg;
    }

    /**
     * Recupera il valore della proprietxE0 in.
     * 
     * @return
     *     possible object is
     *     {@link InType }
     *     
     */
    public InType getIn() {
        return in;
    }

    /**
     * Imposta il valore della proprietxE0 in.
     * 
     * @param value
     *     allowed object is
     *     {@link InType }
     *     
     */
    public void setIn(InType value) {
        this.in = value;
    }

    /**
     * Recupera il valore della proprietxE0 catalog.
     * 
     * @return
     *     possible object is
     *     {@link CatalogType }
     *     
     */
    public CatalogType getCatalog() {
        return catalog;
    }

    /**
     * Imposta il valore della proprietxE0 catalog.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogType }
     *     
     */
    public void setCatalog(CatalogType value) {
        this.catalog = value;
    }

}
