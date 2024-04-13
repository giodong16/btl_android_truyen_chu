package Model;

import java.util.ArrayList;
import java.util.List;

public class TheLoai {
    private String maTheLoai;
    private String tenTheLoai;
    private List<Truyen> truyenList;

    public TheLoai() {
    }

    public TheLoai(String maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.truyenList = new ArrayList<>();
    }

    public TheLoai(String maTheLoai, String tenTheLoai, List<Truyen> truyenList) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.truyenList = truyenList;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public List<Truyen> getTruyenList() {
        return truyenList;
    }

    public void setTruyenList(List<Truyen> truyenList) {
        this.truyenList = truyenList;
    }
}