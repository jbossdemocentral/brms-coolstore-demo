@ECHO OFF
setlocal

set PROJECT_HOME=%~dp0
set DEMO=JBoss BPM Suite Red Hat Cool Store Demo
set AUTHORS=Jason Milliron, Andrew Block, Eric D. Schabell
set PROJECT=git@github.com:jbossdemocentral/brms-coolstore-demo.git"
set PRODUCT=JBoss BRMS
set JBOSS_HOME=%PROJECT_HOME%target\jboss-eap-6.4
set SERVER_DIR=%JBOSS_HOME%\standalone\deployments
set SERVER_CONF=%JBOSS_HOME%\standalone\configuration
set SERVER_BIN=%JBOSS_HOME%\bin
set SUPPORT_DIR=%PROJECT_HOME%support
set SRC_DIR=%PROJECT_HOME%installs
set PRJ_DIR=%PROJECT_HOME%projects\brms-coolstore-demo
set PATCH_DIR=%PROJECT_HOME%\target\jboss-brms-6.1.1-patch
set SUPPORT_LIBS=%PROJECT_HOME%support\libs
set WEB_INF_LIB=%PROJECT_HOME%projects\brms-coolstore-demo\src\main\webapp\WEB-INF\lib\
set BRMS=jboss-brms-6.1.0.GA-installer.jar
set PATCH=jboss-brms-6.1.1-patch.zip
set EAP=jboss-eap-6.4.0-installer.jar
set VERSION=6.1

REM wipe screen.
cls

echo.
echo #################################################################
echo ##                                                             ##   
echo ##  Setting up the %DEMO%     ##
echo ##                                                             ##   
echo ##                                                             ##   
echo ##     ####  ####   #   #   ###    #####                       ##
echo ##     #   # #   # # # # # #       #                           ##
echo ##     ####  ####  #  #  #  ##     #####                       ##
echo ##     #   # # #   #     #    #    #   #                       ##
echo ##     ####  #  #  #     # ###     #####                       ##
echo ##                                                             ##   
echo ##                                                             ##   
echo ##  brought to you by,                                         ##   
echo ##            %AUTHORS%   ##
echo ##                                                             ##   
echo ##  %PROJECT%   ##
echo ##                                                             ##   
echo #################################################################
echo.

REM make some checks first before proceeding. 
if exist %SRC_DIR%\%EAP% (
        echo Product sources are present...
        echo.
) else (
        echo Need to download %EAP% package from the Customer Support Portal
        echo and place it in the %SRC_DIR% directory to proceed...
        echo.
        GOTO :EOF
)

if exist %SRC_DIR%\%BRMS% (
	echo JBoss product sources, %BRMS% present...
	echo.
) else (
	echo Need to download %BRMS% package from the Customer Support Portal and place it in the %SRC_DIR% directory to proceed...
	echo.
	GOTO :EOF
)

REM Remove the old JBoss instance, if it exists.
if exist %JBOSS_HOME% (
	echo - existing JBoss product install detected and removed...
	echo.

	rmdir /s /q "%PROJECT_HOME%\target"
)

REM Run installers.
echo EAP installer running now...
echo.
call java -jar %SRC_DIR%/%EAP% %SUPPORT_DIR%\installation-eap -variablefile %SUPPORT_DIR%\installation-eap.variables


if not "%ERRORLEVEL%" == "0" (
  echo.
	echo Error Occurred During JBoss EAP Installation!
	echo.
	GOTO :EOF
)

echo JBoss BRMS installer running now...
echo.
call java -jar %SRC_DIR%\%BRMS% %SUPPORT_DIR%\installation-brms -variablefile %SUPPORT_DIR%\installation-brms.variables

if not "%ERRORLEVEL%" == "0" (
	echo Error Occurred During %PRODUCT% Installation!
	echo.
	GOTO :EOF
)

echo.
echo JBoss BRMS patch [%PATCH%] installation now...
echo.
cscript /nologo %SUPPORT_DIR%\windows\unzip.vbs %SRC_DIR%\%PATCH% %PROJECT_HOME%\target
cd "%PATCH_DIR%"
apply-updates.bat "%PROJECT_HOME%\target\jboss-eap-6.4" "eap6.x"
cd "%PROJECT_HOME%"

echo - enabling demo accounts role setup in application-roles.properties file...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\application-roles.properties" "%SERVER_CONF%"
echo. 

echo - setting up demo projects...
echo.
xcopy /Y /Q /S "%SUPPORT_DIR%\brms-demo-niogit" "%SERVER_BIN%\.niogit\" 
echo. 

echo   - setting up standalone.xml configuration adjustments...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\standalone.xml" "%SERVER_CONF%"
echo. 

echo - setup email task notification users...
echo.
xcopy "%SUPPORT_DIR%\userinfo.properties" "%SERVER_DIR%\business-central.war\WEB-INF\classes\"

echo   - setting up custom maven settings so KieScanner finds repo updates...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\settings.xml" "%SERVER_BIN%\.settings.xml

REM ensure project lib dir exists
if not exist %WEB_INF_LIB% (
	echo - missing web inf lib directory in project being created...
	echo.
	mkdir %WEB_INF_LIB%
)

call mvn install:install-file -Dfile=%SUPPORT_LIBS%\cdiutils-1.0.0.jar -DgroupId=org.vaadin.virkki -DartifactId=cdiutils -Dversion=1.0.0 -Dpackaging=jar
call mvn install:install-file -Dfile=%SUPPORT_LIBS%\coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar

xcopy /Y /Q "%SUPPORT_LIBS%\cdiutils-1.0.0.jar" "%WEB_INF_LIB%"

cd "%PRJ_DIR%"
call mvn clean install

echo.
echo Deploying the Cool Store web application.
echo.

xcopy /Y /Q "%PRJ_DIR%\target\brms-coolstore-demo.war" "%SERVER_DIR%"
cd "%PROJECT_HOME%"

echo.
echo You can now start the %PRODUCT% with %SERVER_BIN%\standalone.bat
echo.

echo %PRODUCT% %VERSION% %DEMO% Setup Complete.
echo.
