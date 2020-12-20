package dummy.tracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.polidea.rxandroidble2.RxBleDevice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dummy.tracker.api.APIMethods;
import dummy.tracker.api.JsonPlaceHolderApi;
import dummy.tracker.bd.LocalDB;
import dummy.tracker.bluetooth.BLEScanner;
import dummy.tracker.bluetooth.BTInit;
import dummy.tracker.objects.Infected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Intent backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_page);

        backgroundService = new Intent(this, SyncList.class);

        activateBT();

        Button alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(v -> {
            startService(backgroundService);
        }
        );

        Button stop = findViewById(R.id.stop);
        stop.setOnClickListener(v -> stopService(backgroundService));

        Button apestao = findViewById(R.id.apestao);
        apestao.setOnClickListener(v -> {
            APIMethods apiMethods = APIMethods.getInstance();

            TextView mac = findViewById(R.id.mac);
            apiMethods.postInfected(mac.getText().toString());
        });
    }

    public void activateBT() {
        Intent i = new Intent(this, BTInit.class);
        startActivity(i);
    }
}