package thermo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import thermo.models.*;

public class Pgdatabase {

    private final String url = "jdbc:postgresql://postgres/thermostat";
    private final String user = "developer";
    private final String password = "keller";

    /* --------------- Function to connect to the database each time --------------- */
 
    public Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            printSQLException(e);
        }
        return conn;
    }

    /* --------------- Thermostat Initialization ---------------*/

    public Thermostat init(String title) {
        List<Thermostat> tList = new ArrayList<>();
        tList = getAllThermostats();

        for (Thermostat temp : tList) {
            if (temp.getTitle() == title) {
                // // Now get schedule by ID and assign to Thermostat
                // Schedule schedule = getScheduleById(temp.getScheduleId());
                // temp.setSchedule(schedule);
                // // get all settings and add to schedule
                // int scheduleId = schedule.getId();
                // for (Setting set : getAllSettings) {
                //      if (set.getScheduleId() == scheduleId) { schedule.addSettingToList(set) }   
                // }
                return temp;
            }
        }
        return null;
    }

    /* --------------- Thermostat Database functions --------------- */

    public List<Thermostat> getAllThermostats() {
        List<Thermostat> tList = new ArrayList<>();
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM THERMOSTAT;" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String  title = rs.getString("title");
                int threshold = rs.getInt("threshold");
                int scheduleId = rs.getInt("scheduleId");
                Thermostat temp = new Thermostat(id, threshold, title, scheduleId);
                tList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return tList;
    }

    public Thermostat getThermostatById(int id) {

        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM THERMOSTAT WHERE ID = " + Integer.toString(id) + ";" );
            while (rs.next()) {
                String  title = rs.getString("title");
                int threshold = rs.getInt("threshold");
                int scheduleId = rs.getInt("scheduleId");
                Thermostat temp = new Thermostat(id, threshold, title, scheduleId);
                return temp;
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public int createThermostat(Thermostat temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "INSERT INTO THERMOSTAT (TITLE,THRESHOLD) "
            + "VALUES (" + temp.getTitle() + ", " + temp.getThreshold() + " RETURNING ID);" );
            rs.next();
            int id = rs.getInt("id");
            rs.close();
            stmt.close();
            conn.close();
            return id;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return -1;
    }

    public boolean modifyThermostat(Thermostat temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "UPDATE THERMOSTAT set title = " + temp.getTitle() + " AND threshold = " 
            + temp.getThreshold() + " where ID=" + temp.getId() + ";" );
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean removeThermostat(int id) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "DELETE from THERMOSTAT where ID = " + Integer.toString(id) + ";");
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }


    /*--------------- SQL Exception functions for logging --------------- */

    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                    ((SQLException)e).
                    getSQLState()) == false) {
    
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                        ((SQLException)e).getSQLState());
    
                    System.err.println("Error Code: " +
                        ((SQLException)e).getErrorCode());
    
                    System.err.println("Message: " + e.getMessage());
    
                    Throwable t = ex.getCause();
                    while(t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
    
        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;
    
        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;
    
        return false;
    }
}