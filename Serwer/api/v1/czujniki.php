<?php

$command = escapeshellcmd('sudo python /var/www/html/Serwer/PythonScripts/testScript.py');
$tab = shell_exec($command);
print $tab
?>
