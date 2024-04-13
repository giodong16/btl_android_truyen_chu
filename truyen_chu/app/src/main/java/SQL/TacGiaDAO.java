package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.TacGia;
import Model.TheLoai;
import Model.Truyen;

public class TacGiaDAO {
    private SQLiteDatabase database;
    private String DATABASE_NAME = "qltruyen.db";
    private Context context;

    public TacGiaDAO(Context context) {
        this.context = context;
    }

    public List<TacGia> getTacGia(String sql, String[] selectionArgs) {
        List<TacGia> list = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        int maTacGia = cursor.getColumnIndex("MaTacGia");
        int tenTacGia = cursor.getColumnIndex("TenTacGia");
        while (cursor.moveToNext()) {
            TacGia tacGia = new TacGia();
            tacGia.setMaTacGia(cursor.getString(maTacGia));
            tacGia.setTenTacGia(cursor.getString(tenTacGia));

            list.add(tacGia);
        }

        cursor.close();
        return list;
    }

    public List<TacGia> getTacGiaALL() {
        return getTacGia("Select * from tTacGia", null);
    }
    public TacGia getTacGiaByMaTacGia(String maTacGia) {
        return getTacGia("Select * from tTacGia where MaTacGia=?", new String[]{maTacGia}).get(0);
    }

    public boolean create(TacGia tacGia) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("MaTacGia", tacGia.getMaTacGia());
        values.put("TenTacGia", tacGia.getTenTacGia());
        long row = database.insert("tTacGia", null, values);
        return row != -1;
    }

    public boolean update(TacGia tacGia) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("TenTacGia", tacGia.getTenTacGia());
        int row = database.update("tTacGia",  values,"MaTacGia = ?",new String[]{tacGia.getMaTacGia()});
        return row >0;
    }

    public boolean delete(String maTacGia) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rows = database.delete("tTacGia", "MaTacGia=?", new String[]{maTacGia});
        return rows > 0;
    }
}
