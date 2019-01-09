package com.example.nurulmadihah.mahukerja.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.R;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Job> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<Job>recordList){
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView title, skill, payment, status;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.title = row.findViewById(R.id.title);
            holder.skill = row.findViewById(R.id.parttime);
            holder.payment = row.findViewById(R.id.payment);
            holder.imageView = row.findViewById(R.id.image);
            holder.status=row.findViewById(R.id.status);
            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }

        Job model = recordList.get(i);

        holder.title.setText(model.getTitle());
        holder.skill.setText(model.getSkill());
        holder.payment.setText(model.getPayment());
        if(model.getStatus().equals("0")){
            holder.status.setText("Belum Selesai");
        }
        else{
            holder.status.setText("Sudah Selesai");

        }
        String encodeimage = model.getImage();
        byte[] recordImage = Base64.decode(encodeimage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
