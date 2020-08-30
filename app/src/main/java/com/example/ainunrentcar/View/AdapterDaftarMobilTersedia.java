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


public class AdapterDaftarMobilTersedia extends RecyclerView.Adapter<AdapterDaftarMobilTersedia.DaftarMobilTersediaViewHolder> {

    private ArrayList<ModelDaftarMobilTersedia> dataList;

    public AdapterDaftarMobilTersedia(ArrayList<ModelDaftarMobilTersedia> dataList) {
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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_null);
        requestOptions.error(R.drawable.image_null);
        Glide.with(holder.imageViewL)
                .setDefaultRequestOptions(requestOptions)
                .load(dataList.get(position).getUrlGambar())
                .apply(new RequestOptions().fitCenter())
                .into(holder.imageViewL);
//        holder.txtNama.setText(dataList.get(position).getNama());
//        holder.txtNpm.setText(dataList.get(position).getNpm());
//        holder.txtNoHp.setText(dataList.get(position).getNohp());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarMobilTersediaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtNpm, txtNoHp;
        private ImageView imageViewL;

        public DaftarMobilTersediaViewHolder(View itemView) {
            super(itemView);
            imageViewL = (ImageView) itemView.findViewById(R.id.contoh);
//            txtNama = (TextView) itemView.findViewById(R.id.txt_nama_mahasiswa);
//            txtNpm = (TextView) itemView.findViewById(R.id.txt_npm_mahasiswa);
//            txtNoHp = (TextView) itemView.findViewById(R.id.txt_nohp_mahasiswa);
        }
    }
}