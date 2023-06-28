package com.example.happycooking.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.database.sqlite.SQLiteDatabaseKt;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.happycooking.database.database;
import com.example.happycooking.models.BaiViet;
import com.example.happycooking.models.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;
    private database database;
    private Context context;

    public NguoiDungDAO(Context context){
        this.context = context;
        this.database = new database(context);  //tạo database
        this.db = this.database.open(); //cho phép ghi dữ liệu vào database
    }

    //Lấy dữ liệu theo bản ID
    public NguoiDung getByID(int ID){
        Cursor cursor = db.query(database.TB_NGUOIDUNG, null, "id = ?", new String[]{String.valueOf(ID)},null,null,null);

        NguoiDung ND1 = new NguoiDung();
        if (cursor.moveToFirst()) {
            ND1.setID(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            ND1.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            ND1.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
            ND1.setLinkIMG(cursor.getString(cursor.getColumnIndexOrThrow("linkIMG")));
        }
        cursor.close();
        return ND1;
    }

    //Lấy dữ tất cả liệu
    public ArrayList<NguoiDung> getAll(){
        ArrayList<NguoiDung> List = new ArrayList<>();

        //tạo con trỏ đọc dữ liệu bảng người dùng
        Cursor cursor = db.query(database.TB_NGUOIDUNG, null, null,null,null,null,null);
        cursor.moveToFirst(); //di chuyển con trỏ từ bản ghi đầu tiên
        while (!cursor.isAfterLast()) // nếu chưa đến bản ghi cuối cùng thì thực hiện các cv tiếp theo
        {
            NguoiDung ND = new NguoiDung();
            ND.setID(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            ND.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            ND.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
            ND.setLinkIMG(cursor.getString(cursor.getColumnIndexOrThrow("linkIMG")));
            List.add(ND);
            cursor.moveToNext();
        }
        cursor.close();
        return List;
    }

    //thêm
    public boolean InsertND(NguoiDung ND){
        ContentValues contentValues = new ContentValues(); //tạo đối tượng chứa dữ liệu
        //đưa dữ liệu vào đối tượng chứa
        contentValues.put("email", ND.getEmail());
        contentValues.put("pass", ND.getPass());
        contentValues.put("linkIMG", ND.getLinkIMG());

        //thực thi insert
        long kq = db.insert(database.TB_NGUOIDUNG, null, contentValues);  // trả về số dòng được chèn thành công
        if(kq <= 0) return false;
        else return true;
    }

    //Sửa
    public boolean UpdateND(NguoiDung ND, int ID){
        ContentValues contentValues = new ContentValues(); //tạo đối tượng chứa dữ liệu
        //đưa dữ liệu vào đối tượng chứa
        contentValues.put("email", ND.getEmail());
        contentValues.put("pass", ND.getPass());
        contentValues.put("linkIMG", ND.getLinkIMG());

        //thực thi update
        long kq = db.update(database.TB_NGUOIDUNG, contentValues, "ID = ?", new String[]{String.valueOf(ID)});  // trả về số dòng được chèn thành công
        if(kq <= 0) return false;
        else return true;
    }

    //Xóa
    public boolean DeleteND(int ID){
        ContentValues contentValues = new ContentValues();
        long kq = db.delete(database.TB_NGUOIDUNG,"ID = ?", new String[]{String.valueOf(ID)});
        if(kq <= 0) return false;
        else return true;
    }
}
