package com.example.happycooking.adapters;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happycooking.R;
import com.example.happycooking.activities.ThemBVActivity;
import com.example.happycooking.activities.TrangChuActivity;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class BaiVietAdapter extends ArrayAdapter<BaiViet> {
    private Context ct;
    private ArrayList<BaiViet> arr;

    public int ID_BV_CT = -1;
    public BaiVietAdapter(@NonNull Context context, int resource, @NonNull List<BaiViet> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public BaiViet getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lv_bv_01, null);
        }
        if (arr.size() > 0) {
            BaiViet BV = this.arr.get(position);
            TextView TV_TenBV = convertView.findViewById(R.id.TV_tenBV);
            VideoView videoView = convertView.findViewById(R.id.videoView);

            // lấy dữ liệu lên các control
            TV_TenBV.setText(BV.getTenBV());
//            File file = new File(BV.getLinkVD());
//            if (file.exists()) {
            videoView.setVideoURI(Uri.parse(BV.getLinkVD()));
            videoView.start();
//            }


            // gán id của bài viết vào tag của VideoView
            videoView.setTag(BV.getID());
            LinearLayout item_BV = convertView.findViewById(R.id.item_BV);
            EditText ET_TimKiem = convertView.findViewById(R.id.ET_TimKiem);


            //khi click vào video thì đổi màu nền
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) ct.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    ID_BV_CT = (int) v.getTag();

                    // Cập nhật trạng thái đã chọn của VideoView
                    int mSelectedPosition = position;

                    // Duyệt qua danh sách các VideoView và thiết lập màu nền tương ứng
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        View childView = parent.getChildAt(i);
                        LinearLayout childItem = childView.findViewById(R.id.item_BV);
                        VideoView childVideo = childView.findViewById(R.id.videoView);
                        boolean isChildSelected = (mSelectedPosition == i);
                        if (isChildSelected) {
                            childItem.setBackgroundColor(Color.parseColor("#4AF4BBBB"));
                            childVideo.requestFocus();
                        } else {
                            childItem.setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            });
        }
        return convertView;
    }
}
