package com.softaloy.softchaat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softaloy.softchaat.Adapter.ChatAdapter;
import com.softaloy.softchaat.Model.MessageModel;
import com.softaloy.softchaat.databinding.ActivityChatDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Chat_Detail_Activity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ProgressDialog dialog;


    String receiver_name, receiver_uid, sender_uid, url;
     String senderId;
    String recieverId;
    String userName;
    String profilePic;
    String senderRoom, recieverRoom;

    Uri uri;
    private static final int pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image.....");
        dialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

       senderId = auth.getUid();
       recieverId = getIntent().getStringExtra("userId");
       userName = getIntent().getStringExtra("userName");
       profilePic = getIntent().getStringExtra("profilePic");

        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.plc_person).into(binding.chatUserProfileImg);

        binding.arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chat_Detail_Activity.this, MainActivity.class));
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
         final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, recieverId);
         binding.chatRecyclerview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerview.setLayoutManager(layoutManager);

         senderRoom = senderId + recieverId;
         recieverRoom = recieverId + senderId;

        database.getReference().child("Chats").child(senderRoom).child("messages")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();

                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {
                                    MessageModel model = snapshot1.getValue(MessageModel.class);

                                    model.setMessageId(snapshot1.getKey());

                                    messageModels.add(model);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




        binding.sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.msgBox.getText().toString().isEmpty()) {
                    binding.msgBox.setError("Write your Message");

                    return;
                }

                String message = binding.msgBox.getText().toString();
                final MessageModel model= new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.msgBox.setText("");

                database.getReference().child("Chats").child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("Chats").child(recieverRoom)
                                        .child("messages")
                                       .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });


                            }
                        });

            }
        });

        //sending image working here..........................

        binding.camIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // /*/
                startActivityForResult(intent , 25);

            }
        });



//////////sending Image working end  here,,..............................

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25){
            if (data !=null){
                if (data.getData() !=null){

                    Uri selectedImg = data.getData();
                    Calendar calendar = Calendar.getInstance();
                    StorageReference reference = storage.getReference().child("Chats").child(calendar.getTimeInMillis()+ "");
                    dialog.show();
                    reference.putFile(selectedImg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            dialog.dismiss();
                            if (task.isSuccessful()){

                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String filepath = uri.toString();

                                        String message = binding.msgBox.getText().toString();
                                        final MessageModel model= new MessageModel(senderId,message);
                                        model.setTimestamp(new Date().getTime());
                                        model.setMwssage("photo");
                                        model.setImageUrl(filepath);
                                        binding.msgBox.setText("");

                                        database.getReference().child("Chats").child(senderRoom)
                                                .child("messages")
                                                .push()
                                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        database.getReference().child("Chats").child(recieverRoom)
                                                                .child("messages")
                                                                .push()
                                                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {

                                                                    }
                                                                });

                                                    }
                                                });


                                        Toast.makeText(Chat_Detail_Activity.this, filepath, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    });

                }
            }
            
        }


    }
}