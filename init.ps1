Param(
    [switch]$h,
    [switch]$o
)


Write-Host "Installing Coolstore Demo"

# wipe screen
Clear-Host

$PROJECT_HOME = $PSScriptRoot
$DEMO="JBoss BRMS Red Hat Cool Store Demo"
$AUTHORS1="Jason Milliron, Andrew Block,"
$AUTHORS2="AMahdy AbdElAziz, Eric D. Schabell"
$AUTHORS3="Duncan Doyle, Jaen Swart"
$PROJECT="git@github.com:jbossdemocentral/brms-coolstore-demo.git"
$PRODUCT="JBoss BRMS"
$TARGET="$PROJECT_HOME\target"
$JBOSS_HOME="$TARGET\jboss-eap-7.0"
$SERVER_DIR="$JBOSS_HOME\standalone\deployments"
$SERVER_CONF="$JBOSS_HOME\standalone\configuration"
$SERVER_BIN="$JBOSS_HOME\bin"
$SUPPORT_DIR="$PROJECT_HOME\support"
$SRC_DIR="$PROJECT_HOME\installs"
$PRJ_DIR="$PROJECT_HOME\projects\brms-coolstore-demo"
$SUPPORT_LIBS="$PROJECT_HOME\support\libs"
$WEB_INF_LIB="$PROJECT_HOME\projects\brms-coolstore-demo\src\main\webapp\WEB-INF\lib\"
$BRMS="jboss-brms-6.4.0.GA-deployable-eap7.x.zip"
$EAP="jboss-eap-7.0.0-installer.jar"
#$EAP_PATCH="jboss-eap-6.4.7-patch.zip"
$VERSION="6.4"
$PROJECT_GIT_REPO="https://github.com/jbossdemocentral/brms-coolstore-repo"
$PROJECT_GIT_DIR="$PROJECT_HOME\support\demo_project_git"
$OFFLINE_MODE="false"


If ($h) {
	Write-Host "Usage: init.ps1 [args...]"
    Write-Host "where args include:"
    Write-Host "    -o              run this script in offline mode. The project's Git repo will not be downloaded. Instead a cached version will be used if available."
    Write-Host "    -h              prints this help."
	exit
}


Write-Host "##############################################################"
Write-Host "##                                                          ##"
Write-Host "##  Setting up the $DEMO       ##"
Write-Host "##                                                          ##"
Write-Host "##                                                          ##"
Write-Host "##             ####   ####    #   #    ###                  ##"
Write-Host "##             #   #  #   #  # # # #  #                     ##"
Write-Host "##             ####   ####   #  #  #   ##                   ##"
Write-Host "##             #   #  #  #   #     #     #                  ##"
Write-Host "##             ####   #   #  #     #  ###                   ##"
Write-Host "##                                                          ##"
Write-Host "##                                                          ##"
Write-Host "##  brought to you by,                                      ##"
Write-Host "##                     $AUTHORS1        ##"
Write-Host "##                     $AUTHORS2   ##"
Write-Host "##                     $AUTHORS3             ##"
Write-Host "##                                                          ##"
Write-Host "##  $PROJECT ##"
Write-Host "##                                                          ##"
Write-Host "##############################################################`n"


#Test whether Maven is available.
if ((Get-Command "mvn" -ErrorAction SilentlyContinue) -eq $null)
{
   Write-Host "Maven is required but not installed yet... aborting.`n"
   exit
}


If (Test-Path "$SRC_DIR\$EAP") {
	Write-Host "Product sources are present...`n"
} Else {
	Write-Host "Need to download $EAP package from the Customer Support Portal"
	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
	exit
}

<#
If (Test-Path "$SRC_DIR\$EAP_PATCH") {
	Write-Host "Product patches are present...`n"
} Else {
	Write-Host "Need to download $EAP_PATCH package from the Customer Support Portal"
	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
	exit
}
#>

If (Test-Path "$SRC_DIR\$BRMS") {
	Write-Host "Product sources are present...`n"
} Else {
	Write-Host "Need to download $BRMS package from the Customer Support Portal"
	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
	exit
}

#Test whether Java is available.
if ((Get-Command "java.exe" -ErrorAction SilentlyContinue) -eq $null)
{
   Write-Host "The 'java' command is required but not available. Please install Java and add it to your PATH.`n"
   exit
}

if ((Get-Command "javac.exe" -ErrorAction SilentlyContinue) -eq $null)
{
   Write-Host "The 'javac' command is required but not available. Please install Java and add it to your PATH.`n"
   exit
}

