package com.example.happycooking.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happycooking.DAO.BaiVietDAO;
import com.example.happycooking.R;
import com.example.happycooking.adapters.BaiVietAdapter;
import com.example.happycooking.adapters.NguoiDungAdapter;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class TrangChuActivity  extends AppCompatActivity {
    GridView GV;
    ImageButton btn_BaiViet, btn_nguoiDung, btn_dangXuat, btn_xem, btn_them, btn_sua, btn_xoa, btn_LichSu;
    EditText ET_TimKiem;
    TextView TV_LichSu;
    LinearLayout LL_control, LN_search;
    BaiVietAdapter adapter;
    BaiViet BVct = null;
    ArrayList<BaiViet> bvArrayList;
    Intent intent;
    boolean isLSXem = false;
    BaiVietDAO BVDAO;
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
        BVDAO = new BaiVietDAO(this);
        adapter = new BaiVietAdapter(this,0,BVDAO.getAll(null, null));
    }

    private void anhXa(){
        GV = findViewById(R.id.GridView);
        btn_BaiViet = findViewById(R.id.btn_BaiViet);
        btn_nguoiDung = findViewById(R.id.btn_nguoiDung);
        btn_dangXuat = findViewById(R.id.btn_dangXuat);
        btn_xem = findViewById(R.id.btn_xem);
        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);
        btn_xoa = findViewById(R.id.btn_xoa);
        btn_LichSu = findViewById(R.id.btn_LichSu);
        ET_TimKiem = findViewById(R.id.ET_TimKiem);
        LL_control = findViewById(R.id.LL_control);
        LN_search = findViewById(R.id.LN_search);
        TV_LichSu = findViewById(R.id.TV_LichSu);
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

        btn_dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận xóa
                AlertDialog.Builder builder = new AlertDialog.Builder(TrangChuActivity.this);
                builder.setMessage("Bạn có muốn đăng xuất khỏi phần mềm không?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                intent = new Intent(TrangChuActivity.this, DangNhapActivity.class);
                                startActivity(intent);
                            }
                        });

                builder.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btn_BaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLSXem = false;
                btn_sua.setVisibility(View.VISIBLE);
                btn_xem.setVisibility(View.VISIBLE);
                btn_them.setVisibility(View.VISIBLE);
                LN_search.setVisibility(View.VISIBLE);
                TV_LichSu.setVisibility(View.GONE);
                BVDAO = new BaiVietDAO(TrangChuActivity.this);
                adapter = new BaiVietAdapter(TrangChuActivity.this,0, BVDAO.getAll(null, null));
                GV.setAdapter(adapter);
            }
        });

        //tìm kiếm
        ET_TimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String TenBV1 = ET_TimKiem.getText().toString().trim();
                BVDAO = new BaiVietDAO(TrangChuActivity.this);
                adapter = new BaiVietAdapter(TrangChuActivity.this,0, BVDAO.getAll("TenBV LIKE ?", new String[]{"%" + TenBV1+ "%"}));

                GV.setAdapter(adapter);

                // Khôi phục lại vị trí của con trỏ chuột trong EditText
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ET_TimKiem.requestFocus();
                        ET_TimKiem.setSelection(ET_TimKiem.getText().toString().length());
                    }
                });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //xem lịch sử
        btn_LichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLSXem = true;
                btn_sua.setVisibility(View.GONE);
                btn_them.setVisibility(View.GONE);
                btn_xem.setVisibility(View.GONE);
                LN_search.setVisibility(View.GONE);
                TV_LichSu.setVisibility(View.VISIBLE);

                BVDAO = new BaiVietDAO(TrangChuActivity.this);
                adapter = new BaiVietAdapter(TrangChuActivity.this,0, BVDAO.getAll("isXem = 1", null));

                GV.setAdapter(adapter);
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
                if (adapter.ID_BV_CT != -1) {
                    if (isLSXem == true) {
                        // Hiển thị hộp thoại xác nhận xóa
                        AlertDialog.Builder builder = new AlertDialog.Builder(TrangChuActivity.this);
                        builder.setMessage("Bạn có muốn xóa lịch sử xem bài viết này không?");
                        builder.setCancelable(true);

                        builder.setPositiveButton(
                                "Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Thực hiện xóa bản ghi
                                        BVDAO = new BaiVietDAO(TrangChuActivity.this);
                                        BVct = BVDAO.getByID(adapter.ID_BV_CT);
                                        if (BVDAO.UpdateBV(BVct, adapter.ID_BV_CT, true,0)) {
                                            //load lại trang
                                            adapter = new BaiVietAdapter(TrangChuActivity.this,0, BVDAO.getAll("isXem = 1", null));
                                            GV.setAdapter(adapter);

                                            Toast.makeText(TrangChuActivity.this, "Xóa lịch sử xem của bài viết thành công!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(TrangChuActivity.this, "Xóa lịch sử xem bài viết thất bại!\n Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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
                    }else {
                        // Hiển thị hộp thoại xác nhận xóa
                        AlertDialog.Builder builder = new AlertDialog.Builder(TrangChuActivity.this);
                        builder.setMessage("Bạn có muốn xóa bài viết không?");
                        builder.setCancelable(true);

                        builder.setPositiveButton(
                                "Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Thực hiện xóa bản ghi
                                        BVDAO = new BaiVietDAO(TrangChuActivity.this);
                                        if (BVDAO.DeleteBV(adapter.ID_BV_CT)) {
                                            //load lại trang
                                            adapter = new BaiVietAdapter(TrangChuActivity.this, 0, BVDAO.getAll(null, null));
                                            GV.setAdapter(adapter);

                                            Toast.makeText(TrangChuActivity.this, "Xóa bài viết thành công!", Toast.LENGTH_SHORT).show();
                                        } else {
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
                }
                else{
                    Toast.makeText(TrangChuActivity.this, "Bạn chưa chọn bài viết để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
