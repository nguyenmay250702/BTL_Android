package com.example.happycooking.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.happycooking.R;

public class database extends SQLiteOpenHelper {
    //Tên database
    private static String DATABASE_NAME = "HAPPY_COOKING";

    //Bảng người dùng
    public static String TB_NGUOIDUNG = "NGUOI_DUNG";
    public static String TB_BAIVIET= "BAI_VIET";
    private Context context=null;

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //bảng người dùng
        String create_tb_ND = " CREATE TABLE " + TB_NGUOIDUNG
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, email varchar(225) not null,  pass nvarchar(225) not null, linkIMG text)";
        String insert_tb_ND = "INSERT INTO NGUOI_DUNG(email,pass, linkIMG) "
                + "VALUES ('nguyenmay308@gmail.com', '250702', '@drawable/may'), "
                + "('nguyenhongthuong2002@gmail.com', '123456', '@drawable/thuong')";

        //bảng bài viết
        String create_tb_BV = " CREATE TABLE " + TB_BAIVIET
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_ND INTEGER, TenBV NVARCHAR(50), MoTa text, TacGia NVARCHAR(50), LinkVD text)";

        String insert_tb_BV = "INSERT INTO BAI_VIET(ID, ID_ND, TenBV, MoTa, TacGia, LinkVD) "
                + " VALUES (0, 0, 'Dau xào', null, null, 'android.resource://" + context.getPackageName() + "/"+R.raw.dau_xao +"')"
                + ", (1, 0, 'Trứng chiên', null, null, 'android.resource://" + context.getPackageName() + "/"+R.raw.trung_chien +"')";

        //thực thi
        db.execSQL(create_tb_ND);
        db.execSQL(create_tb_BV);
        db.execSQL(insert_tb_ND);
        db.execSQL(insert_tb_BV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TB_NGUOIDUNG);
            db.execSQL("DROP TABLE IF EXISTS " + TB_BAIVIET);
            onCreate(db);
        }
    }

    //mở kết nối CSDL
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
