package com.example.happycooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;

public class ThemBVActivity extends AppCompatActivity {
    Intent intent;
    ImageButton btn_camMeRa;
    Button btn_luu, btn_quayLai;

    EditText ET_TenBV, ET_MoTa, ET_TacGia;
    String key_action = null;

    BaiVietAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.from_bv);

//        Toast.makeText(ThemBVActivity.this, "Đây là trang Thêm", Toast.LENGTH_SHORT).show();

        init();
        anhXa();
        setUp();
        setClick();
    }
    private void init(){

    }

    private void anhXa(){
        btn_camMeRa = findViewById(R.id.btn_camMeRa);
        btn_luu = findViewById(R.id.btn_luu);
        btn_quayLai = findViewById(R.id.btn_quayLai);
        ET_TenBV = findViewById(R.id.ET_TenBV);
        ET_MoTa = findViewById(R.id.ET_MoTa);
        ET_TacGia = findViewById(R.id.ET_TacGia);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String key = extras.getString("key_action");
            key_action = key;
        }
    }

    private void setUp(){
        if(key_action.equals("XEM")){
            btn_luu.setVisibility(View.GONE);
            btn_camMeRa.setVisibility(View.GONE);
            ET_TenBV.setEnabled(false);
            ET_MoTa.setEnabled(false);
            ET_TacGia.setEnabled(false);
//            setContentView(R.layout.from_bv);
        }

    }
    private void setClick(){
        btn_quayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ThemBVActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });
    }
}
