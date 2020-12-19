package dummy.tracker;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SyncList extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start service");

        CountDownTimer c = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                System.out.println("alarm");
                this.start();
            }
        }.start();

        return Service.START_STICKY;
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
