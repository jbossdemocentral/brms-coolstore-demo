#!/bin/sh 
DEMO="JBoss BRMS Red Hat Cool Store Demo"
AUTHORS="Jason Milliron, Eric D. Schabell"
PROJECT="git@github.com:eschabell/brms-coolstore-demo.git"
SRC_DIR=./installs
SUPPORT_DIR=./support
JBOSS_HOME=$SRC_DIR/jboss-eap-6.1
SERVER_DIR=$JBOSS_HOME/standalone/deployments/
SERVER_CONF=$JBOSS_HOME/standalone/configuration/
PRJ_DIR=./
EAP=jboss-eap-6.1.0.zip
BRMS=brms-p-5.3.1.GA-deployable-ee6.zip
BRMS_LIBS=$JBOSS_HOME/standalone/deployments/jboss-brms.war/WEB-INF/lib
SUPPORT_LIBS=./support/libs/
WEB_INF_LIB=./src/main/webapp/WEB-INF/lib/
DESIGNER=designer-patched.war
MVN_VERSION=5.3.1.BRMS
VERSION=5.3.1

# wipe screen.
clear 

echo
echo "#########################################################"
echo "##                                                     ##"   
echo "##  Setting up the ${DEMO}  ##"
echo "##                                                     ##"   
echo "##                                                     ##"   
echo "##             ####   ####    #   #    ###             ##"
echo "##             #   #  #   #  # # # #  #                ##"
echo "##             ####   ####   #  #  #   ##              ##"
echo "##             #   #  #  #   #     #     #             ##"
echo "##             ####   #   #  #     #  ###              ##"
echo "##                                                     ##"   
echo "##                                                     ##"   
echo "##  brought to you by,                                 ##"   
echo "##             ${AUTHORS}        ##"
echo "##                                                     ##"   
echo "##  ${PROJECT}   ##"
echo "##                                                     ##"   
echo "#########################################################"
echo

command -v mvn -q >/dev/null 2>&1 || { echo >&2 "Maven is required but not installed yet... aborting."; exit 1; }

installPom() {
	    mvn -q install:install-file -Dfile=../$SUPPORT_DIR/$2-$MVN_VERSION.pom.xml -DgroupId=$1 -DartifactId=$2 -Dversion=$MVN_VERSION -Dpackaging=pom;
}

installBinary() {
			unzip -q $2-$MVN_VERSION.jar META-INF/maven/$1/$2/pom.xml;
			mvn -q install:install-file -DpomFile=./META-INF/maven/$1/$2/pom.xml -Dfile=$2-$MVN_VERSION.jar -DgroupId=$1 -DartifactId=$2 -Dversion=$MVN_VERSION -Dpackaging=jar;
}

# make some checks first before proceeding.	
if [[ -r $SRC_DIR/$EAP || -L $SRC_DIR/$EAP ]]; then
		echo EAP sources are present...
		echo
else
		echo Need to download $EAP package from the Customer Support Portal 
		echo and place it in the $SRC_DIR directory to proceed...
		echo
		exit
fi

# Move the old JBoss instance, if it exists, to the OLD position.
if [ -x $JBOSS_HOME ]; then
		echo "  - existing JBoss Enterprise EAP 6 detected..."
		echo
		echo "  - moving existing JBoss Enterprise EAP 6 aside..."
		echo
		rm -rf $JBOSS_HOME.OLD
		mv $JBOSS_HOME $JBOSS_HOME.OLD

		# Unzip the JBoss EAP instance.
		echo Unpacking JBoss Enterprise EAP 6...
		echo
		chmod +x $SRC_DIR/$EAP
		unzip -q -d $SRC_DIR $SRC_DIR/$EAP
else
		# Unzip the JBoss EAP instance.
		echo Unpacking new JBoss Enterprise EAP 6...
		echo
		chmod +x $SRC_DIR/$EAP
		unzip -q -d $SRC_DIR $SRC_DIR/$EAP
fi

# Unzip the required files from JBoss BRMS Deployable
echo Unpacking JBoss Enterprise BRMS $VERSION...
echo
cd installs
chmod +x $BRMS
unzip -q $BRMS

echo "  - deploying JBoss Enterprise BRMS Manager WAR..."
echo
unzip -q -d ../$SERVER_DIR jboss-brms-manager-ee6.zip
rm jboss-brms-manager-ee6.zip 

echo "  - deploying jBPM Console WARs..."
echo
unzip -q -d ../$SERVER_DIR jboss-jbpm-console-ee6.zip
rm jboss-jbpm-console-ee6.zip

unzip -q jboss-jbpm-engine.zip 
echo "  - copying jBPM client JARs..."
echo
unzip -q -d ../$SERVER_DIR jboss-jbpm-engine.zip lib/netty.jar
rm jboss-jbpm-engine.zip
rm -rf *.jar modeshape.zip *.RSA lib
rm jboss-brms-engine.zip

cd ../

# ensure project lib dir exists.
if [ ! -d $WEB_INF_LIB ]; then
	echo "  - missing web inf lib directory in project being created..."
	echo
	mkdir -p $WEB_INF_LIB
fi

mvn -s maven/settings.xml install:install-file -Dfile=$SUPPORT_LIBS/cdiutils-1.0.0.jar -DgroupId=org.vaadin.virkki -DartifactId=cdiutils -Dversion=1.0.0 -Dpackaging=jar

