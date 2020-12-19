package dummy.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmSync extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("alarm");
    }
}
