package com.example.ainunrentcar.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ainunrentcar.HasilPencarianMobilActivity;
import com.example.ainunrentcar.Model.ModelDaftarMobilTersedia;
import com.example.ainunrentcar.R;

import java.util.ArrayList;


public class AdapterDaftarMobilTersedia1 extends RecyclerView.Adapter<AdapterDaftarMobilTersedia1.DaftarMobilTersediaViewHolder> {

    private ArrayList<ModelDaftarMobilTersedia> dataList;

    public AdapterDaftarMobilTersedia1(ArrayList<ModelDaftarMobilTersedia> dataList) {
        this.dataList = dataList;
    }

    @Override
    public DaftarMobilTersediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_view_daftar_mobil_tersedia, parent, false);
        return new DaftarMobilTersediaViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(DaftarMobilTersediaViewHolder holder, int position) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.image_null);
//        requestOptions.error(R.drawable.image_null);
//        Glide.with(holder.imageViewL)
//                .setDefaultRequestOptions(requestOptions)
//                .load(dataList.get(position).getUrlGambar())
//                .apply(new RequestOptions().fitCenter())
//                .into(holder.imageViewL);
        holder.namaMitra.setText(dataList.get(position).getNamaMitra());
        holder.unitBrand.setText(dataList.get(position).getUnitBrand());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarMobilTersediaViewHolder extends RecyclerView.ViewHolder {
        private TextView namaMitra, unitBrand;

        public DaftarMobilTersediaViewHolder(View itemView) {
            super(itemView);
            namaMitra = (TextView) itemView.findViewById(R.id.namaMitra);
            unitBrand = (TextView) itemView.findViewById(R.id.unitBrand);
        }
    }
}