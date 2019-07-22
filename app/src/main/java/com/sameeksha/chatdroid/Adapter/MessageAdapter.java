package com.sameeksha.chatdroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sameeksha.chatdroid.MessageActivity;
import com.sameeksha.chatdroid.Model.Chat;
import com.sameeksha.chatdroid.Model.User;
import com.sameeksha.chatdroid.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    FirebaseUser fuser;

    private Context mContext;
    private List<Chat> mChat;
    private String image_url;

    public MessageAdapter(Context mContext, List<Chat> mChat, String image_url) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.image_url = image_url;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());

        if ((image_url.equals("default")))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(image_url).into(holder.profile_image);
        }

        if (position == mChat.size()-1)
        {
            if (chat.isIsseen())
            {
                holder.text_seen.setText("Seen");
            } else {
                holder.text_seen.setText("Delivered");
            }
        } else
            holder.text_seen.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public ImageView profile_image;

        public TextView text_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            text_seen = itemView.findViewById(R.id.text_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
