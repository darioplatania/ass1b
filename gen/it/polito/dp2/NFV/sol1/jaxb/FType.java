//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.17 alle 11:24:50 AM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per fType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="fType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="functionaltypeId" use="required" type="{http://www.example.org/nfvInfo}name" /&gt;
 *       &lt;attribute name="functionalTypeName" use="required" type="{http://www.example.org/nfvInfo}NodeFunctionalType" /&gt;
 *       &lt;attribute name="required_memory" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="required_storage" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fType")
public class FType {

    @XmlAttribute(name = "functionaltypeId", required = true)
    protected String functionaltypeId;
    @XmlAttribute(name = "functionalTypeName", required = true)
    protected NodeFunctionalType functionalTypeName;
    @XmlAttribute(name = "required_memory", required = true)
    protected int requiredMemory;
    @XmlAttribute(name = "required_storage", required = true)
    protected int requiredStorage;

    /**
     * Recupera il valore della proprietxE0 functionaltypeId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionaltypeId() {
        return functionaltypeId;
    }

    /**
     * Imposta il valore della proprietxE0 functionaltypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionaltypeId(String value) {
        this.functionaltypeId = value;
    }

    /**
     * Recupera il valore della proprietxE0 functionalTypeName.
     * 
     * @return
     *     possible object is
     *     {@link NodeFunctionalType }
     *     
     */
    public NodeFunctionalType getFunctionalTypeName() {
        return functionalTypeName;
    }

    /**
     * Imposta il valore della proprietxE0 functionalTypeName.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeFunctionalType }
     *     
     */
    public void setFunctionalTypeName(NodeFunctionalType value) {
        this.functionalTypeName = value;
    }

    /**
     * Recupera il valore della proprietxE0 requiredMemory.
     * 
     */
    public int getRequiredMemory() {
        return requiredMemory;
    }

    /**
     * Imposta il valore della proprietxE0 requiredMemory.
     * 
     */
    public void setRequiredMemory(int value) {
        this.requiredMemory = value;
    }

    /**
     * Recupera il valore della proprietxE0 requiredStorage.
     * 
     */
    public int getRequiredStorage() {
        return requiredStorage;
    }

    /**
     * Imposta il valore della proprietxE0 requiredStorage.
     * 
     */
    public void setRequiredStorage(int value) {
        this.requiredStorage = value;
    }

}
