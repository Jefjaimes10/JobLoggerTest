package com.JobLogger.Test;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.JobLogger.Business.JobLogger;

import junit.framework.Assert;

public class JobLoggerTest  {

	/**
	 * Messages types:
	 * 1 Message
	 * 2 Warning
	 * 3 Error
	 */
	
	@Test
	public void LogMessageDatabase() {
		
		String message = "Inserting a message into a database";
		String result = JobLogger.LogMessageDatabase(message, 1);
		
		assertEquals("[MESSAGE] Inserting a message into a database", result);
	}
	
	@Test
	public void LogWarningMessageDatabase() {
		
		String message = "Inserting a warning message into a database";
		String result = JobLogger.LogMessageDatabase(message, 2);
		
		assertEquals("[WARNING] Inserting a warning message into a database", result);
	}

	@Test
	public void LogErrorMessageDatabase() {
		
		String message = "Inserting an error message into a database";
		String result = JobLogger.LogMessageDatabase(message, 3);
		
		assertEquals("[ERROR] Inserting an error message into a database", result);
	}

	@Test
	public void LogMessageFile() {
		
		String message = "Inserting a message into a file";
		String result = JobLogger.LogMessageFile(message, 1);
		
		assertEquals("[MESSAGE] Inserting a message into a file", result);
	}
	
	@Test
	public void LogWarningMessageFile() {
		
		String message = "Inserting a warning message into a file";
		String result = JobLogger.LogMessageFile(message, 2);
		
		assertEquals("[WARNING] Inserting a warning message into a file", result);
	}

	@Test
	public void LogErrorMessageFile() {
		
		String message = "Inserting an error message into a file";
		String result = JobLogger.LogMessageFile(message, 3);
		
		assertEquals("[ERROR] Inserting an error message into a file", result);
	}
	
	@Test
	public void LogMessageConsole() {
		
		String message = "Inserting a message into the console";
		String result = JobLogger.LogMessageConsole(message, 1);
		
		assertEquals("[MESSAGE] Inserting a message into the console", result);
	}
	
	@Test
	public void LogWarningMessageConsole() {
		
		String message = "Inserting a warning message into the console";
		String result = JobLogger.LogMessageConsole(message, 2);
		
		assertEquals("[WARNING] Inserting a warning message into the console", result);
	}

	@Test
	public void LogErrorMessageConsole() {
		
		String message = "Inserting an error message into the console";
		String result = JobLogger.LogMessageConsole(message, 3);
		
		assertEquals("[ERROR] Inserting an error message into the console", result);
	}
}
