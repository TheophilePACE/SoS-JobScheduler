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
package sos.scheduler.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import sos.spooler.Job_impl;
import sos.util.SOSDate;

import com.sos.JSHelper.Basics.JSVersionInfo;

/**
 * @author andreas.pueschel@sos-berlin.com
 *
 * cleanup history log entries: reduce log files to a given log level
 */
public class JobSchedulerCleanupHistory extends Job_impl {

	/** Attribute: cleanup history for the given number of days */
//	public int			cleanupHistoryInterval			= 7;
	public int			cleanupHistoryInterval			= 0;  // kb 2011-11-22

	/** Attribute: delete history for the given number of days */
	public int			deleteHistoryInterval			= 0;

	/** Attribute: delete FTPhistory for the given number of days */
	public int			deleteFTPHistoryInterval			= 0;
	
	/** Attribute: delete FTPhistory for the given number of days */
	public int			deleteDailyPlanInterval			    = 0;

	/** Attribute: reduce history entries starting with the given date */
	public Date			cleanupHistoryDateFrom			= null;

	/** Attribute: reduce history entries until the given date */
	public Date			cleanupHistoryDateTo			= null;

	/** Attribute: cleanup history for the given task id */
	public int			cleanupHistoryTaskId			= 0;

	/** Attribute: cleanup history for the given order id */
	public String		cleanupHistoryOrderId			= new String("");

	/** Attribute: reduce history entries to the given log level */
	public int			cleanupHistoryLogLevel			= 0;

	/** Attribute: process gzip compressed history entries */
	public boolean		cleanupHistoryLogCompressed		= true;

	/** Attribute: array of history items to cleanup */
	private ArrayList	cleanupHistoryItems				= new ArrayList();

	/** Attribute: array of order history items to cleanup */
	private ArrayList	cleanupHistoryOrderItems		= new ArrayList();

	/** Attribute: current index to array of items to cleanup */
	private int			cleanupHistoryItemIndex			= 0;

	/** Attribute: max. index to array of items to cleanup */
	private int			cleanupHistoryItemMax			= 0;

	/** Attribute: max. number of history entries to process */
	private int			cleanupHistoryItemMaxHistory	= 0;

	/** Attribute: max. number of order history entries to process */
	private int			cleanupHistoryItemMaxOrders		= 0;

	/** Attribut: number of items processed */
	private int			cleanupHistoryItemsProcessed	= 0;

	/** Attribut: name of the history table (for Uwe's old Solaris Scheduler) */
	private String		cleanupHistoryTable				= "";

	/** Attribut: name of the order history table (for Uwe's old Solaris Scheduler) */
	private String		cleanupOrderHistoryTable		= "";

	/** Attribut: name of the order history table */
	private String		cleanupOrderStepHistoryTable	= "";

	private final String		conSVNVersion	= "$Id: JobSchedulerCleanupHistory.java 17236 2012-05-25 08:49:59Z ur $";
	private final static String	conClassName	= "JobSchedulerCleanupHistory";

