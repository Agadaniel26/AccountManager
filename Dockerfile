FROM tomcat:8.5.40
COPY target/accountmanager.war /usr/local/tomcat/webapps/
