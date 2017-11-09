package com.example.seven.myapplication.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seven.myapplication.Enums.OrderStatusEnum;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/25.
 */
public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<QueryOrderData> list;

    public MyAdapter(Context context, ArrayList<QueryOrderData> list) {
        this.context = context;
        this.list = list;
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (QueryOrderData) v.getTag());
        }
    }


    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, QueryOrderData data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        ViewHodler vh=new ViewHodler(view);
        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHodler mHodler = (ViewHodler) holder;

        mHodler.orderView.setText(list.get(position).getOrderSn());
        mHodler.amountView.setText(list.get(position).getAmount());
        mHodler.dateView.setText((DateUtil.TimestampToString(Long.valueOf(list.get(position).getModifyDate()),DateStyle.YYYY_MM_DD_HH_MM_SS_EN)));
        mHodler.payTypeView.setText(list.get(position).getPayTypeTxt());
        mHodler.statusView.setText(list.get(position).getStatus());
        mHodler.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHodler extends RecyclerView.ViewHolder{

        private TextView amountView;
        private TextView orderView;
        private TextView dateView;
        private TextView payTypeView;
        private TextView statusView;
//        private ImageView imageView;
        public ViewHodler(View itemView) {
            super(itemView);

            amountView= (TextView) itemView.findViewById(R.id.amount_view);
            orderView= (TextView) itemView.findViewById(R.id.order_view);
            dateView= (TextView) itemView.findViewById(R.id.date_view);
            payTypeView= (TextView) itemView.findViewById(R.id.pay_type_view);
            statusView= (TextView) itemView.findViewById(R.id.status_view);
//            imageView= (ImageView) itemView.findViewById(R.id.image);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context,"click"+getPosition(),Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
