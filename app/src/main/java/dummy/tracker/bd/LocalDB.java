package dummy.tracker.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LocalDB extends SQLiteOpenHelper {
    private static final String table_name = "contacts";
    private static final String column1 = "macs";
    private static final String column2 = "date";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + table_name + "(" +
            column1 + " TEXT PRIMARY KEY," +
            column2 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + table_name;

    public LocalDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insert(SQLiteDatabase db, String mac, String d) {
        ContentValues values = new ContentValues();
        values.put(column1, mac);
        values.put(column2, d);

        try {
            db.insertOrThrow(table_name, null, values);
        } catch (SQLiteConstraintException e) {
            // update
            String where = column1 + "= ?"; // "mac" == myMac;
            String[] args = {mac};

            db.update(table_name, values, where, args);
        }
    }

    public boolean read(SQLiteDatabase db, String mac) {
        String[] projection = {column1, column2};
        String selection = column1 + "= ?"; // "mac" == myMac;
        String[] selectionArgs = {mac};

        Cursor c = db.query(table_name, projection, selection, selectionArgs,
                null, null, null);

        /*
        c.moveToFirst();
        //String macRES = c.getString(0);
        String dRES = c.getString(1);

        Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(dRES);

        long diffInMillies = Math.abs(newDate.getTime() - d.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        */

        return c.moveToFirst();
    }
}
