JBoss BRMS (jBPM) Cool Store Demo Quickstart Guide
============================================================

Demo based on JBoss BRMS products.


Setup and Configuration
-----------------------

See Quick Start Guide in project as ODT and PDF for details on installation. For those that can't wait:

- see README in 'installs' directory

- add products 

- run 'init.sh' & read output

- read Quick Start Guide

- run 'mvn clean install' on project to build

- copy projects/brms-coolstore-demo/target/brms-coolstore-demo.war deployments directory of installed server

- start JBoss EAP server

- login to BRM (http://localhost:8080/jboss-brms)

- import repository-export from support dir

- build and deploy project in BRM

- open shopping cart and demo away (http://localhost:8080/brms-coolstore-demo)

Windows users see support/windows/README for installation.


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

- v1.3 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1.1, mavenized using JBoss repo. 

- v1.2 is BRMS 5.3.1 deployable, running on JBoss EAP 6.1, mavenized using JBoss repo. 

- v1.1 new welcome screen and doc fixes.

- v1.0 is BRMS 5.3.1 deployable, running on JBoss EAP 6.

