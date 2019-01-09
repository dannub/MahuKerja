package com.example.nurulmadihah.mahukerja.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.Tawaran;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class detail_Job extends AppCompatActivity {
    TextView addwork,skill,payment,deskripsi,pemasang,contactperson,status, nameworker, statuspenawaran,nameworker1;
    ImageView imageView;
    Button iwant, delete, login, update, listtawaran;
    boolean session;
    public Job job;
    User user;
    DatabaseReference datajob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__job);
        addwork = (TextView) findViewById(R.id.addwork);
        skill = (TextView) findViewById(R.id.skill);
        payment = (TextView) findViewById(R.id.payment);
        deskripsi = (TextView) findViewById(R.id.deskripsi);
        pemasang = (TextView) findViewById(R.id.pemasang);
        contactperson = (TextView) findViewById(R.id.cp);
        status = (TextView) findViewById(R.id.status);
        imageView = (ImageView) findViewById(R.id.imageview);
        iwant = (Button) findViewById(R.id.iwant);
        delete = (Button) findViewById(R.id.delete);
        login = (Button) findViewById(R.id.login);
        update = (Button) findViewById(R.id.update);
        nameworker = (TextView) findViewById(R.id.nameworker);
        nameworker1 = (TextView) findViewById(R.id.nameworker1);
        statuspenawaran = (TextView) findViewById(R.id.statuspenawaran);
        listtawaran = (Button) findViewById(R.id.listtawaran);

        datajob=FirebaseDatabase.getInstance().getReference("Job");
        SharedPreferences s = getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = s.getString("Job", "");
        job = gson.fromJson(json, Job.class);
        byte[] recordImage = Base64.decode(job.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        imageView.setImageBitmap(bitmap);
        statuspenawaran.setVisibility(View.GONE);
        Session();
        iwant.setVisibility(View.GONE);
        addwork.setText(job.getTitle());
        skill.setText(job.getSkill());
        payment.setText(job.getPayment());
        deskripsi.setText(job.getDeskripsi());
        contactperson.setText(job.getContacperson());
        if (job.getStatus().equals("0")) {

            status.setText("Belum Selesai");
        } else {
            status.setText("Sudah Selesai");
        }
        if(session) {
            Query tawaran = FirebaseDatabase.getInstance().getReference("Tawaran")
                    .orderByChild("job_user").equalTo(job.getId() + "_" + user.getId());
            tawaran.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Tawaran tawaran = snapshot.getValue(Tawaran.class);
                            if (tawaran.getStatus() == 0) {
                                statuspenawaran.setText("Waiting For Confirmation...");
                            } else if (tawaran.getStatus() == 1) {
                                statuspenawaran.setText("Successfull");
                            } else {
                                statuspenawaran.setText("Unsuccessfull");
                            }
                            statuspenawaran.setVisibility(View.VISIBLE);
                        }

                    } else {
                        statuspenawaran.setVisibility(View.GONE);
                        Session();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        Query userjob = FirebaseDatabase.getInstance() //cara dapatkan id pemasang
                .getReference("User").orderByChild("id").equalTo(job.getPemasang());

        userjob.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        pemasang.setText(user.getUsername());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

        });
        listtawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), listpenawaran.class);
                startActivity(i);
                finish();
            }
        });



        if(!job.getStatus().equals("0")){
            userjob = FirebaseDatabase.getInstance() //cara dapatkan id pemasang
                    .getReference("User").orderByChild("id").equalTo(job.getStatus());

            userjob.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            nameworker.setText(user.getUserpass());
                        }
                        Session();

                    } else {
                        Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

            });
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getBaseContext(), com.example.nurulmadihah.mahukerja.activity.login.class);
                startActivity(i);
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(detail_Job.this);
                alertDialogBuilder.setTitle("Confirmation");
                alertDialogBuilder
                        .setMessage("Did you confirm to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datajob.child(job.getId()).removeValue();
                                Toast.makeText(getApplicationContext(), "Data Berhasil di Hapus", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                                finish();
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences s = getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = s.edit();
                Gson gson = new Gson();
                String json = gson.toJson(job);
                edt.putString("JobUpdate", json);
                edt.putString("update", "true");
                edt.commit();
                Intent i = new Intent(getBaseContext(), Addwork.class);
                startActivity(i);
                finish();

            }
        });
        iwant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user = Save.readakun(getApplication(),"Akun");
//                job.setStatus(user.getId());
//                datajob.getRef().child("/"+ job.getId()).setValue(job);
//                Intent i = new Intent(getBaseContext(), MainActivity.class);
//                Toast.makeText(getApplicationContext(), "Sudah di Daftar", Toast.LENGTH_LONG).show();
//                startActivity(i);
//                finish();
                Intent i = new Intent(getBaseContext(), penawaran.class);
                startActivity(i);
                finish();

            }
        });


    }
    public void Session(){
        session = Boolean.valueOf(Save.readlogin(getApplicationContext(), "session", "false"));
        user = Save.readakun(getApplicationContext(), "Akun");
        if (job.getStatus().equals("0")) {
            iwant.setVisibility(View.VISIBLE);
            nameworker1.setVisibility(View.GONE);
            nameworker.setVisibility(View.GONE);
        } else {
            iwant.setVisibility(View.GONE);
            nameworker1.setVisibility(View.VISIBLE);
            nameworker.setVisibility(View.VISIBLE);
        }
        if (!session) {
            delete.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            iwant.setVisibility(View.GONE);
            listtawaran.setVisibility(View.GONE);

        } else if (user.getId().equals(job.getPemasang())) {
            delete.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            listtawaran.setVisibility(View.VISIBLE);
            iwant.setVisibility(View.GONE);

        } else {
            listtawaran.setVisibility(View.GONE);

            delete.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
        }

    }
}
