
package org.wso2.carbon.aarservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Exception" type="{http://aarservices.carbon.wso2.org}Exception" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "exception"
})
@XmlRootElement(name = "Exception")
public class Exception {

    @XmlElementRef(name = "Exception", namespace = "http://aarservices.carbon.wso2.org", type = JAXBElement.class, required = false)
    protected JAXBElement<Exception2> exception;

    /**
     * Gets the value of the exception property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Exception2 }{@code >}
     *     
     */
    public JAXBElement<Exception2> getException() {
        return exception;
    }

    /**
     * Sets the value of the exception property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Exception2 }{@code >}
     *     
     */
    public void setException(JAXBElement<Exception2> value) {
        this.exception = value;
    }

}
