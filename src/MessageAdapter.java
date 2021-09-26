package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TITLE_LEFT = 0;
    public static final int MSG_TITLE_RIGHT = 1;

    private Context mcontext;
    private ArrayList<Chat> mchat;

    FirebaseUser fuser;

    private ArrayList<Users> musers = new ArrayList<Users>();

    public MessageAdapter(Context mcontext, ArrayList<Users> mchat){
        this.mcontext = mcontext;
    }

    public MessageAdapter(UserFragment userFragment, ArrayList<Users> musers) {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TITLE_RIGHT) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());

        if(position == mchat.size()-1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            }else
                holder.txt_seen.setText("Delivered");
        }else{
            holder.txt_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message, txt_seen;

        public ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mchat.get(position).getSender().equals(fuser.getEmail()))
            return MSG_TITLE_RIGHT;
        else
            return MSG_TITLE_LEFT;
    }
}
