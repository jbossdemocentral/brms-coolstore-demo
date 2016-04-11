JBoss BRMS Suite Cool Store Demo 
================================
This is a retail web store demo where you will find rules, decision tables, events, and a ruleflow 
that is leveraged by a web application. The web application is a WAR built using the JBoss BRMS
generated project as a dependency, providing an example project showing how developers can focus on the 
application code while the business analysts can focus on rules, events, and ruleflows in the 
JBoss BRMS product web based dashboard.

This demo is self contained, it uses a custom maven settings to deploy all built JBoss BRMS knowledge artifacts
into an external maven repository (not your local repository), in /tmp/maven-repo.

There are four options available to you for using this demo; local, Openshift Online, Red Hat CDK OpenShift Enterprise and Containerized.


Option 1 - Install on your machine
----------------------------------
1. [Download and unzip.](https://github.com/jbossdemocentral/brms-coolstore-demo/archive/master.zip)

2. Add products to installs directory.

3. Run 'init.sh' or 'init.bat' file. 'init.bat' must be run with Administrative privileges.

4. Start JBoss BRMS Server by running ./target/jboss-eap-6.4/bin/standalone.sh

5. Login to http://localhost:8080/business-central

    ```
    - login for admin and analyst roles (u:erics / p:jbossbrms1!)
    ```

6. Build and deploy project.

7. Open shopping cart and demo away (http://localhost:8080/brms-coolstore-demo)


Option 2 - Install with one click in xPaaS (brmsPaaS)
-----------------------------------------------------
After clicking button, ensure `Gear` size is set to `large`:

[![Click to install OpenShift](http://launch-shifter.rhcloud.com/launch/light/Install brmsPaaS.svg)](https://openshift.redhat.com/app/console/application_type/custom?&cartridges[]=https://raw.githubusercontent.com/jbossdemocentral/cartridge-bpmPaaS-coolstore-demo/master/metadata/manifest.yml&name=coolstore&gear_profile=large&initial_git_url=)

Once installed you can use the JBoss BRMS logins: 

   * u:erics  p: jbossbrms1!  (admin)

   * u: alan  p: jbossbrms1!  (analyst)

Current hosting of brmsPaaS is on JBoss BRMS 6.0.2 in OpenShift Online.


Option 3 - Install on Red Hat CDK OpenShift Enterprise image
------------------------------------------------------------
The following steps can be used to install this demo on OpenShift Enterprise using the
Red Hat Container Development Kit (CDK)

1. [App Dev Cloud with JBoss Cool Store Demo](https://github.com/redhatdemocentral/rhcs-coolstore-demo)

2. [App Dev Cloud with JBoss Cool Store Persistence Demo](https://github.com/redhatdemocentral/rhcs-coolstore-persistence-demo)


Option 4 - Generate containerized install
-----------------------------------------
The following steps can be used to configure and run the demo in a container

1. [Download and unzip.](https://github.com/jbossdemocentral/brms-coolstore-demo/archive/master.zip)

2. Add products installs directory.

3. Copy contents of support/docker directory to the project root.

4. Build demo image

	```
	docker build -t jbossdemocentral/brms-coolstore-demo .
	```
5. Start demo container

	```
	docker run -it -p 8080:8080 -p 9990:9990 jbossdemocentral/brms-coolstore-demo
	```
6. Login to http://&lt;DOCKER_HOST&gt;:8080/business-central

    ```
    - login for admin and analyst roles (u:erics / p:jbossbrms1!)
    ```

7. Open shopping cart and demo away (http://&lt;DOCKER_HOST&gt;:8080/brms-coolstore-demo)

Additional information can be found in the jbossdemocentral container [developer repository](https://github.com/jbossdemocentral/docker-developer)


Notes
-----
The web application (shopping cart) is built during demo installation with a provided coolstore project jar version 2.0.0. When you 
open the project you will find the version is also set to 2.0.0. You can run the web application as is, but if you build and deploy
a new version of 2.0.0 to your maven repository it will find duplicate rules. To demo you deploy a new version of the coolstore
project by bumping the version number on each build and deploy, noting the KieScanner picking up the new version within 10 seconds 
of a new deployment. For example, initially start project, bump the version to 3.0.0, build and deploy, open web application and
watch KieScanner in server logs pick up the 3.0.0 version. Now change a shipping rule value in decision table, save, bump project
version to 4.0.0, build and deploy, watch for KieScanner picking up new 4.0.0 version, now web application on next run will use new
shipping values.


Supporting Articles
-------------------
- [JBoss BRMS Cool Store UI gets Vaadin facelift](http://www.schabell.org/2016/01/jboss-brms-coolstore-ui-vaadin-facelift.html)

- [7 Steps to Your First Rules with JBoss BRMS Starter Kit](http://www.schabell.org/2015/08/7-steps-first-rules-jboss-brms-starter-kit.html)

- [3 shockingly easy ways into JBoss rules, events, planning & BPM](http://www.schabell.org/2015/01/3-shockingly-easy-ways-into-jboss-brms-bpmsuite.html)

- [Jump Start Your Rules, Events, Planning and BPM Today](http://www.schabell.org/2014/12/jump-start-rules-events-planning-bpm-today.html)

- [4 Foolproof Tips Get You Started With JBoss BRMS 6.0.3](http://www.schabell.org/2014/10/4-foolproof-tips-get-started-jboss-brms-603.html)

- [How to Use Rules and Events to Drive JBoss BRMS Cool Store for xPaaS](http://www.schabell.org/2014/08/how-to-use-rules-events-drive-jboss-brms-coolstore-xpaas.html)

- [Red Hat JBoss BRMS - all product demos updated for version 6.0.2.GA release](http://www.schabell.org/2014/07/redhat-jboss-brms-product-demos-6.0.2-updated.html)

- [Red Hat JBoss BRMS 6 - Demo Cool Store Dynamic Rule Updates (video)] (http://www.schabell.org/2014/05/redhat-jboss-brms6-demo-coolstore-dynamic-rule-updates.html)

- [Red Hat JBoss BRMS 6 - The New Cool Store Demo] (http://www.schabell.org/2014/03/redhat-jboss-brms-v6-coolstore-demo.html)
 
- [JBoss BRMS Cool Store Demo updated with EAP 6.1.1] (http://www.schabell.org/2013/09/jboss-brms-coolstore-demo-updated-eap-611.html)

- [A shopping cart example in the Cool Store Demo] (http://www.schabell.org/2013/04/jboss-brms-coolstore-demo.html)

- [Cool Store installation video part I] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partI.html)

- [Cool Store CEP and Rules video part II] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partII.html)

- [Cool Store BPM and Decision Tables video part III] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partIII.html)


Released versions
-----------------
See the tagged releases for the following versions of the product:

- v3.6 JBoss BRMS 6.2.0-BZ-1299002 on JBoss EAP 6.4.4 with cool store installed and RH CDK on OSE Cloud install option.

- v3.5 JBoss BRMS 6.2.0-BZ-1299002 on JBoss EAP 6.4.4 with cool store installed.

- v3.4 JBoss BRMS 6.2.0, JBoss EAP 6.4.4 and OSE aligned containerization.

- v3.3 JBoss BRMS 6.2.0, JBoss EAP 6.4.4 and cool store installed, UI updated to Vaadin 7.6.0.

- v3.2 JBoss BRMS 6.2.0, JBoss EAP 6.4.4 and cool store installed, UI updated to Vaadin 7.

- v3.1 JBoss BRMS 6.2.0, JBoss EAP 6.4.4 and cool store installed.

- v3.0 JBoss BRMS 6.1.1 (patch update applied) with cool store installed and Albert Wong updates for JBDS project importing.

- v2.9 JBoss BRMS 6.1.1 (patch update applied) with cool store installed.

- v2.8 JBoss BRMS 6.1 with cool store installed.

- v2.7 JBoss BRMS 6.0.3 installer with cool store configured to scan external maven repository.

- v2.6 JBoss BRMS 6.0.3 installer with cool store updated so that project unit tests running again.

- v2.5 JBoss BRMS 6.0.3 with optional containerized installation.

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

![Announcement Sign](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/announce-sign.jpg?raw=true)

[![Video bpmPaaS CoolStore](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/video-brms-coolstore-demo.png?raw=true)](https://vimeo.com/ericschabell/brms-coolstore-demo)

[![Video bpmPaaS CoolStore](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/video-bpmpaas-coolstore.png?raw=true)](http://vimeo.com/ericschabell/bpmpaas-brms-coolstore-demo)

![Decision Table](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-decision-table.png?raw=true)

![Domain Model](https://github.com/jbossdemocentral/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-model.png?raw=true)