	public boolean spooler_init() {

		final String conMethodName = conClassName + "::spooler_init";
		
		spooler_log.info(JSVersionInfo.getVersionString());
		spooler_log.info(conSVNVersion);
		spooler_log.debug(String.format("Enter procedure %1$s ", conMethodName));

		try {
			if (spooler_task.params().var("cleanup_history_interval") != null && spooler_task.params().var("cleanup_history_interval").length() > 0) {
				cleanupHistoryInterval = Integer.parseInt(spooler_task.params().var("cleanup_history_interval"));
				spooler_log.info(".. job parameter [cleanup_history_interval]: " + cleanupHistoryInterval);
			}

			if (spooler_task.params().var("delete_history_interval") != null && spooler_task.params().var("delete_history_interval").length() > 0) {
				deleteHistoryInterval = Integer.parseInt(spooler_task.params().var("delete_history_interval"));
				spooler_log.info(".. job parameter [delete_history_interval]: " + deleteHistoryInterval);
			}

			if (spooler_task.params().var("delete_ftp_history_interval") != null && spooler_task.params().var("delete_ftp_history_interval").length() > 0) {
				deleteFTPHistoryInterval = Integer.parseInt(spooler_task.params().var("delete_ftp_history_interval"));
				spooler_log.info(".. job parameter [delete_ftp_history_interval]: " + deleteFTPHistoryInterval);
			}

			if (spooler_task.params().var("delete_daily_plan_interval") != null && spooler_task.params().var("delete_daily_plan_interval").length() > 0) {
				deleteDailyPlanInterval = Integer.parseInt(spooler_task.params().var("delete_daily_plan_interval"));
				spooler_log.info(".. job parameter [delete_daily_plan_interval]: " + deleteDailyPlanInterval);
			}

			if (spooler_task.params().var("cleanup_history_table") != null && spooler_task.params().var("cleanup_history_table").length() > 0) {
				cleanupHistoryTable = spooler_task.params().var("cleanup_history_table");
				spooler_log.info(".. job parameter [cleanup_history_table]: " + cleanupHistoryTable);
			}
			else
				cleanupHistoryTable = spooler.db_history_table_name();

			if (spooler_task.params().var("cleanup_order_history_table") != null && spooler_task.params().var("cleanup_order_history_table").length() > 0) {
				cleanupOrderHistoryTable = spooler_task.params().var("cleanup_order_history_table");
				spooler_log.info(".. job parameter [cleanup_order_history_table]: " + cleanupOrderHistoryTable);
			}
			else
				cleanupOrderHistoryTable = spooler.db_order_history_table_name();

			if (spooler_task.params().var("cleanup_order_step_history_table") != null
					&& spooler_task.params().var("cleanup_order_step_history_table").length() > 0) {
				cleanupOrderStepHistoryTable = spooler_task.params().var("cleanup_order_step_history_table");
				spooler_log.info(".. job parameter [cleanup_order_step_history_table]: " + cleanupOrderStepHistoryTable);
			}
			else
				cleanupOrderStepHistoryTable = "SCHEDULER_ORDER_STEP_HISTORY"; // wird leider nicht unterst�tztz:
																				// spooler.db_order_history_step_table_name();

			if (spooler_task.params().var("cleanup_history_date_from") != null && spooler_task.params().var("cleanup_history_date_from").length() > 0) {
				cleanupHistoryDateFrom = SOSDate.getDate(spooler_task.params().var("cleanup_history_date_from"));
				spooler_log.info(".. job parameter [cleanup_history_date_from]: " + cleanupHistoryDateFrom);
			}

			if (spooler_task.params().var("cleanup_history_date_to") != null && spooler_task.params().var("cleanup_history_date_to").length() > 0) {
				cleanupHistoryDateTo = SOSDate.getDate(spooler_task.params().var("cleanup_history_date_to"));
				spooler_log.info(".. job parameter [cleanup_history_date_to]: " + cleanupHistoryDateTo);
			}

			if (spooler_task.params().var("cleanup_history_task_id") != null && spooler_task.params().var("cleanup_history_task_id").length() > 0) {
				cleanupHistoryTaskId = Integer.parseInt(spooler_task.params().var("cleanup_history_task_id"));
				spooler_log.info(".. job parameter [cleanup_history_task_id]: " + cleanupHistoryTaskId);
			}

			if (spooler_task.params().var("cleanup_history_order_id") != null && spooler_task.params().var("cleanup_history_order_id").length() > 0) {
				cleanupHistoryOrderId = spooler_task.params().var("cleanup_history_order_id");
				spooler_log.info(".. job parameter [cleanup_history_order_id]: " + cleanupHistoryOrderId);
			}

			if (spooler_task.params().var("cleanup_history_log_level") != null && spooler_task.params().var("cleanup_history_log_level").length() > 0) {
				cleanupHistoryLogLevel = Integer.parseInt(spooler_task.params().var("cleanup_history_log_level"));
				spooler_log.info(".. job parameter [cleanup_history_log_level]: " + cleanupHistoryLogLevel);
			}

			if (spooler_task.params().var("cleanup_history_log_compressed") != null && spooler_task.params().var("cleanup_history_log_compressed").length() > 0) {
				cleanupHistoryLogCompressed = Boolean.valueOf(spooler_task.params().var("cleanup_history_log_compressed")).booleanValue();
				spooler_log.info(".. job parameter [cleanup_history_log_compressed]: " + cleanupHistoryLogCompressed);
			}

		}
		catch (Exception e) {
			spooler_log.error("error occurred in parameter processing: " + e);
		}

		return true;
	}

