/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.coolstore;

import java.io.File;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class DefaultDeployment {

    public static WebArchive deployment() {
    	
    	WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
             .addPackages(true, "com.redhat.coolstore.model")
             .addPackages(true, "com.redhat.coolstore.service")
             .addPackages(true, "com.redhat.coolstore.util")             
             .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), ArchivePaths.create("beans.xml"));
    	
    	File[] f = null;
    	
    	try {
    	
	    	f = Maven.configureResolver()
		        .loadPomFromFile("pom.xml")
		        .resolve(
		        		"org.kie:kie-internal",
		        		"org.kie:kie-ci",
		        		"org.jbpm:jbpm-bpmn2",
		        		"org.mvel:mvel2",
		        		"com.vaadin:vaadin-server",
		        		"com.redhat:coolstore"
		        		)
		        .withTransitivity().asFile();
    	
	    	war.addAsLibraries(f);
    	
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
        
        return war;
        
    }

}
