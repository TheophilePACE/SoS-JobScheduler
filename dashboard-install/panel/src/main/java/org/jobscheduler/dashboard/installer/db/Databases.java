package org.jobscheduler.dashboard.installer.db;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;


public enum Databases {
	HSQLDB(Databases.HSQLDB_NAME, Databases.HSQLDB_SHORTNAME,
			"org.hsqldb.jdbc.JDBCDriver", "") {
	},
	MYSQL(Databases.MYSQL_NAME, Databases.MYSQL_SHORTNAME,
			"com.mysql.jdbc.Driver", "") {
	},
	ORACLE(Databases.ORACLE_NAME, Databases.ORACLE_SHORTNAME,
			"oracle.jdbc.OracleDriver", "") {
	},
	POSTGRESQL(Databases.POSTGRESQL_NAME, Databases.POSTGRESQL_SHORTNAME,
			"org.postgresql.Driver", "jdbc:postgresql://${serverName}:${port}/${databaseName}") {
	},
	SQLSERVER(Databases.SQLSERVER_NAME, Databases.SQLSERVER_SHORTNAME,
			"com.microsoft.sqlserver.jdbc.SQLServerDriver", "") {
	},
	;

	public final static String HSQLDB_NAME = "HyperSQL";
	public final static String HSQLDB_SHORTNAME = "hsqldb";

	public final static String MYSQL_NAME = "MySql";
	public final static String MYSQL_SHORTNAME = "mysql";

	public final static String ORACLE_NAME = "Oracle";
	public final static String ORACLE_SHORTNAME = "oracle";

	public final static String POSTGRESQL_NAME = "PostgreSQL";
	public final static String POSTGRESQL_SHORTNAME = "postgresql";

	public final static String SQLSERVER_NAME = "Sql Server";
	public final static String SQLSERVER_SHORTNAME = "sqlserver";

	private final String name;

	private final String shortName;

	private final String defaultDriver;
	
	private final String urlFormat;

	private Databases(String name,
			String shortName, String defaultDriver, String urlFormat) {
		this.name = name;
		this.shortName = shortName;
		this.defaultDriver = defaultDriver;
		this.urlFormat = urlFormat;
	}

	public final String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDefaultDriver() {
		return defaultDriver;
	}

	public boolean tryUrl(String afterJdbc) {
		return afterJdbc.startsWith(shortName + ':');
	}

	public static String databaseNames(String prefix, String suffix,
			String delimit) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < Databases.values().length; ++i) {
			Databases db = Databases.values()[i];
			if (prefix != null) {
				sb.append(prefix);
			}
			if (i > 0 && delimit != null) {
				sb.append(delimit);
			}
			sb.append(db.getShortName());
			if (suffix != null) {
				sb.append(suffix);
			}
		}

		return sb.toString();
	}

	public static Databases fromUrl(String url) {
		final String afterJdbc = url.substring(5).toLowerCase();
		for (Databases db : values()) {
			if (db.tryUrl(afterJdbc)) {
				return db;
			}
		}
		return null;
	}

	public static String getSupportedDatabases() {
		StringBuilder shortNameDatabases  = new StringBuilder();
		int i =0;
		for (Databases db : Databases.values()) {
			i++;
			shortNameDatabases.append(db.getShortName());
			if (i < Databases.values().length)
				shortNameDatabases.append(",");
		}
		return shortNameDatabases.toString();

	}

	public String getUrl(String serverName, String port,
			String databaseName) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		 valuesMap.put("serverName", serverName);
		 valuesMap.put("port", port);
		 valuesMap.put("databaseName", databaseName);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(urlFormat);
	}

}
