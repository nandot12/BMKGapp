package id.co.imastudio.bmkgapp22W.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import id.co.imastudio.bmkgapp22W.Model.Iklim;

/**
 * Created by nandoseptianhusni on 10/21/17.
 */

public class IklimOperations {

    public static final String LOGTAG = "EMP_MNG_SYS";

    SQLiteOpenHelper dbhelper ;
    SQLiteDatabase database ;


    private static final String[] allcolumn = {
           IklimdbHandler.COLUMN_ID,
            IklimdbHandler.COLUMN_NAME,
            IklimdbHandler.COLUMN_KRITERIA,
            IklimdbHandler.COLUMN_HTH,
            IklimdbHandler.COLUMN_HTD,
            IklimdbHandler.COLUMN_PROV,
            IklimdbHandler.COLUMN_LAT,
            IklimdbHandler.COLUMN_LON,
            IklimdbHandler.COLUMN_ID_PROV


    };

    public IklimOperations(Context c) {
        dbhelper = new IklimdbHandler(c);

    }


    public void open(){
        database = dbhelper.getWritableDatabase() ;



    }

    public void close(){
        dbhelper.close();
    }

    public Iklim addIklim(Iklim iklim){

       // dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IklimdbHandler.COLUMN_ID,iklim.getId());
        values.put(IklimdbHandler.COLUMN_PROV,iklim.getProv());
        values.put(IklimdbHandler.COLUMN_HTD,iklim.getHdt());
        values.put(IklimdbHandler.COLUMN_HTH,iklim.getHdh());
        values.put(IklimdbHandler.COLUMN_ID_PROV,iklim.getIdProv());
        values.put(IklimdbHandler.COLUMN_NAME,iklim.getNama());

       // Log.d("data nama :" , iklim.getNama());
        values.put(IklimdbHandler.COLUMN_LAT,iklim.getLat());
        values.put(IklimdbHandler.COLUMN_LON,iklim.getLng());
        values.put(IklimdbHandler.COLUMN_KRITERIA,iklim.getKriteria());

      long in =  database.insert(IklimdbHandler.TABLE_IKLIM,null,values);



        Log.d("id insert : " ,String.valueOf(in));


      //  dbhelper.close();

        return iklim ;

    }

    public ArrayList<Iklim> getAllIklim(){

        dbhelper.getReadableDatabase();

       // dbhelper.getWritableDatabase();
       // Cursor cursor = database.query(IklimdbHandler.TABLE_IKLIM,allcolumn,null,null,null,null,null);
        Cursor cursor = database.rawQuery("select * from hujan",null);
       ArrayList<Iklim> iklims = new ArrayList<>();

        if(cursor.getCount() > 0 ){
          //  dbhelper.close();
            while (cursor.moveToNext()){



                Iklim iklim = new Iklim();
                iklim.setId(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_ID)));
                iklim.setNama(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_NAME)));
                iklim.setHdh(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_HTH)));
                iklim.setHdt(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_HTD)));
                iklim.setProv(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_PROV)));
                iklim.setIdProv(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_ID_PROV)));
                iklim.setLat(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_LAT)));
                iklim.setLng(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_LON)));
                iklim.setKriteria(cursor.getString(cursor.getColumnIndex(IklimdbHandler.COLUMN_KRITERIA)));

                iklims.add(iklim);


            }
        }
        else{
            Log.d("data kosong :","kosong");
        }



        return iklims;
    }

    public void deleteAll()


    {
       // dbhelper.getWritableDatabase();
        //SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        database.execSQL("delete from hujan");
      //  dbhelper.close();
    }
}
