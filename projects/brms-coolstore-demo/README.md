brms-coolstore-demo: BRMS Cool Store demo
=========================================
Author: Eric D. Schabell
Level: Beginner
Technologies: BRMS
Summary: Demonstrates the use of BRMS consuming rules from a server
Prerequisites: 
Target Product: BRMS
Source: <https://github.com/eschabell/brms-coolstore-demo>

What is it?
-----------

This quickstarts shows an solution for an online shopping cart. Many rules are available online in a BRMS server.

Some products are placed on the Shopping Cart and them some Shipping rules are used to calculate a discount.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on BRMS 5.3.1 and JBoss EAP 6.1

Configure your environment
--------------------------

- Acccess <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html>
- Download BRMS Platform
  1. Under JBoss Enterprise Platforms, select the BRMS Platform product.
  2. Select version 5.3.1 in the Version field.
  3. Download JBoss BRMS 5.3.1 Deployable for EAP 6 (Please note that this is the deployable distribution, not the standalone one.)
  4. Now copy brms-p-5.3.1.GA-deployable-ee6.zip, to the brms-customer-evaluation-demo's installs folder. 
  5. Ensure that this file is executable by running:

        $ chmod +x <path-to-project>/installs/brms-p-5.3.1.GA-deployable-ee6.zip
  
- Download EAP 6 Platform:
  1. Under JBoss Enterprise Platforms, select the Application Platform product.
  2. Select version 6.1.0.Beta in the Version field.
  3. Download JBoss Aplication Platform 6.1.0.
  4. Now copy jboss-eap-6.1.0.zip, to the brms-rewards-demo's installs folder. 
  5. Ensure that this file is executable by running:

        $ chmod +x <path-to-project>/installs/jboss-eap-6.1.0.zip

- From the brms-coolstore-demo folder, run the init.sh script:

        $ ./init.sh


Start JBoss Enterprise Application Platform 6.1
----------------------------------------------

- From the brms-coolstore-demo/target/jboss-eap-6.1/bin run:

        ./standalone.sh

- Open up your Web browser of choice and navigate to http://localhost:8080/jboss-brms/
- Use the default credentials of admin/admin
- Upon logging in, you will see the following prompt:
    This looks like a brand new repository. Would you like to install a sample repository?
    Important: Please be sure to select No thanks. 
- Select the Administration section on the left hand side
- From the Administration list select Import Export. This will open the Import Export window.
- Now select Browse... and navigate to brms-coolstore-demo/support folder and select the repository_export.zip file.
- Lastly, select the Import button. Select OK to confirm that you want to import the artifacts.
- Build the package by selecting from the left navigation Knowledge Bases → Packages → com￼→ redhat →coolstore → Edit tab → Build Package button.

Build and Run the Quickstart
----------------------------

_NOTE: The following build command assumes you have configured your Environment._

1. Open a command line and navigate to the root directory of this quickstart (<repo_root>/projects/brms-customer-evaluation-demo).
2. Type this command to build and runt the tests:

        mvn clean test -Parq-jbossas-remote

_NOTE: To run, need to update guvnor ip and port in BRMSUtil class if not on local box or set the following -D properties:_

       -Dguvnor-ip=xxx.xxx.xxx.xxx and -Dguvnor-port=xxxx


Investigate the Console Output
------------------------------

### Maven

Maven prints summary of performed tests into the console:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running com.redhat.coolstore.service.PromoServiceTest
    log4j:WARN No appenders could be found for logger (org.jboss.logging).
    log4j:WARN Please initialize the log4j system properly.
    Ago 01, 2013 5:29:39 PM org.jboss.arquillian.container.impl.MapObject populate
    WARNING: Configuration contain properties not supported by the backing object org.jboss.as.arquillian.container.remote.RemoteContainerConfiguration
    Unused property entries: {jbossHome=/NotBackedUp/brms-coolstore-demo/target/jboss-eap-6.0}
    Supported property names: [managementPort, username, managementAddress, password]
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.929 sec
    Running com.redhat.coolstore.service.ShippingServiceTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.357 sec
    Running com.redhat.coolstore.service.ShoppingCartServiceTest
    Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.07 sec
    
    Results :
    
    Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
    
