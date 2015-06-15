#!/bin/sh 
DEMO="JBoss BRMS Red Hat Cool Store Demo"
AUTHORS="Jason Milliron, Andrew Block, Eric D. Schabell"
PROJECT="git@github.com:jbossdemocentral/brms-coolstore-demo.git"
PRODUCT=JBoss BRMS
JBOSS_HOME=./target/jboss-eap-6.4
SERVER_DIR=$JBOSS_HOME/standalone/deployments
SERVER_CONF=$JBOSS_HOME/standalone/configuration
SERVER_BIN=$JBOSS_HOME/bin
SUPPORT_DIR=./support
SRC_DIR=./installs
PRJ_DIR=./projects/brms-coolstore-demo
PATCH_DIR=./target/jboss-brms-6.1.1-patch
SUPPORT_LIBS=./support/libs/
WEB_INF_LIB=./projects/brms-coolstore-demo/src/main/webapp/WEB-INF/lib/
BRMS=jboss-brms-6.1.0.GA-installer.jar
PATCH=jboss-brms-6.1.1-patch.zip
EAP=jboss-eap-6.4.0-installer.jar
VERSION=6.1

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
echo "##        ${AUTHORS}    ##"
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
echo "JBoss BRMS installer running now..."
echo
java -jar $SRC_DIR/$BRMS $SUPPORT_DIR/installation-brms -variablefile $SUPPORT_DIR/installation-brms.variables

if [ $? -ne 0 ]; then
	echo
	echo Error occurred during $PRODUCT installation!
	exit
fi

echo
echo "JBoss BRMS patch ($PATCH) installation now..."
echo
unzip $SRC_DIR/$PATCH -d ./target
cd $PATCH_DIR
./apply-updates.sh ../jboss-eap6.4 eap6.x
cd ../..
rm -rf $PATCH_DIR

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

mvn install:install-file -Dfile=$SUPPORT_LIBS/cdiutils-1.0.0.jar -DgroupId=org.vaadin.virkki -DartifactId=cdiutils -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=$SUPPORT_LIBS/coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar
cp $SUPPORT_LIBS/cdiutils-1.0.0.jar $WEB_INF_LIB

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
echo "*       $SERVER_BIN/standalone.sh"
echo "*                                                                       *"
echo "*************************************************************************"
echo

