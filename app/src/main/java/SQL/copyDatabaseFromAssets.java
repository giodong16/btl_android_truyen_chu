package SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class copyDatabaseFromAssets {
    String DB_PATH = "/databases/";
    SQLiteDatabase database=null;

    String DATABASE_NAME="quanlitruyen.db";
    private Context context;

    public copyDatabaseFromAssets(Context context) {
        this.context = context;
        copyCSDLfromAssets();
    }
    private void copyCSDLfromAssets() {
        File dbFile=context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            copySQL();
        }
//        else{
//            dbFile.delete();
//            copySQL();
//        }
    }

    private void copySQL() {
        try {
            InputStream myInput=context.getAssets().open(DATABASE_NAME);
            String outFileName=context.getApplicationInfo().dataDir+DB_PATH+DATABASE_NAME;
            File f=new File(context.getApplicationInfo().dataDir+DB_PATH);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream myOutput=new FileOutputStream(outFileName);
            byte[] buffer=new byte[1024];
            int len;
            while ((len=myInput.read(buffer))>0){
                myOutput.write(buffer,0,len);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();

        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("Lỗi sao chép",e.toString());
        }
    }
}
