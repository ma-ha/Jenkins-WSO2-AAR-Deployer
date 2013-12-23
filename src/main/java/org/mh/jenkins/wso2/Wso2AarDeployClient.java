package org.mh.jenkins.wso2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.wso2.carbon.aarservices.Exception_Exception;
import org.wso2.carbon.aarservices.ServiceUploaderPortType;
import org.wso2.carbon.aarservices.xsd.AARServiceData;
import org.wso2.carbon.aarservices.xsd.ObjectFactory;


public class Wso2AarDeployClient {

	public ServiceUploaderPortType uploadSvc;
	
	/** Constructor sets up the web service proxy client */
	public Wso2AarDeployClient(  String serviceUrl, String adminUser, String adminPwd ) {
	    System.out.println("Set up SOAP admin client...");   	
		
		Properties properties = System.getProperties();
		properties.put( "org.apache.cxf.stax.allowInsecureParser", "1" );
		System.setProperties( properties ); 
		

	    JaxWsProxyFactoryBean clientFactory = new JaxWsProxyFactoryBean(); 
        clientFactory.setAddress( serviceUrl+"ServiceUploader.ServiceUploaderHttpsEndpoint/" );
		clientFactory.setServiceClass( ServiceUploaderPortType.class );
		clientFactory.setUsername("admin");
		clientFactory.setPassword("admin");
		
		uploadSvc =	(ServiceUploaderPortType) clientFactory.create();
		
		Client clientProxy = ClientProxy.getClient( uploadSvc );
		
		HTTPConduit conduit = (HTTPConduit) clientProxy.getConduit();
		HTTPClientPolicy httpClientPolicy = conduit.getClient();
		httpClientPolicy.setAllowChunking(false);
		
		String targetAddr = conduit.getTarget().getAddress().getValue();
		if ( targetAddr.toLowerCase().startsWith("https:") ) {
			TrustManager[] simpleTrustManager = new TrustManager[] { 
					new X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
		
						public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
						}
		
						public void checkServerTrusted(
								java.security.cert.X509Certificate[] certs, String authType) {
						}
					} 
			};
			TLSClientParameters tlsParams = new TLSClientParameters();
			tlsParams.setTrustManagers(simpleTrustManager);
			tlsParams.setDisableCNCheck(true); //TODO enable CN check
			conduit.setTlsClientParameters(tlsParams);
		}
	}
	
	
	/**
	 * Upload artifact to AXIS service via WSO2 SOAP service
	 * @param artifactFileName Filename for AAR artifact to upload to WSO2 Server
	 * @param serviceHierarchy 
	 * @return
	 */
	public boolean uploadAAR(  InputStream fin, String targetFileName, String serviceHierarchy ) {
		boolean result = true;
		try {
			
			List<AARServiceData> serviceDataList = creRequestData( fin, targetFileName, serviceHierarchy );
			
			System.out.println( "Invoking uploadService for "+targetFileName+" ...");
			String callResult = uploadSvc.uploadService( serviceDataList );
			System.out.println( "Call result = "+callResult );
			
		} catch (Exception_Exception e) {
			result = false;
			e.printStackTrace();
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		}
		
		return result ;
	}
	
	
	/** helper factory to set up SOAP request data 
	 * @param serviceHierarchy 
	 * @throws IOException */
	private List<AARServiceData> creRequestData( InputStream fin, String targetFileName, String serviceHierarchy ) throws IOException {
        System.out.println( "Create SOAP request containing "+targetFileName+" ...");
        
        AARServiceData req = new AARServiceData();
        ObjectFactory dataFactory = new ObjectFactory();
        
        byte[] fileContent = readAARfile( fin );

        req.setDataHandler(  dataFactory.createAARServiceDataDataHandler( fileContent )  );
		req.setFileName( dataFactory.createAARServiceDataFileName( targetFileName  ) );
		req.setServiceHierarchy( dataFactory.createAARServiceDataServiceHierarchy( serviceHierarchy ) );
        
        List<AARServiceData> serviceDataList = new ArrayList<AARServiceData>();
		serviceDataList.add( req );
		
		return serviceDataList;
	}

	
	/** helper to read aar into byte array */
	private byte[] readAARfile( InputStream fin ) throws IOException {
		System.out.println( "Prepare data ...");
		byte fileContent[] = new byte[ (int) fin.available() ];
	    int cnt = fin.read( fileContent );
        System.out.println( "Read "+cnt+" bytes" );
        fin.close();
        
        return fileContent;
	}
	
	
}
