
package com.sg.cdf.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sg.cdf.wsclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Distribute_QNAME = new QName("http://service.ws.cdf.sg.com/", "distribute");
    private final static QName _DistributeResponse_QNAME = new QName("http://service.ws.cdf.sg.com/", "distributeResponse");
    private final static QName _NameSpaceClas_QNAME = new QName("http://service.ws.cdf.sg.com", "clas");
    private final static QName _NameSpaceBundle_QNAME = new QName("http://service.ws.cdf.sg.com", "bundle");
    private final static QName _RequestBuilderExtensionId_QNAME = new QName("http://service.ws.cdf.sg.com", "extensionId");
    private final static QName _RequestBuilderPersistence_QNAME = new QName("http://service.ws.cdf.sg.com", "persistence");
    private final static QName _RequestBuilderName_QNAME = new QName("http://service.ws.cdf.sg.com", "name");
    private final static QName _RequestBuilderContentProvider_QNAME = new QName("http://service.ws.cdf.sg.com", "contentProvider");
    private final static QName _RequestBuilderParameters_QNAME = new QName("http://service.ws.cdf.sg.com", "parameters");
    private final static QName _RequestBuilderDistributors_QNAME = new QName("http://service.ws.cdf.sg.com", "distributors");
    private final static QName _ParameterValue_QNAME = new QName("http://service.ws.cdf.sg.com", "value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sg.cdf.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestBuilder }
     * 
     */
    public RequestBuilder createRequestBuilder() {
        return new RequestBuilder();
    }

    /**
     * Create an instance of {@link ArrayOfNameSpace }
     * 
     */
    public ArrayOfNameSpace createArrayOfNameSpace() {
        return new ArrayOfNameSpace();
    }

    /**
     * Create an instance of {@link ArrayOfParameter }
     * 
     */
    public ArrayOfParameter createArrayOfParameter() {
        return new ArrayOfParameter();
    }

    /**
     * Create an instance of {@link Parameter }
     * 
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link NameSpace }
     * 
     */
    public NameSpace createNameSpace() {
        return new NameSpace();
    }

    /**
     * Create an instance of {@link Distribute }
     * 
     */
    public Distribute createDistribute() {
        return new Distribute();
    }

    /**
     * Create an instance of {@link DistributeResponse }
     * 
     */
    public DistributeResponse createDistributeResponse() {
        return new DistributeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Distribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com/", name = "distribute")
    public JAXBElement<Distribute> createDistribute(Distribute value) {
        return new JAXBElement<Distribute>(_Distribute_QNAME, Distribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DistributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com/", name = "distributeResponse")
    public JAXBElement<DistributeResponse> createDistributeResponse(DistributeResponse value) {
        return new JAXBElement<DistributeResponse>(_DistributeResponse_QNAME, DistributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "clas", scope = NameSpace.class)
    public JAXBElement<String> createNameSpaceClas(String value) {
        return new JAXBElement<String>(_NameSpaceClas_QNAME, String.class, NameSpace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "bundle", scope = NameSpace.class)
    public JAXBElement<String> createNameSpaceBundle(String value) {
        return new JAXBElement<String>(_NameSpaceBundle_QNAME, String.class, NameSpace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "extensionId", scope = RequestBuilder.class)
    public JAXBElement<String> createRequestBuilderExtensionId(String value) {
        return new JAXBElement<String>(_RequestBuilderExtensionId_QNAME, String.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "persistence", scope = RequestBuilder.class)
    public JAXBElement<NameSpace> createRequestBuilderPersistence(NameSpace value) {
        return new JAXBElement<NameSpace>(_RequestBuilderPersistence_QNAME, NameSpace.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "name", scope = RequestBuilder.class)
    public JAXBElement<String> createRequestBuilderName(String value) {
        return new JAXBElement<String>(_RequestBuilderName_QNAME, String.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "contentProvider", scope = RequestBuilder.class)
    public JAXBElement<NameSpace> createRequestBuilderContentProvider(NameSpace value) {
        return new JAXBElement<NameSpace>(_RequestBuilderContentProvider_QNAME, NameSpace.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "parameters", scope = RequestBuilder.class)
    public JAXBElement<ArrayOfParameter> createRequestBuilderParameters(ArrayOfParameter value) {
        return new JAXBElement<ArrayOfParameter>(_RequestBuilderParameters_QNAME, ArrayOfParameter.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfNameSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "distributors", scope = RequestBuilder.class)
    public JAXBElement<ArrayOfNameSpace> createRequestBuilderDistributors(ArrayOfNameSpace value) {
        return new JAXBElement<ArrayOfNameSpace>(_RequestBuilderDistributors_QNAME, ArrayOfNameSpace.class, RequestBuilder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "name", scope = Parameter.class)
    public JAXBElement<String> createParameterName(String value) {
        return new JAXBElement<String>(_RequestBuilderName_QNAME, String.class, Parameter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cdf.sg.com", name = "value", scope = Parameter.class)
    public JAXBElement<String> createParameterValue(String value) {
        return new JAXBElement<String>(_ParameterValue_QNAME, String.class, Parameter.class, value);
    }

}
