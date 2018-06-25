package com.tripadvisor.tripadvisortest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private final List<City> cities;

    CityAdapter(List<City> cities, OnItemClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(cities.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return this.cities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCityName;
        TextView tvCountryName;
        TextView tvDescription;
        ImageView ivCityImage;

        ViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tv_city_name);
            tvCountryName = itemView.findViewById(R.id.tv_country_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivCityImage = itemView.findViewById(R.id.iv_city_image);
        }

        void bind(final City city, final OnItemClickListener listener) {
            tvCityName.setText(city.getName());
            tvCountryName.setText(city.getCountry());
            tvDescription.setText(city.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(city);
                }
            });
            Picasso.get().load(city.getUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(ivCityImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load(city.getUrl())
                                    .error(R.drawable.ic_alert_circle)
                                    .into(ivCityImage, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }


                                    );
                        }
                    });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(City item);
    }
}