cp $SUPPORT_LIBS/cdiutils-1.0.0.jar $WEB_INF_LIB

cp $BRMS_LIBS/drools-core-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/drools-compiler-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/drools-decisiontables-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/drools-templates-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/jbpm-bpmn2-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/jbpm-flow-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/jbpm-flow-builder-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/knowledge-api-$MVN_VERSION.jar $WEB_INF_LIB

cp $BRMS_LIBS/mvel2-2.1.3.Final.jar $WEB_INF_LIB

echo
echo Setting permissions and copying support files...
echo

echo Updating to the newest web designer...
echo
rm -rf $SERVER_DIR/designer.war/*
unzip -q support/$DESIGNER -d $SERVER_DIR/designer.war

echo "  - set designer to jboss-brms in profile..."
echo
cp support/designer-jbpm.xml $SERVER_DIR/designer.war/profiles/jbpm.xml

echo "  - enabling demo accounts logins in brms-users.properties file..."
echo
cp support/brms-users.properties $SERVER_CONF

echo "  - enabling demo accounts role setup in brms-roles.properties file..."
echo
cp support/brms-roles.properties $SERVER_CONF

echo "  - adding dodeploy files to deploy all brms components..."
echo 
touch $SERVER_DIR/business-central-server.war.dodeploy
touch $SERVER_DIR/business-central.war.dodeploy
touch $SERVER_DIR/designer.war.dodeploy
touch $SERVER_DIR/jboss-brms.war.dodeploy
touch $SERVER_DIR/jbpm-human-task.war.dodeploy

echo "  - configuring security authentication, copying updated components.xml file to jboss-brms.war..."
echo
cp support/components.xml $SERVER_DIR/jboss-brms.war/WEB-INF/

echo "  - configuring deployment timeout extention and added security domain brms in standalone.xml..."
echo
cp support/standalone.xml $SERVER_CONF

# Add execute permissions to the standalone.sh script.
echo "  - making sure standalone.sh for server is executable..."
echo
chmod u+x $JBOSS_HOME/bin/standalone.sh

echo "  - enabling demo users for human tasks in jbpm-human-task.war web.xml file..."
echo
cp support/jbpm-human-task-war-web.xml $SERVER_DIR/jbpm-human-task.war/WEB-INF/web.xml

echo "  - adding netty dep to business-central-server.war and jbpm-human-task.war..."
echo
cp support/MANIFEST.MF $SERVER_DIR/business-central-server.war/WEB-INF/classes/META-INF/
cp support/MANIFEST.MF $SERVER_DIR/jbpm-human-task.war/WEB-INF/classes/META-INF/


echo "  - mavenizing your repo with BRMS components."
echo
echo
echo Installing the BRMS binaries into the Maven repository...
echo
unzip -q $SRC_DIR/$BRMS jboss-brms-engine.zip
unzip -q jboss-brms-engine.zip binaries/*
unzip -q $SRC_DIR/$BRMS jboss-jbpm-engine.zip
unzip -q -o -d ./binaries jboss-jbpm-engine.zip
cd binaries

echo Installing parent POMs...
echo
installPom org.drools droolsjbpm-parent
installPom org.drools droolsjbpm-knowledge
installPom org.drools drools-multiproject
installPom org.drools droolsjbpm-tools
installPom org.drools droolsjbpm-integration
installPom org.drools guvnor
installPom org.jbpm jbpm

echo Installing Drools binaries...
echo
# droolsjbpm-knowledge
installBinary org.drools knowledge-api
# drools-multiproject
installBinary org.drools drools-core
installBinary org.drools drools-compiler
installBinary org.drools drools-jsr94
installBinary org.drools drools-verifier
installBinary org.drools drools-persistence-jpa
installBinary org.drools drools-templates
installBinary org.drools drools-decisiontables
# droolsjbpm-tools
installBinary org.drools drools-ant
# droolsjbpm-integration
installBinary org.drools drools-camel
# guvnor
installBinary org.drools droolsjbpm-ide-common

echo Installing jBPM binaries...
echo
installBinary org.jbpm jbpm-flow
installBinary org.jbpm jbpm-flow-builder
installBinary org.jbpm jbpm-persistence-jpa
installBinary org.jbpm jbpm-bam
installBinary org.jbpm jbpm-bpmn2
installBinary org.jbpm jbpm-workitems
installBinary org.jbpm jbpm-human-task
installBinary org.jbpm jbpm-test

cd ..
rm -rf binaries
rm jboss-brms-engine.zip
rm jboss-jbpm-engine.zip

echo Installation of binaries "for" BRMS $MVN_VERSION complete.
echo

echo
echo "######################################################################"
echo "##                                                                  ##"
echo "## NOTE: THIS NEXT STEP IS REQUIRED, DEMO WILL NOT WORK WITHOUT IT! ##"
echo "##                                                                  ##"
echo "######################################################################"
echo
echo You will need to add to your personal ~/.m2/settings.xml the settings provided in the file:
echo
echo "  maven/settings.xml" 
echo
echo These are setup to point maven to the local JBoss EAP6 maven artefacts in the /tmp directory
echo repository just extracted for you.
echo 
echo
echo "${DEMO} Setup Complete."
echo

