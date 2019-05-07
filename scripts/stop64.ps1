$procs = Get-CimInstance Win32_Process -Filter "name = 'java.exe'"

foreach ($proc in $procs)
{
    $javaPid = $proc.ProcessId
    $args = $proc.CommandLine

    if ($args -like "*web-service.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }

    if ($args -like "*rest-departments-service.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }

    if ($args -like "*rest-employees-service.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }

    if ($args -like "*rest-locations-service.jar*")
    {
        Write-Output $proc
        kill -Id $javaPid
    }
}
