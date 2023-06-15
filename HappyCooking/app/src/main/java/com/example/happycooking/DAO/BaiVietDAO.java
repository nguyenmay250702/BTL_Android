package com.example.happycooking.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.database.sqlite.SQLiteDatabaseKt;

import com.example.happycooking.database.database;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class BaiVietDAO {
    private SQLiteDatabase db;
    private database database;
    private Context context;

    public BaiVietDAO(Context context){
        this.context = context;
        this.database = new database(context);  //tạo database
        this.db = this.database.open(); //cho phép ghi dữ liệu vào database
    }

    //thêm dữ liệu
    public boolean InsertBV(BaiViet BV){
        ContentValues contentValues = new ContentValues(); //tạo đối tượng chứa dữ liệu
        //đưa dữ liệu vào đối tượng chứa
        contentValues.put("ID", String.valueOf(BV.getID()));
        contentValues.put("ID_ND", BV.getID_ND());
        contentValues.put("TenBV", BV.getTenBV());
        contentValues.put("MoTa", BV.getMoTa());
        contentValues.put("TacGia", BV.getTacGia());
        contentValues.put("LinkVD", BV.getLinkVD());

        //thực thi insert
        long kq = db.insert(database.TB_BAIVIET, null, contentValues);  // trả về số dòng được chèn thành công
        if(kq <= 0) return false;
        else return true;
    }

    public ArrayList<BaiViet> getAll(){
        ArrayList<BaiViet> List = new ArrayList<>();

        //tạo con trỏ đọc dữ liệu bảng bài viết
        Cursor cursor = db.query(database.TB_BAIVIET, null, null,null,null,null,null);
        cursor.moveToFirst(); //di chuyển con trỏ từ bản ghi đầu tiên
        while (!cursor.isAfterLast()) // nếu chưa đến bản ghi cuối cùng thì thực hiện các cv tiếp theo
        {
            BaiViet BV = new BaiViet();
            BV.setID(cursor.getInt(0));
            BV.setID_ND(cursor.getInt(1));
            BV.setTenBV(cursor.getString(2));
            BV.setMoTa(cursor.getString(3));
            BV.setTacGia(cursor.getString(4));
            BV.setLinkVD(cursor.getString(5));
            List.add(BV);
            cursor.moveToNext();
        }
        cursor.close();
        return List;
    }
}
