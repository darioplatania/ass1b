//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.11.16 alle 10:45:49 AM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per hostType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="hostType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="hostName" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="number_VNFs" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="memory" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="disk_storage" use="required" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hostType")
public class HostType {

    @XmlAttribute(name = "hostName", required = true)
    protected String hostName;
    @XmlAttribute(name = "number_VNFs", required = true)
    protected int numberVNFs;
    @XmlAttribute(name = "memory", required = true)
    protected float memory;
    @XmlAttribute(name = "disk_storage", required = true)
    protected float diskStorage;

    /**
     * Recupera il valore della proprietxE0 hostName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Imposta il valore della proprietxE0 hostName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * Recupera il valore della proprietxE0 numberVNFs.
     * 
     */
    public int getNumberVNFs() {
        return numberVNFs;
    }

    /**
     * Imposta il valore della proprietxE0 numberVNFs.
     * 
     */
    public void setNumberVNFs(int value) {
        this.numberVNFs = value;
    }

    /**
     * Recupera il valore della proprietxE0 memory.
     * 
     */
    public float getMemory() {
        return memory;
    }

    /**
     * Imposta il valore della proprietxE0 memory.
     * 
     */
    public void setMemory(float value) {
        this.memory = value;
    }

    /**
     * Recupera il valore della proprietxE0 diskStorage.
     * 
     */
    public float getDiskStorage() {
        return diskStorage;
    }

    /**
     * Imposta il valore della proprietxE0 diskStorage.
     * 
     */
    public void setDiskStorage(float value) {
        this.diskStorage = value;
    }

}
