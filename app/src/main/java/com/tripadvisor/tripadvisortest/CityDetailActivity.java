package com.tripadvisor.tripadvisortest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CityDetailActivity extends Activity {
    public static final String CITY = "city";
    private City city;
    TextView tvCityName;
    TextView tvCountryName;
    TextView tvDescription;
    ImageView ivCityImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        if (savedInstanceState != null) {
            city = savedInstanceState.getParcelable(CITY);
        } else {
            city = getIntent().getParcelableExtra(CITY);
        }

        tvCityName = findViewById(R.id.tv_city_name);
        tvCountryName = findViewById(R.id.tv_country_name);
        tvDescription = findViewById(R.id.tv_description);
        ivCityImage = findViewById(R.id.iv_city_image);
        tvCityName.setText(city.getName());
        tvCountryName.setText(city.getCountry());
        tvDescription.setText(city.getDescription());
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CITY, city);
    }
}
