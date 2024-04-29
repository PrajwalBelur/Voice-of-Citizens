package com.voc.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.voc.base.MyApplication;
import com.voc.model.District;
import com.voc.model.Grama;
import com.voc.model.Panchayath;
import com.voc.model.RegPageDetails;
import com.voc.model.Taluk;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtil {

    private static final String TAG = "SQLiteUtil";

    public static void saveRegPageDetails(Context context, RegPageDetails response) {

        try {
            SQLiteHelper dbInstance = MyApplication.getDBInstance();
            SQLiteDatabase writableDatabase = dbInstance.getWritableDatabase();

            writableDatabase.execSQL(SqliteStatements.TRUNCATE_TBL_DISTRICTS);
            writableDatabase.execSQL(SqliteStatements.TRUNCATE_TBL_TALUK);
            writableDatabase.execSQL(SqliteStatements.TRUNCATE_TBL_GRAMA);
            writableDatabase.execSQL(SqliteStatements.TRUNCATE_TBL_PANCHAYATH);

            String insertDistricts = "INSERT INTO districts(d_rid, d_name) VALUES";

            List<District> districtsList = response.getDistrict();

            for (int i = 0; i < districtsList.size(); i++) {
                if (i > 0) {
                    insertDistricts += ", ";
                }
                insertDistricts += "('" + districtsList.get(i).getRid() + "', '" + districtsList.get(i).getName() + "')";
            }

            if (!districtsList.isEmpty()) {
                writableDatabase.execSQL(insertDistricts);
            }

            String insertTaluk = "INSERT INTO taluk(t_rid, t_name, t_district_id) VALUES";

            List<Taluk> talukList = response.getTaluk();

            for (int i = 0; i < talukList.size(); i++) {
                if (i > 0) {
                    insertTaluk += ", ";
                }

                insertTaluk += "('" + talukList.get(i).getRid() + "', '" + talukList.get(i).getName() + "', '" + talukList.get(i).getDistrictId() + "')";
            }

            if (!talukList.isEmpty()) {
                writableDatabase.execSQL(insertTaluk);
            }

            List<Panchayath> panchayathList = response.getPanchayath();

            String insertPanchayath = "INSERT INTO panchayath(pan_rid, pan_grama_rid, " +
                    "pan_email, pan_president, pan_conatct, pan_tel_no, pan_description) VALUES";

            for (int i = 0; i < panchayathList.size(); i++) {
                if (i > 0) {
                    insertPanchayath += ", ";
                }

                insertPanchayath += "('" + panchayathList.get(i).getRid() + "'," +
                        " '" + panchayathList.get(i).getGramaRid() + "', '" + panchayathList.get(i).getEmail() + "'," +
                        " '" + panchayathList.get(i).getPresident() + "', '" + panchayathList.get(i).getContact() + "'," +
                        " '" + panchayathList.get(i).getTelePhone() + "', '" + panchayathList.get(i).getDescription() + "')";
            }

            if (!panchayathList.isEmpty()) {
                writableDatabase.execSQL(insertPanchayath);
            }

            List<Grama> gramaList = response.getGrama();

            String insertGrama = "INSERT INTO grama(g_rid, g_name, g_taluk_id) VALUES";

            for (int i = 0; i < gramaList.size(); i++) {
                if (i > 0) {
                    insertGrama += ", ";
                }

                insertGrama += "('" + gramaList.get(i).getRid() + "', '" + gramaList.get(i).getName() + "', '" + gramaList.get(i).getTalukId() + "')";
            }

            if (!gramaList.isEmpty()) {
                writableDatabase.execSQL(insertGrama);
            }

            writableDatabase.close();
        } catch (Exception ex) {
            Log.e(TAG, "saveRegPageDetails: ", ex);
            Toast.makeText(context, "SQLite Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static List<District> getDistrictList() {
        SQLiteHelper dbInstance = MyApplication.getDBInstance();
        SQLiteDatabase db = dbInstance.getReadableDatabase();

        String sql = "SELECT * FROM districts ORDER BY d_name ASC";

        Cursor cursor = db.rawQuery(sql, null);

        List<District> districtList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                districtList.add(new District(
                        cursor.getInt(cursor.getColumnIndex("d_rid")),
                        cursor.getString(cursor.getColumnIndex("d_name"))
                ));
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return districtList;
    }

    public static List<Taluk> getTalukList(int districtRid) {
        SQLiteHelper dbInstance = MyApplication.getDBInstance();
        SQLiteDatabase db = dbInstance.getReadableDatabase();

        String sql = "SELECT * FROM taluk" +
                " WHERE t_district_id = ?" +
                " ORDER BY t_name ASC";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(districtRid)});

        List<Taluk> talukList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                talukList.add(new Taluk(
                        cursor.getInt(cursor.getColumnIndex("t_rid")),
                        cursor.getString(cursor.getColumnIndex("t_name")),
                        cursor.getInt(cursor.getColumnIndex("t_district_id"))
                ));
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return talukList;
    }

    public static List<Panchayath> getPanchayathList(int talukRid) {
        SQLiteHelper dbInstance = MyApplication.getDBInstance();
        SQLiteDatabase db = dbInstance.getReadableDatabase();

        String sql = "SELECT * FROM panchayath" +
                " JOIN grama ON (pan_grama_rid = g_rid)" +
                " WHERE g_taluk_id = ?" +
                " ORDER BY g_name ASC";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(talukRid)});

        List<Panchayath> panchayathList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                panchayathList.add(new Panchayath(
                        cursor.getInt(cursor.getColumnIndex("pan_rid")),
                        cursor.getString(cursor.getColumnIndex("g_name")),
                        cursor.getInt(cursor.getColumnIndex("g_rid"))
                ));
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return panchayathList;
    }
}
