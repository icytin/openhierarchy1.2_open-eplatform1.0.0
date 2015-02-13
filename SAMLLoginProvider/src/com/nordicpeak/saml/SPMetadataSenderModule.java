package com.nordicpeak.saml;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.MultiForegroundModuleTracker;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.io.FileUtils;
import se.unlogic.webutils.http.HTTPUtils;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.http.enums.ContentDisposition;


public class SPMetadataSenderModule extends AnnotatedForegroundModule {

	private MultiForegroundModuleTracker<SAMLLoginProviderModule> moduleTracker;

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {

		super.init(moduleDescriptor, sectionInterface, dataSource);

		moduleTracker = new MultiForegroundModuleTracker<SAMLLoginProviderModule>(SAMLLoginProviderModule.class, systemInterface, systemInterface.getRootSection(), true, true);
	}

	@Override
	public void unload() throws Exception {

		moduleTracker.shutdown();

		super.unload();
	}

	@Override
	public ForegroundModuleResponse processRequest(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws URINotFoundException {

		//Find a SAMLLoginProvider configured for this host
		Collection<SAMLLoginProviderModule> loginProviders = moduleTracker.getInstances();

		for(SAMLLoginProviderModule loginProviderModule : loginProviders){

			if(loginProviderModule.getTargetDomains() != null && loginProviderModule.getTargetDomains().contains(req.getServerName()) && FileUtils.fileExists(loginProviderModule.getSpMetadataFilePath())){

				File file = new File(loginProviderModule.getSpMetadataFilePath());

				try {
					log.info("User " + user + " downloading SP metadata for domain " + req.getServerName());

					HTTPUtils.sendFile(file, req, res, ContentDisposition.INLINE);
				} catch (IOException e) {

					log.info("Error sending SP metadata for domain " + req.getServerName() + " to user " + user);
				}

				return null;
			}
		}

		log.info("Unable to find SP metadata for domain " + req.getServerName() + " requested by user " + user);

		throw new URINotFoundException(uriParser);
	}
}
