/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
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

public class DefaultDeployment {

    public static WebArchive deployment() {

        String webInf = "src/main/webapp/WEB-INF/lib/";

        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackages(true, "com.redhat.coolstore.factmodel")
            .addPackages(true, "com.redhat.coolstore.model")
            .addPackages(true, "com.redhat.coolstore.service")
            .addPackages(true, "com.redhat.coolstore.util")
            .addAsLibraries(new File(webInf + "drools-core-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "drools-compiler-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "drools-decisiontables-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "drools-templates-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "jbpm-bpmn2-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "jbpm-flow-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "jbpm-flow-builder-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "knowledge-api-5.3.1.BRMS.jar"))
            .addAsLibraries(new File(webInf + "mvel2-2.1.3.Final.jar"))
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), ArchivePaths.create("beans.xml"));

    }

}