# Test whether 7Zip is available.
# We use 7Zip because it seems to be one of the few ways to extract the BRMS zip file without hitting the 260 character limit problem of the Windows API.
# This is definitely not ideal, but I can't unzip without problems when using the default Powershell unzip utilities.
# 7-Zip can be downloaded here: http://www.7-zip.org/download.html
if ((Get-Command "7z.exe" -ErrorAction SilentlyContinue) -eq $null)
{
   Write-Host "The '7z.exe' command is required but not available. Please install 7-Zip.`n"
	 Write-Host "7-Zip is used to overcome the Windows 260 character limit on paths while extracting the JBoss BRMS ZIP file.`n"
	 Write-Host "7-Zip can be donwloaded here: http://www.7-zip.org/download.html`n"
	 Write-Host "Please make sure to add '7z.exe' to your 'PATH' after installation.`n"
   exit
}

# Remove the old installation if it exists
If (Test-Path "$JBOSS_HOME") {
	Write-Host "Removing existing installation.`n"
	# The "\\?\" prefix is a trick to get around the 256 path-length limit in Windows.
	# If we don't do this, the Remove-Item command fails when it tries to delete files with a name longer than 256 characters.
	Remove-Item "\\?\$JBOSS_HOME" -Force -Recurse
	# The command above does not seem to work reliably, so trying this alternative instead.
	#Get-ChildItem -Path "$JBOSS_HOME\\*" -Recurse | Remove-Item -Force -Recurse
	#Remove-Item $JBOSS_HOME
}

#Run installers.
Write-Host "EAP installer running now...`n"
$argList = "-jar $SRC_DIR\$EAP $SUPPORT_DIR\installation-eap -variablefile $SUPPORT_DIR\installation-eap.variables"
$process = (Start-Process -FilePath java.exe -ArgumentList $argList -Wait -PassThru)
Write-Host "Process finished with return code: " $process.ExitCode
Write-Host "`n"

If ($process.ExitCode -ne 0) {
	Write-Error "Error installing JBoss EAP."
	exit
}

<#
Write-Host "Applying JBoss EAP patch now...`n"
$argList = '--command="patch apply ' + "$SRC_DIR\$EAP_PATCH" + ' --override-all"'
$patchProcess = (Start-Process -FilePath "$JBOSS_HOME\bin\jboss-cli.bat" -ArgumentList $argList -Wait -PassThru)
Write-Host "Process finished with return code: " $patchProcess.ExitCode
Write-Host ""

If ($patchProcess.ExitCode -ne 0) {
	Write-Error "Error occurred during JBoss EAP patch installation."
	exit
}

Write-Host "JBoss EAP patch applied succesfully!`n"
#>

# Using 7-Zip. This currently seems to be the only way to overcome the Windows 260 character path limit.
$argList = "x -o$TARGET -y $SRC_DIR\$BRMS"
$unzipProcess = (Start-Process -FilePath 7z.exe -ArgumentList $argList -Wait -PassThru -NoNewWindow)

If ($unzipProcess.ExitCode -ne 0) {
	Write-Error "Error occurred during JBoss BRMS installation."
	exit
}

Write-Host ""

Write-Host "- enabling demo accounts setup ...`n"
$argList1 = "-a -r ApplicationRealm -u brmsAdmin -p 'jbossbrms1!' -ro 'analyst,admin,manager,user,kie-server,kiemgmt,rest-all' --silent"
$argList2 = "-a -r ApplicationRealm -u erics -p 'jbossbrms1!' -ro 'analyst,admin,manager,user,kie-server,kiemgmt,rest-all' --silent"
try {
	Invoke-Expression "$JBOSS_HOME\bin\add-user.ps1 $argList1"
  Invoke-Expression "$JBOSS_HOME\bin\add-user.ps1 $argList2"
} catch {
	Write-Error "Error occurred during user account setup."
	exit
}

################################# Begin setup demo projects ##########################################

Write-Host "- Setting up demo projects...`n"
New-Item -ItemType directory -Path "$SERVER_BIN\.niogit\" | Out-Null
Copy-Item "$SUPPORT_DIR\brms-demo-niogit\*" "$SERVER_BIN\.niogit\" -force -recurse

