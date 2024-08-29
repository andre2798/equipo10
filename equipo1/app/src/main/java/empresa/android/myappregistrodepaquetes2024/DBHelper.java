package empresa.android.myappregistrodepaquetes2024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PaqueteDB.db";
    public static final String TABLE_NAME = "paquetes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "empresa";
    public static final String COL_3 = "descripcion";
    public static final String COL_4 = "piso";
    public static final String COL_5 = "fecha";
    public static final String COL_6 = "hora";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "empresa TEXT, descripcion TEXT, piso TEXT, fecha TEXT, hora TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String empresa, String descripcion, String piso, String fecha, String hora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, empresa);
        contentValues.put(COL_3, descripcion);
        contentValues.put(COL_4, piso);
        contentValues.put(COL_5, fecha);
        contentValues.put(COL_6, hora);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
