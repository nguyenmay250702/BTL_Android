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
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.models.BaiViet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ThemBVActivity extends AppCompatActivity {
    Intent intent;
    ImageButton btn_camMeRa;
    Button btn_luu, btn_quayLai;
    VideoView videoView;
    EditText ET_TenBV, ET_MoTa, ET_TacGia;
    String key_action = null, VideoURI=null;

    BaiVietAdapter adapter = null;
    BaiViet BV=null;

    Context context = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.from_bv);

        init();
        anhXa();
        setUp();
        setClick();

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//            btn_camMeRa.setEnabled(true);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1111){
            // Lưu video vào bộ nhớ trong của ứng dụng
            this.VideoURI = saveVideoToInternalStorage(data.getData());
//            this.VideoURI = getRealPathFromUri(data.getData());
            videoView.setVideoURI(data.getData());
            videoView.start();
        }
    }
    private String saveVideoToInternalStorage(Uri videoUri) {
        String videoFileName = "video_" + System.currentTimeMillis() + ".mp4";
        String videoFolderPath = getFilesDir().getAbsolutePath() + "/videos";
        File videoFolder = new File(videoFolderPath);
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
        File videoFile = new File(videoFolderPath, videoFileName);
        try (InputStream in = getContentResolver().openInputStream(videoUri);
             OutputStream out = new FileOutputStream(videoFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return videoFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        videoView = findViewById(R.id.VideoView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String key = extras.getString("key_action");
            key_action = key;

            BaiVietDAO BVDAO = new BaiVietDAO(ThemBVActivity.this);
            BV = BVDAO.getByID(extras.getInt("key_ID"));
        }
    }

    private void setUp(){
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        if(key_action.equals("XEM")){
            btn_luu.setVisibility(View.GONE);
            btn_camMeRa.setVisibility(View.GONE);
            ET_TenBV.setEnabled(false);
            ET_MoTa.setEnabled(false);
            ET_TacGia.setEnabled(false);


            //Thiết lập dữ liệu hiển thị lên các control tương ứng
            videoView.setVideoURI(Uri.parse(BV.getLinkVD()));
            videoView.start();
            ET_TenBV.setText(BV.getTenBV());
            ET_TacGia.setText(BV.getTacGia());
            ET_MoTa.setText(BV.getMoTa());
        }
        else if(key_action.equals("THEM")){
//            btn_camMeRa.setEnabled(false);
//            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},111);
//            }
//            else btn_camMeRa.setEnabled(true);

            //

        }else if(key_action.equals("SUA")){

        }

    }

    private void setClick(){
        //nút quay lại trang chủ
        btn_quayLai.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(ThemBVActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });

        //nút quay camera
        btn_camMeRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(i,1111);
            }
        });

        //lưu dữ liệu
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra dữ liệu đầu vào
                if(TextUtils.isEmpty(ET_TenBV.getText().toString().trim())) {
                    ET_TenBV.requestFocus();
                    Toast.makeText(ThemBVActivity.this, "'Tên Bài Viết' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ET_MoTa.getText().toString().trim())) {
                    ET_MoTa.requestFocus();
                    Toast.makeText(ThemBVActivity.this, "'Mô Tả' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ET_TacGia.getText().toString().trim())) {
                    ET_TacGia.requestFocus();
                    Toast.makeText(ThemBVActivity.this, "'Tên Tác Giả' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(VideoURI == null) {
                    btn_camMeRa.requestFocus();
                    Toast.makeText(ThemBVActivity.this, "Chưa có VIDEO hướng dẫn nấu ăn!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(key_action.equals("THEM")){  //trạng thái Insert
                        BaiViet BV = new BaiViet(DangNhapActivity.ID_ND_now, ET_TenBV.getText().toString(), ET_MoTa.getText().toString(), ET_TacGia.getText().toString(), VideoURI);
                        BaiVietDAO BVDAO = new BaiVietDAO(ThemBVActivity.this);
                        if(BVDAO.InsertBV(BV)){
                            intent = new Intent(ThemBVActivity.this, TrangChuActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ThemBVActivity.this, "Thêm bài học thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (key_action.equals("SUA")) {  //trạng thái Update

                    }
                }
            }
        });

    }
}
