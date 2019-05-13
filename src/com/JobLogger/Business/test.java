package com.JobLogger.Business;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public abstract class test {

	private static Properties prop = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try (InputStream input = new FileInputStream("resources/messages.properties")) {

            prop = new Properties();
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("MESSAGE_TYPE_INFO"));
            System.out.println(prop.getProperty("MESSAGE_TYPE_WARNING"));
            System.out.println(prop.getProperty("MESSAGE_TYPE_ERROR"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
		try
	    {
	      Class.forName("org.h2.Driver");
	          Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "" );
	          Statement stmt = con.createStatement();
	          //stmt.executeUpdate( "DROP TABLE table1" );
	          stmt.executeUpdate( "CREATE TABLE table1 ( user varchar(50) )" );
	          stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Claudio' )" );
	          stmt.executeUpdate( "INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )" );
	          ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
	      while( rs.next() )
	      {
	          String name = rs.getString("user");
	          System.out.println( name );
	      }
	      stmt.close();
	      con.close();
	    }
	    catch( Exception e )
	    {
	      System.out.println( e.getMessage() );
	    }
	}

}
