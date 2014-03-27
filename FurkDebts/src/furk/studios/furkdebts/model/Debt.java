package furk.studios.furkdebts.model;

import java.text.NumberFormat;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Debt implements Parcelable {

	private long id;
	private String name;
	private float debt;
	private String comments;
	private String avatar;
	
	
	public Debt(){
	}
	
	public Debt(Parcel in){
		Log.i("FURK", "Parcel constructor");
		
		id = in.readLong();
		name = in.readString();
		comments = in.readString();
		debt = in.readFloat();
		avatar = in.readString();	
	}
	
	//Getters and Setters
	public long getId() {
		return id;
	}
	public void setId(Long id2) {
		this.id = id2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getDebt() {
		return debt;
	}
	public void setDebt(float debt) {
		this.debt = debt;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAvatar(){
		return avatar;
	}
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	
	
	public String toString(){
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return name + "\n(" + nf.format(debt) + ")";
	}
	
	
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.i("FURK", "WriteToParcel");
		
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeString(comments);
		dest.writeFloat(debt);
		dest.writeString(avatar);
	}
	
	public static final Parcelable.Creator<Debt> CREATOR = new Parcelable.Creator<Debt>() {

		@Override
		public Debt createFromParcel(Parcel source) {
			Log.i("FURK", "createFromParcel");
			return new Debt(source);
		}

		@Override
		public Debt[] newArray(int size) {
			Log.i("FURK", "newArray");
			return new Debt[size];
		}
	};
	
}
