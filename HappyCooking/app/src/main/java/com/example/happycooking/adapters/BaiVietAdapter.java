package com.example.happycooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.happycooking.R;
import com.example.happycooking.models.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class BaiVietAdapter extends ArrayAdapter<BaiViet> {
    private Context ct;
    private ArrayList<BaiViet> arr;
    public BaiVietAdapter(@NonNull Context context, int resource, @NonNull List<BaiViet> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lv_bv_01, null);
        }
        if (arr.size() > 0) {
            BaiViet BV = this.arr.get(position);
            ImageButton IMG = convertView.findViewById(R.id.IB_ViDeo);
            TextView TV_TenBV = convertView.findViewById(R.id.TV_tenBV);

            // lấy dữ liệu lên các control
            Glide.with(this.ct).load(BV.getLinkVD()).into(IMG);
            TV_TenBV.setText(BV.getTenBV());
        }

        return convertView;
    }
}
