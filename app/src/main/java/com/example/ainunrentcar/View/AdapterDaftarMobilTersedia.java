package com.example.ainunrentcar.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelDaftarMobilTersedia modelDaftarMobilTersedia = list.get(position);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.image_null);
//        requestOptions.error(R.drawable.image_null);
//        Glide.with(holder.imgSlide)
//                .setDefaultRequestOptions(requestOptions)
//                .load(list.get(position).getUrlGambar())
//                .apply(new RequestOptions().centerCrop())
//                .into(holder.imgSlide);
        holder.namaMitra.setText(String.valueOf(modelDaftarMobilTersedia.getNamaMitra()));
        holder.unitBrand.setText(String.valueOf(modelDaftarMobilTersedia.getUnitBrand()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namaMitra, unitBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            namaMitra = itemView.findViewById(R.id.namaMitra);
            unitBrand = itemView.findViewById(R.id.unitBrand);
        }
    }

}