package com.example.happycooking.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    BaiVietAdapter adapter ;
    BaiViet BVct = null;
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
                if(adapter.ID_BV_CT != -1){
                    intent = new Intent(TrangChuActivity.this, ThemBVActivity.class);
                    intent.putExtra("key_action", "XEM");
                    intent.putExtra("key_ID", adapter.ID_BV_CT);
                    startActivity(intent);
                }else{
                    Toast.makeText(TrangChuActivity.this, "Bạn chưa chọn bài viết để xem!", Toast.LENGTH_SHORT).show();
                }
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

        //Sửa BV
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.ID_BV_CT != -1){
                    intent = new Intent(TrangChuActivity.this, ThemBVActivity.class);
                    intent.putExtra("key_action", "SUA");
                    intent.putExtra("key_ID", adapter.ID_BV_CT);
                    startActivity(intent);
                }else{
                    Toast.makeText(TrangChuActivity.this, "Bạn chưa chọn bài viết để sửa!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Xóa BV
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.ID_BV_CT != -1){
                    // Hiển thị hộp thoại xác nhận xóa
                    AlertDialog.Builder builder = new AlertDialog.Builder(TrangChuActivity.this);
                    builder.setMessage("Bạn có muốn xóa bài viết không?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Có",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Thực hiện xóa bản ghi
                                    BaiVietDAO BVDAO = new BaiVietDAO(TrangChuActivity.this);
                                    if(BVDAO.DeleteBV(adapter.ID_BV_CT)){
                                        //load lại trang
                                        recreate();

                                        Toast.makeText(TrangChuActivity.this, "Xóa bài viết thành công!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(TrangChuActivity.this, "Xóa bài viết thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TrangChuActivity.this, "Bạn chưa chọn bài viết để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
