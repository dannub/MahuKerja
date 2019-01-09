package com.example.nurulmadihah.mahukerja.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.adapter.RecordListAdapter;
import com.example.nurulmadihah.mahukerja.adapter.TawaranListAdapter;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.Tawaran;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listpenawaran extends AppCompatActivity {

    ListView listtawaran;
    ArrayList<Tawaran> list;
    public TawaranListAdapter Adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpenawaran);
        listtawaran = (ListView)findViewById(R.id.listtawaran);

        Job job = Save.readjob(getApplicationContext());
        list = new ArrayList<>();
        Adapter = new TawaranListAdapter(this,R.layout.listtawaran,list,job);
        listtawaran.setAdapter(Adapter);
        list.clear();//menghapus data list lama
        Query tawaran = FirebaseDatabase.getInstance().getReference("Tawaran").orderByChild("id_job").equalTo(job.getId());

        tawaran.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Tawaran tawaranku = snapshot.getValue(Tawaran.class);
                        list.add(tawaranku);
                    }
                    Adapter.notifyDataSetChanged();//mengpdate data list

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Belum Ada Tawaran", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tawaran belum berhasil", Toast.LENGTH_LONG).show();
            }
        });
    }
}
