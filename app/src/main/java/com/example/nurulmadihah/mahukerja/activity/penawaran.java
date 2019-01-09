package com.example.nurulmadihah.mahukerja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.Tawaran;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class penawaran extends AppCompatActivity {

    EditText tawaran;
    Button save;
    Job job;
    DatabaseReference datatawaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penawaran);
        tawaran = (EditText)findViewById(R.id.penawaran);
        save = (Button) findViewById(R.id.save);
        job=Save.readjob(getBaseContext()); //panggil job
        datatawaran = FirebaseDatabase.getInstance().getReference("Tawaran");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =datatawaran.push().getKey();
                User user = Save.readakun(getApplicationContext(),"Akun");
                Tawaran tawaranku = new Tawaran(id,user.getId(),job.getId(),job.getId()+"_"+user.getId(),
                        Integer.parseInt(tawaran.getText().toString())//merubah integer
                         ,0);
                datatawaran.child(id).setValue(tawaranku);
                Toast.makeText(getApplicationContext(), "Tawaran anda masuk", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), detail_Job.class);
                startActivity(i);
                finish();
            }
        });
    }
}
