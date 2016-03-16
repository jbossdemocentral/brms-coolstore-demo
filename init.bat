@ECHO OFF
setlocal

set PROJECT_HOME=%~dp0
set DEMO=JBoss BPM Suite Red Hat Cool Store Demo
set AUTHORS=Jason Milliron, Andrew Block,
set AUTHORS2=AMahdy AbdElAziz, Eric D. Schabell
set PROJECT=git@github.com:jbossdemocentral/brms-coolstore-demo.git"
set PRODUCT=JBoss BRMS
set JBOSS_HOME=%PROJECT_HOME%target\jboss-eap-6.4
set SERVER_DIR=%JBOSS_HOME%\standalone\deployments
set SERVER_CONF=%JBOSS_HOME%\standalone\configuration
set SERVER_BIN=%JBOSS_HOME%\bin
set SUPPORT_DIR=%PROJECT_HOME%support
set SRC_DIR=%PROJECT_HOME%installs
set PRJ_DIR=%PROJECT_HOME%projects\brms-coolstore-demo
set SUPPORT_LIBS=%PROJECT_HOME%support\libs
set WEB_INF_LIB=%PROJECT_HOME%projects\brms-coolstore-demo\src\main\webapp\WEB-INF\lib\
set BRMS=jboss-brms-installer-6.2.0.BZ-1299002.jar
set EAP=jboss-eap-6.4.0-installer.jar
set EAP_PATCH=jboss-eap-6.4.4-patch.zip
set VERSION=6.2

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
echo ##                     %AUTHORS%   ##
echo ##                     %AUTHORS2%   ##
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

if exist %SRC_DIR%\%EAP_PATCH% (
        echo Product patches are present...
        echo.
) else (
        echo Need to download %EAP_PATCH% package from the Customer Support Portal
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

call set NOPAUSE=true

echo.
echo Applying JBoss EAP patch now...
echo.
call %JBOSS_HOME%/bin/jboss-cli.bat --command="patch apply %SRC_DIR%/%EAP_PATCH% --override-all"

if not "%ERRORLEVEL%" == "0" (
  echo.
	echo Error Occurred During JBoss EAP Patch Installation!
	echo.
	GOTO :EOF
)

echo.
echo JBoss BRMS installer running now...
echo.
call java -jar %SRC_DIR%\%BRMS% %SUPPORT_DIR%\installation-brms -variablefile %SUPPORT_DIR%\installation-brms.variables

if not "%ERRORLEVEL%" == "0" (
	echo Error Occurred During %PRODUCT% Installation!
	echo.
	GOTO :EOF
)

echo.
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
xcopy /Y /Q "%SUPPORT_DIR%\userinfo.properties" "%SERVER_DIR%\business-central.war\WEB-INF\classes\"

echo   - setting up custom maven settings so KieScanner finds repo updates...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\settings.xml" "%SERVER_BIN%\.settings.xml

REM ensure project lib dir exists
if not exist %WEB_INF_LIB% (
	echo - missing web inf lib directory in project being created...
	echo.
	mkdir %WEB_INF_LIB%
)

call mvn install:install-file -Dfile=%SUPPORT_LIBS%\coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar

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
