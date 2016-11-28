
# wipe screen
Clear-Host

$PROJECT_HOME = $PSScriptRoot
$DEMO="JBoss BRMS Red Hat Cool Store Demo"
$AUTHORS1="Jason Milliron, Andrew Block,"
$AUTHORS2="AMahdy AbdElAziz, Eric D. Schabell"
$AUTHORS3="Duncan Doyle, Jaen Swart"
$PROJECT="git@github.com:jbossdemocentral/brms-coolstore-demo.git"
$PRODUCT="JBoss BRMS"
$DOCKERFILE="support\docker\Dockerfile"
$SRC_DIR="$PROJECT_HOME\installs"
$SUPPORT_DIR="$PROJECT_HOME\support"
$BRMS="jboss-brms-6.4.0.GA-deployable-eap7.x.zip"
$EAP="jboss-eap-7.0.0-installer.jar"
$VERSION="6.4"

set NOPAUSE=true

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


If (Test-Path "$SRC_DIR\$EAP") {
	Write-Host "Product sources are present...`n"
} Else {
	Write-Host "Need to download $EAP package from the Customer Support Portal"
	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
	exit
}

#If (Test-Path "$SRC_DIR\$EAP_PATCH") {
#	Write-Host "Product patches are present...`n"
#} Else {
#	Write-Host "Need to download $EAP_PATCH package from the Customer Support Portal"
#	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
#	exit
#}

If (Test-Path "$SRC_DIR\$BRMS") {
	Write-Host "Product sources are present...`n"
} Else {
	Write-Host "Need to download $BRMS package from the Customer Support Portal"
	Write-Host "and place it in the $SRC_DIR directory to proceed...`n"
	exit
}

Copy-Item "$SUPPORT_DIR\docker\Dockerfile" "$PROJECT_HOME" -force

Write-Host "Starting Docker build.`n"

$argList = "build -t jbossdemocentral/brms-coolstore-demo $PROJECT_HOME"
$process = (Start-Process -FilePath docker.exe -ArgumentList $argList -Wait -PassThru -NoNewWindow)
Write-Host "`n"

If ($process.ExitCode -ne 0) {
	Write-Error "Error occurred during Docker build!"
	exit
}

Write-Host "Docker build finished.`n"

Remove-Item "$PROJECT_HOME\Dockerfile" -Force

Write-Host "=================================================================================="
Write-Host "=                                                                                ="
Write-Host "=  You can now start the $PRODUCT in a Docker container with:              ="
Write-Host "=                                                                                ="
Write-Host "=  docker run -it -p 8080:8080 -p 9990:9990 jbossdemocentral/brms-coolstore-demo ="
Write-Host "=                                                                                ="
Write-Host "=  Login into business central at:                                               ="
Write-Host "=                                                                                ="
Write-Host "=    http://localhost:8080/business-central  (u:brmsAdmin / p:jbossbrms1!)       ="
Write-Host "=                                                                                ="
Write-Host "=  Login into the Coolstore application at:                                      ="
Write-Host "=                                                                                ="
Write-Host "=    http://localhost:8080/brms-coolstore-demo                                   ="
Write-Host "=                                                                                ="
Write-Host "=  See README.md for general details to run the various demo cases.              ="
Write-Host "=                                                                                ="
Write-Host "=  $PRODUCT $VERSION $DEMO Setup Complete.                        ="
Write-Host "=                                                                              ="
Write-Host "================================================================================"
