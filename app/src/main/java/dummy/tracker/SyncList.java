package dummy.tracker;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.polidea.rxandroidble2.RxBleDevice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dummy.tracker.api.APIMethods;
import dummy.tracker.bd.LocalDB;
import dummy.tracker.bluetooth.BLEScanner;

import static dummy.tracker.App.CHANNEL_ID;
import static dummy.tracker.App.manager;
import static dummy.tracker.Encrypt.getMD5;

public class SyncList extends Service {

    private LocalDB localDB;
    private SQLiteDatabase db;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start service");

        // Tapping the notification will open the specified Activity.
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // This always shows up in the notifications area when this Service is running.
        Notification not = new NotificationCompat.Builder(this, CHANNEL_ID).
                setContentTitle(getText(R.string.app_name)).
                setContentText("Doing stuff in the background...").
                setSmallIcon(R.mipmap.ic_launcher).
                setContentIntent(pendingIntent).build();
        startForeground(1, not);

        BLEScanner bleScanner = BLEScanner.getInstance();
        bleScanner.setContext(getBaseContext());
        localDB = new LocalDB(getBaseContext(), "contacts", null);
        db = localDB.getWritableDatabase();

        CountDownTimer c1 = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished > 14100) bleScanner.scan();
            }

            @Override
            public void onFinish() {
                Date date = Calendar.getInstance().getTime();
                for(RxBleDevice d:bleScanner.getDevices()) {
                    String mac = getMD5(d.getMacAddress());
                    System.out.println("MAC: " + mac + " ||| Date: " + new SimpleDateFormat("yyyy/MM/dd").format(date));
                    localDB.insert(db, mac, new SimpleDateFormat("yyyy/MM/dd").format(date));
                }
                this.start();
            }
        }.start();

        // tiempos prueba
        CountDownTimer c2 = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                try {
                    if(APIMethods.getInstance().getInfected(localDB, db)){
                        Log.i("Noti", "Entra");
                        showNotification();
                    }else{
                        Log.i("Noti", "NoEntra");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.start();
            }
        }.start();

        return START_NOT_STICKY;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Contacto!")
                .setContentText("Has estado en contacto con un positivo!")
                .setPriority(Notification.PRIORITY_MAX);

        // notificationId is a unique int for each notification that you must define
        manager.notify(25500, builder.build());
    }

    @Override
    public void onDestroy() {
        System.out.println("stop service");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
