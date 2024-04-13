package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import Model.TaiKhoan;

public class TaiKhoanDAO {
    private SQLiteDatabase database;
    private final String DATABASE_NAME = "quanlitruyen.db";
    private final Context context;

    public TaiKhoanDAO(Context context) {
        this.context = context;
    }
    public List<TaiKhoan> getAllTaiKhoan(String sql, String[] selectionArgs) {
        List<TaiKhoan> list = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
                TaiKhoan taiKhoan = new TaiKhoan(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                list.add(taiKhoan);
        }
        cursor.close();
        return list;
    }
    public TaiKhoan isTaiKhoanByUserNameAndPassword(String username, String password) {
        TaiKhoan taiKhoan=new TaiKhoan();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM tTaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?", new String[]{username, password});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                 taiKhoan = new TaiKhoan(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            }
            cursor.close();
            return taiKhoan;

        }
        return null;
    }
    public TaiKhoan getTaiKhoanByID(int id) {
        TaiKhoan taiKhoan=new TaiKhoan();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM tTaiKhoan WHERE id = ?", new String[]{id+""});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                taiKhoan = new TaiKhoan(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            }
            cursor.close();
            return taiKhoan;

        }
        return null;
    }
    public boolean createTaiKhoan(TaiKhoan taiKhoan) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
//        values.put("ID", taiKhoan.getId());
        values.put("TenTaiKhoan", taiKhoan.getTenTaiKhoan());
        values.put("TenDangNhap",taiKhoan.getTenDangNhap());
        values.put("MatKhau",taiKhoan.getMatKhau());
        values.put("Anh",taiKhoan.getAnh());
        long row = database.insert("tTaiKhoan", null, values);
        return row != -1;
    }

    public boolean updateTaiKhoan(TaiKhoan taiKhoan) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("TenTaiKhoan", taiKhoan.getTenTaiKhoan());
        values.put("TenDangNhap",taiKhoan.getTenDangNhap());
        values.put("MatKhau",taiKhoan.getMatKhau());
        values.put("Anh",taiKhoan.getAnh());
        int row = database.update("tTaiKhoan",  values,"ID = ?",new String[]{taiKhoan.getId()+""});
        return row >0;
    }
}
