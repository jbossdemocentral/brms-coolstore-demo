package com.redhat.coolstore.util;

import java.io.File;
import java.io.FilenameFilter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import com.redhat.coolstore.factmodel.ShoppingCart;
import com.redhat.coolstore.factmodel.ShoppingCartItem;

@Singleton
public class BRMSUtil {

    private static RuntimeEngine runtime;
    private KieServices kieServices = null;
    private ReleaseId releaseId = null;
    private KieContainer kContainer = null;
    private KieScanner kScanner = null;
    private static KieSession session = null;
    private static RuntimeManager manager = null;
    
    public BRMSUtil() {	    	    	
    }

    @PostConstruct
    public void postConstruct() {
        
//        kieServices = KieServices.Factory.get();
//        releaseId = kieServices.newReleaseId( "com.redhat", "bpm-suite-coolstore-demo", "2.0.0-Final" );
//        kContainer = kieServices.newKieContainer( releaseId );
//        kScanner = kieServices.newKieScanner( kContainer );
//
//
//        // Start the KieScanner polling the Maven repository every 10 seconds
//        kScanner.start( 10000L );
    }

    
    public StatelessKieSession getStatelessSession() {

        return runtime.getKieSession().getKieBase().newStatelessKieSession();

    }

    /*
     * KieSession is the new StatefulKnowledgeSession from BRMS 5.3.
     */
    public KieSession getStatefulSession() {

        return runtime.getKieSession();

    }


    private static RuntimeManager createRuntimeManager() {
    	
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.h2database.h2");
        RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get()
            .newClasspathKmoduleDefaultBuilder()
            .entityManagerFactory(emf);
        return RuntimeManagerFactory.Factory.get()
            .newSingletonRuntimeManager(builder.get(), "com.redhat.coolstore.PriceProcess:1.0");
    }
    
    
    public static void main(String[] args) {

        BRMSUtil b = new BRMSUtil();
        
        manager = createRuntimeManager();
		runtime = manager.getRuntimeEngine(ProcessInstanceIdContext.get());
		session = runtime.getKieSession();
		
        ShoppingCart sc = new ShoppingCart();

        ShoppingCartItem sci = new ShoppingCartItem();
        sci.setPrice(10.00);
        sci.setQuantity(1);
        sci.setShoppingCart(sc);

        session.insert(sc);
        session.insert(sci);	

        session.startProcess("com.redhat.coolstore.PriceProcess");

        session.fireAllRules();

        session.dispose();

        System.exit(0);

    }
}
