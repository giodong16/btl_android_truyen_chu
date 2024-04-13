package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.trang_chu.ListTruyenActivity;

import java.util.ArrayList;
import java.util.List;

import Model.Chuong;
import Model.TheLoai;
import Model.Truyen;

public class TruyenDAO {
    SQLiteDatabase database;
    final String DATABASE_NAME = "qltruyen.db";
    private final Context context;
    public TruyenDAO(Context context){
        this.context=context;
    }
    public TruyenDAO(Context context, SQLiteDatabase database){
        this.context=context;
        this.database=database;
    }
    public List<Truyen> getTruyen(String sql,String[] selectionArgs){
        List<Truyen> list=new ArrayList<>();
        SQLiteDatabase database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery(sql,selectionArgs);
        int maTruyen=cursor.getColumnIndex("MaTruyen");
        int tenTruyen=cursor.getColumnIndex("TenTruyen");
        int gioiThieu=cursor.getColumnIndex("GioiThieu");
        int trangThai=cursor.getColumnIndex("TrangThai");
        int anh=cursor.getColumnIndex("Anh");
        int yeuThich=cursor.getColumnIndex("YeuThich");
        int maTacGia=cursor.getColumnIndex("MaTacGia");
        while (cursor.moveToNext()){
            Truyen truyen=new Truyen();
            truyen.setMaTruyen(cursor.getString(maTruyen));
            truyen.setTenTruyen(cursor.getString(tenTruyen));
            truyen.setGioiThieu(cursor.getString(gioiThieu));
            truyen.setTrangThai(cursor.getInt(trangThai));
            truyen.setAnh(cursor.getString(anh));
            truyen.setYeuThich(cursor.getInt(yeuThich));
            truyen.setMaTacGia(cursor.getString(maTacGia));
            list.add(truyen);
        }
        cursor.close();

        for (Truyen truyen:list){
            List<Chuong>  listChuong=new ArrayList<>();
            List<TheLoai> theLoaiList=new ArrayList<>();

            //chương và thể loại
            listChuong=new ChuongDAO(context).getChuongByMaTruyen(truyen.getMaTruyen());
            theLoaiList=new TheLoaiDAO(context).getTheLoaibyTruyen(truyen.getMaTruyen());
            truyen.setChuongList(listChuong);
            truyen.setTheLoaiList(theLoaiList);
        }
        return list;
    }
    public List<Truyen> getTruyenForItemVertical(String sql,String[] selectionArgs){
        List<Truyen> list=new ArrayList<>();
        SQLiteDatabase database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery(sql,selectionArgs);

        int maTruyen=cursor.getColumnIndex("MaTruyen");
        int tenTruyen=cursor.getColumnIndex("TenTruyen");
        int anh=cursor.getColumnIndex("Anh");
        while (cursor.moveToNext()){
            Truyen truyen=new Truyen();
            truyen.setMaTruyen(cursor.getString(maTruyen));
            truyen.setTenTruyen(cursor.getString(tenTruyen));
            truyen.setAnh(cursor.getString(anh));
            list.add(truyen);
        }
        cursor.close();
        return list;
    }
    public List<Truyen> getTruyenForItemHorizonal(String sql,String[] selectionArgs){
        List<Truyen> list=new ArrayList<>();
        SQLiteDatabase database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery(sql,selectionArgs);
        int maTruyen=cursor.getColumnIndex("MaTruyen");
        int tenTruyen=cursor.getColumnIndex("TenTruyen");
        int anh=cursor.getColumnIndex("Anh");
        int maTacGia=cursor.getColumnIndex("MaTacGia");
        while (cursor.moveToNext()){
            Truyen truyen=new Truyen();
            truyen.setMaTruyen(cursor.getString(maTruyen));
            truyen.setTenTruyen(cursor.getString(tenTruyen));
            truyen.setAnh(cursor.getString(anh));
            truyen.setMaTacGia(cursor.getString(maTacGia));
            list.add(truyen);
        }
        cursor.close();

        for (Truyen truyen:list){
            List<TheLoai> theLoaiList=new ArrayList<>();

            // thể loại
            theLoaiList=new TheLoaiDAO(context).getTheLoaibyTruyen(truyen.getMaTruyen());
            truyen.setTheLoaiList(theLoaiList);
        }
        return list;
    }
    public List<Truyen> getTruyenAll(){
        return getTruyen("Select * from tTruyen",null);
    }
    public List<Truyen> getTruyenByTacGia(String maTacGia){
        return getTruyen("Select * from tTruyen join tTacGia on tTruyen.MaTacGia=tTacGia.MaTacGia where tTacGia.MaTacGia=?",new String[]{maTacGia});
    }
    public List<Truyen> getTruyenByTheLoai(String maTheLoai){
        return getTruyen("Select * from tTruyen join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=?",new String[]{maTheLoai});
    }
    public Truyen getTruyenbyMaTruyen(String maTruyen){
        return getTruyen("Select *from tTruyen where MaTruyen=?",new String[]{maTruyen}).get(0);
    }
    public boolean create(Truyen truyen) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("MaTruyen", truyen.getMaTruyen());
        values.put("TenTruyen",truyen.getTenTruyen());
        values.put("GioiThieu",truyen.getGioiThieu());
        values.put("TrangThai",truyen.getTrangThai());
        values.put("Anh",truyen.getAnh());
        values.put("YeuThich",truyen.getYeuThich());
        values.put("MaTacGia",truyen.getMaTacGia());
        long row = database.insert("tTruyen", null, values);
        return row != -1;
    }

//    public boolean update(Truyen truyen) {
//        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
//        ContentValues values = new ContentValues();
//        values.put("TenTruyen",truyen.getTenTruyen());
//        values.put("GioiThieu",truyen.getGioiThieu());
//        values.put("TrangThai",truyen.getTrangThai());
//        values.put("Anh",truyen.getAnh());
//        values.put("YeuThich",truyen.getYeuThich());
//        int rows = database.update("tTruyen", values, "MaTruyen=?", new String[]{truyen.getMaTruyen()});
//        return rows > 0;
//    }
public boolean update(Truyen truyen) {
    SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    database.beginTransaction(); // Bắt đầu transaction

    try {
        ContentValues values = new ContentValues();
        values.put("TenTruyen", truyen.getTenTruyen());
        values.put("GioiThieu", truyen.getGioiThieu());
        values.put("TrangThai", truyen.getTrangThai());
        values.put("Anh", truyen.getAnh());
        values.put("YeuThich", truyen.getYeuThich());
        values.put("MaTacGia",truyen.getMaTacGia());
        int rows = database.update("tTruyen", values, "MaTruyen=?", new String[]{truyen.getMaTruyen()});

        database.setTransactionSuccessful(); // Đánh dấu transaction thành công
        return rows > 0;
    } finally {
        database.endTransaction(); // Kết thúc transaction
    }
}

    public boolean delete(String maTruyen) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rows = database.delete("tTruyen", "MaTruyen=?", new String[]{maTruyen});
        return rows > 0;
    }
}
