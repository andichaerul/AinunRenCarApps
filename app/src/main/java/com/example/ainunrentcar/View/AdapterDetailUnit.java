package com.example.ainunrentcar.View;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ainunrentcar.R;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ainunrentcar.Model.ModelDetailUnit;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDetailUnit extends RecyclerView.Adapter<AdapterDetailUnit.ViewHolder> {

    private Context context;
    private List<ModelDetailUnit> list;

    public AdapterDetailUnit(Context context, List<ModelDetailUnit> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_view_img_detail_unit, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelDetailUnit modelDetailUnit = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_null);
        requestOptions.error(R.drawable.image_null);
        Glide.with(holder.urlImgUnit)
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getUrlImage())
                .apply(new RequestOptions().centerCrop())
                .into(holder.urlImgUnit);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView urlImgUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            urlImgUnit = itemView.findViewById(R.id.imgDetailUnit);

        }
    }

}