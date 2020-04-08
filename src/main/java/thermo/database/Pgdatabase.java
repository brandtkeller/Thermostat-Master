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
}