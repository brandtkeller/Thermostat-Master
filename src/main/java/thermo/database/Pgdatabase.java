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
 
    private Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

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
                Thermostat temp = new Thermostat(id, threshold, title);
                tList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {

        }
        return tList;
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
            return false;
        }
    }
}