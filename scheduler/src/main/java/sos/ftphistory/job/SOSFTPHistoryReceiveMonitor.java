/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package sos.ftphistory.job;

import sos.connection.SOSConnection;
import sos.spooler.Monitor_impl;
import sos.spooler.Variable_set;
import sos.util.SOSClassUtil;
import sos.util.SOSSchedulerLogger;

public class SOSFTPHistoryReceiveMonitor extends Monitor_impl {

	@SuppressWarnings("unused")
	private final String		conClassName					= "SOSFTPHistoryReceiveMonitor";
	public final String			conSVNVersion					= "$Id: SOSDataExchangeEngine.java 19091 2013-02-08 12:49:32Z kb $";

    /**
    * Parameter replacing und replacement am Auftrag setzen. Dadurch wird die original Datei
    * beim Transfer nach einem bestimmten Muster umbenannt.
    *
    * Initialisierung vor Verarbeitung eines Auftrags
    * @see sos.spooler.Monitor_impl#spooler_process_before()
    */
    @Override
	public boolean spooler_process_before() {

        try {
          /*
          if(spooler_task.order().params().value("replacing") == null || spooler_task.order().params().value("replacing").length() == 0){
            spooler_task.order().params().set_var("replacing","[.]csv");
          }*/

          spooler_task.order().params().set_var("replacing","[.]csv");
          // siehe SOSFTPHistoryReceiveMonitor.fillPosition#remoteFilename
          spooler_task.order().params().set_var("replacement","{sos[date:yyyyMMddHHmmssSSS]sos}.csv");

        return true;
        }
        catch (Exception e) {
          spooler_log.warn("error occurred in spooler_process_before(222): " + e.getMessage());
          return false;
        }
    }

  /* (non-Javadoc)
   * @see sos.spooler.Monitor_impl#spooler_process_after(boolean)
   * @Override
   */
  @Override
public boolean spooler_process_after(final boolean arg0) throws Exception {

    if (!arg0) return arg0;

    SOSConnection       conn        = null;
    SOSSchedulerLogger  log         = null;
    Variable_set        parameters  = null;
    String              host        = null;
    String              remoteDir   = null;

    try{
      parameters = spooler.create_variable_set();
      if(spooler_task.params() != null) parameters.merge(spooler_task.params());
      if (spooler_job.order_queue() != null)parameters.merge(spooler_task.order().params());

      SOSFTPHistory.debugParams(parameters, spooler_log);

      if(parameters != null && parameters.count() > 0){
        if (parameters.value("ftp_result_files") == "0"){
          spooler_log.debug9("no files were received");
        }
        else{
          host      = parameters.value("ftp_host");
          remoteDir = parameters.value("ftp_remote_dir");

          if(host != null && host.length() > 0 && remoteDir != null && remoteDir.length() > 0){
            try{
              String[] files  = parameters.value("ftp_result_filepaths").split( ";" );

              log             = new SOSSchedulerLogger(spooler_log);
              conn            = SOSFTPHistory.getConnection(spooler, conn, parameters, log);

              for (String file : files) {
                fillPosition(conn,host,remoteDir,file);
              }
            }
            catch(Exception e){
              spooler_log.info("ERROR : cannot found position : "+e.getMessage());
            }
            finally{
              try{
                if(conn != null){
                  conn.disconnect();
                }
              }
              catch(Exception e){}
            }
          }
          else{
            spooler_log.debug3("missing host or ftp_remote_dir for file positions : host = "+host+" ftp_remote_dir = "+remoteDir );
          }
        }
      }
    }
    catch(Exception e){

    }
    return super.spooler_process_after(arg0);
  }


  /**
   *
   * @param conn
   * @param host
   * @param remoteDir
   * @param file
   * @throws Exception
   */
  private void fillPosition(final SOSConnection conn,String host,String remoteDir,String file) throws Exception{

    StringBuffer sql            = new StringBuffer();
    String      remoteFilename  = "";
    String      localFilename   = "";

    try{
      host          = SOSFTPHistory.getNormalizedValue(host);
      remoteDir     = SOSFTPHistory.getNormalizedValue(remoteDir);
      file          = SOSFTPHistory.getNormalizedValue(file);

      String[] fp   = file.split("/");
      localFilename = fp[fp.length-1];

      //bei FTP �bertragung original Datei wird umbenannt
        //aus "1.csv" wird 1{sos20081218132248123sos}.csv
        //{sos + timestamp + sos}
      //siehe spooler_process_before#
      remoteFilename = localFilename.toLowerCase().replaceAll("(\\{sos)(.)*(sos\\})(\\.csv)$","\\.csv");

      sql = new StringBuffer("select \"LOCAL_FILENAME\" from "+SOSFTPHistory.TABLE_FILES_POSITIONS+ " ")
          .append("where \"HOST\" = '"+SOSFTPHistory.getNormalizedField(conn,host,128)+"' and ")
          .append("   \"REMOTE_DIR\" = '"+SOSFTPHistory.getNormalizedField(conn,remoteDir,255)+"' and ")
          .append("   \"REMOTE_FILENAME\" = '"+SOSFTPHistory.getNormalizedField(conn,remoteFilename,255)+"'");


      String lastLocalFileName = conn.getSingleValue(sql.toString());
      //FileSize wird in SOSFTPHistoryJob.importFile() gesetzt
      if(lastLocalFileName == null || lastLocalFileName.length() == 0){
        sql = new StringBuffer("insert into "+SOSFTPHistory.TABLE_FILES_POSITIONS+"(\"HOST\",\"REMOTE_DIR\",\"REMOTE_FILENAME\",\"LOCAL_FILENAME\",\"FILE_SIZE\",\"POSITION\") ")
            .append("values('"+SOSFTPHistory.getNormalizedField(conn,host,128)+"','"+SOSFTPHistory.getNormalizedField(conn,remoteDir,255)+"','"+SOSFTPHistory.getNormalizedField(conn,remoteFilename,255)+"','"+SOSFTPHistory.getNormalizedField(conn,localFilename,255)+"',0,0)");
      }
      else{
        //wir ersetzen alte gefundene local_filename durch die neue (mit dem aktuellen timestamp)
        sql = new StringBuffer("update "+SOSFTPHistory.TABLE_FILES_POSITIONS+ " ")
            .append("set \"LOCAL_FILENAME\" = '"+SOSFTPHistory.getNormalizedField(conn,localFilename,255)+"' ")
        .append("where \"LOCAL_FILENAME\" = '"+SOSFTPHistory.getNormalizedField(conn,lastLocalFileName,255)+"'");

      }

      conn.execute(sql.toString());
      conn.commit();
    }
    catch(Exception e){
      try{conn.rollback();}catch(Exception ee){}
      throw new Exception(SOSClassUtil.getMethodName()+" : "+e.getMessage());
    }
  }

}
