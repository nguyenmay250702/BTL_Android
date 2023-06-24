package com.example.happycooking.models;

public class BaiViet {
    private int ID, ID_ND;
    private String TenBV, MoTa, TacGia ,LinkVD;
    public BaiViet(){}
    public BaiViet(int MaND, String tenBV, String moTa, String tacGia, String linkVD) {
        ID_ND = MaND;
        TenBV = tenBV;
        MoTa = moTa;
        TacGia = tacGia;
        LinkVD = linkVD;
    }
    public int getID() {
        return ID;
    }

    public void setID(int MaBV) {
        ID = MaBV;
    }

    public int getID_ND() {
        return ID_ND;
    }

    public void setID_ND(int MaND) {
        ID_ND = MaND;
    }

    public String getTenBV() {
        return TenBV;
    }

    public void setTenBV(String tenBV) {
        TenBV = tenBV;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public String getLinkVD() {
        return LinkVD;
    }

    public void setLinkVD(String linkVD) {
        LinkVD = linkVD;
    }
}