	public boolean spooler_open() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_open";
		
		spooler_log.debug(String.format("Enter procedure %1$s ", conMethodName));

		
		String historyQuery = new String("");
		String historyDeleteQuery = new String("");
		String orderHistoryDeleteQuery = new String("");
		String orderStepHistoryDeleteQuery = new String("");
		String orderHistoryQuery = new String("");
		String sosFtpFilesDeleteQuery = new String("");
		String sosDailyPlanDeleteQuery = new String("");
		String sosFtpFilesHistoyDeleteQuery = new String("");
		String sqlCondition = new String("");
		String sqlDeleteCondition = new String("");
		String sqlAnd = new String(" WHERE ");
		String sqlAndSpoolerId = new String("");

		sos.hostware.File db = null;
		sos.hostware.Record record = null;

		try {
			if (spooler.db_name() == null || spooler.db_name().length() == 0)
				return false;

			if (cleanupHistoryDateFrom != null || cleanupHistoryDateTo != null) {
				if (cleanupHistoryDateFrom != null) {
					sqlCondition += sqlAnd + "\"START_TIME\" >= %timestamp('" + SOSDate.getDateAsString(cleanupHistoryDateFrom) + "')";
					sqlAnd = " AND ";
				}
				if (cleanupHistoryDateTo != null) {
					sqlCondition += sqlAnd + "\"START_TIME\" <= %timestamp('" + SOSDate.getDateAsString(cleanupHistoryDateTo) + "')";
					sqlAnd = " AND ";
				}
			}
			else
				if (cleanupHistoryDateTo != null) {
					sqlCondition += sqlAnd + "\"START_TIME\" <= %timestamp('" + SOSDate.getDateAsString(cleanupHistoryDateTo) + "')";
					sqlAnd = " AND ";
				}
				else
					if (cleanupHistoryInterval > 0) {
						GregorianCalendar now = new GregorianCalendar();
						now.add(GregorianCalendar.DAY_OF_YEAR, -cleanupHistoryInterval);
						sqlCondition += sqlAnd + "\"START_TIME\" >= %timestamp('" + SOSDate.getDateAsString(now.getTime()) + "')";
						sqlAnd = " AND ";
					}
					else
						if (cleanupHistoryInterval == 0) {
							sqlCondition += sqlAnd + "1=0";
							sqlAnd = " AND ";
						}

			if (cleanupHistoryTaskId > 0) {
				historyQuery = " SELECT \"ID\" FROM " + cleanupHistoryTable + " WHERE \"ID\"=" + cleanupHistoryTaskId + " ORDER BY \"ID\" ASC";
			}
			else {
				historyQuery = " SELECT \"ID\" FROM " + cleanupHistoryTable + " " + sqlCondition + sqlAndSpoolerId + " ORDER BY \"ID\" ASC";
			}

			if (deleteHistoryInterval > 0) {
				GregorianCalendar now = new GregorianCalendar();
				now.add(GregorianCalendar.DAY_OF_YEAR, -deleteHistoryInterval);
				sqlDeleteCondition = " WHERE \"START_TIME\" <= %timestamp('" + SOSDate.getDateAsString(now.getTime()) + "')";

				historyDeleteQuery = " DELETE FROM " + cleanupHistoryTable + " " + sqlDeleteCondition + sqlAndSpoolerId;
				spooler_log.debug1("processing history delete query: " + historyDeleteQuery);
				db = new sos.hostware.File();
				db.open("-in -out " + spooler.db_name());

				db.put_line(historyDeleteQuery);
				db.put_line("commit");

				orderHistoryDeleteQuery = " DELETE FROM " + cleanupOrderHistoryTable + " " + sqlDeleteCondition + sqlAndSpoolerId;
				spooler_log.debug1("processing order history delete query: " + orderHistoryDeleteQuery);
				db.put_line(orderHistoryDeleteQuery);
				db.put_line("commit");

				// STEP_HISTORY kennt die SPOOLER_ID nicht.
				orderStepHistoryDeleteQuery = " DELETE FROM " + cleanupOrderStepHistoryTable + " " + sqlDeleteCondition;
				spooler_log.debug1("processing order history delete query: " + orderStepHistoryDeleteQuery);
				db.put_line(orderStepHistoryDeleteQuery);
				db.put_line("commit");

 
 				
				db.close();
				db.destruct();
			}
			
			if (deleteFTPHistoryInterval > 0) {
				
				db = new sos.hostware.File();
				db.open("-in -out " + spooler.db_name());
				
				GregorianCalendar now = new GregorianCalendar();
				now.add(GregorianCalendar.DAY_OF_YEAR, -deleteFTPHistoryInterval);
				sqlDeleteCondition = " WHERE \"CREATED\" <= %timestamp('" + SOSDate.getDateAsString(now.getTime()) + "')";
 
				sosFtpFilesDeleteQuery = " DELETE FROM SOSFTP_FILES " + sqlDeleteCondition;
				spooler_log.debug1("processing ftp files  delete query: " + sosFtpFilesDeleteQuery);
				db.put_line(sosFtpFilesDeleteQuery);
				db.put_line("commit");

				sosFtpFilesHistoyDeleteQuery = " DELETE FROM SOSFTP_FILES_HISTORY " + sqlDeleteCondition;;
				spooler_log.debug1("processing ftp files history  delete query: " + sosFtpFilesHistoyDeleteQuery);
				db.put_line(sosFtpFilesHistoyDeleteQuery);
				db.put_line("commit");
 				
				db.close();
				db.destruct();
			}

          if (deleteDailyPlanInterval > 0) {
				
				db = new sos.hostware.File();
				db.open("-in -out " + spooler.db_name());
				
				GregorianCalendar now = new GregorianCalendar();
				now.add(GregorianCalendar.DAY_OF_YEAR, -deleteDailyPlanInterval);
				sqlDeleteCondition = " WHERE \"CREATED\" <= %timestamp('" + SOSDate.getDateAsString(now.getTime()) + "')";
 
				sosDailyPlanDeleteQuery = " DELETE FROM DAYS_SCHEDULE " + sqlDeleteCondition;
				spooler_log.debug1("processing daily plan delete query: " + sosDailyPlanDeleteQuery);
				db.put_line(sosDailyPlanDeleteQuery);
				db.put_line("commit");
 
				db.close();
				db.destruct();
			}

			spooler_log.debug1("processing history query: " + historyQuery);
			db = new sos.hostware.File();
			db.open("-in " + spooler.db_name() + historyQuery);
			while (!db.eof()) {
				record = db.get();
				cleanupHistoryItems.add(cleanupHistoryItemMaxHistory++, record.string(0));
			}
			db.close();
			db.destruct();
			if (record != null)
				record.destruct();

			if (cleanupHistoryOrderId != null && cleanupHistoryOrderId.length() > 0) {
				// orderHistoryQuery = " SELECT \"HISTORY_ID\" AS \"ID\" FROM " + cleanupOrderHistoryTable + " WHERE \"ORDER_ID\"='" +
				// cleanupHistoryOrderId + "' ORDER BY \"ID\" ASC";
				orderHistoryQuery = " SELECT \"HISTORY_ID\" AS \"ID\" FROM " + cleanupOrderHistoryTable + " WHERE \"ORDER_ID\"='" + cleanupHistoryOrderId
						+ "' ORDER BY 1 ASC";
			}
			else {
				// orderHistoryQuery = " SELECT \"HISTORY_ID\" AS \"ID\" FROM " + cleanupOrderHistoryTable + " " + sqlCondition +
				// " ORDER BY \"ID\" ASC";
				orderHistoryQuery = " SELECT \"HISTORY_ID\" AS \"ID\" FROM " + cleanupOrderHistoryTable + " " + sqlCondition + " ORDER BY 1 ASC";
			}

			spooler_log.debug1("processing order history query: " + orderHistoryQuery);
			db = new sos.hostware.File();
			db.open("-in " + spooler.db_name() + orderHistoryQuery);

			while (!db.eof()) {
				record = db.get();
				cleanupHistoryOrderItems.add(cleanupHistoryItemMaxOrders++, record.string(0));
			}
			db.close();
			db.destruct();
			if (record != null)
				record.destruct();

			cleanupHistoryItemMax = cleanupHistoryItemMaxHistory + cleanupHistoryItemMaxOrders;

			spooler_log.info(cleanupHistoryItemMax + " entries scheduled for cleanup, " + cleanupHistoryItemMaxHistory + " history entries, "
					+ cleanupHistoryItemMaxOrders + " order history entries");

			return (cleanupHistoryItemMax > 0);

		}
		catch (Exception e) {
			spooler_log.error("error occurred processing history query: " + e);
		}

