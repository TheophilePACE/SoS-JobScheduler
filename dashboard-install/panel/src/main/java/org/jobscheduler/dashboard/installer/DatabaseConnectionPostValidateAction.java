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
package org.jobscheduler.dashboard.installer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.PanelActionConfiguration;
import com.izforge.izpack.api.handler.AbstractUIHandler;
import com.izforge.izpack.data.PanelAction;

public class DatabaseConnectionPostValidateAction implements PanelAction {

	JDBCManager jdbcManager = new JDBCManager();

	public void executeAction(InstallData installData, AbstractUIHandler handler) {
		String serverName = installData
				.getVariable("jobscheduler.db.host.name");
		String port = installData.getVariable("jobscheduler.db.port.number");
		String databaseName = installData
				.getVariable("jobscheduler.db.schema.name");
		String dbUser = installData.getVariable("jobscheduler.db.user.name");
		String dbPassword = installData.getVariable("jobscheduler.db.password");
		List<String> command = new ArrayList<String>();
		command.add("--db");
		command.add("postgresql");
		command.add("--serverName");
		command.add(serverName);
		command.add("--port");
		command.add(port);
		command.add("--databaseName");
		command.add(databaseName);
		command.add("--dbUser");
		command.add(dbUser);
		command.add("--dbPassword");
		command.add(dbPassword);

		try {
			CommandLine cmd = jdbcManager.run(command.toArray(new String[0]));
			jdbcManager.createApplicationPropertiesFile(
					installData.getInstallPath(), cmd);
		} catch (Exception e) {
			e.printStackTrace();
			handler.emitWarning("Invalid connection ",
					"Your database connection doesn't work " + "[ " + "DB="
							+ "postgresql" + "  SERVER_NAME=" + serverName
							+ " PORT=" + port + " DB_USER_NAME=" + dbUser
							+ " DB_USER_PASSWORD" + "=" + dbPassword + "]"
							+ ". \nYou need to modify application-jobscheduler.yml configuration file in  "
							+ installData.getInstallPath() + File.separator + "config/ after installation");
			// don't check JDBC connection
			command.add("--notest");
			try {
				CommandLine cmd = jdbcManager.run(command
						.toArray(new String[0]));
				jdbcManager.createApplicationPropertiesFile(
						installData.getInstallPath(), cmd);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

	}

	public void initialize(PanelActionConfiguration actionConfiguration) {
	}

}
