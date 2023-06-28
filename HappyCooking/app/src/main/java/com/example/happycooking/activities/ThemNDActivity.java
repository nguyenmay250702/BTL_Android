package com.example.happycooking.activities;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.DAO.NguoiDungDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.adapters.NguoiDungAdapter;
import com.example.happycooking.models.BaiViet;
import com.example.happycooking.models.NguoiDung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ThemNDActivity extends AppCompatActivity {
    Intent intent;
    ImageButton btn_camMeRa;
    Button btn_luu, btn_quayLai;
    ImageView IMGView;
    EditText ET_Email, ET_Pass;
    String key_action = null, imgURI=null;

    NguoiDungAdapter adapter = null;
    NguoiDung ND = null;
    int ID_ND;

    Context context = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.from_nd);

        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init(){}

    private void anhXa(){
        btn_camMeRa = findViewById(R.id.btn_camMeRa);
        btn_luu = findViewById(R.id.btn_luu);
        btn_quayLai = findViewById(R.id.btn_quayLai);
        ET_Email = findViewById(R.id.ET_Email);
        ET_Pass = findViewById(R.id.ET_Pass);
        IMGView = findViewById(R.id.IMGView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key_action = extras.getString("key_action");
            ID_ND = extras.getInt("key_ID");

            NguoiDungDAO NDDAO = new NguoiDungDAO(ThemNDActivity.this);
            ND = NDDAO.getByID(ID_ND);
        }
    }

    private void setUp(){
        if(key_action.equals("XEM")){
            btn_luu.setVisibility(View.GONE);
            btn_camMeRa.setVisibility(View.GONE);
            ET_Email.setEnabled(false);
            ET_Pass.setEnabled(false);

            //Thiết lập dữ liệu hiển thị lên các control tương ứng
//            videoView.setVideoURI(Uri.parse(BV.getLinkVD()));
            context = getApplicationContext();
            int imageResId = context.getResources().getIdentifier(ND.getLinkIMG(), null, context.getPackageName());
            if(imageResId !=0) {
                Glide.with(context)
                        .load(imageResId)
                        .circleCrop()
                        .into(IMGView);
            }
            else{
                Glide.with(context)
                        .load(new File(ND.getLinkIMG()))
                        .circleCrop()
                        .into(IMGView);
            }

            ET_Email.setText(ND.getEmail());
            ET_Pass.setText(ND.getPass());
        }
        else if(key_action.equals("THEM")){

        }else if(key_action.equals("SUA")){
//            VideoURI = BV.getLinkVD();
//
//            //Thiết lập dữ liệu hiển thị lên các control tương ứng
//            videoView.setVideoURI(Uri.parse(BV.getLinkVD()));
//            videoView.start();
//            ET_TenBV.setText(BV.getTenBV());
//            ET_TacGia.setText(BV.getTacGia());
//            ET_MoTa.setText(BV.getMoTa());
        }
    }

    private void setClick(){
        //nút quay lại trang chủ
        btn_quayLai.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(ThemNDActivity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });

        //nút quay camera
        btn_camMeRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //lưu dữ liệu
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra dữ liệu đầu vào
                if(TextUtils.isEmpty(ET_Email.getText().toString().trim())) {
                    ET_Email.requestFocus();
                    Toast.makeText(ThemNDActivity.this, "'Tên Bài Viết' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ET_Pass.getText().toString().trim())) {
                    ET_Pass.requestFocus();
                    Toast.makeText(ThemNDActivity.this, "'Mô Tả' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(imgURI == null) {
                    btn_camMeRa.requestFocus();
                    Toast.makeText(ThemNDActivity.this, "Chưa có VIDEO hướng dẫn nấu ăn!", Toast.LENGTH_SHORT).show();
                }
                else{
//                    if(key_action.equals("THEM")){  //trạng thái Insert
//                        BaiViet BV = new BaiViet(DangNhapActivity.ID_ND_now, ET_TenBV.getText().toString(), ET_MoTa.getText().toString(), ET_TacGia.getText().toString(), VideoURI);
//                        BaiVietDAO BVDAO = new BaiVietDAO(ThemBVActivity.this);
//                        if(BVDAO.InsertBV(BV)){
//                            intent = new Intent(ThemBVActivity.this, TrangChuActivity.class);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(ThemBVActivity.this, "Thêm bài viết thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (key_action.equals("SUA")) {  //trạng thái Update
//                        BaiViet BV = new BaiViet(DangNhapActivity.ID_ND_now, ET_TenBV.getText().toString(), ET_MoTa.getText().toString(), ET_TacGia.getText().toString(), VideoURI);
//                        BaiVietDAO BVDAO = new BaiVietDAO(ThemBVActivity.this);
//                        if(BVDAO.UpdateBV(BV,ID_BV)){
//                            intent = new Intent(ThemBVActivity.this, TrangChuActivity.class);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(ThemBVActivity.this, "Sửa bài viết thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }
            }
        });
    }
}

