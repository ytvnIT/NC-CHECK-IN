package com.example.login.Menu.TKB;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTKB extends Fragment {
    private int day;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private List<TKB> tkbList = new ArrayList<TKB>();
    private List<TKB> tkbs = new ArrayList<TKB>();
    private DB_TKB db_tkb;

    public FragmentTKB() {
    }

    @SuppressLint("ValidFragment")
    public FragmentTKB(int day) {
        this.day = day;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //KHoi tao fragment
        View view = inflater.inflate(R.layout.fragment_tkb,container,false);


        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tkb);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final CustomAdapter customAdapter = new CustomAdapter(getContext(),tkbList);

        db_tkb = new DB_TKB(getContext());

        List<TKB> t =null;
        switch (day){
            case 2:
                t = db_tkb.getTKB(2);
                tkbList.addAll(t);
                break;
            case 3:
                t = db_tkb.getTKB(3);
                tkbList.addAll(t);
                break;
            case 4:
                t = db_tkb.getTKB(4);
                tkbList.addAll(t);
                break;
            case 5:
                t = db_tkb.getTKB(5);
                tkbList.addAll(t);
                break;
            case 6:
                t = db_tkb.getTKB(6);
                tkbList.addAll(t);
                break;
            case 7:
                t = db_tkb.getTKB(7);
                tkbList.addAll(t);
                break;

//            case 8:
//                t = db_tkb.getTKB(8);
//                tkbList.addAll(t);
//                break;
        }
//
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapter);
        return view;
    }


}
