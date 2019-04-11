package com.example.tree;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    private ArrayList<Leaf> mDataset;
    static Map<String, String> colorAssociations = new HashMap<String, String>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private TextView userNameView;
        private ImageButton chatBtn;
        private ImageButton addFriendBtn;

        private RelativeLayout rl;


        public TextView getUserNameView() {
            return userNameView;
        }

        public void setUserNameView(TextView userNameView) {
            this.userNameView = userNameView;
        }

        public ImageButton getChatBtn() {
            return chatBtn;
        }

        public void setChatBtn(ImageButton chatBtn) {
            this.chatBtn = chatBtn;
        }

        public ImageButton getAddFriendBtn() {
            return addFriendBtn;
        }

        public void setAddFriendBtn(ImageButton addFriendBtn) {
            this.addFriendBtn = addFriendBtn;
        }

        public RelativeLayout getRl() {
            return rl;
        }

        public void setRl(RelativeLayout rl) {
            this.rl = rl;
        }

        public MyViewHolder(View v) {
            super(v);
            userNameView = v.findViewById(R.id.userName);
            chatBtn = v.findViewById(R.id.chatBtn);
            addFriendBtn = v.findViewById(R.id.addFriendBtn);
            //rl = v.findViewById(R.id.msgBg);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersAdapter(ArrayList<Leaf> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public  UsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.user_view, parent , false);
        viewHolder = new MyViewHolder(v);
        MyViewHolder vh = new MyViewHolder(v);
        return (MyViewHolder)viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        MyViewHolder m = (MyViewHolder)holder;
        configureViewHolder(m , position);

    }
    private void configureViewHolder(MyViewHolder vh1, final int position) {
        vh1.getUserNameView().setText(mDataset.get(position).getName());
        vh1.getChatBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo.otherUserName = mDataset.get(position).getName();
                UserInfo.otherUserEmail = mDataset.get(position).getEmail();
                Home.room = "privatechatrooms/" + UserInfo.user.getName() + "_" + UserInfo.otherUserName;
                Home.room2 = "privatechatrooms/" + UserInfo.otherUserName + "_" + UserInfo.user.getName();
                v.getContext().startActivity(new Intent(v.getContext() , Home.class));
            }
        });
        System.out.println("NAME: " + mDataset.get(position).getName());
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public String generateRandomColor() {
        String color;
        String[] range = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        String c0 = range[(int) (Math.random() * range.length)];
        String c1 = range[(int) (Math.random() * range.length)];
        String c2 = range[(int) (Math.random() * range.length)];
        String c3 = range[(int) (Math.random() * range.length)];
        String c4 = range[(int) (Math.random() * range.length)];
        String c5 = range[(int) (Math.random() * range.length)];
        String c6 = range[(int) (Math.random() * range.length)];
        String c7 = range[(int) (Math.random() * range.length)];

        color = "#" + c0 + c1 + c2 + c3 + c4 + c5;
        // color = "#" + c0 + c1 + c2;
        return color;
    }
}