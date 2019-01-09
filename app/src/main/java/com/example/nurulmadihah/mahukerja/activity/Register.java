package com.example.nurulmadihah.mahukerja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register  extends AppCompatActivity  {
    EditText editfirstname,editlastname,editusername,editemail,editpassword;
    Button register;
    TextView sudahregister;
    DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editfirstname=(EditText)findViewById(R.id.editfirstname);
        editlastname=(EditText)findViewById(R.id.editlastname);
        editusername=(EditText)findViewById(R.id.editusername);
        editemail=(EditText)findViewById(R.id.editemail);
        editpassword=(EditText)findViewById(R.id.editpassword);
        register=(Button)findViewById(R.id.register);
        databaseUser=FirebaseDatabase.getInstance().getReference("User");
        sudahregister=(TextView) findViewById(R.id.sudahregister);

        sudahregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), login.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editfirstname.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Please fill First Name", Toast.LENGTH_LONG).show();
                }
                else if(editlastname.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Please fill Last Name", Toast.LENGTH_LONG).show();
                }
                else if(editusername.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Please fill Userame", Toast.LENGTH_LONG).show();
                }
                else if(editemail.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Please fill Email", Toast.LENGTH_LONG).show();
                }
                else if(editpassword.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Please fill Password", Toast.LENGTH_LONG).show();
                }
                else {
                    AddUser();
                    Toast.makeText(Register.this, "User Added", Toast.LENGTH_LONG).show();
                    Clear();
                    Intent i = new Intent(getBaseContext(),login.class);
                    startActivity(i);
                }
            }
        });
    }
    private void AddUser(){
        String id =databaseUser.push().getKey();
        User user = new User(id,editfirstname.getText().toString(),editlastname.getText().toString(),editemail.getText().toString(),editpassword.getText().toString(),editusername.getText().toString(),editusername.getText().toString()+"_"+editpassword.getText().toString());
        databaseUser.child(id).setValue(user);
    }
    private  void Clear(){
        editfirstname.setText("");
        editlastname.setText("");
        editemail.setText("");
        editusername.setText("");
        editpassword.setText("");
    }
}
