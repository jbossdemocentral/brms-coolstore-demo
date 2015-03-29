package com.redhat.coolstore.util;

import javax.enterprise.context.ApplicationScoped;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

@ApplicationScoped
public class BRMSUtil {

    private KieContainer kContainer = null;
    
    public BRMSUtil() {	    
    	
    	KieServices kServices = KieServices.Factory.get();

			ReleaseId releaseId = kServices.newReleaseId( "com.redhat", "coolstore", "LATEST" );

			kContainer = kServices.newKieContainer( releaseId );

			KieScanner kScanner = kServices.newKieScanner( kContainer );


			// Start the KieScanner polling the maven repository every 10 seconds
			System.out.println("Starting KieScanner...");
			System.out.println();
		
			try {
				kScanner.start( 10000L );
			} catch (Exception e) {
				System.out.println("Failed to start KieScanner...");
				System.out.println();
				System.out.println(e.getMessage());
			}
				
			System.out.println("Started KieScanner sucessfully...");
			System.out.println();
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
