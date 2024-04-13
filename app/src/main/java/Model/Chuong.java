package Model;

import android.util.Log;

public class Chuong {
    private String maChuong;
    private String tenChuong;
    private int soChuong;
    private String noiDung;
    private String maTruyen;
    public Chuong(){
    }
    public Chuong(String maChuong, String tenChuong, int soChuong, String noiDung) {
        this.maChuong = maChuong;
        this.tenChuong = tenChuong;
        this.soChuong = soChuong;
        this.noiDung = noiDung;
        maTruyen=null;
    }
    public Chuong(String maChuong, String tenChuong, int soChuong, String noiDung,String maTruyen) {
        this.maChuong = maChuong;
        this.tenChuong = tenChuong;
        this.soChuong = soChuong;
        this.noiDung = noiDung;
        this.maTruyen=maTruyen;
    }

    public String getMaTruyen() {
        return maTruyen;
    }

    public void setMaTruyen(String maTruyen) {
        this.maTruyen = maTruyen;
    }

    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public int getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(int soChuong) {
        this.soChuong = soChuong;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

//    public Truyen getTruyen() {
//        return truyen;
//    }
//
//    public void setTruyen(Truyen truyen) {
//        this.truyen = truyen;
//    }
}
