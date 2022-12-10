package com.softaloy.softchaat.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.softaloy.softchaat.Model.MessageModel;
import com.softaloy.softchaat.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter{


    ArrayList<MessageModel> list;
    Context context;

    String recId;
    int Sender_View_Type = 1;
    int Reciever_View_type = 2;

    public ChatAdapter(ArrayList<MessageModel> list, Context context, String recId) {
        this.list = list;
        this.context = context;
        this.recId = recId;
    }

    public ChatAdapter(ArrayList<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType== Sender_View_Type){

            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent,false);
            return new senderViewHolder(view);
        }

        else {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent,false);
            return new recieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){

             return Sender_View_Type;

        }else {
            return Reciever_View_type;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = list.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure! You want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;

                                database.getReference().child("Chats").child(senderRoom).child("messages")
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if (holder.getClass()== senderViewHolder.class){
            senderViewHolder viewHolder = (senderViewHolder)holder;

           if (messageModel.getMwssage().equals("photo")){


                viewHolder.tv_sender.setVisibility(View.GONE);
                viewHolder.senderMsg.setVisibility(View.GONE);
                viewHolder.senderTime.setVisibility(View.GONE);
                viewHolder.iv_send_img.setVisibility(View.VISIBLE);
                viewHolder.image_send.setVisibility(View.VISIBLE);
                Picasso.get().load(messageModel.getImageUrl()).into(viewHolder.image_send);

            }


            viewHolder.senderMsg.setText(messageModel.getMwssage());



            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date);
            ((senderViewHolder)holder).senderTime.setText(strDate.toString());

        }else {

           recieverViewHolder viewHolder = (recieverViewHolder)holder;

            if (messageModel.getMwssage().equals("photo")){

                viewHolder.reciver_img.setVisibility(View.VISIBLE);
                viewHolder.recieverMsg.setVisibility(View.GONE);
                Picasso.get().load(messageModel.getImageUrl()).into(viewHolder.reciver_img);
            }


            ((recieverViewHolder)holder).recieverMsg.setText(messageModel.getMwssage());

            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date);
            ((recieverViewHolder)holder).recieverTime.setText(strDate.toString());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class recieverViewHolder extends RecyclerView.ViewHolder {

        TextView recieverMsg, recieverTime;
        ImageView reciver_img;

        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverMsg = itemView.findViewById(R.id.reciever_text);
            recieverTime = itemView.findViewById(R.id.reciever_time);
            reciver_img = itemView.findViewById(R.id.recive_img);
        }
    }

    public class senderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMsg, senderTime;
        ImageView image_send;
        ConstraintLayout iv_send_img, tv_sender;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.sender_text);
            senderTime = itemView.findViewById(R.id.sender_time);
            image_send = itemView.findViewById(R.id.image_send);
            tv_sender = itemView.findViewById(R.id.tv_sender);
            iv_send_img = itemView.findViewById(R.id.iv_send_img);

        }
    }

}
