//
// Questo file xe8 stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0-b170531.0717 
// Vedere <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Qualsiasi modifica a questo file andrxe0 persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.12.09 alle 07:42:25 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeFunctionalType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="NodeFunctionalType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CACHE"/&gt;
 *     &lt;enumeration value="DPI"/&gt;
 *     &lt;enumeration value="FW"/&gt;
 *     &lt;enumeration value="MAIL_CLIENT"/&gt;
 *     &lt;enumeration value="MAIL_SERVER"/&gt;
 *     &lt;enumeration value="NAT"/&gt;
 *     &lt;enumeration value="SPAM"/&gt;
 *     &lt;enumeration value="VPN"/&gt;
 *     &lt;enumeration value="WEB_CLIENT"/&gt;
 *     &lt;enumeration value="WEB_SERVER"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NodeFunctionalType")
@XmlEnum
public enum NodeFunctionalType {

    CACHE,
    DPI,
    FW,
    MAIL_CLIENT,
    MAIL_SERVER,
    NAT,
    SPAM,
    VPN,
    WEB_CLIENT,
    WEB_SERVER;

    public String value() {
        return name();
    }

    public static NodeFunctionalType fromValue(String v) {
        return valueOf(v);
    }

}
