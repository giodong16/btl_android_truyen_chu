package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.TheLoai;
import Model.Truyen;

public class TheLoaiDAO {
    private SQLiteDatabase database;
    private final String DATABASE_NAME = "quanlitruyen.db";
    private final Context context;

    public TheLoaiDAO(Context context) {
        this.context = context;
    }

    public List<TheLoai> getTheLoaiDAO(String sql, String[] selectionArgs) {
        List<TheLoai> list = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        int maTheLoaiIndex = cursor.getColumnIndex("MaTheLoai");
        int tenTheLoaiIndex = cursor.getColumnIndex("TenTheLoai");
        while (cursor.moveToNext()) {
            TheLoai theLoai = new TheLoai();
            theLoai.setMaTheLoai(cursor.getString(maTheLoaiIndex));
            theLoai.setTenTheLoai(cursor.getString(tenTheLoaiIndex));

            list.add(theLoai);
        }

        cursor.close();
        return list;
    }
    public String getMaTheLoaiDAO(String sql, String[] selectionArgs) {
       String maTheLoai= "";
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        int maTheLoaiIndex = cursor.getColumnIndex("MaTheLoai");

        while (cursor.moveToNext()) {
            maTheLoai=cursor.getString(maTheLoaiIndex);
        }

        cursor.close();
        return maTheLoai;
    }

    public List<TheLoai> getTheLoaiALL() {
        return getTheLoaiDAO("Select * from tTheLoai", null);
    }
    public List<TheLoai> getTheLoaibyTruyen(String matruyen) {
        return getTheLoaiDAO("Select tTheLoai.MaTheLoai,tTheLoai.TenTheLoai from tTheLoai join tTruyen_TheLoai on tTheLoai.MaTheLoai= tTruyen_TheLoai.MaTheLoai where MaTruyen = ?", new String[]{matruyen});
    }

    public boolean create(TheLoai theLoai) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("MaTheLoai", theLoai.getMaTheLoai());
        values.put("TenTheLoai", theLoai.getTenTheLoai());
        long row = database.insert("tTheLoai", null, values);
        return row != -1;
    }

    public boolean update(TheLoai theLoai) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("TenTheLoai", theLoai.getTenTheLoai());
        int rows = database.update("tTheLoai", values, "MaTheLoai=?", new String[]{theLoai.getMaTheLoai()});
        return rows > 0;
    }

    public boolean delete(String maTheLoai) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rows = database.delete("tTheLoai", "MaTheLoai=?", new String[]{maTheLoai});
        return rows > 0;
    }
}