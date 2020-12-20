package dummy.tracker;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.IBinder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static dummy.tracker.App.CHANNEL_ID;

public class SyncList extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start service");

        // Tapping the notification will open the specified Activity.
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // This always shows up in the notifications area when this Service is running.
        Notification not = new NotificationCompat.Builder(this, CHANNEL_ID).
                setContentTitle(getText(R.string.app_name)).
                setContentInfo("Doing stuff in the background...").
                setSmallIcon(R.mipmap.ic_launcher).
                setContentIntent(pendingIntent).build();
        startForeground(1, not);

        CountDownTimer c = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                System.out.println("alarm");
                this.start();
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
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
