<?php
$TEXT_WARTOSC =strval($_GET["text"]);
$R_WARTOSC =intval($_GET["R"]);
$G_WARTOSC =intval($_GET["G"]);
$B_WARTOSC =intval($_GET["B"]);
$command = escapeshellcmd("sudo python /var/www/html/Serwer/PythonScripts/diody.py $R_WARTOSC $G_WARTOSC $B_WARTOSC $TEXT_WARTOSC");
$tab = shell_exec($command);
echo $TEXT_WARTOSC
?>
