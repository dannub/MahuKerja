package com.example.nurulmadihah.mahukerja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText username,password;
    Button login;
    DatabaseReference user;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username=(EditText)findViewById(R.id.editusername);
        password=(EditText)findViewById(R.id.editpassword);
        login =(Button)findViewById(R.id.login);
        register=(TextView)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Register.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    Toast.makeText(login.this,"Please Fill Your Username",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(login.this,"Please Fill Your Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Query userquery=FirebaseDatabase.getInstance()
                            .getReference("User")
                            .orderByChild("userpass")
                            .equalTo(username.getText().toString()+"_"+password.getText().toString());

                    userquery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount()==1){

                                Intent i = new Intent(getBaseContext(),MainActivity.class);
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        User user = snapshot.getValue(User.class);
                                        Save.save(getBaseContext(),"session","true",user);
                                    }
                                    startActivity(i);
                                    finish();
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Login Gagal",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }



}
