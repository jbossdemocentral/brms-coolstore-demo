@ECHO OFF
setlocal

SET DEMO=CoolStore Demo
SET JBOSS_HOME=.\target\jboss-eap-6.0
SET SERVER_DIR=%JBOSS_HOME%\standalone\deployments\
SET SERVER_CONF=%JBOSS_HOME%/standalone/configuration\
SET LIB_DIR=.\support\lib\
SET SRC_DIR=.\installs\
SET EAP=jboss-eap-6.0.1.zip
SET BRMS=brms-p-5.3.1.GA-deployable-ee6.zip
SET EAP_REPO=jboss-eap-6.0.1-maven-repository
SET VERSION=5.3.1

echo.
echo Setting up the JBoss Enterprise EAP 6 %DEMO% environment...
echo.

REM Make some checks before proceeding.
if exist %SRC_DIR%\%EAP% (
	echo EAP sources are present...
	echo.
) else (
	echo Need to download %EAP% package from the Customer Support Portal
	echo and place it in the %SRC_DIR% directory to proceed...
	echo.
	GOTO :EOF
)

REM Create the target directory if it does not already exist
if not exist target (
	echo  - creating the target directory...
	echo.
	mkdir target
) else (
	echo  - detected target directory, moving on...
	echo.
)

REM Move the old JBoss instance, if it exists, to the OLD position.
if exist %JBOSS_HOME% (
 	echo  - existing JBoss Enterprise EAP 6 detected...
 	echo.
 	echo  - moving existing JBoss Enterprise EAP 6 aside...
 	echo.
	
	if exist "%JBOSS_HOME%.OLD" (
		rmdir /s /q "%JBOSS_HOME%.OLD"
	)
	
 	move "%JBOSS_HOME%" "%JBOSS_HOME%.OLD"
	
	REM Unzip the JBoss EAP instance.
	echo.
 	echo Unpacking JBoss Enterprise EAP 6...
 	echo.
 	cscript /nologo unzip.vbs %SRC_DIR%/%EAP% target
	
 ) else (
		
	REM Unzip the JBoss EAP instance.
 	echo Unpacking new JBoss Enterprise EAP 6...
 	echo.
 	cscript /nologo unzip.vbs %SRC_DIR%/%EAP% target
 )
 
 REM Unzip the required files from JBoss BRMS Deployable
echo Unpacking JBoss Enterprise BRMS %VERSION%...
echo.
cd installs
cscript /nologo ../unzip.vbs %BRMS% .

echo. - deploying JBoss Enterprise BRMS Manager WAR...
echo.
cscript /nologo ../unzip.vbs jboss-brms-manager-ee6.zip "../%SERVER_DIR%"
del jboss-brms-manager-ee6.zip 

echo  - deploying jBPM Console WARs...
echo.
cscript /nologo ../unzip.vbs jboss-jbpm-console-ee6.zip "../%SERVER_DIR%"
del jboss-jbpm-console-ee6.zip

cscript /nologo ../unzip.vbs jboss-jbpm-engine.zip .

echo  - copying jBPM client JARs...
echo.

xcopy /Y /Q "lib\netty.jar" "../%SERVER_DIR%"
xcopy /Y /Q lib "../%LIB_DIR%"
xcopy /Y /Q jbpm-test-5.3.1.BRMS.jar "../%LIB_DIR%"
xcopy /Y /Q jbpm-human-task-5.3.1.BRMS.jar "../%LIB_DIR%"
xcopy /Y /Q jbpm-persistence-jpa-5.3.1.BRMS.jar "../%LIB_DIR%"
xcopy /Y /Q jbpm-workitems-5.3.1.BRMS.jar "../%LIB_DIR%"

del /F jboss-jbpm-engine.zip
del -F *.jar
del /F modeshape.zip 
del /F *.RSA 
rmdir /q /s lib
del /F jboss-brms-engine.zip

echo.
echo Rounding up, setting permissions and copying support files...
echo.
cd ..

echo  - enabling demo accounts logins in brms-users.properties file...
echo.
xcopy /Y /Q "support\brms-users.properties" "%SERVER_CONF%"

echo.
echo  - enabling demo accounts role setup in brms-roles.properties file...
echo.
xcopy /Y /Q  "support\brms-roles.properties" "%SERVER_CONF%"

echo.
echo - adding dodeploy files to deploy all brms components...
echo.

REM Create Empty DoDeploy Files
<nul (set/p z=) >"%SERVER_DIR%\business-central-server.war.dodeploy"
<nul (set/p z=) >"%SERVER_DIR%\business-central.war.dodeploy"
<nul (set/p z=) >"%SERVER_DIR%\designer.war.dodeploy"
<nul (set/p z=) >"%SERVER_DIR%\jboss-brms.war.dodeploy"
<nul (set/p z=) >"%SERVER_DIR%\jbpm-human-task.war.dodeploy"

echo - configuring security authentication, copying updated components.xml file to jboss-brms.war...
echo.
xcopy /Y /Q "support\components.xml" "%SERVER_DIR%\jboss-brms.war\WEB-INF\"

echo.
echo - configuring deployment timeout extention and added security domain brms in standalone.xml...
echo.
xcopy /Y /Q "support\standalone.xml" "%SERVER_CONF%"

echo. 
echo - enabling demo users for human tasks in jbpm-human-task.war web.xml file...
echo.
xcopy /Y /Q "support\jbpm-human-task-war-web.xml" "%SERVER_DIR%\jbpm-human-task.war\WEB-INF\web.xml"

echo.
echo - enabling work items by registering Email and Log nodes...
echo.
xcopy /Y /Q "support\drools.session.conf" "%SERVER_DIR%\business-central-server.war\WEB-INF\classes\META-INF"
xcopy /Y /Q "support\CustomWorkItemHandlers.conf" "%SERVER_DIR%\business-central-server.war\WEB-INF\classes\META-INF"

echo.
echo - adding model jar to business central admin console classpath...
echo.
xcopy /Y /Q "support\customereval-model.jar" "%SERVER_DIR%\business-central-server.war\WEB-INF\lib"

echo. 
echo - adding netty dep to business-central-server.war and jbpm-human-task.war...
echo.
xcopy /Y /Q "support\MANIFEST.MF" "%SERVER_DIR%\business-central-server.war\WEB-INF\classes\META-INF\"
xcopy /Y /Q "support\MANIFEST.MF" "%SERVER_DIR%\jbpm-human-task.war\WEB-INF\classes\META-INF\"

echo.
echo JBoss Enterprise BRMS %VERSION% %DEMO% Setup Complete.
echo.
