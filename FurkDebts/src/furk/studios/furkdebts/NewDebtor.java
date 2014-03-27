package furk.studios.furkdebts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.makeramen.RoundedImageView;

import furk.studios.furkdebts.db.DebtsDataSource;
import furk.studios.furkdebts.model.Debt;

public class NewDebtor extends Activity {

	private EditText nameET;
	private EditText debtET;
	private EditText commentsET;
	DebtsDataSource datasource;
	private RoundedImageView mImageView;
	private String currentAvatarName;
	private Button saveButton;
	private Button chooseContactBtn;
	public String nameFromContacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_debtor);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		datasource = new DebtsDataSource(this);

		datasource.openDB();

		nameET = (EditText) findViewById(R.id.debtors_name);
		debtET = (EditText) findViewById(R.id.initial_debt);
		commentsET = (EditText) findViewById(R.id.comments);
		mImageView = (RoundedImageView) findViewById(R.id.previewImageView);

		chooseContactBtn = (Button) findViewById(R.id.pick_contact);
		chooseContactBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(intent, 1);
			}

		});

		saveButton = (Button) findViewById(R.id.save_debt);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				saveAndFinish();
			}
		});

	}

	private void saveAndFinish() {

		Debt debt = new Debt();

		String name = nameET.getText().toString();
		Log.e("FURK CHECK toString", name);
		String debtAmount = debtET.getText().toString();
		String comments = commentsET.getText().toString();

		Intent intent = new Intent();

		if (debtET.length() > 0 && nameET.length() > 0) {
			debt.setName(name);
			debt.setDebt(Float.valueOf(debtAmount));
			debt.setComments(comments);
			if (currentAvatarName.contains("default")) {
				debt.setAvatar("default_contact");
			} else {

				debt.setAvatar(currentAvatarName);
			}
			datasource.createEntry(debt);
			setResult(RESULT_OK, intent);

		} else {
			setResult(RESULT_CANCELED, intent);
		}

		finish();
	}

	@Override
	public void onBackPressed() {

		saveAndFinish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			saveAndFinish();

		}
		return false;
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {

				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,
						null, null);
				cursor.moveToFirst();

				// Get Name and Set to Edit Text
				int nameIndex = cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
				nameFromContacts = cursor.getString(nameIndex);
				nameET.setText(nameFromContacts);

				// Get Photo and set to ImageView
				String photoUri = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
				if (photoUri == null) {
					mImageView.setImageResource(R.drawable.default_contact);
					currentAvatarName = "default";
				} else {
					mImageView.setImageURI(Uri.parse(photoUri));
					currentAvatarName = photoUri;
				}

			} else {
				return;
			}
		}
	}

}
