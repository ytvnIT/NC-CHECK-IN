package com.example.login.Menu.TKB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    Context context;
    List<TKB> listTKB;
    public CustomAdapter(Context context, List<TKB> listTKB) {
        this.context = context;
        this.listTKB = listTKB;
    }
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tkb, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        int thu = listTKB.get(position).getId();
        String tiet = listTKB.get(position).getTiet();
        String phong = listTKB.get(position).getPhong();
        holder.tvMonhoc.setText(listTKB.get(position).getMamh());
        holder.tvTiet.setText("Thứ "+thu +" tiết " + tiet + " P."+phong);
        holder.tvGv.setText(listTKB.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listTKB.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMonhoc;
        TextView tvTiet;
        TextView tvGv;



        public ViewHolder(View itemView) {
            super(itemView);
            tvMonhoc = itemView.findViewById(R.id.tv_monhoc);
            tvGv = itemView.findViewById(R.id.tv_gv);
            tvTiet = itemView.findViewById(R.id.tv_tiet);
        }
    }
}
