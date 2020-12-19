package dummy.tracker.bluetooth;

import android.bluetooth.BluetoothClass;
import android.content.Context;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

public class BLEScanner {
    private static BLEScanner instance = null;
    private RxBleClient rxBleClient;

    private BLEScanner() {
    }

    public static BLEScanner getInstance() {
        if(instance == null) instance = new BLEScanner();
        return instance;
    }

    public void setContext(Context context) {
        rxBleClient = RxBleClient.create(context);
    }

    public void scan() {
        Disposable scan = rxBleClient.scanBleDevices
                (new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build())
                .take(5, TimeUnit.SECONDS)
                .subscribe(this::onScanResult);
    }

    public void onScanResult(ScanResult bleScanResult) {
        int rssi = bleScanResult.getRssi();
        BluetoothClass bluetoothClass = bleScanResult.getBleDevice().getBluetoothDevice().getBluetoothClass();
        //if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.PHONE_SMART) {
            //if(rssi >= -51)
            System.out.println(rssi);
            System.out.println(Integer.toHexString(bluetoothClass.getDeviceClass()));
            System.out.println(bleScanResult.getBleDevice().getName());
            System.out.println(bleScanResult.getBleDevice().getMacAddress());
        //}
    }
}
