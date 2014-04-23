#!/bin/sh
#
# ------------------------------------------------------------
# Company: Software- und Organisations-Service GmbH
# Author : Andreas Püschel <andreas.pueschel@sos-berlin.com>
# Author : Oliver Haufe <oliver.haufe@sos-berlin.com>
# Dated  : 2012-08-07
# Purpose: start FTP Processing
# ------------------------------------------------------------
#
CLASSPATH_BASE="%{INSTALL_PATH}"
CUR_DIR=`pwd`
if [ ! -d "$CLASSPATH_BASE" ]
then
  echo "Classpath directory \"$CLASSPATH_BASE\" does not exist."
  exit 1
fi
test -z "$JAVA_HOME" && JAVA_HOME="%{JAVA_HOME}"
JAVA_BIN="$JAVA_HOME/bin/java"

CP="commons-net-2.2.jar:log4j-1.2.16.jar:mail.jar:trilead-ssh2-build211.jar:xalan.jar"
SOS_JARS="connection configuration JobSchedulerLocalization JSHelper marshalling net settings textprocessor util VirtualFileSystem xml"

cd "$CLASSPATH_BASE"
# set_classpath
for SOS_JAR in $SOS_JARS
do
  SOS_JAR2=""
  for SOS_JAR1 in `ls com.sos.$SOS_JAR-*-*-*.jar 2>/dev/null`
  do
    SOS_JAR2=$SOS_JAR1
  done
  test -z "$SOS_JAR2" || CP="$CP:$SOS_JAR2"
done

"$JAVA_BIN" -classpath "$CP" sos.net.SOSFTPCommand "$@" -current_pid=$$ -ppid=$PPID
RC=$?

cd "$CUR_DIR"

exit $RC