		return true;
	}

	public boolean spooler_process() {

		try {
			if (cleanupHistoryItemIndex >= cleanupHistoryItemMax)
				return false;
			// spooler_log.debug9("CleanupHistoryItem: "+cleanupHistoryItems.get(cleanupHistoryItemIndex).toString());
			if (cleanupHistoryItemIndex < cleanupHistoryItemMaxHistory) {
				cleanupHistory(Integer.parseInt(cleanupHistoryItems.get(cleanupHistoryItemIndex++).toString()), cleanupHistoryLogLevel);
			}
			else {
				cleanupOrderHistory(Integer.parseInt(cleanupHistoryOrderItems.get(cleanupHistoryItemIndex++ - cleanupHistoryItemMaxHistory).toString()),
						cleanupHistoryLogLevel);
			}
		}
		catch (Exception e) {
			spooler_log.warn("an error occurred: " + e.toString());
			spooler_task.end();
		}

		return (cleanupHistoryItemIndex < cleanupHistoryItemMax);
	}

	public void spooler_exit() {

		try {
			spooler_log.info("cleanup processed " + cleanupHistoryItemMax + " items, cleanup done for " + cleanupHistoryItemsProcessed + " items");
		}
		catch (Exception e) {
			spooler_log.warn("an error occurred: " + e.toString());
		}
	}

	public void cleanupHistory(int id, int logLevel) throws Exception {

		String log_string = "";
		String log_line = "";
		boolean line_ok = true;
		int linesSuppressed = 0;
		int linesProcessed = 0;

		if (spooler_task.id() == id) {
			spooler_log.debug1("cleanup skipped for current task with history entry: " + id);
			return;
		}

		String logCompressed = cleanupHistoryLogCompressed ? "gzip | " : "";

		try {
			sos.hostware.File cleanupHistoryDb = new sos.hostware.File();
			cleanupHistoryDb.open("-in nl | " + logCompressed + spooler.db_name() + " -table=" + cleanupHistoryTable + " -blob=LOG WHERE \"ID\"=" + id);

			if (!cleanupHistoryDb.eof()) {
				try {
					log_string = "";
					do {
						line_ok = true;
						linesProcessed++;
						log_line = cleanupHistoryDb.get_line();
						int pos_date = log_line.indexOf(' ');
						if (pos_date > -1) {
							int pos_time = log_line.indexOf(' ', pos_date + 1);
							if (pos_time > -1) {
								int pos_type = log_line.indexOf(' ', pos_time + 1);
								if (pos_type == -1)
									pos_type = log_line.length();
								String log_type = log_line.substring((pos_time + 2), (pos_time + 2 + pos_type - pos_time - 3));
								try {
									int log_level_value = Integer.parseInt(log_type.substring(log_type.length() - 1));
									line_ok = (log_level_value <= cleanupHistoryLogLevel);
								}
								catch (Exception e) {
									line_ok = !(cleanupHistoryLogLevel == 0 && log_type.equalsIgnoreCase("debug"));
								}
							}
						}
						if (line_ok) {
							log_string += log_line + "\r\n";
						}
						else {
							linesSuppressed++;
						}
					}
					while (!cleanupHistoryDb.eof());

				}
				catch (Exception e) {
					throw new Exception("error occurred processing log in cleanup for history entry [" + id + "], line " + linesProcessed + ": " + e.toString());
				}
			}

			try {
				cleanupHistoryDb.close();
			}
			catch (Exception e) {
			}
			cleanupHistoryDb.destruct();

			if (linesSuppressed <= 0) {
				spooler_log.debug9("history entry needs no cleanup: " + id);
				return;
			}
		}
		catch (Exception e) {
			throw new Exception("error occurred reading log for cleanup of history entry [" + id + "]: " + e.toString());
		}

		try {
			sos.hostware.File cleanupHistoryDb = new sos.hostware.File();
			cleanupHistoryDb.open("-out nl | " + logCompressed + spooler.db_name() + " -commit -table=" + cleanupHistoryTable + " -blob=LOG WHERE \"ID\"=" + id);
			cleanupHistoryDb.put_line(log_string);
			try {
				cleanupHistoryDb.close();
			}
			catch (Exception e) {
			}
			cleanupHistoryDb.destruct();

			cleanupHistoryItemsProcessed++;
			spooler_log.debug5("cleanup successful for history entry [" + id + "]: " + linesProcessed + " lines processed, " + linesSuppressed
					+ " lines suppressed");
		}
		catch (Exception e) {
			throw new Exception("error occurred writing log after cleanup of history entry [" + id + "]: " + e.toString());
		}
	}

	public void cleanupOrderHistory(int id, int logLevel) throws Exception {

		String log_string = "";
		String log_line = "";
		boolean line_ok = true;
		int linesSuppressed = 0;
		int linesProcessed = 0;

		String logCompressed = cleanupHistoryLogCompressed ? "gzip | " : "";

		try {
			sos.hostware.File cleanupHistoryDb = new sos.hostware.File();
			cleanupHistoryDb.open("-in nl | " + logCompressed + spooler.db_name() + " -table=" + cleanupOrderHistoryTable + " -blob=LOG WHERE \"HISTORY_ID\"="
					+ id);

			if (!cleanupHistoryDb.eof()) {
				try {
					log_string = "";
					do {
						line_ok = true;
						linesProcessed++;
						log_line = cleanupHistoryDb.get_line();
						int pos_date = log_line.indexOf(' ');
						if (pos_date > -1) {
							int pos_time = log_line.indexOf(' ', pos_date + 1);
							if (pos_time > -1) {
								int pos_type = log_line.indexOf(' ', pos_time + 1);
								if (pos_type == -1)
									pos_type = log_line.length();
								String log_type = log_line.substring((pos_time + 2), (pos_time + 2 + pos_type - pos_time - 3));
								try {
									int log_level_value = Integer.parseInt(log_type.substring(log_type.length() - 1));
									line_ok = (log_level_value <= cleanupHistoryLogLevel);
								}
								catch (Exception e) {
									line_ok = !(cleanupHistoryLogLevel == 0 && log_type.equalsIgnoreCase("debug"));
								}
							}
						}
						if (line_ok) {
							log_string += log_line + "\r\n";
						}
						else {
							linesSuppressed++;
						}
					}
					while (!cleanupHistoryDb.eof());

				}
				catch (Exception e) {
					throw new Exception("error occurred processing log in cleanup for order history entry [" + id + "], line " + linesProcessed + ": "
							+ e.toString());
				}
			}

			try {
				cleanupHistoryDb.close();
			}
			catch (Exception e) {
			}
			cleanupHistoryDb.destruct();

			if (linesSuppressed <= 0) {
				spooler_log.debug9("order history entry needs no cleanup: " + id);
				return;
			}
		}
		catch (Exception e) {
			throw new Exception("error occurred reading log for cleanup of order history entry [" + id + "]: " + e.toString());
		}

		try {
			sos.hostware.File cleanupHistoryDb = new sos.hostware.File();
			cleanupHistoryDb.open("-out nl | " + logCompressed + spooler.db_name() + " -commit -table=" + cleanupOrderHistoryTable
					+ " -blob=LOG WHERE \"HISTORY_ID\"=" + id);
			cleanupHistoryDb.put_line(log_string);
			try {
				cleanupHistoryDb.close();
			}
			catch (Exception e) {
			}
			cleanupHistoryDb.destruct();

			cleanupHistoryItemsProcessed++;
			spooler_log.debug5("cleanup successful for order history entry [" + id + "]: " + linesProcessed + " lines processed, " + linesSuppressed
					+ " lines suppressed");
		}
		catch (Exception e) {
			throw new Exception("error occurred writing log after cleanup of order history entry [" + id + "]: " + e.toString());
		}
	}
}
