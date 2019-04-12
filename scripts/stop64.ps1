$procs = Get-CimInstance Win32_Process -Filter "name = 'java.exe'"

foreach ($proc in $procs)
{
    $javaPid = $proc.ProcessId
    $args = $proc.CommandLine

    if ($args -like "*web-service-1.1-SNAPSHOT.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }
    if ($args -like "*rest-employees-service-1.0-SNAPSHOT.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }

    if ($args -like "*rest-locations-service-1.0-SNAPSHOT.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }
}
