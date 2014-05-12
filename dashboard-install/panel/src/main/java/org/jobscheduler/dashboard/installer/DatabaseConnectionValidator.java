package org.jobscheduler.dashboard.installer;

import java.util.ArrayList;
import java.util.List;

import com.izforge.izpack.api.data.InstallData;

public class DatabaseConnectionValidator implements com.izforge.izpack.api.installer.DataValidator {

	JDBCManager jdbcManager = new JDBCManager();
	
	String errorMessage;
	
	public boolean getDefaultAnswer() {
		return false;
	}

	public String getErrorMessageId() {
		return errorMessage;
	}

	public String getWarningMessageId() {
		return null;
	}

	public Status validateData(InstallData installData) {
//		String serverName = installData
//				.getVariable("jobscheduler.db.host.name");
//		String port = installData.getVariable("jobscheduler.db.port.number");
//		String databaseName = installData
//				.getVariable("jobscheduler.db.schema.name");
//		String dbUser = installData.getVariable("jobscheduler.db.user.name");
//		String dbPassword = installData.getVariable("jobscheduler.db.password");
//		List<String> command = new ArrayList<String>();
//		command.add("--db");
//		command.add("postgresql");
//		command.add("--serverName");
//		command.add(serverName);
//		command.add("--port");
//		command.add(port);
//		command.add("--databaseName");
//		command.add(databaseName);
//		command.add("--dbUser");
//		command.add(dbUser);
//		command.add("--dbPassword");
//		command.add(dbPassword);
//		try {
//			jdbcManager.run(command.toArray(new String[0]));
//			return Status.OK;
//		} catch (Exception e) {
//			e.printStackTrace();
//			errorMessage = "Your database connection doesn't work " + "[ " + "DB="
//					+ "postgresql" + "  SERVER_NAME=" + serverName
//					+ " PORT=" + port + " DB_USER_NAME=" + dbUser
//					+ " DB_USER_PASSWORD" + "=" + dbPassword + "]" 
//			return Status.ERROR;
//		}
		return Status.OK;
	}

}
