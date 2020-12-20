package dummy.tracker.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class BTInit extends AppCompatActivity {
    private BluetoothAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBT();
        //setDiscoverability();
        initGPS();
        finish();
    }

    private void initBT() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        Toast t;
        if(adapter == null) {
            t = Toast.makeText(this, "tu android no tiene bluetooth jaja", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        if(!adapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBT);
        }
    }

    private void setDiscoverability() {
        Intent setDiscover = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(setDiscover);
    }

    private void initGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }
}
