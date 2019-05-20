package com.example.uchat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uchat.Model.Chat;
import com.example.uchat.R;
import com.example.uchat.listener.AudioClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    private AudioClickListener listener;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl, AudioClickListener listener) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, final int position) {

        final Chat chat = mChat.get(position);


        if (chat.getType().equals("text")) {
            holder.show_message.setText(chat.getMessage());
            Linkify.addLinks(holder.show_message, Linkify.ALL);
            holder.show_message.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (chat.getType().equals("audio")) {
            holder.profile_image.setVisibility(View.GONE);
            holder.show_message.setVisibility(View.GONE);
            holder.ivImage.setVisibility(View.GONE);
            holder.ivAudio.setVisibility(View.VISIBLE);
        }else{
            holder.profile_image.setVisibility(View.GONE);
            holder.show_message.setVisibility(View.GONE);
            holder.ivAudio.setVisibility(View.GONE);
            holder.ivImage.setVisibility(View.VISIBLE);

            Glide.with(mContext).load(chat.getMessage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.ivImage);
        }

        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if (position == mChat.size() - 1) {
            if (chat.isIsseen()) {
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

        holder.ivAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAudio(position, chat.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public ImageView ivAudio;
        public ImageView ivImage;

        public ViewHolder(final View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            ivAudio = itemView.findViewById(R.id.iv_audio);
            ivImage = itemView.findViewById(R.id.iv_msg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}