brms-coolstore-demo: BRMS Cool Store demo
=========================================
Author: Eric D. Schabell
Level: Intermediate
Technologies: BRMS
Summary: Demonstrates the use of BRMS consuming rules from a server
Prerequisites: 
Target Product: BRMS
Source: <https://github.com/eschabell/brms-coolstore-demo>

What is it?
-----------

This quickstart shows how to use BRMS to create a solution for an online shopping cart. As products are placed in the shopping cart, the shipping rules calculate the shipping cost. You can modify the shipping rules, then run the tests again to see changes in to the shipping total.

Configure and Run the Quickstart
-------------------

This quickstart has more complex setup and configuration requirements than many of the other quickstarts. Please see the `Quick Start Guide` located in the `docs/` folder for complete instructions on how to configrre and run this quickstart. The file is provided in both PDF and ODT formats.

The following is a brief summary of the steps you will take to configure and run the quickstart. _Note: These steps are not meant to replace the complete instructions contained in the `docs/Quick Start Guide.odt` or `docs/Quick Start Guide.pdf` files!_

1. Download the following from the JBoss Customer Portal at <https://access.redhat.com/jbossnetwork/restricted/listSoftware.html> into the quickstart `installs/` directory:
    * BRMS (brms-p-5.3.1.GA-deployable-ee6.zip)	
    * EAP (jboss-eap-6.1.0.zip)
2. Run `init.sh` to install EAP 6 and deploy BRMS. Verify the output and make sure the command completes successfully.
3. Configure JBoss Developer Studio (JBDS).
    * Install the SOA tools.
    * Add the BRMS platform server runtime.
    * Import the project.
4. Run `mvn clean install` on the project to ensure it builds successfully.
5. Start the JBoss EAP server.
6. Run 'mvn jboss-as:deploy' on the project to deploy to the server
7. Login to BRM at <http://localhost:8080/jboss-brms>.
8. Import the project repository `repository-export.zip` file from the `support/` directory.
9. Build and deploy project in BRM.
9. Login to the Coolstore Demo application at <localhost:8080/brms-coolstore-demo>.
10. Add Items to the cart and watch the shopping cart `Shipping` and `Cart Total` costs change.

_Note: Windows users should see `support/windows/README` for installation procedures._

Supporting Articles
-------------------

[A shopping cart example in the Cool Store Demo] (http://www.schabell.org/2013/04/jboss-brms-coolstore-demo.html)

[Cool Store installation video part I] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partI.html)

[Cool Store CEP and Rules video part II] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partII.html)

[Cool Store BPM and Decision Tables video part III] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partIII.html)


Released versions
-----------------

See the tagged releases for the following versions of the product:

- v1.2 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1, mavenized using JBoss repo. 

- v1.1 new welcome screen and doc fixes.

- v1.0 is BRMS 5.3.1 deployable, running on JBoss EAP 6.
