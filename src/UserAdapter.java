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


public class USerAdapter extends RecyclerView.Adapter<USerAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Users> musers = new ArrayList<Users>();
    private boolean ischat;

    public USerAdapter(Context mcontext, ArrayList<Users> musers, boolean ischat){
        this.musers = musers;
        this.mcontext = mcontext;
        this.ischat = ischat;
    }

    public USerAdapter(UserFragment userFragment, ArrayList<Users> musers) {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_item,parent,false);
        return new USerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = musers.get(position);
        holder.username.setText(user.getUsername());

        if(ischat){
            if(user.getStatus().equals("Online")){
                holder.online.setVisibility(View.VISIBLE);
                holder.offline.setVisibility(View.GONE);
            }else{
                holder.offline.setVisibility(View.VISIBLE);
                holder.online.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("user_email", user.getEmail());
                mcontext.startActivities(new Intent[]{intent});
            }
        });
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        private TextView online, offline;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            online = itemView.findViewById(R.id.online);
            offline = itemView.findViewById(R.id.offline);
        }
    }
}
