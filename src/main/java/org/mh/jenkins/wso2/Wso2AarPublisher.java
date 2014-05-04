package org.mh.jenkins.wso2;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSet;
import hudson.maven.MavenModuleSetBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;


/**
 * Jenkins Plug-In to deploy an Axis2 AAR artifact to a WSO2 Server via SOAP admin API
 * 
 * @author mh
 *
 */
public class Wso2AarPublisher extends Recorder {

	public  String aarSource;
	public  String aarTargetFileName;
	public  String wso2URL;
	public  String wso2AdminUser;
	public  String wso2AdminPwd;
	public  String serviceHierarchy;

	/** Constructor using fields */
	@DataBoundConstructor
	public Wso2AarPublisher( String aarSource, String aarTargetFileName, String wso2URL, String wso2AdminUser, String wso2AdminPwd, String serviceHierarchy ) {
		super();
		this.aarSource = aarSource.trim();
		this.aarTargetFileName = aarTargetFileName.trim();
		this.wso2URL = wso2URL.trim();
		this.wso2AdminUser = wso2AdminUser.trim();
		this.wso2AdminPwd  = wso2AdminPwd.trim();
		this.serviceHierarchy = serviceHierarchy.trim();
	}


	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}


	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	// --------------------------------------------------------------------------------------------

	/** Check input params and tart the deployment */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean perform( AbstractBuild build, Launcher launcher, BuildListener listener ) throws InterruptedException, IOException {
		EnvVars env = build.getEnvironment( listener ); 	
		String xAarSource = aarSource;
		String xAarTargetFileName = aarTargetFileName;
		String xWso2URL = wso2URL;
		String xWso2AdminUser = wso2AdminUser;
		String xWso2AdminPwd = wso2AdminPwd;
		if ( build.getResult().isWorseOrEqualTo( Result.FAILURE) ) {
			listener.getLogger().println( "[WSO2 Deployer] WSO2 AS AAR upload: STOP, due to worse build result!" );
			return true; // nothing to do
		}
		listener.getLogger().println( "[WSO2 Deployer] WSO2 AS AAR upload initiated (baseDir="+build.getArtifactsDir().getPath()+")" );

		if ( StringUtils.isBlank( xAarTargetFileName ) ) {
			listener.error( "[WSO2 Deployer] AAR file name must be set!" ); 
			return false;
		} else {
			if ( xAarTargetFileName.startsWith( "$" ) ) {
				String envVar = xAarTargetFileName.substring( 1 );
				listener.getLogger().println( "[WSO2 Deployer] 'AAR Target File Name' from env var: "+envVar );
				xAarTargetFileName = env.get( envVar );
			}
		}
		if ( StringUtils.isBlank( xAarSource ) ) {
			listener.error( "[WSO2 Deployer] AAR source name must be set!" ); 
			return false;
		} else {
			if ( xAarSource.startsWith( "$" ) ) {
				String envVar = xAarSource.substring( 1 );
				listener.getLogger().println( "[WSO2 Deployer] 'AAR Source' from env var: "+envVar );
				xAarSource = env.get( envVar );
			}
		}
		if ( StringUtils.isBlank( xWso2URL ) ) {
			listener.error( "[WSO2 Deployer] WSO2 server URL must be set!" ); 
			return false;
		} else {
			
			if ( xWso2URL.startsWith( "$" ) ) {
				String envVar = xWso2URL.substring( 1 );
				listener.getLogger().println( "[WSO2 Deployer] 'WSO2 Server URL' from env var: "+envVar );
		        xWso2URL = env.get( envVar );
			}
			
			if ( ! xWso2URL.endsWith("/") ) {
				xWso2URL += "/";
			}

		}
		// Validates that the organization token is filled in the project configuration.
		if ( StringUtils.isBlank( xWso2AdminUser ) ) {
			listener.error( "[WSO2 Deployer] Admin user name must be set!" ); 
			return false;
		} else {
			if ( xWso2AdminUser.startsWith( "$" ) ) {
				String envVar = xWso2AdminUser.substring( 1 );
				listener.getLogger().println( "[WSO2 Deployer] 'WSO2 Admin User' from env var: "+envVar );
				xWso2AdminUser = env.get( envVar );
			}
		}
		// Validates that the organization token is filled in the project configuration.
		if ( StringUtils.isBlank( xWso2AdminPwd ) ) {
			listener.error( "[WSO2 Deployer] Admin password must be set!" ); 
			return false;
		} else {
			if ( xWso2AdminPwd.startsWith( "$" ) ) {
				String envVar = xWso2AdminPwd.substring( 1 );
				listener.getLogger().println( "[WSO2 Deployer] 'WSO2 Admin Password' from env var: "+envVar );
				xWso2AdminPwd = env.get( envVar );
			}
		}

		String version = artifactVersion( build, listener );

		boolean result = true;

		FilePath[] aarList = build.getWorkspace().list( xAarSource );
		if ( aarList.length == 0 ) {
			listener.error( "[WSO2 Deployer] No AAR file found for '"+xAarSource+"'" );   
			return false;
		} else if ( aarList.length != 1  ) {
			listener.error( "[WSO2 Deployer] Multiple AAR files found for '"+xAarSource+"'" );   
			for ( FilePath aarFile : aarList ) {
				listener.getLogger().println( "AAR is n="+aarFile.toURI() );
			}
			return false;
		} else {
			for ( FilePath aarFile : aarList ) {
				listener.getLogger().println( "[WSO2 Deployer] WSO2 URL = "+ xWso2URL );
				listener.getLogger().println( "[WSO2 Deployer] AAR is   = "+ aarFile.toURI() );
				listener.getLogger().println( "[WSO2 Deployer] AAR ver  = "+ version );
				listener.getLogger().println( "[WSO2 Deployer] AAR size = "+ aarFile.length() );

				InputStream fileIs = aarFile.read();

				Wso2AarDeployClient deployer = new Wso2AarDeployClient( xWso2URL, xWso2AdminUser, xWso2AdminPwd, listener );
				deployer.uploadAAR( fileIs, xAarTargetFileName, serviceHierarchy );
			}
		}
		return result;
	}


	/** helper to get the version of the artifact from pom definition */
	@SuppressWarnings("rawtypes")
	private String artifactVersion( AbstractBuild build, BuildListener listener ) {
		String version = "1.0";
		if ( build instanceof MavenModuleSetBuild ) {
			try {
				MavenModuleSetBuild mavenBuild = (MavenModuleSetBuild) build;
				MavenModuleSet parent = mavenBuild.getParent();
				Collection<MavenModule> modules = parent.getModules();
				MavenModule module = modules.iterator().next();
				version = module.getVersion();
				listener.getLogger().println( "[WSO2 Deployer] "+aarTargetFileName+" version: "+version );
			} catch (Exception e) {
				e.printStackTrace();
				listener.getLogger().println( "[WSO2 Deployer] Waning: Version is set to default (1.0)" );
			}
		} else {
			listener.getLogger().println( "[WSO2 Deployer] Waning: Version is set to default (1.0)" );
		}
		return version;
	}

	// --------------------------------------------------------------------------------------------

	@Extension // This indicates to Jenkins that this is an implementation of an extension point.
	public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

		public DescriptorImpl() {
			super( Wso2AarPublisher.class );
			load();
		}

		public boolean isApplicable( Class<? extends AbstractProject> aClass ) {
			// Indicates that this builder can be used with all kinds of project types
			return true;
		}

		@Override
		public boolean configure( StaplerRequest req, JSONObject json ) throws FormException {
			req.bindJSON(this, json);
			save();
			return true;
		}

		/** This human readable name is used in the configuration screen. */
		public String getDisplayName() {
			return "Deploy AAR to WSO2 Server";
		}
	}




}
