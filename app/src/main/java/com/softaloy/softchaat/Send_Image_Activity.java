package com.softaloy.softchaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softaloy.softchaat.Model.MessageModel;
import com.softaloy.softchaat.databinding.ActivitySendImageBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Send_Image_Activity extends AppCompatActivity {

    ActivitySendImageBinding binding;
   /* String url, recceiver_name, reveiver_uid, sender_uid;
    Uri imageurl;
    UploadTask uploadTask;

    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rooRef1, rootRef2;

    private Uri uri;

    MessageModel messageModel;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivitySendImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       /* messageModel = new MessageModel();

        storageReference = FirebaseStorage.getInstance().getReference("Chats");

        Bundle bundle = getIntent().getExtras();

        if (bundle !=null){


            url = bundle.getString("u");
            recceiver_name = bundle.getString("n");
            reveiver_uid = bundle.getString("ruid");
            sender_uid = bundle.getString("suid");
        }
        else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        Picasso.get().load(url).into(binding.ivSendImg);
        imageurl = Uri.parse(url);

        rooRef1 = database.getReference("image").child(sender_uid).child(reveiver_uid);
        rootRef2 = database.getReference("image").child(reveiver_uid).child(sender_uid);

        binding.btnSendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendImage();

                binding.tvDont.setVisibility(View.VISIBLE);
            }
        });


    }

    private String getFileExt (Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }

    private void sendImage() {

        if (imageurl !=null){

            binding.pbSendImg.setVisibility(View.VISIBLE);

            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(imageurl));
            uploadTask = reference.putFile(imageurl);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){

                        Uri downloadUri = task.getResult();

                        Calendar cdate = Calendar.getInstance();
                        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MM-yyyy");
                        final String savedate = currentdate.format(cdate.getTime());

                        Calendar ctime = Calendar.getInstance();
                        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                        final String savetime = currenttime.format(ctime.getTime());

                        String time = savedate+":"+savetime;

                        messageModel.setMwssage(downloadUri.toString());
                        messageModel.setMwssage("photo");

                        String id = rooRef1.push().getKey();
                        rooRef1.child(id).setValue(messageModel);

                        String id1 = rootRef2.push().getKey();
                        rootRef2.child(id1).setValue(messageModel);

                        binding.pbSendImg.setVisibility(View.INVISIBLE);
                        binding.tvDont.setVisibility(View.INVISIBLE);







                    }
                }
            });


        }else {

            Toast.makeText(this, "Please select something", Toast.LENGTH_SHORT).show();
        }*/

    }
}