package thermo.models;

public class Node {
    private int id;
    private String ipaddress;
    private int port;
    private int temperature;
    private String title;
    private String type;
    private int thermostatId;

    public Node(int id, String ip, int port, int temperature, String type, int thermostatId) {
        this.id = id;
        this.ipaddress = ip;
        this.port = port;
        this.temperature = temperature;
        this.title = null;
        this.type = type;
        this.thermostatId = thermostatId;
    }

    public Node(int id, String ip, int port, int temperature, String title, String type, int thermostatId) {
        this.id = id;
        this.ipaddress = ip;
        this.port = port;
        this.temperature = temperature;
        this.title = title;
        this.type = type;
        this.thermostatId = thermostatId;
    }

    @Override
    public String toString() { 
        return String.format("{'type':'node','id':'" + id + "','attributes':{'title':'" + title + "','type':'" + type + "','ipAddress':'" + ipaddress + "'}},"); 
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return this.ipaddress;
    }

    public void setIp(String ip) {
        this.ipaddress = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort( int port) {
        this.port = port;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    public int getThermostatId() {
        return this.thermostatId;
    }

    public void setThermostatId(int id) {
        this.thermostatId = id;
    }
}