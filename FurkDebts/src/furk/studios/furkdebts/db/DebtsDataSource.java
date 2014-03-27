package furk.studios.furkdebts.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HeaderIterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import furk.studios.furkdebts.model.Debt;
import furk.studios.furkdebts.model.History;

public class DebtsDataSource {

	private static final String LOGTAG = "FURK";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	private static final String[] allColumns = { DebtsDBOpenHelper.COLUMN_ID,
			DebtsDBOpenHelper.COLUMN_NAME, DebtsDBOpenHelper.COLUMN_DEBT,
			DebtsDBOpenHelper.COLUMN_COMM, DebtsDBOpenHelper.COLUMN_AVATAR };

	private static final String[] allHistColumns = {
			HistDBOpenHelper.COLUMN_ID, HistDBOpenHelper.COLUMN_TIMESTAMP,
			HistDBOpenHelper.COLUMN_DEBT, HistDBOpenHelper.COLUMN_COMM,
			HistDBOpenHelper.COLUMN_DEBTOR_ID };

	public DebtsDataSource(Context context) {
		dbhelper = new DebtsDBOpenHelper(context);

	}

	public void openDB() {
		Log.i(LOGTAG, "DB Opened");
		database = dbhelper.getWritableDatabase();
	}

	public void closeDB() {
		Log.i(LOGTAG, "DB Closed");
		dbhelper.close();
	}

	public Debt createEntry(Debt debt) {

		ContentValues values = new ContentValues();
		values.put(DebtsDBOpenHelper.COLUMN_NAME, debt.getName());
		values.put(DebtsDBOpenHelper.COLUMN_DEBT, debt.getDebt());
		values.put(DebtsDBOpenHelper.COLUMN_COMM, debt.getComments());
		values.put(DebtsDBOpenHelper.COLUMN_AVATAR, debt.getAvatar());
		Log.v("FURK", "" + values);
		long insertid = database.insert(DebtsDBOpenHelper.TABLE_DEBTS, null,
				values);
		debt.setId(insertid);
		return debt;
	}

	public boolean updateEntry(Debt debt, long id) {

		ContentValues values = new ContentValues();
		values.put(DebtsDBOpenHelper.COLUMN_DEBT, debt.getDebt());
		database.update(DebtsDBOpenHelper.TABLE_DEBTS, values,
				DebtsDBOpenHelper.COLUMN_ID + " = " + id, null);
		return true;
	}

	public List<History> findAllHistory() {
		Log.v("FURK", "Start of the findHistory Method");
		Cursor cursor = database.query(HistDBOpenHelper.TABLE_HISTORY,
				allHistColumns, null, null, null, null, null);
		List<History> histories = cursorToHistList(cursor);
		return histories;
	}

	public List<History> findHistory(long id) {
		
		Log.v("FURK", HistDBOpenHelper.COLUMN_DEBTOR_ID+ " == " + id);
		
		Cursor cursor = database.query(HistDBOpenHelper.TABLE_HISTORY,
				allHistColumns, HistDBOpenHelper.COLUMN_DEBTOR_ID+ " == " + id, null, null, null, null);
		List<History> histories = cursorToHistList(cursor);
		return histories;
	}

	public List<Debt> findAll() {

		Cursor cursor = database.query(DebtsDBOpenHelper.TABLE_DEBTS,
				allColumns, null, null, null, null, null);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Debt> debts = cursorToList(cursor);
		return debts;

	}

	public List<Debt> findFiltered(String selection, String orderBy) {

		Cursor cursor = database.query(DebtsDBOpenHelper.TABLE_DEBTS,
				allColumns, selection, null, null, null, orderBy);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Debt> debts = cursorToList(cursor);
		return debts;

	}

	private List<Debt> cursorToList(Cursor cursor) {
		List<Debt> debts = new ArrayList<Debt>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Debt debt = new Debt();
				debt.setId(cursor.getLong(cursor
						.getColumnIndex(DebtsDBOpenHelper.COLUMN_ID)));
				debt.setName(cursor.getString(cursor
						.getColumnIndex(DebtsDBOpenHelper.COLUMN_NAME)));
				debt.setDebt(cursor.getFloat(cursor
						.getColumnIndex(DebtsDBOpenHelper.COLUMN_DEBT)));
				debt.setComments(cursor.getString(cursor
						.getColumnIndex(DebtsDBOpenHelper.COLUMN_COMM)));
				debt.setAvatar(cursor.getString(cursor
						.getColumnIndex(DebtsDBOpenHelper.COLUMN_AVATAR)));
				debts.add(debt);
			}
		}
		return debts;
	}

	private List<History> cursorToHistList(Cursor cursor) {
		List<History> histories = new ArrayList<History>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				History hist = new History();
				hist.setId(cursor.getLong(cursor
						.getColumnIndex(HistDBOpenHelper.COLUMN_ID)));
				hist.setDebt(cursor.getFloat(cursor
						.getColumnIndex(HistDBOpenHelper.COLUMN_DEBT)));
				hist.setDebtorID(cursor.getLong(cursor
						.getColumnIndex(HistDBOpenHelper.COLUMN_DEBTOR_ID)));
				hist.setTimestamp(cursor.getString(cursor
						.getColumnIndex(HistDBOpenHelper.COLUMN_TIMESTAMP)));
				histories.add(hist);
				Log.v("FURK", "Current Cursor Value = " + hist);
				Log.v("FURK", hist.getTimestamp());
			}
		}
		Log.v("FURK", histories + "");
		return histories;
	}

	public void deleteEntry(long debtId) {
		String debt = "" + debtId;
		String[] string = { debt };
		database.delete(DebtsDBOpenHelper.TABLE_DEBTS,
				DebtsDBOpenHelper.COLUMN_ID + " = ?", string);
	}

	// History Stuff below

	public Debt logDebtHistory(Debt debt, float oldDebt) {

		ContentValues values = new ContentValues();

		String timestamp = createTimestamp();

		values.put(HistDBOpenHelper.COLUMN_DEBT, oldDebt);
		values.put(HistDBOpenHelper.COLUMN_DEBTOR_ID, debt.getId());
		values.put(HistDBOpenHelper.COLUMN_TIMESTAMP, timestamp);

		Log.v("FURK", "" + values);

		database.insert(HistDBOpenHelper.TABLE_HISTORY, null, values);

		// long insertid = database.insert(DebtsDBOpenHelper.TABLE_DEBTS, null,
		// values);
		// debt.setId(insertid);

		return debt;
	}

	public boolean updateHistoryEntry(Debt debt, long id) {

		ContentValues values = new ContentValues();
		values.put(DebtsDBOpenHelper.COLUMN_DEBT, debt.getDebt());
		database.update(DebtsDBOpenHelper.TABLE_DEBTS, values,
				DebtsDBOpenHelper.COLUMN_ID + " = " + id, null);
		return true;
	}

	private String createTimestamp() {

		Locale locale = new Locale("en_US");
		Locale ol = Locale.getDefault();
		Locale.setDefault(locale);

		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String key = formatter.format(new Date());

		Locale.setDefault(ol);

		return key;
	}

}
