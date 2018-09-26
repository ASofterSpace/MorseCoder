IF NOT EXIST ..\Toolbox-Java\ (
	echo "It looks like you did not yet get the Toolbox-Java project - please do so (and put it as a folder next to the CDM Script Editor folder.)"
	EXIT
)

cd app\src\main\java\com\asofterspace

rd /s /q toolbox

md toolbox
cd toolbox

md coders

cd ..\..\..\..\..\..\..

copy "..\Toolbox-Java\src\com\asofterspace\toolbox\coders\*.*" "app\src\main\java\com\asofterspace\toolbox\coders"

pause
