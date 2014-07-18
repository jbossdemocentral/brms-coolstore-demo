@ECHO OFF
setlocal

set PROJECT_HOME=%~dp0
set DEMO=JBoss BPM Suite Red Hat Cool Store Demo
set AUTHORS=Jason Milliron, Eric D. Schabell
set PROJECT=git@github.com:eschabell/brms-coolstore-demo.git"
set PRODUCT=JBoss BRMS
set JBOSS_HOME=%PROJECT_HOME%target\jboss-eap-6.1
set SERVER_DIR=%JBOSS_HOME%\standalone\deployments
set SERVER_CONF=%JBOSS_HOME%\standalone\configuration
set SERVER_BIN=%JBOSS_HOME%\bin
set SUPPORT_DIR=%PROJECT_HOME%support
set SRC_DIR=%PROJECT_HOME%installs
set PRJ_DIR=%PROJECT_HOME%projects\brms-coolstore-demo
set EAP=jboss-eap-6.1.1.zip
set BRMS=jboss-brms-6.0.2.GA-redhat-5-deployable-eap6.x.zip
set SUPPORT_LIBS=%PROJECT_HOME%support\libs
set WEB_INF_LIB=%PROJECT_HOME%projects\brms-coolstore-demo\src\main\webapp\WEB-INF\lib\
set VERSION=6.0.2.GA

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
echo ##             %AUTHORS%                ##
echo ##                                                             ##   
echo ##  %PROJECT%          ##
echo ##                                                             ##   
echo #################################################################
echo.

REM make some checks first before proceeding.	
if exist %SRC_DIR%\%EAP% (
        echo EAP sources are present...
        echo.
) else (
        echo Need to download %EAP% package from the Customer Support Portal
        echo and place it in the %SRC_DIR% directory to proceed...
        echo.
        GOTO :EOF
)

REM Create the target directory if it does not already exist.
if not exist %PROJECT_HOME%\target (
        echo - creating the target directory...
        echo.
        mkdir %PROJECT_HOME%\target
) else (
        echo - detected target directory, moving on...
        echo.
)

REM Move the old JBoss instance, if it exists, to the OLD position.
if exist %JBOSS_HOME% (
         echo - existing JBoss Enterprise EAP 6 detected...
         echo.
         echo - moving existing JBoss Enterprise EAP 6 aside...
         echo.
        
        if exist "%JBOSS_HOME%.OLD" (
                rmdir /s /q "%JBOSS_HOME%.OLD"
        )
        
         move "%JBOSS_HOME%" "%JBOSS_HOME%.OLD"
        
        REM Unzip the JBoss EAP instance.
        echo.
        echo Unpacking JBoss Enterprise EAP 6...
        echo.
        cscript /nologo %SUPPORT_DIR%\windows\unzip.vbs %SRC_DIR%\%EAP% %PROJECT_HOME%\target
        
 ) else (
                
        REM Unzip the JBoss EAP instance.
        echo Unpacking new JBoss Enterprise EAP 6...
        echo.
        cscript /nologo %SUPPORT_DIR%\windows\unzip.vbs %SRC_DIR%\%EAP% %PROJECT_HOME%\target
 )
 
REM Unzip the required files from JBoss product deployable.
echo Unpacking %PRODUCT% %VERSION%...
echo.
cscript /nologo %SUPPORT_DIR%\windows\unzip.vbs %SRC_DIR%\%BRMS% %PROJECT_HOME%\target

echo - enabling demo accounts logins in application-users.properties file...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\application-users.properties" "%SERVER_CONF%"
echo. 

echo - enabling demo accounts role setup in application-roles.properties file...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\application-roles.properties" "%SERVER_CONF%"
echo. 

echo - enabling management accounts login setup in mgmt-users.properties file...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\mgmt-users.properties" "%SERVER_CONF%"
echo. 

echo - setting up demo projects...
echo.
xcopy /Y /Q /S "%SUPPORT_DIR%\brms-demo-niogit" "%SERVER_BIN%\.niogit\" 
echo. 

echo   - setting up standalone.xml configuration adjustments...
echo.
xcopy /Y /Q "%SUPPORT_DIR%\standalone.xml" "%SERVER_CONF%"
echo. 

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
