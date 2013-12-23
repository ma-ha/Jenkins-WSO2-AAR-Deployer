
package org.wso2.carbon.aarservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wso2.carbon.aarservices package. 
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

    private final static QName _UploadServiceResponseReturn_QNAME = new QName("http://aarservices.carbon.wso2.org", "return");
    private final static QName _Exception2Message_QNAME = new QName("http://aarservices.carbon.wso2.org", "Message");
    private final static QName _ExceptionException_QNAME = new QName("http://aarservices.carbon.wso2.org", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wso2.carbon.aarservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link Exception2 }
     * 
     */
    public Exception2 createException2() {
        return new Exception2();
    }

    /**
     * Create an instance of {@link UploadService }
     * 
     */
    public UploadService createUploadService() {
        return new UploadService();
    }

    /**
     * Create an instance of {@link UploadServiceResponse }
     * 
     */
    public UploadServiceResponse createUploadServiceResponse() {
        return new UploadServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org", name = "return", scope = UploadServiceResponse.class)
    public JAXBElement<String> createUploadServiceResponseReturn(String value) {
        return new JAXBElement<String>(_UploadServiceResponseReturn_QNAME, String.class, UploadServiceResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org", name = "Message", scope = Exception2 .class)
    public JAXBElement<String> createException2Message(String value) {
        return new JAXBElement<String>(_Exception2Message_QNAME, String.class, Exception2 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org", name = "Exception", scope = Exception.class)
    public JAXBElement<Exception2> createExceptionException(Exception2 value) {
        return new JAXBElement<Exception2>(_ExceptionException_QNAME, Exception2 .class, Exception.class, value);
    }

}
