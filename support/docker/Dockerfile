# Use jbossdemocentral/developer as the base
FROM jbossdemocentral/developer

# Maintainer details
MAINTAINER Andrew Block <andy.block@gmail.com>

# Environment Variables 
ENV BRMS_HOME /opt/jboss/brms
ENV BRMS_VERSION_MAJOR 6
ENV BRMS_VERSION_MINOR 0
ENV BRMS_VERSION_MICRO 3

# ADD Installation Files
COPY support/installation-brms support/installation-brms.variables installs/jboss-brms-installer-$BRMS_VERSION_MAJOR.$BRMS_VERSION_MINOR.$BRMS_VERSION_MICRO.GA-redhat-1.jar  /opt/jboss/

# Configure project prerequisites and run installer and cleanup installation components
RUN mkdir -p /opt/jboss/brms-projects \
  && mkdir -p /opt/jboss/brms-projects/libs/ \
  && sed -i "s:<installpath>.*</installpath>:<installpath>$BRMS_HOME</installpath>:" /opt/jboss/installation-brms \
  && java -jar /opt/jboss/jboss-brms-installer-$BRMS_VERSION_MAJOR.$BRMS_VERSION_MINOR.$BRMS_VERSION_MICRO.GA-redhat-1.jar  /opt/jboss/installation-brms -variablefile /opt/jboss/installation-brms.variables \
  && rm -rf /opt/jboss/jboss-brms-installer-$BRMS_VERSION_MAJOR.$BRMS_VERSION_MINOR.$BRMS_VERSION_MICRO.GA-redhat-1.jar /opt/jboss/installation-brms /opt/jboss/installation-brms.variables $BRMS_HOME/jboss-eap-6.1/standalone/configuration/standalone_xml_history/

# Copy demo and support files
COPY support/brms-demo-niogit $BRMS_HOME/jboss-eap-6.1/bin/.niogit
COPY projects /opt/jboss/brms-projects
COPY support/libs /opt/jboss/brms-projects/libs
COPY support/application-roles.properties support/standalone.xml $BRMS_HOME/jboss-eap-6.1/standalone/configuration/

# Swtich back to root user to perform build and cleanup
USER root

# Run Demo Maven build and cleanup
RUN cp /opt/jboss/brms-projects/libs/cdiutils-1.0.0.jar /opt/jboss/brms-projects/brms-coolstore-demo/src/main/webapp/WEB-INF/lib/ \
  && mvn install:install-file -Dfile=/opt/jboss/brms-projects/libs/cdiutils-1.0.0.jar -DgroupId=org.vaadin.virkki -DartifactId=cdiutils -Dversion=1.0.0 -Dpackaging=jar \
  && mvn install:install-file -Dfile=/opt/jboss/brms-projects/libs/coolstore-2.0.0.jar -DgroupId=com.redhat -DartifactId=coolstore -Dversion=2.0.0 -Dpackaging=jar \
  && mvn clean install -f /opt/jboss/brms-projects/brms-coolstore-demo/pom.xml \
  && cp /opt/jboss/brms-projects/brms-coolstore-demo/target/brms-coolstore-demo.war $BRMS_HOME/jboss-eap-6.1/standalone/deployments/ \
  && chown -R jboss:jboss $BRMS_HOME/jboss-eap-6.1/bin/.niogit $BRMS_HOME/jboss-eap-6.1/standalone/configuration/application-roles.properties $BRMS_HOME/jboss-eap-6.1/standalone/configuration/standalone.xml $BRMS_HOME/jboss-eap-6.1/standalone/deployments/brms-coolstore-demo.war \
  && rm -rf ~/.m2/repository /opt/jboss/brms-projects  

# Run as JBoss 
USER jboss

# Expose Ports
EXPOSE 9990 9999 8080

# Run BRMS
CMD ["/opt/jboss/brms/jboss-eap-6.1/bin/standalone.sh","-c","standalone.xml","-b", "0.0.0.0","-bmanagement","0.0.0.0"]
