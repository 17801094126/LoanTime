package com.loan.time.adaperts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.bean.ResponseBean;

import java.util.ArrayList;

public class HomeRvAdapert extends RecyclerView.Adapter<HomeRvAdapert.HomeRvHolder> {

    private Context context;
    private ArrayList<ResponseBean> mList;

    public HomeRvAdapert(Context context, ArrayList<ResponseBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HomeRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_item, parent,false);
        return new HomeRvAdapert.HomeRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HomeRvHolder extends RecyclerView.ViewHolder {

        ImageView img_item;
        TextView tv_name;
        TextView tv_price;
        TextView tv_Interest_number;
        TextView tv_content;

        public HomeRvHolder(@NonNull View itemView) {
            super(itemView);

            img_item=itemView.findViewById(R.id.img_item);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_Interest_number=itemView.findViewById(R.id.tv_Interest_number);
            tv_content=itemView.findViewById(R.id.tv_content);
        }
    }
}
