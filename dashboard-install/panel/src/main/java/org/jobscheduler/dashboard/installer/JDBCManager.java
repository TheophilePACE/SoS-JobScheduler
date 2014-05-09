package org.jobscheduler.dashboard.installer;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.WHITE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.fusesource.jansi.AnsiConsole;
import org.jobscheduler.dashboard.installer.db.Databases;

public class JDBCManager {

	private static final String DB = "db";
	private static final String SERVER_NAME = "serverName";
	private static final String PORT = "port";
	private static final String DATABASE_NAME = "databaseName";
	private static final String DB_USER_NAME = "dbUser";
	private static final String DB_USER_PASSWORD = "dbPassword";

	private static final String NO_TEST_CONNECTION = "notest";
	CommandLine cmd;

	public static void main(String[] args) throws Exception {
		JDBCManager manager = new JDBCManager();
		manager.run(args);
	}

	public void run(String[] args) throws Exception {
		AnsiConsole.systemInstall();
		Options options = getOptions();

		CommandLineParser parser = new GnuParser();
		try {
			cmd = parser.parse(options, args);
			// test database connection
			if (cmd.getOptionValue(NO_TEST_CONNECTION) != null) {
				createConnection();
			}
		} catch (Exception e) {
			HelpFormatter formatter = new HelpFormatter();
			System.out.println(ansi().fg(RED).a("JDBCManager").fg(WHITE));
			formatter.printHelp(
					"java org.jobscheduler.dashboard.installer.JDBCManager",
					options);
			throw e;
		}

	}

	private Options getOptions() {
		Options options = new Options();

		Option dbName = OptionBuilder
				.withArgName(DB)
				.isRequired()
				.hasArg()
				.withDescription(
						"Database (e.g : " + Databases.getSupportedDatabases()
								+ ")").create(DB);
		options.addOption(dbName);

		Option serverName = OptionBuilder.withArgName(SERVER_NAME).isRequired()
				.hasArg().withDescription("Server name").create(SERVER_NAME);
		options.addOption(serverName);

		Option portNumberOption = OptionBuilder.withArgName(PORT).isRequired()
				.hasArg().withDescription("Port number").create(PORT);
		options.addOption(portNumberOption);

		Option dataBaseName = OptionBuilder.withArgName(DATABASE_NAME)
				.isRequired().hasArg().withDescription("Database name")
				.create(DATABASE_NAME);
		options.addOption(dataBaseName);

		Option userNameOption = OptionBuilder.withArgName(DB_USER_NAME).hasArg()
				.withDescription("User name").create(DB_USER_NAME);
		options.addOption(userNameOption);

		Option passwordOption = OptionBuilder.withArgName(DB_USER_PASSWORD)
				.hasArg().withDescription("Password").create(DB_USER_PASSWORD);
		options.addOption(passwordOption);

		Option noTestConnectionOption = OptionBuilder.withArgName(NO_TEST_CONNECTION)
				.withDescription("Don't test database connection").create(NO_TEST_CONNECTION);
		options.addOption(noTestConnectionOption);
		
		return options;
	}

	/**
	 * Factory method that creates a sql Connection based on given args.
	 * 
	 * @return Connection
	 * @throws Exception
	 */
	public Connection createConnection() throws Exception {
		Connection conn = null;
		try {
			// register appropriate jdbc driver
			Databases database;
			try {
				database = Databases.valueOf(cmd.getOptionValue(DB)
						.toUpperCase());
			} catch (Exception e) {
				throw new IllegalStateException("Database "
						+ cmd.getOptionValue(DB) + " unknown");
			}
			Class.forName(database.getDefaultDriver());
			conn = DriverManager.getConnection(
					database.getUrl(cmd.getOptionValue(SERVER_NAME),
							cmd.getOptionValue(PORT),
							cmd.getOptionValue(DATABASE_NAME)),
					cmd.getOptionValue(DB_USER_NAME),
					cmd.getOptionValue(DB_USER_PASSWORD));
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		return conn;
	}

	@Override
	public String toString() {
		if (cmd == null) {
			return super.toString();
		} else {
			return "[ " + DB + "=" + cmd.getOptionValue(DB) + "  "
					+ SERVER_NAME + "=" + cmd.getOptionValue(SERVER_NAME) 
					+ PORT + "=" + cmd.getOptionValue(PORT )
					+ DB_USER_NAME + "=" + cmd.getOptionValue(DB_USER_NAME) 
					+ DB_USER_PASSWORD + "=" + cmd.getOptionValue(DB_USER_PASSWORD) + "]";
		}

	}

}
