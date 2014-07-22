
package com.sg.cdf.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>NameSpace complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="NameSpace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bundle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameSpace", propOrder = {
    "bundle",
    "clas"
})
public class NameSpace {

    @XmlElementRef(name = "bundle", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bundle;
    @XmlElementRef(name = "clas", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> clas;

    /**
     * 获取bundle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBundle() {
        return bundle;
    }

    /**
     * 设置bundle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBundle(JAXBElement<String> value) {
        this.bundle = value;
    }

    /**
     * 获取clas属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClas() {
        return clas;
    }

    /**
     * 设置clas属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClas(JAXBElement<String> value) {
        this.clas = value;
    }

}
