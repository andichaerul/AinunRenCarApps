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
import com.example.ainunrentcar.DetailOffersActivity;
import com.example.ainunrentcar.Model.ModelOffers;
import com.example.ainunrentcar.R;

import java.util.List;

public class AdapterOffers extends RecyclerView.Adapter<AdapterOffers.ViewHolder> {

    private Context context;
    private List<ModelOffers> list;

    public AdapterOffers(Context context, List<ModelOffers> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_view_offers_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelOffers modelOffers = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_place_holder);
        requestOptions.error(R.drawable.img_place_holder);
        Glide.with(holder.imgSlide)
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getUrlGambar())
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgSlide);
    }

//    private String getIdOffers(String value) {
//        return value;
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSlide;
        CardView untukClickSlide;

        ViewHolder(View itemView) {
            super(itemView);
            imgSlide = itemView.findViewById(R.id.imageSlide);
            untukClickSlide = itemView.findViewById(R.id.slideOffers);
            untukClickSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idOffers = list.get(getAdapterPosition()).idOffer;
                    Intent i = new Intent(context, DetailOffersActivity.class);
                    i.putExtra("idOffers", idOffers);
                    context.startActivity(i);
                }
            });
        }
    }

}