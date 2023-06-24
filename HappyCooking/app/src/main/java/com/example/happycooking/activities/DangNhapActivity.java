package com.example.happycooking.activities;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.happycooking.R;
import com.example.happycooking.database.database;

public class DangNhapActivity extends AppCompatActivity{
    private SQLiteDatabase SQLiteDB = null;
    private EditText ET_Email, ET_Pass;
    Button btn_nhan, btn_dk;
    public static int ID_ND_now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);

        //sử lý file .gif
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.drawable.login_01).into(imageView);

        //
        ET_Email = findViewById(R.id.ET_Email);
        ET_Pass = findViewById(R.id.ET_Pass);
        btn_nhan = findViewById(R.id.button_nhan);
        btn_dk = findViewById(R.id.button_dk);
        btn_dk.setText(Html.fromHtml("<u>" + "Đăng Ký" + "</u>"));
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
                String query = "SELECT * FROM NGUOI_DUNG WHERE email = '" + email +"' and pass = '" + pass + "'";
                Cursor cursor = SQLiteDB.rawQuery(query,null);
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    ID_ND_now = cursor.getInt(0);
                    Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                    startActivity(intent);
                }else{
                    ET_Email.requestFocus();
                    Toast  toast = Toast.makeText(DangNhapActivity.this, "Email hoặc Mật khẩu chưa đúng!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 500, 500);
                    toast.show();
                }
//                SQLiteDB.close();
            }
        });

        //nhấn nút đăng ký -> chuyển sang trang đăng ký
        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent1);
            }
        });
    }
}