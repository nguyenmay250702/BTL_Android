package com.example.happycooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;

public class NguoiDungActivity extends AppCompatActivity {
    ImageButton btn_BaiViet, btn_nguoiDung,btn_xem, btn_them, btn_sua, btn_xoa;
    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoi_dung);

        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init(){}

    private void anhXa(){

        btn_BaiViet = findViewById(R.id.btn_BaiViet);
        btn_nguoiDung = findViewById(R.id.btn_nguoiDung);
        btn_xem = findViewById(R.id.btn_xem);
        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);
        btn_xoa = findViewById(R.id.btn_xoa);
    }

    private void setUp() {}
    private void setClick(){
        //Trang chủ
        btn_BaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NguoiDungActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });

        //sử lý điều hướng nút xem, thêm, sửa, xóa

    }
}
