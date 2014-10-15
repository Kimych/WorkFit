set JAVA_HOME="c:\Program Files\Java\jre1.8.0_20"
set JAVAEXE=%JAVA_HOME%\bin\java
%JAVAEXE% -Xmx512m -Xms128m %DLL% -cp ".;lib/*" by.uniterra.system.main.WorkFitFrame
pause