If (! $o) {
  # Not in offline mode, so downloading the latest repo. We first download the repo in a temp dir and we only delete the old, cached repo, when the download is succesful.
  Write-Host "  - cloning the project's Git repo from: $PROJECT_GIT_REPO`n"
  If (Test-Path "$PROJECT_HOME\target\temp") {
	Remove-Item "$PROJECT_HOME\target\temp" -Force -Recurse
  }
  $argList = "clone --bare $PROJECT_GIT_REPO $PROJECT_HOME\target\temp\brms-coolstore-repo.git"
  $gitProcess = (Start-Process -FilePath "git" -ArgumentList $argList -Wait -PassThru -NoNewWindow)
  If ($gitProcess.ExitCode -ne 0) {
		Write-Host "Error cloning the project's Git repo. If there is no Internet connection available, please run this script in 'offline-mode' ('-o') to use a previously downloaded and cached version of the project's Git repo... Aborting"
		exit 1
  }
  Write-Host ""
  Write-Host "  - replacing cached project git repo: $PROJECT_GIT_DIR/brms-coolstore-repo.git`n"
  If (Test-Path "$PROJECT_GIT_DIR") {
	Remove-Item "$PROJECT_GIT_DIR" -Force -Recurse
  }
  New-Item -ItemType directory -Path "$PROJECT_GIT_DIR"
  Copy-Item "$PROJECT_HOME\target\temp\brms-coolstore-repo.git" "$PROJECT_GIT_DIR\brms-coolstore-repo.git" -Force -Recurse
  Remove-Item "$PROJECT_HOME\target\temp" -Force -Recurse
} else {
  Write-Host "  - running in offline-mode, using cached project's Git repo.`n"

  If (-Not (Test-Path "$PROJECT_GIT_DIR\brms-coolstore-repo.git")) {
    Write-Host "No project Git repo found. Please run the script without the 'offline' ('-o') option to automatically download the required Git repository!`n"
    exit 1
  }
}
# Copy the repo to the JBoss BPMSuite installation directory.
Remove-Item "$JBOSS_HOME\bin\.niogit\coolstore-demo.git" -Force -Recurse
Copy-Item "$PROJECT_GIT_DIR\brms-coolstore-repo.git" "$SERVER_BIN\.niogit\coolstore-demo.git" -force -recurse

################################# End setup demo projects ##########################################

Write-Host "- setting up standalone.xml configuration adjustments...`n"
Copy-Item "$SUPPORT_DIR\standalone.xml" "$SERVER_CONF" -force

Write-Host "- setup email task notification user...`n"
Copy-Item "$SUPPORT_DIR\userinfo.properties" "$SERVER_DIR\business-central.war\WEB-INF\classes\" -force

Write-Host "- setting up custom maven settings so KieScanner finds repo updates..."
Copy-Item "$SUPPORT_DIR\settings.xml" "$SERVER_BIN\.settings.xml"


If (!(Test-Path "$WEB_INF_LIB")) {
	Write-Host "- missing web inf lib directory in project being created..."
	New-Item -ItemType directory -Path "$WEB_INF_LIB" | Out-Null
}

Write-Host "Installing Coolstore JAR in Maven repository..."
$argList = "install:install-file -Dfile=$SUPPORT_LIBS\coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar"
$mvnFileProcess = (Start-Process -FilePath mvn -ArgumentList $argList -Wait -PassThru -NoNewWindow)
Write-Host "Process finished with return code: " $mvnFileProcess.ExitCode
Write-Host ""

Write-Host "Running 'mvn clean install' on project."
$argList = "clean install"
$mvnProcess = (Start-Process -FilePath mvn -WorkingDirector "$PRJ_DIR" -ArgumentList $argList -Wait -PassThru -NoNewWindow)
Write-Host "Process finished with return code: " $mvnProcess.ExitCode
Write-Host ""

Write-Host "Deploying the Cool Store web application."
Copy-Item "$PRJ_DIR\target\brms-coolstore-demo.war" "$SERVER_DIR"


Write-Host "============================================================================"
Write-Host "=                                                                          ="
Write-Host "=  You can now start the $PRODUCT with:                                 ="
Write-Host "=                                                                          ="
Write-Host "=   $SERVER_BIN\standalone.ps1     ="
Write-Host "=       or                                                                 ="
Write-Host "=   $SERVER_BIN\standalone.bat     ="
Write-Host "=                                                                          ="
Write-Host "=  Login into business central at:                                         ="
Write-Host "=                                                                          ="
Write-Host "=    http://localhost:8080/business-central  (u:brmsAdmin / p:jbossbrms1!) ="
Write-Host "=                                                                          ="
Write-Host "=  Login into the Coolstore application at:                                ="
Write-Host "=                                                                          ="
Write-Host "=    http://localhost:8080/brms-coolstore-demo                             ="
Write-Host "=                                                                          ="
Write-Host "=  See README.md for general details to run the various demo cases.        ="
Write-Host "=                                                                          ="
Write-Host "=  $PRODUCT $VERSION $DEMO Setup Complete.   ="
Write-Host "=                                                                          ="
Write-Host "============================================================================"
