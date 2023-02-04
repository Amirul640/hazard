package com.example.hazardcrowdsourcing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.hazardcrowdsourcing.Model.Member;
import com.example.hazardcrowdsourcing.adapter.MemberAdapter;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements  AdapterView.OnItemClickListener{

    Dialog myDialog;
    RecyclerView memberRecycler, asiaRecycler;
    MemberAdapter memberAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
               View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);//Make sure you have this line of code.
        myDialog = new Dialog(getContext());


        List<Member> memberList = new ArrayList<>();

        memberList.add(new Member("MUHAMMAD FAIZUL FANSURI BIN MANSOR","2020470202","RCS2405B","CS240","FSKM", R.drawable.faizul));
        memberList.add(new Member("MOHAMAD AMIRUL IKRAM BIN NORDIN","2020853042","RCS2405B","CS240","FSKM", R.drawable.ikram));
        memberList.add(new Member("MUHAMMAD ASHRAF BIN ABD RAHMAN","2020131597","RCS2405A","CS240","FSKM",R.drawable.ashraf));
        memberList.add(new Member("HALIMATUN SA'DIAH BT ROSMI","2020845226","RCS2405A","CS240","FSKM", R.drawable.halimatun));

        memberRecycler = rootView.findViewById(R.id.popular_recycler);
        setMemberRecycler(memberList);

        rootView.findViewById(R.id.github_link).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.repo_link))));
        });

        return rootView;
    }

    private void setMemberRecycler(List<Member> memberList) {


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        memberRecycler.setLayoutManager(layoutManager);
        memberAdapter = new MemberAdapter(getContext(), memberList);
        memberRecycler.setAdapter(memberAdapter);

    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Copyright");
        alertDialog.setMessage("Hazard Crowsourcing Mobile Application ©2023 Hazard Crowsourcing, Inc. All Right Reserved ");
        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        // onClick button code here
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });
        alertDialog.show();
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.copyright, menu) ;
//    }

    private void URL(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.copy:
//                showDialog();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//



}