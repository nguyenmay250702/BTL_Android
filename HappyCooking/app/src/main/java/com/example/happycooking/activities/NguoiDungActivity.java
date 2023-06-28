package com.example.happycooking.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.DAO.NguoiDungDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.adapters.NguoiDungAdapter;

public class NguoiDungActivity extends AppCompatActivity {
    GridView GV;
    ImageButton btn_BaiViet, btn_nguoiDung, btn_xem, btn_them, btn_sua, btn_xoa;
    Intent intent;
    NguoiDungAdapter adapter ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoi_dung);

        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init(){
        NguoiDungDAO NDDAO = new NguoiDungDAO(this);
        adapter = new NguoiDungAdapter(this,0, NDDAO.getAll());
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

    private void setUp() {
        GV.setAdapter(adapter);
    }
    private void setClick(){
        //Trang chủ
        btn_BaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NguoiDungActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });

        //Xem bài viết
        btn_xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.ID_ND_CT != -1){
                    intent = new Intent(NguoiDungActivity.this, ThemNDActivity.class);
                    intent.putExtra("key_action", "XEM");
                    intent.putExtra("key_ID", adapter.ID_ND_CT);
                    startActivity(intent);
                }else{
                    Toast.makeText(NguoiDungActivity.this, "Bạn chưa chọn tài khoản để xem!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Xóa BV
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.ID_ND_CT != -1){
                    // Hiển thị hộp thoại xác nhận xóa
                    AlertDialog.Builder builder = new AlertDialog.Builder(NguoiDungActivity.this);
                    builder.setMessage("Bạn có muốn xóa tài khoản này không?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Có",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Thực hiện xóa bản ghi
                                    NguoiDungDAO NDDAO = new NguoiDungDAO(NguoiDungActivity.this);
                                    if(NDDAO.DeleteND(adapter.ID_ND_CT)){
                                        //load lại trang
                                        recreate();

                                        Toast.makeText(NguoiDungActivity.this, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(NguoiDungActivity.this, "Xóa tài khoản thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    builder.setNegativeButton(
                            "Không",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Không thực hiện xóa bản ghi
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    Toast.makeText(NguoiDungActivity.this, "Bạn chưa chọn tài khoản để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
