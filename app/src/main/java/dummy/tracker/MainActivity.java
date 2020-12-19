package dummy.tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

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
    }


}