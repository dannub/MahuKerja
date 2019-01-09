package com.example.nurulmadihah.mahukerja.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.SQLHelper.SQLiteHelper;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.adapter.RecordListAdapter;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button signin;
    Button signup,logout;
    Button addwork;
    User user;
    TextView username;
    ListView listView;
    ArrayList<Job> list;
    public RecordListAdapter Adapter = null;
    boolean session;
    ImageView imageViewIcon;
    Query jobquery;
    public static SQLiteHelper sqLiteHelper;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sqLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        //sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, skill VARCHAR, payment VARCHAR, contacperson VARCHAR, deskripsi VARCHAR,pemasang VARCHAR,status Long, image BLOB)");



        logout=(Button) findViewById(R.id.logout);
        username=(TextView)findViewById(R.id.username);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        addwork = (Button)findViewById(R.id.addwork2);
        listView = findViewById(R.id.recyclerview);

        Session();
        list = new ArrayList<>();
        Adapter = new RecordListAdapter(this, R.layout.listview, list);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position==0){
                    jobquery=FirebaseDatabase.getInstance().getReference("Job");
                }
                else if (position==1){
                    jobquery=FirebaseDatabase.getInstance().getReference("Job");

                }
                else if (position==2) {

                    jobquery=FirebaseDatabase.getInstance().getReference("Job").orderByChild("status")
                            .equalTo("0");
                }
                else {

                    jobquery = FirebaseDatabase.getInstance().getReference("Job").orderByChild("pemasang")
                            .equalTo(user.getId());
                }



                //Cursor cursor =sqLiteHelper.getData("SELECT * FROM RECORD");
                list.clear();
                listView.setAdapter(Adapter);
                jobquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Job job = snapshot.getValue(Job.class);
                                if(position==1){
                                    if(!job.getStatus().equals("0")){
                                        list.add(job);
                                    }
                                }else{
                                    list.add(job);
                                }
                            }

                            Adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Data Kosong",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
                    }


                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );





        /*while (cursor.moveToNext()){
           String id = cursor.getString(0);
            String title = cursor.getString(1);
            String skill = cursor.getString(2);
            String payment = cursor.getString(3);
            String contacperson = cursor.getString(4);
            String deskripsi = cursor.getString(5);
            String pemasang = cursor.getString(6);
            int status = cursor.getInt(7);
            byte[] image = cursor.getBlob(8);
            String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
            list.add(new Job(id, title, skill, payment, contacperson, deskripsi, pemasang,status, encodedImage));
        }*/




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences s = getSharedPreferences("MahuKerja",Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = s.edit();
                Gson gson = new Gson();
                String json = gson.toJson(list.get(i));
                edt.putString("Job",json);
                edt.commit();
                Toast.makeText(getBaseContext(), list.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Intent detail = new Intent(getBaseContext(),detail_Job.class);
                startActivity(detail);
            }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),login.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save.save(getBaseContext(),"session","false",null);
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Register.class);
                startActivity(i);
            }
        });

        addwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences s = getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = s.edit();
                edt.putString("JobUpdate", "");
                edt.putString("update", "false");
                edt.commit();
                Intent i = new Intent(getBaseContext(),Addwork.class);
                startActivity(i);
            }
        });

        listView = findViewById(R.id.recyclerview);

    }
    public void Session(){
        session = Boolean.valueOf(Save.readlogin(getApplicationContext(),"session","false"));
        spinner=(Spinner) findViewById(R.id.spinner1);
        List<String> categories = new ArrayList<String>();
        categories.add("Semua Job");
        categories.add("Sudah");
        categories.add("Belum");
        if(!session){
            logout.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            signin.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            addwork.setVisibility(View.GONE);
        }else{
            logout.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            addwork.setVisibility(View.VISIBLE);
            categories.add("Job Saya");
            signin.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            user = Save.readakun(getApplicationContext(),"Akun");
            username.setText(user.getUsername());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner, categories);
        spinner.setAdapter(dataAdapter);
    }
}
