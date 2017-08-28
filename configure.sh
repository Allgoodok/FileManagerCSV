PROJECT_BASE=$(pwd)
export CSV_REPO="${PROJECT_BASE}/csvfiles"
export CATALINA_BASE="${PROJECT_BASE}/apache-tomcat-7.0.81"
export CATALINA_HOME="${PROJECT_BASE}/apache-tomcat-7.0.81"
mvn clean tomcat7:run-war
