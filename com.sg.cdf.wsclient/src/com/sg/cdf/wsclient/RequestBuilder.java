
package com.sg.cdf.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RequestBuilder complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RequestBuilder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentProvider" type="{http://service.ws.cdf.sg.com}NameSpace" minOccurs="0"/>
 *         &lt;element name="distributors" type="{http://service.ws.cdf.sg.com}ArrayOfNameSpace" minOccurs="0"/>
 *         &lt;element name="extensionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://service.ws.cdf.sg.com}ArrayOfParameter" minOccurs="0"/>
 *         &lt;element name="persistence" type="{http://service.ws.cdf.sg.com}NameSpace" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestBuilder", propOrder = {
    "contentProvider",
    "distributors",
    "extensionId",
    "name",
    "parameters",
    "persistence"
})
public class RequestBuilder {

    @XmlElementRef(name = "contentProvider", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<NameSpace> contentProvider;
    @XmlElementRef(name = "distributors", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfNameSpace> distributors;
    @XmlElementRef(name = "extensionId", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> extensionId;
    @XmlElementRef(name = "name", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> name;
    @XmlElementRef(name = "parameters", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfParameter> parameters;
    @XmlElementRef(name = "persistence", namespace = "http://service.ws.cdf.sg.com", type = JAXBElement.class, required = false)
    protected JAXBElement<NameSpace> persistence;

    /**
     * 获取contentProvider属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link NameSpace }{@code >}
     *     
     */
    public JAXBElement<NameSpace> getContentProvider() {
        return contentProvider;
    }

    /**
     * 设置contentProvider属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link NameSpace }{@code >}
     *     
     */
    public void setContentProvider(JAXBElement<NameSpace> value) {
        this.contentProvider = value;
    }

    /**
     * 获取distributors属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfNameSpace }{@code >}
     *     
     */
    public JAXBElement<ArrayOfNameSpace> getDistributors() {
        return distributors;
    }

    /**
     * 设置distributors属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfNameSpace }{@code >}
     *     
     */
    public void setDistributors(JAXBElement<ArrayOfNameSpace> value) {
        this.distributors = value;
    }

    /**
     * 获取extensionId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getExtensionId() {
        return extensionId;
    }

    /**
     * 设置extensionId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setExtensionId(JAXBElement<String> value) {
        this.extensionId = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setName(JAXBElement<String> value) {
        this.name = value;
    }

    /**
     * 获取parameters属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfParameter }{@code >}
     *     
     */
    public JAXBElement<ArrayOfParameter> getParameters() {
        return parameters;
    }

    /**
     * 设置parameters属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfParameter }{@code >}
     *     
     */
    public void setParameters(JAXBElement<ArrayOfParameter> value) {
        this.parameters = value;
    }

    /**
     * 获取persistence属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link NameSpace }{@code >}
     *     
     */
    public JAXBElement<NameSpace> getPersistence() {
        return persistence;
    }

    /**
     * 设置persistence属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link NameSpace }{@code >}
     *     
     */
    public void setPersistence(JAXBElement<NameSpace> value) {
        this.persistence = value;
    }

}
