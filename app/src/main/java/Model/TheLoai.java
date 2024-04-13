package Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TheLoai implements Parcelable {
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

    protected TheLoai(Parcel in) {
        maTheLoai = in.readString();
        tenTheLoai = in.readString();
        truyenList = in.createTypedArrayList(Truyen.CREATOR);
    }

    public static final Creator<TheLoai> CREATOR = new Creator<TheLoai>() {
        @Override
        public TheLoai createFromParcel(Parcel in) {
            return new TheLoai(in);
        }

        @Override
        public TheLoai[] newArray(int size) {
            return new TheLoai[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(maTheLoai);
        dest.writeString(tenTheLoai);
        dest.writeList(truyenList);
    }
}