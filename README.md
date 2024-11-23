# COMP2042 Coursework
This is a coursework for students of University of Nottingham Malaysia (2023-2026) batch for the module COMP2042 Developing Maintainable Software.

## Details
- Written by: Chea Yong Zen (20617094)
- GitHub Repository for COMP2042_Coursework: https://github.com/YzENT/COMP2042_CW2024

## Pre-requisites
- Java JDK (21 and above)
- JavaFX SDK (23 and above)
- Environmental Variables `JAVA_HOME` and `PATH_TO_FX`
- Machine equipped with Windows 10 and above

To verify whether the environmental variables above are set correctly, launch `cmd` and enter the following commands.

```
echo %JAVA_HOME%
```

```
echo %PATH_TO_FX%
```

If set up correctly, similar outputs should show:

````
> echo %JAVA_HOME%
C:\Program Files\Java\jdk-21

> echo %PATH_TO_FX%
C:\Program Files\Java\javafx-sdk-23.0.1\lib
````

## Compilation instructions
Clone the repository to local device
```
git clone https://github.com/YzENT/COMP2042_CW2024.git
```
### Automatic
An automated script `start.bat` can be found in the root directory. Launch the script and it will automatically install and compile everything. The application will be launched after everything has been completed.

### Manual
1. After cloning the repository to local device, launch `cmd` and navigate to root directory.
2. Enter the following command in `cmd`:
```
mvnw install -DskipTests
```
3. Navigate to the output folder:
```
cd target
```
4. Launch the .jar file created
```
"%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar CW2024-1.0-SNAPSHOT.jar
```

# Features