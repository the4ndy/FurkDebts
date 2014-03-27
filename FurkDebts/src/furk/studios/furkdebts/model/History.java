package furk.studios.furkdebts.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class History implements Parcelable {

	private long id;
	private float value;
	private String timestamp;
	private long debtorID;

	public History() {
	}

	public History(Parcel in) {
		Log.i("FURK", "Parcel constructor");

		id = in.readLong();
		timestamp = in.readString();
		value = in.readFloat();
		debtorID = in.readLong();
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(Long id2) {
		this.id = id2;
	}

	public float getDebt() {
		return value;
	}

	public void setDebt(float debt) {
		this.value = debt;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public long getDebtorID() {
		return debtorID;
	}

	public void setDebtorID(long id) {
		this.debtorID = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.i("FURK", "WriteToParcel");

		dest.writeLong(id);
		dest.writeString(timestamp);
		dest.writeFloat(value);
	}

	public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {

		@Override
		public History createFromParcel(Parcel source) {
			Log.i("FURK", "createFromParcel");
			return new History(source);
		}

		@Override
		public History[] newArray(int size) {
			Log.i("FURK", "newArray");
			return new History[size];
		}
	};

}
