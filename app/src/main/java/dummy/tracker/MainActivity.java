package dummy.tracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_page);

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
    }
}