package com.example.nurulmadihah.mahukerja.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.activity.Addwork;
import com.example.nurulmadihah.mahukerja.activity.MainActivity;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.Tawaran;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TawaranListAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private ArrayList<Tawaran> recordList;
    Job job;

    public TawaranListAdapter(Context context, int layout, ArrayList<Tawaran> recordList, Job job) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
        this.job = job;
    }

    @Override
    public int getCount()  {
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

        TextView namatawaran, hargapenawaran, statuspenawaran;
        Button confirm;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.namatawaran = row.findViewById(R.id.namatawaran);
            holder.hargapenawaran = row.findViewById(R.id.hargapenawaran);
            holder.statuspenawaran = row.findViewById(R.id.statuspenawaran);
            holder.confirm = row.findViewById(R.id.confirm);
            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }

        final Tawaran model = recordList.get(i);
        Query user = FirebaseDatabase.getInstance().getReference("User").orderByChild("id").equalTo(model.getId_user());

        final ViewHolder finalHolder = holder;
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                       finalHolder.namatawaran.setText(user.getUsername());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.hargapenawaran.setText(Integer.toString(model.getTawaran()));
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirmation");
                alertDialogBuilder
                        .setMessage("Did you confirm?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Query tawaran = FirebaseDatabase.getInstance().getReference("Tawaran").orderByChild("id_job").equalTo(job.getId());

                                tawaran.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                Tawaran tawaranku = snapshot.getValue(Tawaran.class);
                                                if (tawaranku.getId_user().equals(model.getId_user())){
                                                    //Jika tawaran sama status benar adalah 1 (diterima)
                                                    tawaranku.setStatus(1);

                                                }
                                                else{
                                                    //Jika tawaran tidak sama adalah 2 (ditolak)
                                                    tawaranku.setStatus(2);

                                                }
                                                FirebaseDatabase.getInstance().getReference("Tawaran").getRef().child("/"+ tawaranku.getId()).setValue(tawaranku);
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Belum Ada Tawaran", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(context, "Tawaran belum berhasil", Toast.LENGTH_LONG).show();
                                    }
                                });
                                job.setStatus(model.getId_user());
                                FirebaseDatabase.getInstance().getReference("Job").getRef().child("/"+ job.getId()).setValue(job);
                                Toast.makeText(context, "Tawaran telah berhasil", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(context, MainActivity.class);
                                context.startActivity(i);
                                ((Activity)context).finish();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        if (job.getStatus().equals("0")){
            holder.statuspenawaran.setVisibility(View.GONE);
            holder.confirm.setVisibility(View.VISIBLE);
        }else {
            holder.statuspenawaran.setVisibility(View.VISIBLE);
            holder.confirm.setVisibility(View.GONE);
        }


        if (model.getStatus()==1){
            holder.statuspenawaran.setText("Diterima");
        } else if (model.getStatus()==2)
        {
            holder.statuspenawaran.setText("Ditolak");
        }
        return row;

    }


}
