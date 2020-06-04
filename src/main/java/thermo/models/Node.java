package thermo.models;

public class Node {
    private int id;
    private String ipaddress;
    private int port;
    private String title;
    private String type;

    public Node(int id, String ip, int port, String type) {
        this.id = id;
        this.ipaddress = ip;
        this.port = port;
        this.title = null;
        this.type = type;
    }

    public Node(int id, String ip, int port, String title, String type) {
        this.id = id;
        this.ipaddress = ip;
        this.port = port;
        this.title = title;
        this.type = type;
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

    // Add a GET request to node here?
    public int getRequest() {
        if (type == "airtemp") {
            // send get request IPaddress:port/temperature
        }

        return -1;
    }
}