#!/bin/sh 
DEMO="JBoss BRMS Red Hat Cool Store Demo"
AUTHORS="Jason Milliron, Eric D. Schabell"
PROJECT="git@github.com:eschabell/brms-coolstore-demo.git"
PRODUCT=JBoss BRMS
JBOSS_HOME=./target/jboss-eap-6.1
SERVER_DIR=$JBOSS_HOME/standalone/deployments
SERVER_CONF=$JBOSS_HOME/standalone/configuration
SERVER_BIN=$JBOSS_HOME/bin
SUPPORT_DIR=./support
SRC_DIR=./installs
PRJ_DIR=./projects/brms-coolstore-demo
EAP=jboss-eap-6.1.1.zip
BRMS=jboss-brms-6.0.2.GA-redhat-5-deployable-eap6.x.zip
SUPPORT_LIBS=./support/libs/
WEB_INF_LIB=./projects/brms-coolstore-demo/src/main/webapp/WEB-INF/lib/
VERSION=6.0.2.GA

# wipe screen.
clear 

echo
echo "##############################################################"
echo "##                                                          ##"   
echo "##  Setting up the ${DEMO}       ##"
echo "##                                                          ##"   
echo "##                                                          ##"   
echo "##             ####   ####    #   #    ###                  ##"
echo "##             #   #  #   #  # # # #  #                     ##"
echo "##             ####   ####   #  #  #   ##                   ##"
echo "##             #   #  #  #   #     #     #                  ##"
echo "##             ####   #   #  #     #  ###                   ##"
echo "##                                                          ##"   
echo "##                                                          ##"   
echo "##  brought to you by,                                      ##"   
echo "##             ${AUTHORS}             ##"
echo "##                                                          ##"   
echo "##  ${PROJECT}        ##"
echo "##                                                          ##"   
echo "##############################################################"
echo

command -v mvn -q >/dev/null 2>&1 || { echo >&2 "Maven is required but not installed yet... aborting."; exit 1; }

# make some checks first before proceeding.	
if [ -r $SRC_DIR/$EAP ] || [-L ${SRC_DIR/$EAP} ]; then
		echo EAP sources are present...
		echo
else
		echo Need to download $EAP package from the Customer Portal 
		echo and place it in the $SRC_DIR directory to proceed...
		echo
		exit
fi

# Create the target directory if it does not already exist.
if [ ! -x target ]; then
		echo "  - creating the target directory..."
		echo
		mkdir target
else
		echo "  - detected target directory, moving on..."
		echo
fi

# Move the old JBoss instance, if it exists, to the OLD position.
if [ -x $JBOSS_HOME ]; then
		echo "  - existing JBoss Enterprise EAP 6 detected..."
		echo
		echo "  - moving existing JBoss Enterprise EAP 6 aside..."
		echo
		rm -rf $JBOSS_HOME.OLD
		mv $JBOSS_HOME $JBOSS_HOME.OLD
fi

# Unzip the JBoss EAP instance.
echo Unpacking new JBoss Enterprise EAP 6...
echo
unzip -q -d target $SRC_DIR/$EAP

# Unzip the required files from JBoss product deployable.
echo Unpacking $PRODUCT $VERSION...
echo
unzip -q -o -d target $SRC_DIR/$BRMS

echo "  - enabling demo accounts logins in application-users.properties file..."
echo
cp $SUPPORT_DIR/application-users.properties $SERVER_CONF

echo "  - enabling demo accounts role setup in application-roles.properties file..."
echo
cp $SUPPORT_DIR/application-roles.properties $SERVER_CONF

echo "  - enabling management accounts login setup in mgmt-users.properties file..."
echo
cp $SUPPORT_DIR/mgmt-users.properties $SERVER_CONF

echo "  - setting up demo projects..."
echo
cp -r $SUPPORT_DIR/brms-demo-niogit $SERVER_BIN/.niogit

echo "  - setting up standalone.xml configuration adjustments..."
echo
cp $SUPPORT_DIR/standalone.xml $SERVER_CONF

echo "  - making sure standalone.sh for server is executable..."
echo
chmod u+x $JBOSS_HOME/bin/standalone.sh

# ensure project lib dir exists.
if [ ! -d $WEB_INF_LIB ]; then
	echo "  - missing web inf lib directory in project being created..."
	echo
	mkdir -p $WEB_INF_LIB
fi

mvn install:install-file -Dfile=$SUPPORT_LIBS/cdiutils-1.0.0.jar -DgroupId=org.vaadin.virkki -DartifactId=cdiutils -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=$SUPPORT_LIBS/coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar

cp $SUPPORT_LIBS/cdiutils-1.0.0.jar $WEB_INF_LIB

echo
echo Copying BPM Suite Cool Store application into the JBoss BPM Suite.
echo
cd $PRJ_DIR
mvn clean install
cp target/brms-coolstore-demo.war ../../$SERVER_DIR
cd ../..

echo "You can now start the $PRODUCT with $SERVER_BIN/standalone.sh"
echo

echo "$PRODUCT $VERSION $DEMO Setup Complete."
echo

