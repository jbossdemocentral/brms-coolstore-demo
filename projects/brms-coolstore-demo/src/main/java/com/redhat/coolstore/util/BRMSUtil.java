package com.redhat.coolstore.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Singleton;

import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ChangeSetImpl;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import com.redhat.coolstore.factmodel.ShoppingCart;
import com.redhat.coolstore.factmodel.ShoppingCartItem;

@Singleton
public class BRMSUtil {

	private KnowledgeAgent kagent = null;

	public BRMSUtil() {

		kagent = KnowledgeAgentFactory.newKnowledgeAgent("BRMS Agent");
		
		ChangeSetImpl changeSet = new ChangeSetImpl();
		changeSet.setResourcesAdded( buildResourceURLCollection() );
				
		// resource to the change-set xml for the resources to add                                                                  
		kagent.applyChangeSet( changeSet );
		
		ResourceChangeScannerConfiguration changeScannerConfiguration = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		
		changeScannerConfiguration.setProperty("drools.resource.scanner.interval", Integer.toString(1));

		ResourceFactory.getResourceChangeScannerService().configure(changeScannerConfiguration); 
		
		ResourceFactory.getResourceChangeNotifierService().start();

		ResourceFactory.getResourceChangeScannerService().start();
		
	}
	
	public StatelessKnowledgeSession getStatelessSession() {
		
		return kagent.getKnowledgeBase().newStatelessKnowledgeSession();
		
	}
	
	public StatefulKnowledgeSession getStatefulSession() {
		
		return kagent.getKnowledgeBase().newStatefulKnowledgeSession();
		
	}
	
	public void stopResourceChangeScannerServices() {
		
		ResourceFactory.getResourceChangeNotifierService().stop();

		ResourceFactory.getResourceChangeScannerService().stop();
		
		kagent = null;
		
	}
	
	private Collection<Resource> buildResourceURLCollection() {

		Collection<Resource> resources = new ArrayList<Resource>();
		
		List<String> urlArrayList = new ArrayList<String>();
		
		String guvnorIPOverride = System.getProperty("guvnor-ip");
		String guvnorPortOverride = System.getProperty("guvnor-port");

		String guvnorIP = "localhost";
		String guvnorPort = "8080";
		
		if ( guvnorIPOverride != null ) {
			
			guvnorIP = guvnorIPOverride;
			
		}
		
		if ( guvnorPortOverride != null ) {
			
			guvnorPort = guvnorPortOverride;
			
		}
		
		urlArrayList.add("http://" + guvnorIP + ":" + guvnorPort +"/jboss-brms/org.drools.guvnor.Guvnor/package/com.redhat.coolstore/LATEST");
		
		for (String url : urlArrayList){
			
			UrlResource standardUrlResource = (UrlResource) ResourceFactory.newUrlResource(url);
			
			standardUrlResource.setBasicAuthentication("enabled");
			standardUrlResource.setUsername("admin");
			standardUrlResource.setPassword("admin");
			standardUrlResource.setResourceType(ResourceType.PKG);
			resources.add(standardUrlResource);
			
		}
		
		return resources;
	}
	
	public static void main(String[] args) {
		
		BRMSUtil b = new BRMSUtil();
		
		StatefulKnowledgeSession session = b.getStatefulSession();
		
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
	
		b.stopResourceChangeScannerServices();
						
		System.exit(0);
		
	}
}
