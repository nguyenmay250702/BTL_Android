package com.example.happycooking.models;

public class NguoiDung {
    private int ID;
    private String email, pass, linkIMG;

    public NguoiDung(){};
    public NguoiDung(int ID, String email, String pass, String linkIMG) {
        this.ID = ID;
        this.email = email;
        this.pass = pass;
        this.linkIMG = linkIMG;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLinkIMG() {
        return linkIMG;
    }

    public void setLinkIMG(String linkIMG) {
        this.linkIMG = linkIMG;
    }
}
