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
    // Pass these as runtime environment variables
    private String url;
    private String user;
    private String password;

    public Pgdatabase(String url, String user, String pass) {
        List<Thermostat> tList = new ArrayList<>();

        setUrl(url);
        setUser(user);
        setPassword(pass);

        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();

            // DB Table initialization
            stmt.executeUpdate( "CREATE TABLE IF NOT EXISTS thermostat (id SERIAL PRIMARY KEY, Title VARCHAR(255), Threshold INT, Scheduleid SERIAL, Mode VARCHAR(255));" );
            stmt.executeUpdate( "CREATE TABLE IF NOT EXISTS schedule (id SERIAL PRIMARY KEY, Title VARCHAR(255));" );
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS setting (id SERIAL PRIMARY KEY, Day VARCHAR(255), Scheduleid SERIAL," +
            " Wake VARCHAR(255), WakeTemp INT, Leave VARCHAR(255), LeaveTemp INT," +
            " Home VARCHAR(255), HomeTemp INT, Sleep VARCHAR(255), SleepTemp INT);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS node (id SERIAL PRIMARY KEY, Title VARCHAR(255), ipAddress VARCHAR(255), port INT, Temperature INT, Type VARCHAR(255), ThermostatId SERIAL);");
            

            // If there are no thermostats, then we need to create whole stack
            tList = getAllThermostats();
            if ( tList.size() == 0) {
                // Delete all entries from schedule and setting for fresh stack.
                stmt.executeUpdate( "DELETE FROM SCHEDULE" );
                stmt.executeUpdate( "DELETE FROM SETTING" );
                ResultSet rs = stmt.executeQuery("INSERT INTO schedule (Title) VALUES ('Main') RETURNING ID;");
                rs.next();
                int schedId = rs.getInt("id");
                rs.close();
                stmt.executeUpdate("INSERT INTO thermostat (Title, Threshold, Scheduleid, Mode) " +
                "VALUES ('Master', 3, " + schedId + ", 'Heat');");
                
                stmt.executeUpdate("INSERT INTO Setting (DAY, SCHEDULEID, WAKE, WAKETEMP, LEAVE, LEAVETEMP, HOME, HOMETEMP, SLEEP, SLEEPTEMP)" +
                " VALUES ('Sun', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Mon', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Tue', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Wed', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Thu', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Fri', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55)," +
                "('Sat', "+ schedId +", '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55);");

                // Test code for initial testing purposes TODO: Remove
                stmt.executeUpdate("INSERT INTO node (Title, ipAddress, port, Temperature, Type, ThermostatId) VALUES ('Test', 'localhost', '6666', '70','airtemp', '1');");
            }
            
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
    }

    public void setUrl(String url) {
        this.url = "jdbc:postgresql://" + url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
                String mode = rs.getString("mode");
                Thermostat temp = new Thermostat(id, threshold, title, scheduleId, mode);
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
                String mode = rs.getString("mode");
                Thermostat temp = new Thermostat(id, threshold, title, scheduleId, mode);
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
            ResultSet rs = stmt.executeQuery( "INSERT INTO THERMOSTAT (TITLE,THRESHOLD, SCHEDULEID, MODE) "
            + "VALUES (" + temp.getTitle() + ", " + temp.getThreshold() + ", " + temp.getScheduleId() + ", " + temp.getMode() + ") RETURNING ID);" );
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
            + temp.getThreshold() + " AND scheduleId = " + temp.getScheduleId() + 
            " AND mode = " + temp.getMode() + " where ID=" + temp.getId() + ";" );
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

    /*---------------Schedule database functions --------------- */

    public List<Schedule> getAllSchedules() {
        List<Schedule> sList = new ArrayList<>();
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM SCHEDULE;" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String  title = rs.getString("title");
                Schedule temp = new Schedule(id, title);
                sList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return sList;
    }

    public Schedule getScheduleById(int id) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM SCHEDULE WHERE ID = " + Integer.toString(id) + ";" );
            while (rs.next()) {
                String  title = rs.getString("title");
                Schedule temp = new Schedule(id, title);

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

    public int createSchedule(Schedule temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "INSERT INTO Schedule (TITLE) "
            + "VALUES (" + temp.getTitle() + ") RETURNING ID);" );
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

    public boolean modifySchedule(Schedule temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "UPDATE Schedule set title = " + temp.getTitle() + " where ID=" + temp.getId() + ";" );
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean removeSchedule(int id) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "DELETE from Schedule where ID = " + Integer.toString(id) + ";");
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    /* --------------- Settings database functions --------------- */

    public List<Setting> getAllSettings() {
        List<Setting> sList = new ArrayList<>();
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM SETTING;" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String  day = rs.getString("day");
                int scheduleId = rs.getInt("scheduleid");
                String wake = rs.getString("wake");
                int wakeTemp = rs.getInt("wakeTemp");
                String leave = rs.getString("leave");
                int leaveTemp = rs.getInt("leaveTemp");
                String home = rs.getString("home");
                int homeTemp = rs.getInt("homeTemp");
                String sleep = rs.getString("sleep");
                int sleepTemp = rs.getInt("sleepTemp");
                Setting temp = new Setting(id, scheduleId, day, wake, wakeTemp, leave, leaveTemp, home, homeTemp, sleep, sleepTemp);
                sList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return sList;
    }

    public Setting getSettingById(int id) {

        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM SETTING WHERE ID = " + Integer.toString(id) + ";" );
            while (rs.next()) {
                String  day = rs.getString("day");
                int scheduleId = rs.getInt("scheduleid");
                String wake = rs.getString("wake");
                int wakeTemp = rs.getInt("wakeTemp");
                String leave = rs.getString("leave");
                int leaveTemp = rs.getInt("leaveTemp");
                String home = rs.getString("home");
                int homeTemp = rs.getInt("homeTemp");
                String sleep = rs.getString("sleep");
                int sleepTemp = rs.getInt("sleepTemp");
                Setting temp = new Setting(id, scheduleId, day, wake, wakeTemp, leave, leaveTemp, home, homeTemp, sleep, sleepTemp);
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

    public List<Setting> getSettingsBySchedule(int scheduleId) {
        List<Setting> sList = new ArrayList<>();
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM SETTING WHERE SCHEDULEID = " + Integer.toString(scheduleId) + ";" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String  day = rs.getString("day");
                String wake = rs.getString("wake");
                int wakeTemp = rs.getInt("wakeTemp");
                String leave = rs.getString("leave");
                int leaveTemp = rs.getInt("leaveTemp");
                String home = rs.getString("home");
                int homeTemp = rs.getInt("homeTemp");
                String sleep = rs.getString("sleep");
                int sleepTemp = rs.getInt("sleepTemp");
                Setting temp = new Setting(id, scheduleId, day, wake, wakeTemp, leave, leaveTemp, home, homeTemp, sleep, sleepTemp);
                sList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return sList;
    }

    public int createSetting(Setting temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "INSERT INTO Setting (SCHEDULEID, DAY, WAKE, WAKETEMP, LEAVE, LEAVETEMP, HOME, HOMETEMP, LEAVE, LEAVETEMP) "
            + "VALUES (" + temp.getScheduleId() + ", " + temp.getDay() + ", " 
            + temp.getWakeTime() + ", " + temp.getWakeTemp() +  ", " 
            + temp.getLeaveTime() +  ", " + temp.getLeaveTemp() +  ", " 
            + temp.getHomeTime() +  ", " + temp.getHomeTemp() +  ", " 
            + temp.getSleepTime() +  ", " + temp.getSleepTemp() +  
            ") RETURNING ID);" );
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

    public boolean modifySetting(Setting temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "UPDATE Setting set day = " + temp.getDay() + ", scheduleid = " + temp.getScheduleId() 
            + ", wake = " + temp.getWakeTime() + ", waketemp = " + temp.getWakeTemp()
            + ", leave = " + temp.getLeaveTime() + ", leavetemp = " + temp.getLeaveTemp()
            + ", home = " + temp.getHomeTime() + ", hometemp = " + temp.getHomeTemp()
            + ", sleep = " + temp.getSleepTime() + ", sleeptemp = " + temp.getSleepTemp()
            + " where ID=" + temp.getId() + ";" );
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean removeSetting(int id) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "DELETE from Setting where ID = " + Integer.toString(id) + ";");
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    /*--------------- Node database functions --------------- */

    public List<Node> getAllNodes() {
        List<Node> nList = new ArrayList<>();
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM NODE;" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String ipaddress = rs.getString("ipAddress");
                int port = rs.getInt("port");
                int temperature = rs.getInt("temperature");
                String type = rs.getString("type");
                // Could be a problem here getting a nmll value?
                String title = rs.getString("title");
                int thermoId = rs.getInt("thermostatId");
                Node temp = new Node(id, ipaddress, port, temperature, title, type, thermoId);
                nList.add(temp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            printSQLException(e);
        }
        return nList;
    }

    public Node getNodeById(int id) {

        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM NODE WHERE ID = " + Integer.toString(id) + ";" );
            while (rs.next()) {
                String ipaddress = rs.getString("ipAddress");
                int port = rs.getInt("port");
                int temperature = rs.getInt("temperature");
                String type = rs.getString("type");
                // Could be a problem here getting a nmll value?
                String title = rs.getString("title");
                int thermoId = rs.getInt("thermostatId");
                Node temp = new Node(id, ipaddress, port, temperature, title, type, thermoId);
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

    public int createNode(Node temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "INSERT INTO Node (Title, ipAddress, port, Temperature, Type, ThermostatId) "
            + "VALUES (" + temp.getTitle() + ", " + temp.getIp() + ", " + temp.getPort() + ", " + temp.getTemperature() + ", " + temp.getType() + ", " + temp.getThermostatId() +
            ") RETURNING ID);" );
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

    public boolean modifyNode(Node temp) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "UPDATE Node set IPADDRESS = " + temp.getIp() + ", TYPE = " + temp.getType() + ", TITLE = " 
            + temp.getTitle() + ", TEMPERATURE = " + temp.getTemperature() + ", THERMOSTATID = " + temp.getThermostatId() + " where ID = " + temp.getId() + ";" );
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean removeNode(int id) {
        Connection conn = connect(); 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "DELETE from Node where ID = " + Integer.toString(id) + ";");
            rs.close();
            stmt.close();
            conn.close();
            return true;
        } catch(SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    /* Node temperature bias/ mean average logic */

    public int getAllNodeTempsByThermostat(int thermoId) {
        Connection conn = connect(); 
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM NODE WHERE THERMOSTATID = " + Integer.toString(thermoId) + ";" );
            int temperature = 0;
            int count = 0;
            while (rs.next()) {
                temperature += rs.getInt("temperature");
                count++;
            }
            rs.close();
            stmt.close();
            conn.close();
            if (count == 0) {
                return -1;
            }
            return temperature / count;

        } catch(SQLException e) {
            printSQLException(e);
        }
        return -1;
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