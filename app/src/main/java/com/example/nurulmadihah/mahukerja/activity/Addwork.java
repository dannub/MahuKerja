package com.example.nurulmadihah.mahukerja.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurulmadihah.mahukerja.R;
import com.example.nurulmadihah.mahukerja.Save.Save;
import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class Addwork extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY=999;

    EditText edittypework, editskill, editpayment,deskripsi,contactperson;
    TextView addwork;
    Button saverecord,update, listwork;
    ImageView insertimage;
    DatabaseReference databaseJob;
    Job jobupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addwork);
        edittypework=(EditText) findViewById(R.id.edittypework);
        editskill=(EditText) findViewById(R.id.editskill);
        editpayment=(EditText) findViewById(R.id.editpayment);
        saverecord = (Button) findViewById(R.id.saverecord);
        listwork = (Button) findViewById(R.id.listwork);
        insertimage = (ImageView) findViewById(R.id.imageview);
        deskripsi=(EditText)findViewById(R.id.deskripsi);
        contactperson=(EditText)findViewById(R.id.contactperson);
        addwork=(TextView) findViewById(R.id.addwork) ;
        update=(Button) findViewById(R.id.update);
        databaseJob=FirebaseDatabase.getInstance().getReference("Job");


        SharedPreferences s = getSharedPreferences("MahuKerja",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        Gson gson = new Gson();
        if (Boolean.valueOf(s.getString("update","false"))){
            String json = s.getString("JobUpdate", "");
            jobupdate = gson.fromJson(json, Job.class);
            edt.commit();
            edittypework.setText(jobupdate.getTitle());
            editpayment.setText(jobupdate.getPayment());
            editskill.setText(jobupdate.getSkill());
            contactperson.setText(jobupdate.getContacperson());
            deskripsi.setText(jobupdate.getDeskripsi());
            String encodeimage =jobupdate.getImage();
            byte[] recordImage = Base64.decode(encodeimage,Base64.DEFAULT);
            Bitmap bitmap =BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
            insertimage.setImageBitmap(bitmap);
            saverecord.setVisibility(View.GONE);
            addwork.setText("Update Work");
        }else {
            saverecord.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            edittypework.setText("");
            editskill.setText("");
            editpayment.setText("");
            deskripsi.setText("");
            contactperson.setText("");
            insertimage.setImageResource(R.drawable.ic_add_a_photo_black_24dp);

        }


        insertimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        Addwork.this,
                        new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
                );
            }
        });

        saverecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /*MainActivity.sqLiteHelper.insertData(
                            edittypework.getText().toString().trim(),
                            editskill.getText().toString().trim(),
                            editpayment.getText().toString().trim(),
                            deskripsi.getText().toString().trim(),
                            contactperson.getText().toString().trim(),
                            "Admin",0,
                            imageViewToByte(insertimage)
                    );*/
                    AddJob();
                    Toast.makeText(Addwork.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    edittypework.setText("");
                    editskill.setText("");
                    editpayment.setText("");
                    deskripsi.setText("");
                    contactperson.setText("");
                    insertimage.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateJob(jobupdate);
                Toast.makeText(getApplicationContext(),"Update Succesfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        listwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult (int RequestCode, @NonNull String [] permissions, @NonNull int[] grandResult ){
       if (RequestCode == REQUEST_CODE_GALLERY){
           if (grandResult.length>0 && grandResult[0]== PackageManager.PERMISSION_GRANTED){
               Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
               galleryIntent.setType("image/*");
               startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
           }
           else{
               Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
           }
           return;
       }
       super.onRequestPermissionsResult(RequestCode, permissions, grandResult);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode != RESULT_CANCELED){

            Uri resultUri = data.getData();
            insertimage.setImageURI(resultUri);

        }


        super.onActivityResult(requestCode,resultCode,data);
    }
    private void AddJob(){
        String id =databaseJob.push().getKey();
        byte[] image =imageViewToByte(insertimage);
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
        User user = Save.readakun(getApplicationContext(),"Akun");
        Job job = new Job(id,edittypework.getText().toString(),editskill.getText().toString(),editpayment.getText().toString(),contactperson.getText().toString(),deskripsi.getText().toString(),user.getId(),"0",
                encodedImage);
        databaseJob.child(id).setValue(job);
    }

    private void UpdateJob(Job jobupdate){
        byte[] image = imageViewToByte(insertimage);
        String encodeImage = Base64.encodeToString(image, Base64.DEFAULT);
        Job job = new Job(jobupdate.getId(),edittypework.getText().toString(),editpayment.getText().toString(),
                editskill.getText().toString(),contactperson.getText().toString(),deskripsi.getText().toString(),
                jobupdate.getPemasang(),jobupdate.getStatus(), encodeImage );
        databaseJob.getRef().child("/"+ job.getId()).setValue(job);
    }

    public static byte [] imageViewToByte (ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream  stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
