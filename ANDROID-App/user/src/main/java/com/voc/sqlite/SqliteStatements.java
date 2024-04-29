package com.voc.sqlite;

public class SqliteStatements {

    public static final String TBL_DISTRICTS = "CREATE TABLE IF NOT EXISTS `districts` (" +
            "`d_rid` INTEGER," +
            "`d_name` TEXT" +
            ");";

    public static final String TBL_GRAMA = "CREATE TABLE IF NOT EXISTS `grama` (" +
            "`g_rid` INTEGER," +
            "`g_code` TEXT," +
            "`g_name` TEXT," +
            "`g_taluk_id` INTEGER" +
            ");";

    public static final String TBL_TALUK = "CREATE TABLE IF NOT EXISTS `taluk` (" +
            "`t_rid` INTEGER," +
            "`t_name` TEXT," +
            "`t_district_id` INTEGER" +
            ");";

    public static final String TBL_PANCHAYATH = "CREATE TABLE IF NOT EXISTS `panchayath` (" +
            " `pan_rid` INTEGER," +
            " `pan_grama_rid` INTEGER," +
            " `pan_code` TEXT," +
            " `pan_email` TEXT," +
            " `pan_president` TEXT," +
            " `pan_conatct` TEXT," +
            " `pan_tel_no` TEXT," +
            " `pan_description` TEXT" +
            ");";

    public static final String TRUNCATE_TBL_DISTRICTS = "DELETE FROM `districts`";
    public static final String TRUNCATE_TBL_GRAMA = "DELETE FROM `grama`";
    public static final String TRUNCATE_TBL_TALUK = "DELETE FROM `taluk`";
    public static final String TRUNCATE_TBL_PANCHAYATH = "DELETE FROM `panchayath`";

}
