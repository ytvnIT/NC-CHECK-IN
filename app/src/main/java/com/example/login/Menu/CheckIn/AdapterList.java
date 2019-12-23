package com.example.login.Menu.CheckIn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.login.R;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<CheckInList> checkInLists;

    public AdapterList(Context context, int layout, ArrayList<CheckInList> checkInLists) {
        this.context = context;
        this.layout = layout;
        this.checkInLists = checkInLists;
    }

    @Override
    public int getCount() {
        return checkInLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private  class ViewHolder{
        TextView tvCheckInList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(layout, null);
            holder.tvCheckInList=convertView.findViewById(R.id.tvCheckInList);
            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        CheckInList checkInList = checkInLists.get(position);
        holder.tvCheckInList.setText("Sinh viên " + checkInList.getMAHV() +" điểm danh thành công lớp: " +
                checkInList.getMAMH() + "\n" +
                checkInList.getTime());
        return convertView;
    }
}
