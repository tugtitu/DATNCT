package com.example.tug_pc.restaurantmanagermini.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckConnection {
    public static boolean haveNetworkConnection(Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void checkNetworkConnection() {

        ApiConnect apiConnect = ApiClient.getClient().create(ApiConnect.class);
        Call<Connect> call = apiConnect.haveConnection();
        call.enqueue(new Callback<Connect>() {
            @Override
            public void onResponse(@NonNull Call<Connect> call, @NonNull Response<Connect> response) {
                Connect connect = response.body();
                assert connect != null;

            }

            @Override
            public void onFailure(@NonNull Call<Connect> call, @NonNull Throwable t) {
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }

    public static void showToast_long(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
