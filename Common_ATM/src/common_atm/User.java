package common_atm;

import java.io.Serializable;


public class User implements Serializable{
    private String name;
    private String address;
    private String phone;
    private String cmnd;
    private int id_parent;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String address, String phone, String cmnd, int id_parent) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cmnd = cmnd;
        this.id_parent = id_parent;
    }

    public User(String name, String address, String phone, String cmnd, int id_parent, int id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cmnd = cmnd;
        this.id_parent = id_parent;
        this.id = id;
    }

    public User(String name, String address, String phone, String cmnd) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cmnd = cmnd;
    }
    
    
}
