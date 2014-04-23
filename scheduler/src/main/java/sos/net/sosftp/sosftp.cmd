@echo off
@rem 
@rem ------------------------------------------------------------
@rem Company: Software- und Organisations-Service GmbH
@rem Author : Andreas Püschel <andreas.pueschel@sos-berlin.com>
@rem Author : Oliver Haufe <oliver.haufe@sos-berlin.com>
@rem Dated  : 2012-06-12
@rem Purpose: start FTP Processing
@rem ------------------------------------------------------------
SETLOCAL

set CLASSPATH_BASE=${INSTALL_PATH}
set CUR_DIR=%CD%
if not exist "%CLASSPATH_BASE%" (
  echo Classpath directory "%CLASSPATH_BASE%" does not exist.
  exit /B 1
)
if not defined JAVA_HOME set JAVA_HOME=${JAVA_HOME}
set JAVA_BIN=%JAVA_HOME%\bin\java.exe
if not exist "%JAVA_BIN%" set JAVA_BIN=java.exe

set CP=commons-net-2.2.jar;log4j-1.2.16.jar;mail.jar;trilead-ssh2-build211.jar;xalan.jar
set SOS_JARS=connection configuration JobSchedulerLocalization JSHelper marshalling net settings textprocessor util VirtualFileSystem xml

cd /D "%CLASSPATH_BASE%"

@rem classpath extends with versioned sos jars
for /D %%a in (%SOS_JARS%) do call :set_classpath %%a
goto start

:set_classpath
set SOS_JAR=
for /F "usebackq" %%b in (`dir /B "com.sos.%1-*-*-*.jar" 2^>nul`) do set SOS_JAR=;%%b
set CP=%CP%%SOS_JAR%
goto final

:start
"%JAVA_BIN%" -classpath "%CP:\=/%" sos.net.SOSFTPCommand %* 
set /a RC=%ERRORLEVEL%

cd /D "%CUR_DIR%"

exit /B %RC%

ENDLOCAL
:final