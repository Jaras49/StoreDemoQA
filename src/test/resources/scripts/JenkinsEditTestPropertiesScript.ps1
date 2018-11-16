[String]$BROWSER_PARAM = "driver"
[String]$SCREENSHOTS_PARAM = "dir.screenshots"

gc src\test\resources\test.properties | Foreach-Object { 
	$_ -replace "$BROWSER_PARAM.+", "$BROWSER_PARAM=$env:BROWSER" `
	-replace "$SCREENSHOTS_PARAM.+", "$SCREENSHOTS_PARAM=$env:SCREENSHOTS_DIR" ` 
	} | Set-Content src\test\resources\file1
	rm src\test\resources\test.properties
	rename-item src\test\resources\file1 test.properties