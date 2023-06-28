package com.example.happycooking.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.happycooking.R;
import com.example.happycooking.models.NguoiDung;
//import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungAdapter extends ArrayAdapter<NguoiDung> {
    private Context ct;
    private ArrayList<NguoiDung> arr;

    public int ID_ND_CT = -1;
    public NguoiDungAdapter(@NonNull Context context, int resource, @NonNull List<NguoiDung> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lv_nd, null);
        }
        if (arr.size() > 0) {
            NguoiDung ND = this.arr.get(position);
            TextView TV_Email = convertView.findViewById(R.id.TV_Email);
            ImageView IMG = convertView.findViewById(R.id.IMG);

//             lấy dữ liệu lên các control
            TV_Email.setText(ND.getEmail());

            int imageResId = getContext().getResources().getIdentifier(ND.getLinkIMG(), null, getContext().getPackageName());
            if(imageResId !=0) {
                Glide.with(this.getContext())
                        .load(imageResId)
                        .circleCrop()
                        .into(IMG);
            }
            else{
                Glide.with(this.getContext())
                        .load(new File(ND.getLinkIMG()))
                        .circleCrop()
                        .into(IMG);
            }

            //khi click vào item thì đổi màu nền
            LinearLayout item_ND = convertView.findViewById(R.id.item_ND);
            item_ND.setTag(ND.getID());
            item_ND.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ID_ND_CT = (int) v.getTag();
//                    Toast.makeText(getContext(), "may", Toast.LENGTH_SHORT).show();

                    // Cập nhật trạng thái đã chọn của VideoView
                    int mSelectedPosition = position;

                    // Duyệt qua danh sách các VideoView và thiết lập màu nền tương ứng
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        View childView = parent.getChildAt(i);
                        LinearLayout childItem = childView.findViewById(R.id.item_ND);
                        boolean isChildSelected = (mSelectedPosition == i);
                        if (isChildSelected) {
                            childItem.setBackgroundColor(Color.parseColor("#4AF4BBBB"));
                            childItem.requestFocus();
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
