Jenkins-WSO2-AAR-Deployer
=========================

Jenkins Plugin: Deploy Axis2 AAR Artifact to WSO2 Application Server

Prepare Jenkins:
----------------
1. You should start the WSO2 Server and open the carbon console in the browser. 
2. Copy the certificate to a local file, e.g. wso2-as.cert
3. Load the certificate into your keystore (it will go to ~/.keystore by default):<br>
   <tt>keytool -import -trustcacerts -alias wso2as-key -file wso2-as.cert</tt>
4. Add parameter in Jenkins for Maven to trust the certificate of the WSO2 AS server: Manage Jenkins > Configure System > Global MAVEN_OPTS:<br>
  -Djavax.net.ssl.trustStore=/home/mh/.keystore -Djavax.net.ssl.trustStorePassword=changeit

Build the Plugin
----------------
Get the sources via

<tt>git clone https://github.com/ma-ha/Jenkins-WSO2-AAR-Deployer.git</tt>

To build the plugin, simply call:

<tt>mvn clean install -Dmaven.test.skip=true</tt>

Use the Jenkins Plugin:
----------------------
1. Load the Plugin (target/Jenkins-WSO2-AAR-Deployer.hpi) into Jenkins (Manage Jenkins > Manage Plugins)
2. Restart Jenkins
3. Configure your project to use "Deploy AAR to WSO2 Server" as "Post-build Actions"
4. Fill out the configuration form

Warning: The plugin will also return "SUCCESS", even if the WSO2 Application Server detects the deployment as faulty. 
The reason can be an other service with same service name and different AAR file name. 
