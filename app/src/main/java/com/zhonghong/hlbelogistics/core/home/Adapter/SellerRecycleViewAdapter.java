package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public class SellerRecycleViewAdapter extends RecyclerView.Adapter<SellerRecycleViewAdapter.MyViewHolder> implements View.OnClickListener {
    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private int postion;
    private MyViewHolder holder;
    private View view;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }

    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public SellerRecycleViewAdapter(Context context, List<String> datas,int postion){
        this. mContext=context;
        this.mDatas=datas;
        this.postion=postion;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (postion) {
            case R.id.infoshow_rbBuyers:
                view= inflater.inflate(R.layout.infoshow_buyers_item,parent, false);
                holder= new MyViewHolder(view);
                view.setOnClickListener(this);
                break;
            case R.id.infoshow_rbSellers:
                view = inflater.inflate(R.layout.infoshow_sellers_item,parent, false);
                holder= new MyViewHolder(view);
                view.setOnClickListener(this);
                break;
            case R.id.infoshow_rbGoods:
                view = inflater.inflate(R.layout.infoshow_goods_item,parent, false);
                holder= new MyViewHolder(view);
                view.setOnClickListener(this);
                break;
            case R.id.infoshow_rbCarSource:
                view = inflater.inflate(R.layout.infoshow_carsource_item,parent, false);
                holder= new MyViewHolder(view);
                view.setOnClickListener(this);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        holder.itemView.setTag(mDatas.get(position));

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.infoshow_buyers_item_title);
        }

    }

}
