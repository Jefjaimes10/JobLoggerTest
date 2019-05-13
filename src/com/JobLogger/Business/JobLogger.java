package com.JobLogger.Business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {

	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map dbParams;
	private static Logger logger;
	private static Properties prop = null;
	
	private final static String SQL_DROP_TABLE = "DROP TABLE IF EXISTS LOG_VALUES";
	private final static String SQL_CREATE_LOG_VALUES = "CREATE TABLE LOG_VALUES ( TYPE VARCHAR(15),MESSAGE VARCHAR(150) )";
	private final static String SQL_INSERT_MESSAGE = "INSERT INTO LOG_VALUES(TYPE,MESSAGE) VALUES (?,?)";
	private final static String SQL_SELECT_LOG_VALUES = "SELECT TYPE, MESSAGE FROM LOG_VALUES";


	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");  
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
		
	}
	
	public static String LogMessageDatabase(String messageText, int messageType)  {
		
		String query = "";
		loadProperties();
		boolean availability = messageAvailability(messageType);
		if(availability) {
			
			String messageTxt = messageText.trim();
			String typeMessage = validateMessage(messageType);
			int index = 1;
			
			Connection connection = null;
			PreparedStatement preparedStmt = null;
			ResultSet rs = null;
			
			
			try {
	
				
				Properties connectionProps = new Properties();
				connectionProps.put(prop.getProperty("JDBC_USER"), prop.getProperty("JDBC_USER_NAME"));
				connectionProps.put(prop.getProperty("JDBC_PASSWORD"), prop.getProperty("JDBC_PASSWORD_VALUE"));
				connection = DriverManager.getConnection(prop.getProperty("JDBC_CONNECTION"), connectionProps);
				
				preparedStmt = connection.prepareStatement(SQL_DROP_TABLE);
				preparedStmt.executeUpdate();
				preparedStmt = connection.prepareStatement(SQL_CREATE_LOG_VALUES);
				preparedStmt.executeUpdate();
				preparedStmt = connection.prepareStatement(SQL_INSERT_MESSAGE);
				preparedStmt.setString(index++, typeMessage);
				preparedStmt.setString(index++, messageTxt);
				preparedStmt.executeUpdate();
				preparedStmt = connection.prepareStatement(SQL_SELECT_LOG_VALUES);
		        rs = preparedStmt.executeQuery();
		      
		        while( rs.next() )
				  {
				      query = rs.getString("TYPE") + " " + rs.getString("MESSAGE");
				  }
		        preparedStmt.close();
		        connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return query;
	}
	
	public static String LogMessageFile(String messageText, int messageType) {
		
		String message = "";
		loadProperties();
		boolean availability = messageAvailability(messageType);
		if(availability) {
					
			String messageTxt = messageText.trim();
			String typeMessage = validateMessage(messageType);
			
			logger = Logger.getLogger("MyLog"); 
			
			String filePath = prop.getProperty("FILE_FOLDER") 
					+ prop.getProperty("FILE_NAME") 
					+ prop.getProperty("FILE_EXTENSION");
			
			File logFile = new File(filePath);
			try {
				if (!logFile.exists()) {
					
						logFile.createNewFile();
				}
						FileHandler fh = new FileHandler(filePath);
						message = typeMessage + " " + messageText;
						logger.addHandler(fh);
						logger.log(Level.INFO, message); 
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message = "";
			}
		}
		return message;
	}
	
	public static String LogMessageConsole(String messageText, int messageType) {
		
		String message = "";
		loadProperties();
		boolean availability = messageAvailability(messageType);
		if(availability) {

			String messageTxt = messageText.trim();
			String typeMessage = validateMessage(messageType);
			
			logger = Logger.getLogger("MyLog"); 
			
			ConsoleHandler ch = new ConsoleHandler();
			message = typeMessage + " " + messageText;
			logger.addHandler(ch);
			logger.log(Level.INFO, message);
		}
		return message;
	}
	
	public static String validateMessage(int type) {
		
		String typeMessage = null;
		
		switch (type) {
		case 1:
			typeMessage = prop.getProperty("MESSAGE_TYPE_INFO");
			break;
		case 2:
			typeMessage = prop.getProperty("MESSAGE_TYPE_WARNING");
			break;
		case 3:
			typeMessage = prop.getProperty("MESSAGE_TYPE_ERROR");
			break;
		default:
			typeMessage = "";
			break;
		}
		
		return typeMessage;
	}
	
	public static boolean messageAvailability(int type) {
		
		boolean message = Boolean.parseBoolean(prop.getProperty("E_D_MESSAGE"));
		boolean warning = Boolean.parseBoolean(prop.getProperty("E_D_WARNING"));
		boolean error = Boolean.parseBoolean(prop.getProperty("E_D_ERROR"));
		boolean availability = true;
		
		switch (type) {
		case 1:
			availability = message;
			break;
		case 2:
			availability = warning;
			break;
		case 3:
			availability = error;
			break;
		default:
			availability = true;
			break;
		}
		return availability;
	}
	
	public static void loadProperties() {
		try (InputStream input = new FileInputStream("resources/messages.properties")) {

            prop = new Properties();
            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
