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
                + "('2051063623@e.tlu.edu.vn', '123456', '@drawable/thuong')";

        //bảng bài viết
        String create_tb_BV = " CREATE TABLE " + TB_BAIVIET
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_ND INTEGER, TenBV NVARCHAR(50), MoTa text, TacGia NVARCHAR(50), LinkVD text, isXem INTEGER)";

        String insert_tb_BV = "INSERT INTO BAI_VIET(ID, ID_ND, TenBV, MoTa, TacGia, LinkVD, isXem) "
                + " VALUES (0, 0, 'Dau xào', '1.  Sơ chế nguyên liệu\n" +
                "2. Chiên trứng.', null, 'android.resource://" + context.getPackageName() + "/"+R.raw.dau_xao +"', 0)"
                + ", (1, 0, 'Trứng chiên', '1. Bắc chảo nóng, cho dầu ăn và phi thơm tỏi cùng gừng. ...\n" +
                "2. Tiếp tục phi thơm tỏi, cho súp lơ, ớt chuông, cà rốt vào xào trước.\n" +
                "3. Sau đó cho dứa, cà chua vào đảo cùng, nêm nếm thêm một chút nước mắm, bột ngọt.\n" +
                "4. Đến khi rau củ chín tới thì cho thịt bò vào đảo nhanh trong khoảng 1 phút. ...\n" +
                "5. Cuối cùng cho rau mùi vào.', null, 'android.resource://" + context.getPackageName() + "/"+R.raw.trung_chien +"', 0)";

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
