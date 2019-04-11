package com.example.tree;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private ArrayList<Message> mDataset;
    static Map<String, String> colorAssociations = new HashMap<String, String>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView userNameView;
        private TextView messsageView;
        private TextView timeStampView;

        private RelativeLayout rl;

        public MyViewHolder(View v) {
            super(v);
            userNameView = v.findViewById(R.id.avatar);
            messsageView = v.findViewById(R.id.message);
            timeStampView = v.findViewById(R.id.msgTimeStamp);
            rl = v.findViewById(R.id.msgBg);
        }
        public TextView getUserNameView() {
            return userNameView;
        }

        public RelativeLayout getRl() {
            return rl;
        }

        public void setRl(RelativeLayout rl) {
            this.rl = rl;
        }

        public void setUserNameView(TextView userNameView) {
            this.userNameView = userNameView;
        }

        public TextView getMesssageView() {
            return messsageView;
        }

        public void setMesssageView(TextView messsageView) {
            this.messsageView = messsageView;
        }

        public TextView getTimeStampView() {
            return timeStampView;
        }

        public void setTimeStampView(TextView timeStampView) {
            this.timeStampView = timeStampView;
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessagesAdapter(ArrayList<Message> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.message_view, parent , false);
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
    private void configureViewHolder(MyViewHolder vh1, int position) {
       // System.out.print(mDataset.get(0).getMessage());
        if(mDataset.get(position).getUserName().equals(UserInfo.user.getName())){
            vh1.getUserNameView().setText("You");
           // String color = generateRandomColor();
            if (!colorAssociations.containsKey(mDataset.get(position).getUserName())) {
                colorAssociations.put(mDataset.get(position).getUserName(), generateRandomColor());
                //colorAssociations.put("color", generateRandomColor());
            }
            vh1.getRl().setBackgroundColor(Color.parseColor(colorAssociations.get(mDataset.get(position).getUserName())));
            System.out.println("COLOR: " + colorAssociations.get(mDataset.get(position).getUserName()));
        }else{
            vh1.getUserNameView().setText(mDataset.get(position).getUserName());
            if (!colorAssociations.containsKey(mDataset.get(position).getUserName())) {
                colorAssociations.put(mDataset.get(position).getUserName(), generateRandomColor());
                //colorAssociations.put("color", generateRandomColor());
            }
            vh1.getRl().setBackgroundColor(Color.parseColor(colorAssociations.get(mDataset.get(position).getUserName())));
            //System.out.println("COLOR: " + colorAssociations.get(mDataset.get(position).getUserName()));
        }

        vh1.getMesssageView().setText(mDataset.get(position).getMessage());

        String dateStr;
        Date d = mDataset.get(position).getD();
        //SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
        dateStr = displayFormat.format(d);

        vh1.getTimeStampView().setText(dateStr);

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