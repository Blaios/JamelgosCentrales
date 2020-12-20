package dummy.tracker.api;

import android.bluetooth.BluetoothAdapter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import dummy.tracker.bluetooth.BLEScanner;
import dummy.tracker.objects.Infected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIMethods {
    private static APIMethods instance;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private APIMethods() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://dummytracker.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public static APIMethods getInstance() {
        if(instance == null) instance = new APIMethods();
        return instance;
    }

    public void getInfected() {
        Call<List<Infected>> call = jsonPlaceHolderApi.getInfected();
            call.enqueue(new Callback<List<Infected>>() {
            @Override
            public void onResponse(Call<List<Infected>> call, Response<List<Infected>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                List<Infected> infecteds = response.body();
                for(Infected i: infecteds) {
                    String c = "";
                    c += "MAC: " + i.getMAC() + "\n";
                    c += "Date: " + i.getNoticedTime() + "\n";
                    System.out.println(c);
                }
            }

            @Override
            public void onFailure(Call<List<Infected>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void postInfected(String s) {
        Infected infected = new Infected(getMD5(s));

        Call<Infected> call = jsonPlaceHolderApi.createInfected(infected);
        call.enqueue(new Callback<Infected>() {
            @Override
            public void onResponse(Call<Infected> call, Response<Infected> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }

                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Infected> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
