package com.example.ainunrentcar.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ainunrentcar.DetailMobilActivity;
import com.example.ainunrentcar.Model.ModelDaftarMobilTersedia;
import com.example.ainunrentcar.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDaftarMobilTersedia extends RecyclerView.Adapter<AdapterDaftarMobilTersedia.ViewHolder> {

    private Context context;
    private List<ModelDaftarMobilTersedia> list;

    public AdapterDaftarMobilTersedia(Context context, List<ModelDaftarMobilTersedia> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_view_daftar_mobil_tersedia, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelDaftarMobilTersedia modelDaftarMobilTersedia = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_null);
        requestOptions.error(R.drawable.image_null);
        Glide.with(holder.urlImgUnit)
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getUrlImgUnit())
                .apply(new RequestOptions().centerCrop())
                .into(holder.urlImgUnit);
        holder.hargaPerhari.setText(String.valueOf(modelDaftarMobilTersedia.getHargaPerhari()) + " / Hari");
        holder.namaMitra.setText(String.valueOf(modelDaftarMobilTersedia.getNamaMitra()));
        holder.namaLengkapUnit.setText(String.valueOf(modelDaftarMobilTersedia.getNamaLengkapUnit()));
        holder.alamatMitra.setText(String.valueOf(modelDaftarMobilTersedia.getAlamatMitra()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namaMitra, namaLengkapUnit, hargaPerhari, alamatMitra;
        public ImageView urlImgUnit;
        public CardView clickPilihMobil;

        public ViewHolder(View itemView) {
            super(itemView);
            hargaPerhari = itemView.findViewById(R.id.hargaPerhari);
            namaMitra = itemView.findViewById(R.id.namaMitra);
            namaLengkapUnit = itemView.findViewById(R.id.namaLengkapUnit);
            alamatMitra = itemView.findViewById(R.id.alamatMitra);
            urlImgUnit = itemView.findViewById(R.id.imgUnit);
            clickPilihMobil = itemView.findViewById(R.id.clickPilihMobil);
            clickPilihMobil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idMobil = list.get(getAdapterPosition()).idMobil;
                    Intent i = new Intent(context, DetailMobilActivity.class);
                    i.putExtra("idMobil", idMobil);
                    context.startActivity(i);
                }
            });

        }
    }

}