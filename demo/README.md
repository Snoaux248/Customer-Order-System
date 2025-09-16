# Java Application README

---
### Requirements

- **Java Development Kit (JDK) 24**
- All `.class` and `.txt` files should be placed in the same folder

### Running the terminal application

1. Open a terminal or command prompt.
2. Navigate to the folder containing the application files.

## How to Run the Program

### Prerequisites

- Ensure that **Java Development Kit (JDK) 24 or later** is installed.
  You can verify this by running the command:
- `java -version`
in your terminal
- To launch:
- run `cd /%path to project Folder%/demo/src/` in you terminal
- run `javac demo/*.java` in your terminal to compile the program
- run `java demo.Main` in your terminal to run the program


## Running the native mac or windows binary

### For mac
- Program Files are stored in `/Users/user/Library/MustafaCart`

### For windows 
- Program Files are stored in `C:\Program Files\MustafaCart`


## Manual compilation
  ### for manual native binary compilation on mac you will need 
  - homebrew
  - maven
  - java-jdk-24.x
  - javafx-sdk-24 (for mac)

  - then run these two commands when in the project directory:(Note same as windows different than terminal)
  - `mvn clean package`
  - ``` 
    jpackage \
      --type dmg \
      --name MustafaCart \
      --input "'path to package'/demo/target" \
      --main-jar demo-1.0-SNAPSHOT.jar \
      --main-class com.project.three.demo.HelloApplication \
      --java-options "--enable-native-access=ALL-UNNAMED" \
      --add-modules java.base,javafx.controls,javafx.fxml,javafx.graphics,java.logging,  \
      --module-path "$JAVA_HOME/jmods:'path to javafx-sdk'/javafx-sdk-mac-24.0.2/lib"  \
      --java-options "-Dprism.verbose=true -Djava.library.path='path to project'/demo/javafx-sdk-mac-24.0.2/lib" \
      --icon 'path to project'/demo/Mustafa.icns \
      --mac-package-identifier com.example.project3 \
      --mac-package-name MustafaCart \
      --verbose 
      ```

  ### for manual native binary compilation on windows you will need 
  - choco "for maven install"
  - Wix toolset
  - maven
  - java-jdk-24.x
  - javafx-sdk-24.x (for windows)
  - javafx-jmods-24.x (for windows)

  - then run these two commands when in the project directory:(Note same as mac diffrent than terminal)
  - `mvn clean package`
  - ``` 
    jpackage ^
      --type exe ^
      --name MustafaCart ^
      --input "'path to project'\demo\target" ^
      --main-jar demo-1.0-SNAPSHOT.jar ^
      --main-class com.project.three.demo.HelloApplication ^
      --java-options "--enable-native-access=ALL-UNNAMED -Dprism.verbose=true -Djava.library.path='path to javafx-sdk'\lib" ^
      --add-modules java.base,javafx.controls,javafx.fxml,javafx.graphics,java.logging ^
      --module-path "%JAVA_HOME%\jmods;C:'path to javfx-jmods'\javafx-jmods-24.0.2" ^
      --icon "'path to project'\Mustafa.ico" ^
      --win-menu ^
      --win-shortcut ^
      --verbose 
      ```
