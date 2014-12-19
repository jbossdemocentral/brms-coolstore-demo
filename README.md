JBoss BPM Suite Cool Store Demo 
===============================

This is a retail web store demo where you will find rules, decision tables, events, and a process 
that is leveraged by a web application. The web application is a WAR built using the JBoss BPM Suite 
generated kjar as a dependency, providing an example project showing how developers can focus on the 
application code while the business analysts can focus on the rules, event, and processed in the 
JBoss BPM Suite product web user interface.


Install with one click in xPaaS (brmsPaaS)
-----------------------------------------
After clicking button, ensure `Gear` size is set to `large`:

[![Click to install OpenShift](http://launch-shifter.rhcloud.com/launch/light/Install brmsPaaS.svg)](https://openshift.redhat.com/app/console/application_type/custom?&cartridges[]=https://raw.githubusercontent.com/jbossdemocentral/cartridge-bpmPaaS-coolstore-demo/master/metadata/manifest.yml&name=coolstore&gear_profile=large&initial_git_url=)

Once installed you can use the JBoss BRMS logins: 

   * u:erics  p: jbossbrms1!  (admin)

   * u: alan  p: jbossbrms1!  (analyst)


Install on your machine
-----------------------
1. [Download and unzip.](https://github.com/jbossdemocentral/brms-coolstore-demo/archive/master.zip)

2. Add products to installs directory.

3. Run 'init.sh' or 'init.bat' file. 'init.bat' must be run with Administrative privileges.

4. Start JBoss BRMS Server by running ./target/jboss-eap-6.1/bin/standalone.sh

5. Login to http://localhost:8080/business-central

    ```
  - login for admin and analyst roles (u:erics / p:jbossbrms1!)
    ```

6. Build and deploy project.

7. Open shopping cart and demo away (http://localhost:8080/brms-coolstore-demo)


Supporting Articles
-------------------

[How to Use Rules and Events to Drive JBoss BRMS Cool Store for xPaaS](http://www.schabell.org/2014/08/how-to-use-rules-events-drive-jboss-brms-coolstore-xpaas.html)

[Red Hat JBoss BRMS - all product demos updated for version 6.0.2.GA release](http://www.schabell.org/2014/07/redhat-jboss-brms-product-demos-6.0.2-updated.html)

[Red Hat JBoss BRMS 6 - Demo Cool Store Dynamic Rule Updates (video)] (http://www.schabell.org/2014/05/redhat-jboss-brms6-demo-coolstore-dynamic-rule-updates.html)

[Red Hat JBoss BRMS 6 - The New Cool Store Demo] (http://www.schabell.org/2014/03/redhat-jboss-brms-v6-coolstore-demo.html)
 
[JBoss BRMS Cool Store Demo updated with EAP 6.1.1] (http://www.schabell.org/2013/09/jboss-brms-coolstore-demo-updated-eap-611.html)

[A shopping cart example in the Cool Store Demo] (http://www.schabell.org/2013/04/jboss-brms-coolstore-demo.html)

[Cool Store installation video part I] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partI.html)

[Cool Store CEP and Rules video part II] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partII.html)

[Cool Store BPM and Decision Tables video part III] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partIII.html)


Released versions
-----------------

See the tagged releases for the following versions of the product:

- v2.4 moved to JBoss Demo Central, with updated windows init.bat support and one click install button.

- v2.3 JBoss BRMS 6.0.3 installer with cool store demo installed.

- v2.2 JBoss BPM Suite 6.0.2, JBoss EAP 6.1.1, cool store demo installed.

- v2.1 JBoss BPM Suite 6.0.1, JBoss EAP 6.1.1, cool store demo installed.

- v2.0 JBoss BPM Suite 6.0.0, JBoss EAP 6.1.1, cool store demo installed.

- v1.4 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1.1, integrated BRMS maven repo into project so no longer need to add to
	personal settings configuration which fully automates project build.

- v1.3 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1.1, and added Forge Laptop Sticker to store.

- v1.2 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1, mavenized using JBoss repo. 

- v1.1 new welcome screen and doc fixes.

- v1.0 is BRMS 5.3.1 deployable, running on JBoss EAP 6.


[![Video bpmPaaS CoolStore](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/video-bpmpaas-coolstore.png?raw=true)](http://vimeo.com/ericschabell/bpmpaas-brms-coolstore-demo)

![Digital Sign Annoucement](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/announce-sign.jpg?raw=true)

![Decision Table](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-decision-table.png?raw=true)

![Domain Model](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-model.png?raw=true)

