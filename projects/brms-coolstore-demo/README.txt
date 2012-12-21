To run, need to update guvnor ip and port in BRMSUtil class if not on local box or set the following -D properties:

-Dguvnor-ip=xxx.xxx.xxx.xxx and -Dguvnor-port=xxxx

---------------------------------------------------------------

MAVEN: download the jboss maven repo from access.redhat.com and then point your settings.xml file to it.

You can see my example in maven/settings.xml

---------------------------------------------------------------

Additional Required jars for WEB-INF/lib

drools-compiler-5.3.0.BRMS.jar
drools-core-5.3.0.BRMS.jar
drools-decisiontables-5.3.0.BRMS.jar
drools-templates-5.3.0.BRMS.jar
jbpm-bpmn2-5.3.0.BRMS.jar
jbpm-flow-5.3.0.BRMS.jar
jbpm-flow-builder-5.3.0.BRMS.jar
knowledge-api-5.3.0.BRMS.jar
mvel2-2.1.0.drools16.jar

----------------------------------------------------------------

BRMS repo can be found in brms/repository_export.zip