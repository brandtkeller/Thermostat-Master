package thermo.models;

public class Node {
    private int id;
    private String ipaddress;
    private String title;
    private String type;

    public Node(int id, String ip, String type) {
        this.id = id;
        this.ipaddress = ip;
        this.title = null;
        this.type = type;
    }

    public Node(int id, String ip, String title, String type) {
        this.id = id;
        this.ipaddress = ip;
        this.title = title;
        this.type = type;
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

}