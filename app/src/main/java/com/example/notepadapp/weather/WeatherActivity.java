package com.example.notepadapp.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.notepadapp.R;
import com.example.notepadapp.bean.WeatherBean;
import com.example.notepadapp.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class WeatherActivity extends Activity {

    private LocationManager locationManager;

    private SVProgressHUD locHud;

    private Handler mHandler;

    private SVProgressHUD hud;

    private TextView tv_city;

    private ImageView iv_weather;

    private TextView tv_weather;

    private TextView tv_temperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mHandler = new Handler();
        bindViews();
        getUserPosition();
    }

    private void bindViews() {
        tv_city = findViewById(R.id.tv_city);
        iv_weather = findViewById(R.id.iv_weather);
        tv_weather = findViewById(R.id.tv_weather);
        tv_temperature = findViewById(R.id.tv_temperature);
    }

    OkHttpClient client = new OkHttpClient();

    @SuppressWarnings("all")
    private void requestWeather(String lat, String lng) {
        final SVProgressHUD hud = new SVProgressHUD(this);
        hud.show();
        String url = String.format("https://api.weatherapi.com/v1/current.json?" +
                "key=847801a0918f49ab95a32001202305&q=%s,%s", lat, lng);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                hud.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                hud.dismiss();
                String json = response.body().string();
                Log.d("aaa", json);
                final WeatherBean bean = new Gson().fromJson(json, WeatherBean.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(bean);
                    }
                });
            }
        });
    }

    private List<WeatherBean.ConditionsBean> conditions;

    private WeatherBean.ConditionsBean findCondition(int code) {
        if (conditions == null) {
            String jsonPath = "weather_conditions.json";
            String conditionsJson = CommonUtil.getJson(this, jsonPath);
            conditions = new Gson().fromJson(conditionsJson,
                    new TypeToken<List<WeatherBean.ConditionsBean>>() {
                    }.getType());
        }
        for (WeatherBean.ConditionsBean condition :
                conditions) {
            if (condition.getCode() == code) {
                return condition;
            }
        }
        return null;
    }

    private void updateUI(WeatherBean bean) {
        try {
            int code = bean.getCurrent().getCondition().getCode();
            boolean isDay = bean.getCurrent().getIs_day() != 0;
            WeatherBean.ConditionsBean condition = findCondition(code);
            if (condition != null) {
                String weather = isDay ? condition.getDay() : condition.getNight();
                tv_weather.setText(weather);
                String isDayStr = isDay ? "day" : "night";
                String path = String.format("64x64/%s/%s.png", isDayStr, condition.getIcon());
                InputStream inputStream = getAssets().open(path);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                iv_weather.setImageBitmap(bitmap);
            }
            tv_city.setText(bean.getLocation().getName());
            String temp = String.format(Locale.getDefault(), "%.0f", bean.getCurrent().getTemp_c());
            tv_temperature.setText(temp+"°C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //use the internet positioning in preference
    private String getBestProvider() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        }
        return null;
    }

    private void getUserPosition() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager == null) return;
        String provider = getBestProvider();
        if (provider == null){
            Toast.makeText(this, "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No location permission, please open it",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            showLocation(location);
            return;
        }
        locHud = new SVProgressHUD(this);
        locHud.show();
        locationManager.requestLocationUpdates(provider, 5000, 1,
                locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle
                extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {
            locHud.dismiss();
        }
        @Override
        public void onLocationChanged(Location location) {
            locHud.dismiss();
            showLocation(location);
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void showLocation(Location location) {
        locationManager.removeUpdates(locationListener);
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        Log.d("aaa", currentPosition);
        requestWeather(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
    }

}
