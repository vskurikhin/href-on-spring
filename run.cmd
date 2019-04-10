@ECHO OFF
REM Start all microservices.
SET CS="encoding=UTF-8"
SET CP=-cp "target\classes;target\dependency\*;target\*"
SET JAVA_OPTS=%CP% -Dfile.%CS% -Dsun.stdout.%CS% -Dsun.err.%CS%
CHCP 65001
START /B java %JAVA_OPTS% -jar .\rest-employees-service\target\rest-employees-service-1.0-SNAPSHOT.jar
START /B java %JAVA_OPTS% -jar .\rest-locations-service\target\rest-locations-service-1.0-SNAPSHOT.jar
TIMEOUT 5 > NUL
java %JAVA_OPTS% -jar .\web-service\target\web-service-1.0-SNAPSHOT.jar
