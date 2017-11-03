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
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.QueryResult;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/25.
 */
public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<QueryResult.Info> list;

    public MyAdapter(Context context, ArrayList<QueryResult.Info> list) {
        this.context = context;
        this.list = list;
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (QueryResult.Info) v.getTag());
        }
    }


    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, QueryResult.Info data);
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

        mHodler.textview.setText("this is item "+position );
        Glide.with(context)
                .load(list.get(position).url)
                .centerCrop()
                .placeholder(R.color.app_primary_color)
                .crossFade()
                .into(mHodler.imageView);
        Log.i("aaaa", list.get(position)._id);
        mHodler.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHodler extends RecyclerView.ViewHolder{

        private TextView textview;
        private ImageView imageView;
        public ViewHodler(View itemView) {
            super(itemView);
            textview= (TextView) itemView.findViewById(R.id.textview);
            imageView= (ImageView) itemView.findViewById(R.id.image);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context,"click"+getPosition(),Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
