//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.23 alle 11:53:33 AM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per linkType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="linkType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="link_name" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="sourceNode" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="destinationNode" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="min_throughput" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="max_latency" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkType")
public class LinkType {

    @XmlAttribute(name = "link_name", required = true)
    protected String linkName;
    @XmlAttribute(name = "sourceNode", required = true)
    protected String sourceNode;
    @XmlAttribute(name = "destinationNode", required = true)
    protected String destinationNode;
    @XmlAttribute(name = "min_throughput")
    protected Float minThroughput;
    @XmlAttribute(name = "max_latency")
    protected Integer maxLatency;

    /**
     * Recupera il valore della proprietxE0 linkName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * Imposta il valore della proprietxE0 linkName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkName(String value) {
        this.linkName = value;
    }

    /**
     * Recupera il valore della proprietxE0 sourceNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceNode() {
        return sourceNode;
    }

    /**
     * Imposta il valore della proprietxE0 sourceNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceNode(String value) {
        this.sourceNode = value;
    }

    /**
     * Recupera il valore della proprietxE0 destinationNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationNode() {
        return destinationNode;
    }

    /**
     * Imposta il valore della proprietxE0 destinationNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationNode(String value) {
        this.destinationNode = value;
    }

    /**
     * Recupera il valore della proprietxE0 minThroughput.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMinThroughput() {
        return minThroughput;
    }

    /**
     * Imposta il valore della proprietxE0 minThroughput.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMinThroughput(Float value) {
        this.minThroughput = value;
    }

    /**
     * Recupera il valore della proprietxE0 maxLatency.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxLatency() {
        return maxLatency;
    }

    /**
     * Imposta il valore della proprietxE0 maxLatency.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxLatency(Integer value) {
        this.maxLatency = value;
    }

}
