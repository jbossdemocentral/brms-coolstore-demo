#!/bin/sh 
DEMO="JBoss BRMS Red Hat Cool Store Demo"
AUTHORS="Jason Milliron, Andrew Block,"
AUTHORS2="AMahdy AbdElAziz, Eric D. Schabell"
PROJECT="git@github.com:jbossdemocentral/brms-coolstore-demo.git"
PRODUCT="JBoss BRMS"
JBOSS_HOME=./target/jboss-eap-6.4
SERVER_DIR=$JBOSS_HOME/standalone/deployments
SERVER_CONF=$JBOSS_HOME/standalone/configuration
SERVER_BIN=$JBOSS_HOME/bin
SUPPORT_DIR=./support
SRC_DIR=./installs
PRJ_DIR=./projects/brms-coolstore-demo
SUPPORT_LIBS=./support/libs/
WEB_INF_LIB=./projects/brms-coolstore-demo/src/main/webapp/WEB-INF/lib/
BRMS=jboss-brms-installer-6.2.0.BZ-1299002.jar
EAP=jboss-eap-6.4.0-installer.jar
EAP_PATCH=jboss-eap-6.4.4-patch.zip
VERSION=6.2

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
echo "##                     ${AUTHORS}        ##"
echo "##                     ${AUTHORS2}   ##"
echo "##                                                          ##"   
echo "##  ${PROJECT} ##"
echo "##                                                          ##"   
echo "##############################################################"
echo

command -v mvn -q >/dev/null 2>&1 || { echo >&2 "Maven is required but not installed yet... aborting."; exit 1; }

# make some checks first before proceeding.	
if [ -r $SRC_DIR/$EAP ] || [ -L $SRC_DIR/$EAP ]; then
	echo Product sources are present...
	echo
else
	echo Need to download $EAP package from the Customer Portal 
	echo and place it in the $SRC_DIR directory to proceed...
	echo
	exit
fi

if [ -r $SRC_DIR/$EAP_PATCH ] || [ -L $SRC_DIR/$EAP_PATCH ]; then
	echo Product patches are present...
	echo
else
	echo Need to download $EAP_PATCH package from the Customer Portal 
	echo and place it in the $SRC_DIR directory to proceed...
	echo
	exit
fi

if [ -r $SRC_DIR/$BRMS ] || [ -L $SRC_DIR/$BRMS ]; then
	echo JBoss product sources are present...
	echo
else
	echo Need to download $BRMS package from the Customer Portal 
	echo and place it in the $SRC_DIR directory to proceed...
	exit
fi

# Remove the old JBoss instance, if it exists.
if [ -x $JBOSS_HOME ]; then
	echo "  - existing JBoss product install detected and removed..."
	echo
	rm -rf ./target
fi

# Run installers.
echo "JBoss EAP installer running now..."
echo
java -jar $SRC_DIR/$EAP $SUPPORT_DIR/installation-eap -variablefile $SUPPORT_DIR/installation-eap.variables

if [ $? -ne 0 ]; then
	echo
	echo Error occurred during JBoss EAP installation!
	exit
fi

echo
echo "Applying JBoss EAP 6.4.4 patch now..."
echo
$JBOSS_HOME/bin/jboss-cli.sh --command="patch apply $SRC_DIR/$EAP_PATCH"

if [ $? -ne 0 ]; then
	echo
	echo Error occurred during JBoss EAP patching!
	exit
fi
			
echo
echo "JBoss BRMS installer running now..."
echo
java -jar $SRC_DIR/$BRMS $SUPPORT_DIR/installation-brms -variablefile $SUPPORT_DIR/installation-brms.variables

if [ $? -ne 0 ]; then
	echo
	echo Error occurred during $PRODUCT installation!
	exit
fi

echo
echo "  - enabling demo accounts role setup in application-roles.properties file..."
echo
cp $SUPPORT_DIR/application-roles.properties $SERVER_CONF

echo "  - setting up demo projects..."
echo
cp -r $SUPPORT_DIR/brms-demo-niogit $SERVER_BIN/.niogit

echo "  - setting up standalone.xml configuration adjustments..."
echo
cp $SUPPORT_DIR/standalone.xml $SERVER_CONF

echo "  - setup email notification users..."
echo
cp $SUPPORT_DIR/userinfo.properties $SERVER_DIR/business-central.war/WEB-INF/classes/

echo "  - making sure standalone.sh for server is executable..."
echo
chmod u+x $JBOSS_HOME/bin/standalone.sh

echo "  - setting up custom maven settings so KieScanner finds repo updates..."
echo
cp $SUPPORT_DIR/settings.xml $SERVER_BIN/.settings.xml

# ensure project lib dir exists.
if [ ! -d $WEB_INF_LIB ]; then
	echo "  - missing web inf lib directory in project being created..."
	echo
	mkdir -p $WEB_INF_LIB
fi

mvn install:install-file -Dfile=$SUPPORT_LIBS/coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar

echo
echo Deploying the Cool Store web application. 
echo
cd $PRJ_DIR
mvn clean install
cp target/brms-coolstore-demo.war ../../$SERVER_DIR
cd ../..

echo
echo "*************************************************************************"
echo "*                                                                       *"
echo "*   JBoss BRMS Cool Store install completed.                            *"
echo "*                                                                       *"
echo "*   You can now start the server with:                                  *"
echo "*                                                                       *"
echo "*       $SERVER_BIN/standalone.sh                        *"
echo "*                                                                       *"
echo "*************************************************************************"
echo

