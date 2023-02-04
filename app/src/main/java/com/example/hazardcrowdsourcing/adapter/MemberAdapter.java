package com.example.hazardcrowdsourcing.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hazardcrowdsourcing.Model.Member;
import com.example.hazardcrowdsourcing.R;

import java.util.List;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.PopularFoodViewHolder> {

    Context context;
    Dialog myDialog;
    List<Member> memberList;



    public MemberAdapter(Context context, List<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.team_list, parent, false);
        myDialog = new Dialog(context);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {



        holder.foodImage.setImageResource(memberList.get(position).getImageUrl());
        holder.name.setText(memberList.get(position).getName());
        holder.sid.setText(memberList.get(position).getSid());
        holder.group.setText(memberList.get(position).getGroup());
        holder.code.setText(memberList.get(position).getCode());
        holder.fakulti.setText(memberList.get(position).getFakulti());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtclose,sid,name,group,code,fakulti;
                myDialog.setContentView(R.layout.custompopup);
                ImageView image;
                image = (ImageView) myDialog.findViewById(R.id.profile1);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                sid =(TextView) myDialog.findViewById(R.id.sid);
                name =(TextView) myDialog.findViewById(R.id.name);
                group =(TextView) myDialog.findViewById(R.id.group);
                code =(TextView) myDialog.findViewById(R.id.code);
                fakulti =(TextView) myDialog.findViewById(R.id.fakulti);

                txtclose.setText("x");
                image.setImageResource(memberList.get(position).getImageUrl());
                sid.setText(memberList.get(position).getSid());
                name.setText(memberList.get(position).getName());
                group.setText(memberList.get(position).getGroup());
                code.setText(memberList.get(position).getCode());
                fakulti.setText(memberList.get(position).getFakulti());


                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView sid, name,group,code,fakulti;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            name = itemView.findViewById(R.id.name);
            sid = itemView.findViewById(R.id.sid);
            group = itemView.findViewById(R.id.group);
            code = itemView.findViewById(R.id.code);
            fakulti = itemView.findViewById(R.id.fakulti);



        }
    }

}
