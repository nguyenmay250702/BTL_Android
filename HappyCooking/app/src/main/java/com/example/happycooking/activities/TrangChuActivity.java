package com.example.happycooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class TrangChuActivity  extends AppCompatActivity {
    GridView GV;
    ImageButton btn_BaiViet, btn_nguoiDung,btn_xem, btn_them, btn_sua, btn_xoa;
    BaiVietAdapter adapter;
    ArrayList<BaiViet> bvArrayList;

    Intent intent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init(){
        BaiVietDAO BVDAO = new BaiVietDAO(this);
        adapter = new BaiVietAdapter(this,0,BVDAO.getAll());
    }

    private void anhXa(){
        GV = findViewById(R.id.GridView);
        btn_BaiViet = findViewById(R.id.btn_BaiViet);
        btn_nguoiDung = findViewById(R.id.btn_nguoiDung);
        btn_xem = findViewById(R.id.btn_xem);
        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);
        btn_xoa = findViewById(R.id.btn_xoa);
    }

    private void setUp(){
        GV.setAdapter(adapter);
    }
    private void setClick(){
        //Trang người dùng
        btn_nguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TrangChuActivity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });
        //Xem bài viết
        btn_xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(TrangChuActivity.this, ThemBVActivity.class);
                intent.putExtra("key_action", "XEM");
                startActivity(intent);
            }
        });
        //Thêm bài viết
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TrangChuActivity.this, ThemBVActivity.class);
                intent.putExtra("key_action", "THEM");
                startActivity(intent);
            }
        });

    }
}
