package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.Chuong;
import Model.TacGia;

public class ChuongDAO {
    private SQLiteDatabase database;
    final String DATABASE_NAME = "qltruyen.db";
    private Context context;

    public ChuongDAO(Context context) {
        this.context = context;
    }
    public List<Chuong> getChuong(String sql,String[] selectionArgs){
        List<Chuong> list=new ArrayList<>();
        database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);

        Cursor cursor=   database.rawQuery(sql,selectionArgs);
        int maChuong = cursor.getColumnIndex("MaChuong");
        int tenChuong = cursor.getColumnIndex("TenChuong");
        int soChuong = cursor.getColumnIndex("SoChuong");
        int noiDung = cursor.getColumnIndex("NoiDung");
        while (cursor.moveToNext()) {
            Chuong chuong;
            chuong = new Chuong();
            chuong.setMaChuong(cursor.getString(maChuong));
            chuong.setTenChuong(cursor.getString(tenChuong));
            chuong.setSoChuong(cursor.getInt(soChuong));
            chuong.setNoiDung(cursor.getString(noiDung));
            list.add(chuong);
        }

        cursor.close();
        return list;
    }
    public List<Chuong> getChuongByMaTruyen(String maTruyen){
        return getChuong("SELECT * FROM tChuong WHERE maTruyen = ? ORDER BY soChuong ASC",new String[]{maTruyen});

    }
    public int getSoLuongChuongByMaTruyen(String maTruyen){
        List<Chuong> list=new ArrayList<>();
        database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        int size=0;
        size=database.rawQuery("SELECT MaChuong FROM tChuong WHERE maTruyen = ? ORDER BY soChuong ASC",new String[]{maTruyen}).getCount();
        return size;
    }
    public boolean create(Chuong chuong){
        ContentValues contentValues=new ContentValues();
        contentValues.put("MaChuong",chuong.getMaChuong());
        contentValues.put("SoChuong",chuong.getSoChuong());
        contentValues.put("TenChuong",chuong.getTenChuong());
        contentValues.put("NoiDung",chuong.getNoiDung());
        long row=database.insert("tChuong",null,contentValues);
        return row>0;
    }
    public boolean update(Chuong chuong){
        ContentValues contentValues=new ContentValues();
        contentValues.put("SoChuong",chuong.getSoChuong());
        contentValues.put("TenChuong",chuong.getTenChuong());
        contentValues.put("NoiDung",chuong.getNoiDung());
        int row=database.update("tChuong",contentValues,"MaChuong=?",new String[]{chuong.getMaChuong()});
        return row>0;
    }
    public boolean delete(String maChuong){
        int row= database.delete("tChuong","MaChuong=?",new String[]{maChuong});
        return row>0;
    }
}
