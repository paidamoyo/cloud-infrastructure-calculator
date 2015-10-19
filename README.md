cloud-infrastructure-statistics
===============================

## Tools setup (command instructions are for OSX)

1. Install Gradle (http://www.gradle.org/installation)

2. Install Java 1.8

## Build and run tests

```
./gradlew
```
## Run application

```
./gradlew run '-PfilePaths=InstanceStateTxtFilePath,HostStateTxtFilePath'
```
## Development Setup with IntelliJ

```
$ ./gradlew idea
$ open *.ipr  #on OSX
```
*
Anytime you adjust the gradle build files, you have to rerun `./gradlew idea` and
let the IDE reload the project.

* Install "lombok" plugin & Enable: Settings -> Compiler -> Annotation Processor -> Enable annotation processing
* Enable: Settings -> Editor -> Appearance -> Show line numbers
* Disable: Settings -> Editor -> Allow placement of caret after end of line
