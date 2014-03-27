package furk.studios.furkdebts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DebtsDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "FURK";

	private static final String DATABASE_NAME = "debts.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_DEBTS = "debts";
	public static final String COLUMN_ID = "debtId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_COMM = "comments";
	public static final String COLUMN_DEBT = "debt";
	public static final String COLUMN_AVATAR = "avatar";

	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_DEBTS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_COMM + " TEXT, " +
			COLUMN_DEBT + " NUMERIC, " +
			COLUMN_AVATAR + " TEXT " +
			")";
			

	public DebtsDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(HistDBOpenHelper.TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBTS);
		onCreate(db);
	
	}

}
