FROM leonkuchinka/wildfly:latest

ADD ./target/iea.war /opt/jboss/wildfly/standalone/deployments/
COPY ./standalone.xml /opt/jboss/wildfly/standalone/configuration/
