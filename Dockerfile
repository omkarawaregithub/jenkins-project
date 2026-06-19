# Use the official Tomcat image as a base
FROM tomcat:9.0-jdk11-openjdk

# Remove the default Tomcat webapps to keep it clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your war file into the Tomcat webapps directory
# Replace 'your-app-name.war' with your actual filename
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]