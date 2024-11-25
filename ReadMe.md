#<span style="color: #097969">Tavant-Playwright-BaseFramework</span>#

 🔗 _[Playwright-Cucumber-Java-Framework](https://github.com/VikramManikantaNadella/playwright-cucumber)_

- - - - 

##MAVEN COMMAND TO RUN TEST CASES

```
mvn clean test -DbrowserName=chrome -DappName=ProjectName -Dlanguage=English -DlogLevel=info -DthreadCount=2 -Dheadless=false -Denv=QA -Dcucumber.filter.tags=@Test
```

##MAVEN COMMAND PARAMETERS
- browserName

```
Browser Name to be Provided where Execution needs to be Performed. 
Eg: chrome, msedge, firefox, webkit, chromium
```
- appName

```
Application Name or Project Name needs to be provided.
Eg: TWOD
```
- language

```
Language needs to be provided.
Eg: English
```
- logLevel

```
Log Level needs to be provided.
Eg: info, debug
```
- threadCount

```
Thread Count needs to be provided.
Eg: 3
```
- headless

```
HeadLess Flag needs to be provided.
Eg: false
```
- env

```
Environment or Org need to be proviede.
Eg: QA
```
- cucumber.filter.tags

```
Tags need to be provided.
Eg: @Test
```

##CONFIG PROPERTIES
- config.properties

```
config.properties file should be created under ../src/test/resources/
```
- Required Parameters in config.properties

```
useTestDataJsonFile : Flag that tells to Use TestData.json File.
Eg: true or false
```
```
usefieldLabels_LanguageFile : Flag that tells to Use FieldLabels_Language JSON or Excel File.
Eg: true or false
```
```
useLocatorsJsonFile : Flag that tells to Use use Locators.json File.
Eg: true or false
```
```
testData_Path : TestData.json File Path should be Provided.
Eg: src\\test\\resources\\QA\\TestData.json
```
```
editableFields_Path : EditableFields.json File Path should be Provided.
Eg: src\\test\\resources\\EditableFieldsCheck.json
```
```
detailPageFields_Path : DetailPageFields.json File Path should be Provided.
Eg: src\\test\\resources\\DetailPageFields.json
```
```
locators_Path : Locators.json File Path should be Provided.
Eg: src\\test\\resources\\Locators.json
```
```
fieldLabels_LanguagePath : FieldLabels_Language.json or FieldLabels_Language.xlsx File Path should be Provided.
Eg: src\\test\\resources\\FieldLabels_Language.json
```
```
filesThreshold_Reports : Reports Files threshold count should be Provided.
Eg: 5
```
```
defaultTimeOut : Default TimeOut for element visibility should be Provided in Milliseconds.
Eg: 60000
```
```
pageLoadTimeout : Page Load TimeOut should be Provided in Milliseconds.
Eg: 120000
```
```
sloMoTime : Slow Motion Time should be Provided in Milliseconds.
Eg: 200
```
```
screenRecord_Tracing : If Screen Recording and Tracings to be Saved.
Eg: true, false
```
- Project Specific or Environment Specific config.properties

```
config.properties should be created in Environment named folder under ../src/test/resources/
Eg: src\\test\\resources\\QA\\config.properties
```
##REPORTS

```
Reports will be Generated under Reports Folder in Project
```
##VERSION

```
Updated to v1.0
```

