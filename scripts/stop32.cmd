@ECHO OFF
REM Stopt all microservices.
wmic Path win32_process Where "CommandLine Like '%web-service-1.1-SNAPSHOT.jar%'" Call Terminate
TIMEOUT 5 > NUL
wmic Path win32_process Where "CommandLine Like '%rest-employees-service-1.0-SNAPSHOT.jar%'" Call Terminate
wmic Path win32_process Where "CommandLine Like '%rest-locations-service-1.0-SNAPSHOT.jar%'" Call Terminate
