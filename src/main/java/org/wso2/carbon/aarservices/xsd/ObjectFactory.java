
package org.wso2.carbon.aarservices.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wso2.carbon.aarservices.xsd package. 
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

    private final static QName _AARServiceDataServiceHierarchy_QNAME = new QName("http://aarservices.carbon.wso2.org/xsd", "serviceHierarchy");
    private final static QName _AARServiceDataFileName_QNAME = new QName("http://aarservices.carbon.wso2.org/xsd", "fileName");
    private final static QName _AARServiceDataDataHandler_QNAME = new QName("http://aarservices.carbon.wso2.org/xsd", "dataHandler");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wso2.carbon.aarservices.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AARServiceData }
     * 
     */
    public AARServiceData createAARServiceData() {
        return new AARServiceData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org/xsd", name = "serviceHierarchy", scope = AARServiceData.class)
    public JAXBElement<String> createAARServiceDataServiceHierarchy(String value) {
        return new JAXBElement<String>(_AARServiceDataServiceHierarchy_QNAME, String.class, AARServiceData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org/xsd", name = "fileName", scope = AARServiceData.class)
    public JAXBElement<String> createAARServiceDataFileName(String value) {
        return new JAXBElement<String>(_AARServiceDataFileName_QNAME, String.class, AARServiceData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aarservices.carbon.wso2.org/xsd", name = "dataHandler", scope = AARServiceData.class)
    public JAXBElement<byte[]> createAARServiceDataDataHandler(byte[] value) {
        return new JAXBElement<byte[]>(_AARServiceDataDataHandler_QNAME, byte[].class, AARServiceData.class, ((byte[]) value));
    }

}
