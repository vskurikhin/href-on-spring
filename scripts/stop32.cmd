@ECHO OFF
REM Stopt all microservices.
wmic Path win32_process Where "CommandLine Like '%web-service.jar%'" Call Terminate
TIMEOUT 5 > NUL
wmic Path win32_process Where "CommandLine Like '%rest-departments-service.jar%'" Call Terminate
wmic Path win32_process Where "CommandLine Like '%rest-employees-service.jar%'" Call Terminate
wmic Path win32_process Where "CommandLine Like '%rest-locations-service.jar%'" Call Terminate
