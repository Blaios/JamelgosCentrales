package dummy.tracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.polidea.rxandroidble2.RxBleDevice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dummy.tracker.bd.LocalDB;
import dummy.tracker.bluetooth.BLEScanner;
import dummy.tracker.bluetooth.BTInit;

public class MainActivity extends AppCompatActivity {

    private Intent backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_page);

        backgroundService = new Intent(this, SyncList.class);

        Button activateBT = findViewById(R.id.activateBT);
        activateBT.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), BTInit.class);
            startActivity(i);
        });

        Button scanBLE = findViewById(R.id.scan);
        scanBLE.setOnClickListener(v -> {
            BLEScanner bleScanner = BLEScanner.getInstance();
            bleScanner.setContext(this);
            bleScanner.scan();
        });

        Button insertDevices = findViewById(R.id.insert);
        insertDevices.setOnClickListener(v -> {
            BLEScanner bleScanner = BLEScanner.getInstance();
            ArrayList<RxBleDevice> devices = bleScanner.getDevices();
            LocalDB localDB = new LocalDB(this, "contacts", null, 1);
            SQLiteDatabase db = localDB.getWritableDatabase();
            for(RxBleDevice d:devices) {
                Date newDate = Calendar.getInstance().getTime();
                localDB.insert(db, d.getMacAddress(), new SimpleDateFormat("yyyy/MM/dd").format(newDate));
            }
        });

        Button alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(v -> {
            startService(backgroundService);
        }
        );

        Button stop = findViewById(R.id.stop);
        stop.setOnClickListener(v -> stopService(backgroundService));
    }

    @Override
    protected void onDestroy() {
        //stopService(backgroundService);
        super.onDestroy();
    }
}