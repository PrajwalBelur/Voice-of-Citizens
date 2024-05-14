# Use a base image with Tomcat 
FROM tomcat:latest 

# Copy the WAR file into the webapps directory of Tomcat 
COPY CipherCloud.war /usr/local/tomcat/webapps/ 

# Expose port 8080 (default Tomcat port) 
EXPOSE 8080 

# Start Tomcat when the container starts 
CMD ["catalina.sh", "run"]
