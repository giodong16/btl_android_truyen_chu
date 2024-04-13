package Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Truyen implements Parcelable {
    private String maTruyen;
    private String tenTruyen;
    private String gioiThieu;
    private int trangThai;
    private String anh;
    private int yeuThich;
    private String maTacGia;

    private List<Chuong> chuongList;
    private List<TheLoai> theLoaiList;

    public Truyen() {
    }

    public Truyen(String maTruyen, String tenTruyen, String gioiThieu, int trangThai, String anh, int yeuThich, String maTacGia) {
        this.maTruyen = maTruyen;
        this.tenTruyen = tenTruyen;
        this.gioiThieu = gioiThieu;
        this.trangThai = trangThai;
        this.anh = anh;
        this.yeuThich = yeuThich;
        this.maTacGia = maTacGia;
    }

    public Truyen(String maTruyen, String tenTruyen, String gioiThieu, int trangThai, String anh, int yeuThich, String maTacGia, List<Chuong> chuongList, List<TheLoai> theLoaiList) {
        this.maTruyen = maTruyen;
        this.tenTruyen = tenTruyen;
        this.gioiThieu = gioiThieu;
        this.trangThai = trangThai;
        this.anh = anh;
        this.yeuThich = yeuThich;
        this.maTacGia = maTacGia;
        this.chuongList = chuongList;
        this.theLoaiList = theLoaiList;
    }

    protected Truyen(Parcel in) {
        maTruyen = in.readString();
        tenTruyen = in.readString();
        gioiThieu = in.readString();
        trangThai = in.readInt();
        anh = in.readString();
        yeuThich = in.readInt();
        maTacGia = in.readString();
        chuongList = new ArrayList<>();
        in.readList(chuongList, Chuong.class.getClassLoader());
        theLoaiList = new ArrayList<>();
        in.readList(theLoaiList, TheLoai.class.getClassLoader());
    }

    public static final Creator<Truyen> CREATOR = new Creator<Truyen>() {
        @Override
        public Truyen createFromParcel(Parcel in) {
            return new Truyen(in);
        }

        @Override
        public Truyen[] newArray(int size) {
            return new Truyen[size];
        }
    };

    public String getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getMaTruyen() {
        return maTruyen;
    }

    public void setMaTruyen(String maTruyen) {
        this.maTruyen = maTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(int yeuThich) {
        this.yeuThich = yeuThich;
    }

    public List<Chuong> getChuongList() {
        return chuongList;
    }

    public void setChuongList(List<Chuong> chuongList) {
        this.chuongList = chuongList;
    }

    public List<TheLoai> getTheLoaiList() {
        return theLoaiList;
    }

    public void setTheLoaiList(List<TheLoai> theLoaiList) {
        this.theLoaiList = theLoaiList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

   @Override
   public void writeToParcel(Parcel dest, int flags){
       dest.writeString(maTruyen);
       dest.writeString(tenTruyen);
       dest.writeString(gioiThieu);
       dest.writeInt(trangThai);
       dest.writeString(anh);
       dest.writeInt(yeuThich);
       dest.writeString(maTacGia);
       dest.writeList(chuongList);
       dest.writeList(theLoaiList);

   }
}