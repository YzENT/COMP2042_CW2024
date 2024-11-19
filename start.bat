@echo off
if defined JAVA_HOME (
	echo JAVA_HOME detected.
	) else (
    echo JAVA_HOME is not set.
	exit /b 1
)

if defined PATH_TO_FX (
		echo PATH_TO_FX detected.
	) else (
		echo PATH_TO_FX is not set.
		exit /b 1
	)

call .\mvnw.cmd clean install
if errorlevel 1 (
    echo Maven build failed.
    exit /b 1
)

if exist "target" (
    cd target
    "%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar CW2024-1.0-SNAPSHOT.jar
	cd ..
) else (
    echo Error: "target" folder not found. Maven build might have failed.
    exit /b 1
)
