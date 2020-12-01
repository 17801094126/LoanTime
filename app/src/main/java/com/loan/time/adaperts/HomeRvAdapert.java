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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.loan.time.R;
import com.loan.time.bean.ResponseBean;

import java.util.ArrayList;
import java.util.List;

public class HomeRvAdapert extends RecyclerView.Adapter<HomeRvAdapert.HomeRvHolder> {

    private Context context;
    private List<ResponseBean.DataBean.ProductListBean> mList;
    private int status;//首页0    列表页1
    private RvItemClickListener listener;

    public HomeRvAdapert(Context context, List<ResponseBean.DataBean.ProductListBean> mList,int status) {
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
        ResponseBean.DataBean.ProductListBean data = mList.get(position);
        holder.tv_name.setText(data.getTitle());
        holder.tv_price.setText("₹"+data.getMaxBorrowAmt());
        holder.tv_Interest_number.setText(data.getInterestCycleRate()+"%/Day");
        holder.tv_content.setText(data.getProductIntroduction());
        RequestOptions options=new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        //Glide 加载图片简单用法
        Glide.with(context).load(data.getLogo()).apply(options).into(holder.img_item);
        switch (data.getStatus()){
            case "publish":
                //正常
                holder.tv_bt.setText("Apply Now");
                holder.tv_bt.setBackground(ContextCompat.getDrawable(context,R.drawable.home_rv_bt_back));
                break;
            case "uv_partner_limit":
                //名额已满
                holder.tv_bt.setText("Full");
                holder.tv_bt.setBackground(ContextCompat.getDrawable(context,R.drawable.home_rv_bt_full));
                break;
            case "applied":
                //今日已申请
                holder.tv_bt.setText("Full");
                holder.tv_bt.setBackground(ContextCompat.getDrawable(context,R.drawable.home_rv_bt_full));
                break;
        }
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
        return mList.size();
    }

    public class HomeRvHolder extends RecyclerView.ViewHolder {

        ImageView img_item;
        TextView tv_name;
        TextView tv_price;
        TextView tv_Interest_number;
        TextView tv_content;
        View tvback;
        ConstraintLayout allView;
        TextView tv_bt;

        public HomeRvHolder(@NonNull View itemView) {
            super(itemView);

            allView=itemView.findViewById(R.id.all_view);
            img_item=itemView.findViewById(R.id.img_item);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_Interest_number=itemView.findViewById(R.id.tv_Interest_number);
            tv_content=itemView.findViewById(R.id.tv_content);
            tvback=itemView.findViewById(R.id.tv_back);
            tv_bt=itemView.findViewById(R.id.tv_bt);
        }
    }

    public interface RvItemClickListener{
        void onClickerListener(int position);
    }
}
