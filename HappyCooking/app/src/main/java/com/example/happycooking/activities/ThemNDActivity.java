package com.example.happycooking.activities;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;

import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.NguoiDungDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.NguoiDungAdapter;
import com.example.happycooking.models.NguoiDung;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
    private Camera mCamera ;

    Context context = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_nd);

        init();
        anhXa();
        setUp();
        setClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//            IMGView.setImageBitmap(imageBitmap);
            this.imgURI = saveImageToInternalStorage(imageBitmap);
            Glide.with(this)
                    .load(new File(this.imgURI))
                    .circleCrop()
                    .into(IMGView);
        }
    }
    private String generateUniqueFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timeStamp = dateFormat.format(new Date());
        return "IMG_" + timeStamp + ".jpg";
    }

    private String saveImageToInternalStorage(Bitmap imageBitmap) {
        // Tạo tên file độc nhất cho ảnh
        String fileName = generateUniqueFileName();

        // Tạo đối tượng File để lưu ảnh vào bộ nhớ trong
        File file = new File(getApplicationContext().getFilesDir(), fileName);

        try {
            // Mở một luồng đầu ra để ghi dữ liệu ảnh vào
            FileOutputStream outputStream = new FileOutputStream(file);

            // Nén ảnh và ghi vào luồng đầu ra
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            // Đóng luồng đầu ra
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Trả về đường dẫn của ảnh đã lưu
        return file.getAbsolutePath();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==111 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            btn_camMeRa.setEnabled(true);
        }
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
        context = getApplicationContext();
        if(key_action.equals("XEM")){
            btn_luu.setVisibility(View.GONE);
            btn_camMeRa.setVisibility(View.GONE);
            ET_Email.setEnabled(false);
            ET_Pass.setEnabled(false);

            //Thiết lập dữ liệu hiển thị lên các control tương ứng
//            videoView.setVideoURI(Uri.parse(BV.getLinkVD()));
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
            imgURI = ND.getLinkIMG();

            //Thiết lập dữ liệu hiển thị lên các control tương ứng
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
//                mCamera = Camera.open();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,101);
            }
        });

        //lưu dữ liệu
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra dữ liệu đầu vào
                if(TextUtils.isEmpty(ET_Email.getText().toString().trim())) {
                    ET_Email.requestFocus();
                    Toast.makeText(ThemNDActivity.this, "'Email' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ET_Pass.getText().toString().trim())) {
                    ET_Pass.requestFocus();
                    Toast.makeText(ThemNDActivity.this, "'Pass' không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(imgURI == null) imgURI = "@drawable/user1";
                    NguoiDung ND2 = new NguoiDung(ET_Email.getText().toString().trim(), ET_Pass.getText().toString().trim(), imgURI);
                    NguoiDungDAO NDDAO = new NguoiDungDAO(ThemNDActivity.this);
                    if(key_action.equals("THEM")){  //trạng thái Insert
                        if(NDDAO.InsertND(ND2)){
                            intent = new Intent(ThemNDActivity.this, NguoiDungActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ThemNDActivity.this, "Thêm tài khoản thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (key_action.equals("SUA")) {  //trạng thái Update
                        if(NDDAO.UpdateND(ND2,ID_ND)){
                            intent = new Intent(ThemNDActivity.this, NguoiDungActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ThemNDActivity.this, "Sửa tài khoản thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
