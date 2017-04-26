package lta.com.audioRecord.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import lta.com.audioRecord.data.db.model.RecordModel;

/**
 * @author: LuTaiAn
 * @className: DBHelper
 * @description:
 * @date: 2017/4/26
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String TAG = "DBHelper";
    private static final int VERSION = 1;
    private static DBHelper sInstance;

    public DBHelper(Context context) {
        super(context,  "audio_record_pr.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecordModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }
}
