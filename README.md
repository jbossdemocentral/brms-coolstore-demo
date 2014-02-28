JBoss BPM Suite Cool Store Demo Quickstart Guide
================================================

Demo based on JBoss BPM Suite product.


Setup and Configuration
-----------------------

See Quick Start Guide in project as ODT and PDF for details on installation. For those that can't wait:

- see README in 'installs' directory

- add products 

- run 'init.sh' & read output

- read Quick Start Guide

- start JBoss EAP server, ./target/jboss-eap-6.1/bin/standalone.sh

- inspect project in BPM Suite, login http://localhost:8080/business central (u:erics / p:bpmsuite)

- open shopping cart and demo away (http://localhost:8080/bpm-suite-coolstore-demo)


Supporting Articles
-------------------

[JBoss BRMS Cool Store Demo updated with EAP 6.1.1] (http://www.schabell.org/2013/09/jboss-brms-coolstore-demo-updated-eap-611.html)

[A shopping cart example in the Cool Store Demo] (http://www.schabell.org/2013/04/jboss-brms-coolstore-demo.html)

[Cool Store installation video part I] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partI.html)

[Cool Store CEP and Rules video part II] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partII.html)

[Cool Store BPM and Decision Tables video part III] (http://www.schabell.org/2013/05/jboss-brms-coolstore-demo-video-partIII.html)


Released versions
-----------------

See the tagged releases for the following versions of the product:

- v2.0 JBoss BPM Suite 6.0.0.GA, JBoss EAP 6.1.1, cool store demo installed.

- v1.4 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1.1, integrated BRMS maven repo into project so no longer need to add to
	personal settings configuration which fully automates project build.

- v1.3 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1.1, and added Forge Laptop Sticker to store.

- v1.2 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1, mavenized using JBoss repo. 

- v1.1 new welcome screen and doc fixes.

- v1.0 is BRMS 5.3.1 deployable, running on JBoss EAP 6.

![Install Console](https://github.com/eschabell/brms-coolstore-demo/blob/master/docs/demo-images/install-console.png?raw=true)

![Decision Table](https://github.com/eschabell/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-decision-table.png?raw=true)

![Domain Model](https://github.com/eschabell/brms-coolstore-demo/blob/master/docs/demo-images/coolstore-model.png?raw=true)
