package com.example.happycooking.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.happycooking.R;
import com.example.happycooking.database.database;

public class DangKyActivity extends AppCompatActivity {
    private SQLiteDatabase SQLiteDB = null;
    private EditText ET_Email, ET_Pass;
    Button btn_nhan, btn_dn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky);

        //sử lý file .gif
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.drawable.login_01).into(imageView);

        //
        ET_Email = findViewById(R.id.ET_Email);
        ET_Pass = findViewById(R.id.ET_Pass);
        btn_nhan = findViewById(R.id.button_nhan);
        btn_dn = findViewById(R.id.button_dn);
        btn_dn.setText(Html.fromHtml("<u>" + "Đăng Nhập" + "</u>"));
        ET_Email.requestFocus();
        ET_Pass.setNextFocusDownId(R.id.button_nhan);

        //mở kết nối
        database createDB = new database(this);
        SQLiteDB = createDB.open();

        //nhấn nút "nhận" -> kiểm tra thông tin đăng nhập -> nếu tm thì đi đến trang chủ
        btn_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ET_Email.getText().toString().trim();
                String pass = ET_Pass.getText().toString().trim();
                String query = "SELECT * FROM NGUOI_DUNG WHERE email = '" + email +"'";
                Cursor cursor = SQLiteDB.rawQuery(query,null);

                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
                    ET_Email.requestFocus();
                    Toast toast = Toast.makeText(DangKyActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 500, 500);
                    toast.show();
                }
                else if(TextUtils.isEmpty(pass)){
                    ET_Pass.requestFocus();
                    Toast toast = Toast.makeText(DangKyActivity.this, "Mật khẩu không được bỏ trống!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 500, 500);
                    toast.show();
                }
                else if(cursor.getCount() != 0){
                    ET_Email.requestFocus();
                    Toast toast = Toast.makeText(DangKyActivity.this, "Email đã tồn tại. Vui lòng sủ dụng email khác!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 500, 500);
                    toast.show();
                }else{

                }
            }
        });

        //nhấn nút đăng nhập -> chuyển sang trang đăng nhập
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent1);
            }
        });
    }
}
