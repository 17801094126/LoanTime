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
import com.loan.time.bean.RvBean;

import java.util.ArrayList;

public class AuthorityRv extends RecyclerView.Adapter<AuthorityRv.AuthorityHolder> {

    private ArrayList<RvBean> mList;
    private Context context;

    public AuthorityRv(ArrayList<RvBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.authority_item, parent,false);
        return new AuthorityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorityHolder holder, int position) {

        holder.img.setImageDrawable(mList.get(position).getDrawable());
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvContent.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AuthorityHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvTitle;
        TextView tvContent;

        public AuthorityHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.rv_img);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvContent=itemView.findViewById(R.id.tv_content);
        }
    }
}
