package com.loan.time.adaperts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.bean.RvBean;

import java.util.ArrayList;

public class PlatRvAdapert extends RecyclerView.Adapter<PlatRvAdapert.PlatHolder> {

    private ArrayList<String> mList;
    private Context context;

    public PlatRvAdapert(ArrayList<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plat_item, parent,false);
        return new PlatRvAdapert.PlatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatHolder holder, int position) {
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PlatHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public PlatHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
        }
    }
}
