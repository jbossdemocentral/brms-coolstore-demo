#!/bin/sh
DEMO="JBoss BRMS Red Hat Cool Store Demo"
AUTHORS1="Jason Milliron, Andrew Block,"
AUTHORS2="AMahdy AbdElAziz, Eric D. Schabell"
AUTHORS3="Duncan Doyle, Jaen Swart"
PROJECT="git@github.com:jbossdemocentral/brms-coolstore-demo.git"
PRODUCT="JBoss BRMS"
TARGET=./target
JBOSS_HOME=$TARGET/jboss-eap-7.0
SERVER_DIR=$JBOSS_HOME/standalone/deployments
SERVER_CONF=$JBOSS_HOME/standalone/configuration
SERVER_BIN=$JBOSS_HOME/bin
SUPPORT_DIR=./support
SRC_DIR=./installs
PRJ_DIR=./projects/brms-coolstore-demo
SUPPORT_LIBS=./support/libs/
WEB_INF_LIB=./projects/brms-coolstore-demo/src/main/webapp/WEB-INF/lib/
BRMS=jboss-brms-6.4.0.GA-deployable-eap7.x.zip
EAP=jboss-eap-7.0.0-installer.jar
#EAP_PATCH=jboss-eap-6.4.7-patch.zip
VERSION=6.4
PROJECT_GIT_REPO=https://github.com/jbossdemocentral/brms-coolstore-repo
PROJECT_GIT_DIR=./support/demo_project_git
OFFLINE_MODE=false

# wipe screen.
clear

function usage {
      echo "Usage: init.sh [args...]"
      echo "where args include:"
      echo "    -o              run this script in offline mode. The project's Git repo will not be downloaded. Instead a cached version will be used if available."
      echo "    -h              prints this help."
}

#Parse the params
while getopts "oh" opt; do
  case $opt in
    o)
      OFFLINE_MODE=true
      ;;
    h)
      usage
      exit 0
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

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
echo "##                     ${AUTHORS1}        ##"
echo "##                     ${AUTHORS2}   ##"
echo "##                     ${AUTHORS3}             ##"
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

#if [ -r $SRC_DIR/$EAP_PATCH ] || [ -L $SRC_DIR/$EAP_PATCH ]; then
#	echo Product patches are present...
#	echo
#else
#	echo Need to download $EAP_PATCH package from the Customer Portal
#	echo and place it in the $SRC_DIR directory to proceed...
#	echo
#	exit
#fi

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

#echo
#echo "Applying JBoss EAP 6.4.7 patch now..."
#echo
#$JBOSS_HOME/bin/jboss-cli.sh --command="patch apply $SRC_DIR/$EAP_PATCH"

#if [ $? -ne 0 ]; then
#	echo
#	echo Error occurred during JBoss EAP patching!
#	exit
#fi

echo
echo "Deploying JBoss BRMS now..."
echo
unzip -qo $SRC_DIR/$BRMS -d $TARGET

if [ $? -ne 0 ]; then
	echo
	echo Error occurred during $PRODUCT installation!
	exit
fi

echo
echo "  - enabling demo accounts setup..."
echo
$JBOSS_HOME/bin/add-user.sh -a -r ApplicationRealm -u brmsAdmin -p jbossbrms1! -ro analyst,admin,manager,user,kie-server,kiemgmt,rest-all --silent
$JBOSS_HOME/bin/add-user.sh -a -r ApplicationRealm -u erics -p jbossbrms1! -ro analyst,admin,manager,user,kie-server,kiemgmt,rest-all --silent

echo "  - setting up demo projects..."
echo
# Copy the default (internal) BPMSuite repo's.
cp -r $SUPPORT_DIR/brms-demo-niogit $SERVER_BIN/.niogit
# Copy the demo project repo.
if ! $OFFLINE_MODE
then
  # Not in offline mode, so downloading the latest repo. We first download the repo in a temp dir and we only delete the old, cached repo, when the download is succesful.
  echo "  - cloning the project's Git repo from: $PROJECT_GIT_REPO"
  echo
  rm -rf ./target/temp && git clone --bare $PROJECT_GIT_REPO ./target/temp/brms-coolstore-repo.git || { echo; echo >&2 "Error cloning the project's Git repo. If there is no Internet connection available, please run this script in 'offline-mode' ('-o') to use a previously downloaded and cached version of the project's Git repo... Aborting"; echo; exit 1; }

  echo "  - replacing cached project git repo: $PROJECT_GIT_DIR/brms-coolstore-repo.git"
  echo
  rm -rf $PROJECT_GIT_DIR/brms-coolstore-repo.git && mkdir -p $PROJECT_GIT_DIR && cp -R target/temp/brms-coolstore-repo.git $PROJECT_GIT_DIR/brms-coolstore-repo.git && rm -rf ./target/temp
else
  echo "  - running in offline-mode, using cached project's Git repo."
  echo
  if [ ! -d "$PROJECT_GIT_DIR" ]
  then
    echo "No project Git repo found. Please run the script without the 'offline' ('-o') option to automatically download the required Git repository!"
    echo
    exit 1
  fi
fi
# Copy the repo to the JBoss BPMSuite installation directory.
rm -rf $JBOSS_HOME/bin/.niogit/coolstore-demo.git && cp -R $PROJECT_GIT_DIR/brms-coolstore-repo.git $SERVER_BIN/.niogit/coolstore-demo.git


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
echo "============================================================================"
echo "=                                                                          ="
echo "=  You can now start the $PRODUCT with:                                 ="
echo "=                                                                          ="
echo "=   $SERVER_BIN/standalone.sh                               ="
echo "=                                                                          ="
echo "=  Login into business central at:                                         ="
echo "=                                                                          ="
echo "=    http://localhost:8080/business-central  (u:brmsAdmin / p:jbossbrms1!) ="
echo "=                                                                          ="
echo "=  Login into the Coostore application at:                                 ="
echo "=                                                                          ="
echo "=    http://localhost:8080/brms-coolstore-demo                             ="
echo "=                                                                          ="
echo "=  See README.md for general details to run the various demo cases.        ="
echo "=                                                                          ="
echo "=  $PRODUCT $VERSION $DEMO Setup Complete.        ="
echo "=                                                                          ="
echo "============================================================================"
