package com.redhat.coolstore.util;

import javax.ejb.Singleton;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

@Singleton
public class BRMSUtil {

    private KieContainer kContainer = null;
    
    public BRMSUtil() {	    
    	
    	KieServices kServices = KieServices.Factory.get();

		ReleaseId releaseId = kServices.newReleaseId( "com.redhat", "coolstore", "2.0" );

		kContainer = kServices.newKieContainer( releaseId );

		KieScanner kScanner = kServices.newKieScanner( kContainer );


		// Start the KieScanner polling the maven repository every 1 seconds

		kScanner.start( 1000L );
    }


    
    public StatelessKieSession getStatelessSession() {

        return kContainer.newStatelessKieSession();

    }

    /*
     * KieSession is the new StatefulKnowledgeSession from BRMS 5.3.
     */
    public KieSession getStatefulSession() {

        return kContainer.newKieSession();

    }

}
