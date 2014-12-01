Running tests:

1. Utilize an already running JBoss+BRMS instance: 	mvn -Parq-jbossas-remote clean test
	Assumes:
		(a) running on localhost without port offset
		(b) Using the management user created during coolstore init script (erics/bpmsuite)
		
2. Point to a particular, local instance: mvn -Parq-jbossas-managed clean test
	Assumes:
		(a) Instance is not already running
		(b) Base directory is referred to in the JBOSS_HOME environment variable