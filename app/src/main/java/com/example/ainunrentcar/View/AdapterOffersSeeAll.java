package com.example.ainunrentcar.View;


import android.content.Context;
import android.content.Intent;
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
import com.example.ainunrentcar.Model.ModelOffersSeeAll;
import com.example.ainunrentcar.R;

import java.util.List;

public class AdapterOffersSeeAll extends RecyclerView.Adapter<AdapterOffersSeeAll.ViewHolder> {

    private Context context;
    private List<ModelOffersSeeAll> list;

    public AdapterOffersSeeAll(Context context, List<ModelOffersSeeAll> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_view_see_all_offers, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelOffersSeeAll modelOffersSeeAll = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_place_holder);
        requestOptions.error(R.drawable.img_place_holder);
        Glide.with(holder.imagePromo)
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getGambarPromo())
                .apply(new RequestOptions().centerCrop())
                .into(holder.imagePromo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePromo;
        CardView untukClickSlide;

        ViewHolder(View itemView) {
            super(itemView);
            imagePromo = itemView.findViewById(R.id.imagePromo);
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