package id.co.imastudio.bmkgapp22W.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nandoseptianhusni on 10/21/17.
 */

public class IklimdbHandler extends SQLiteOpenHelper {

    private static final  String DATABASE_NAME = "iklim.db";
    private static final int DATABASE_VERSION = 3;
    public static final  String TABLE_IKLIM = "hujan";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "nama";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_HTD= "htd";
    public static final String COLUMN_HTH= "hth";
    public static final String COLUMN_KRITERIA= "kriteria";
    public static final String COLUMN_PROV= "provinsi";
    public static final String COLUMN_ID_PROV= "id_prov";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_IKLIM;

    private static final String TABLE_CREATE =
            "CREATE TABLE "+ TABLE_IKLIM + "(" +
                    COLUMN_ID + " TEXT," +
                    COLUMN_ID_PROV  + " TEXT," +
                    COLUMN_PROV + " TEXT," +
                    COLUMN_LAT + " TEXT," +
                    COLUMN_LON + " TEXT,"+
                    COLUMN_HTD + " TEXT," +
                    COLUMN_HTH + " TEXT," +
                    COLUMN_NAME + " TEXT,"+
                    COLUMN_KRITERIA + " TEXT" + ")";


    public IklimdbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
