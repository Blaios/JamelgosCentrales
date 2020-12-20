package dummy.tracker;

<<<<<<< Updated upstream
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import java.util.Set;
=======
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dummy.tracker.api.APIMethods;
import dummy.tracker.bluetooth.BTInit;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_DISCOVERABLE_BT = 2; // Unique request code
    private static final int DISCOVERABLE_DURATION = 120; // Discoverable duration time in seconds

    Button Scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH_ADMIN},1);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Scan = findViewById(R.id.button);
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter.startDiscovery();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    Log.i("Device", device.getName());
                    int rssi = new Intent().getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);
                    Log.i("Device2", String.valueOf(rssi));
                }
            }
        });

        // 0 means always discoverable
        // maximum value is 3600

// ...

<<<<<<< Updated upstream
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
        startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT);
=======
        Button alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(v -> {
            startService(backgroundService);
        }
        );
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        Log.i("MAC", address);
>>>>>>> Stashed changes


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        final BroadcastReceiver bReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.i("Receiver", "Entra");
                }
            }
        };
        registerReceiver(bReciever, filter);
        mBluetoothAdapter.startDiscovery();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}