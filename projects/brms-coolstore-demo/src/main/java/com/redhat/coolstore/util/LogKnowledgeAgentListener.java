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
package com.redhat.coolstore.util;

import org.apache.log4j.Logger;
import org.drools.event.knowledgeagent.AfterChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.AfterChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.AfterResourceProcessedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.BeforeResourceProcessedEvent;
import org.drools.event.knowledgeagent.KnowledgeAgentEventListener;
import org.drools.event.knowledgeagent.KnowledgeBaseUpdatedEvent;
import org.drools.event.knowledgeagent.ResourceCompilationFailedEvent;

public class LogKnowledgeAgentListener implements KnowledgeAgentEventListener {

    Logger log = Logger.getLogger(LogKnowledgeAgentListener.class);

    @Override
    public void beforeChangeSetApplied(BeforeChangeSetAppliedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterChangeSetApplied(AfterChangeSetAppliedEvent event) {
        log.info(event.toString());
    }

    @Override
    public void beforeChangeSetProcessed(BeforeChangeSetProcessedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterChangeSetProcessed(AfterChangeSetProcessedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeResourceProcessed(BeforeResourceProcessedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterResourceProcessed(AfterResourceProcessedEvent event) {
        log.info(event.toString());
    }

    @Override
    public void knowledgeBaseUpdated(KnowledgeBaseUpdatedEvent event) {
        log.info(event.toString());
    }

    @Override
    public void resourceCompilationFailed(ResourceCompilationFailedEvent event) {
        // TODO Auto-generated method stub

    }

}
