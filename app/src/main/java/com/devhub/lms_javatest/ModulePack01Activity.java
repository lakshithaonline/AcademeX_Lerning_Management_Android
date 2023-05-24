package com.devhub.lms_javatest;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ModulePack01Activity extends AppCompatActivity {

    Button down;
    FirebaseStorage firebaseStorage;
    StorageReference storageReferenceWeek3;

    StorageReference ref;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulepack01);

        //Week01 Download
        down=findViewById(R.id.week1btn);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadWeek01();
            }
        });

        // Week01 Upload Button
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Button uploadButton = findViewById(R.id.week01upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose file to upload
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });

        //Week02_1 Upload Button
        Button uploadButtonW2_1 = findViewById(R.id.week2up);
        uploadButtonW2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose file to upload
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });

        //Week02_2 Upload Button
        Button uploadButtonW2_2 = findViewById(R.id.week2_1up);
        uploadButtonW2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose file to upload
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });

        //Week03_1 Download Button
        down=findViewById(R.id.week3btn1);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download_1();
            }
        });

        //Week03_2 Download Button
        down=findViewById(R.id.week3btn2);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download_2();
            }
        });

        // Navigate button
        Button startButton = findViewById(R.id.module01back);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when button is clicked
                Intent intent = new Intent(ModulePack01Activity.this, ModuleGalleryActivity.class);
                startActivity(intent);
            }
        });
    }

    //week1 download button function
    public void downloadWeek01(){
        storageReferenceWeek3=firebaseStorage.getInstance().getReference();
        ref=storageReferenceWeek3.child("Downloads/Assignment.pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFile(ModulePack01Activity.this, "Assignment", ".pdf", DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    //week01


    //week2 download button function
    //week02

    //week3 download button function
    public void download_1(){
        storageReferenceWeek3=firebaseStorage.getInstance().getReference();
        ref=storageReferenceWeek3.child("Downloads/Assignment.pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFile(ModulePack01Activity.this, "Assignment", ".pdf", DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void download_2(){
        storageReferenceWeek3=firebaseStorage.getInstance().getReference();
        ref=storageReferenceWeek3.child("Downloads/Assignment.pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFile(ModulePack01Activity.this, "Assignment", ".pdf", DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void downloadFile (Context context, String fileName, String fileExtention, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtention);

        downloadManager.enqueue(request);
    } //week03


    //Week01 Upload
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the selected file's URI
            Uri fileUri = data.getData();

            // Upload file
            StorageReference fileRef = storageReference.child("uploads").child(fileUri.getLastPathSegment());

            fileRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // File uploaded successfully
                            Toast.makeText(ModulePack01Activity.this, "File uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed upload
                            Toast.makeText(ModulePack01Activity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
