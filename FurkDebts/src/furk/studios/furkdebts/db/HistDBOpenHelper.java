package furk.studios.furkdebts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HistDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "FURK";

	private static final String DATABASE_NAME = "debts.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_HISTORY = "history";
	public static final String COLUMN_ID = "autoIncId";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_COMM = "comments";
	public static final String COLUMN_DEBT = "debt";
	public static final String COLUMN_DEBTOR_ID = "debtorID";

	public static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_HISTORY + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TIMESTAMP + " TEXT, " +
			COLUMN_COMM + " TEXT, " +
			COLUMN_DEBT + " NUMERIC, " +
			COLUMN_DEBTOR_ID + " NUMERIC " +
			")";
			

	public HistDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
		onCreate(db);
	
	}

}
