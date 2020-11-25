package com.loan.time.adaperts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.bean.ResponseBean;

import java.util.ArrayList;

public class HomeRvAdapert extends RecyclerView.Adapter<HomeRvAdapert.HomeRvHolder> {

    private Context context;
    private ArrayList<ResponseBean> mList;
    private int status;//首页0    列表页1
    private RvItemClickListener listener;

    public HomeRvAdapert(Context context, ArrayList<ResponseBean> mList,int status) {
        this.context = context;
        this.mList = mList;
        this.status=status;
    }

    @NonNull
    @Override
    public HomeRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_item, parent,false);
        return new HomeRvAdapert.HomeRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvHolder holder, int position) {

        switch (status){
            case 0:
                holder.tvback.setAlpha(0.05f);
                holder.tvback.setBackground(ContextCompat.getDrawable(context,R.drawable.item_tv_back));
                break;
            case 1:
                holder.tvback.setAlpha(1.0f);
                holder.tvback.setBackground(ContextCompat.getDrawable(context,R.drawable.list_item_tv_back));
                break;
        }
        holder.allView.setOnClickListener(v -> {
           if (listener!=null)
               listener.onClickerListener(position);
        });

    }

    public void setOnClickListener(RvItemClickListener listener){
        this.listener=listener;
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
        View tvback;
        ConstraintLayout allView;

        public HomeRvHolder(@NonNull View itemView) {
            super(itemView);

            allView=itemView.findViewById(R.id.all_view);
            img_item=itemView.findViewById(R.id.img_item);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_Interest_number=itemView.findViewById(R.id.tv_Interest_number);
            tv_content=itemView.findViewById(R.id.tv_content);
            tvback=itemView.findViewById(R.id.tv_back);
        }
    }

    public interface RvItemClickListener{
        void onClickerListener(int position);
    }
}
