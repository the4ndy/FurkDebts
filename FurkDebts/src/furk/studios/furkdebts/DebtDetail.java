package furk.studios.furkdebts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;
import com.makeramen.RoundedImageView;

import furk.studios.furkdebts.ValueLabelAdapter.LabelOrientation;
import furk.studios.furkdebts.color.ColorArt;
import furk.studios.furkdebts.db.DebtsDataSource;
import furk.studios.furkdebts.model.Debt;
import furk.studios.furkdebts.model.History;

public class DebtDetail extends Activity {

	TextView nameTextView;
	TextView debtTextView;
	TextView commentsTextView;
	RoundedImageView avatarImageView;
	DebtsDataSource datasource;
	List<History> histories;
	ChartView lineGraph;
	ColorArt scheme;
	Bitmap mBitmap = null;

	Debt debt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debt_detail);

		Log.e("FURK", "OnCreate");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();
		debt = b.getParcelable(".model.Debt");

		datasource = new DebtsDataSource(this);
		datasource.openDB();

		bindViews();

		refreshDisplay();

		try {
			getColors();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void bindViews() {
		nameTextView = (TextView) findViewById(R.id.nameText);
		debtTextView = (TextView) findViewById(R.id.debtText);
		commentsTextView = (TextView) findViewById(R.id.commentsText);
		avatarImageView = (RoundedImageView) findViewById(R.id.avatarImageView1);
		lineGraph = (ChartView) findViewById(R.id.lineGraph1);
	}

	private void refreshDisplay() {

		Log.v("FURK", "RefreshDisplay");

		nameTextView.setText(debt.getName());

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		debtTextView.setText(nf.format(debt.getDebt()));

		commentsTextView.setText(debt.getComments());

		// Log.v("FUFK AVATAR", debt.getAvatar());
		// Log.v("FURK AVATAR LENGTH", debt.getAvatar().length() + "");

		if (debt.getAvatar().length() > 0) {
			if (debt.getAvatar().contains("default")) {
				int imageResource = getResources().getIdentifier(
						debt.getAvatar(), "drawable", getPackageName());

				if (imageResource != 0) {
					avatarImageView.setImageResource(imageResource);
				}
			} else {
				avatarImageView.setImageURI(Uri.parse(debt.getAvatar()));
			}
		}

		histories = datasource.findHistory(debt.getId());

		ListView mListView = (ListView) findViewById(R.id.historyListView);
		ArrayAdapter<History> adapter = new HistoryListAdapter(this, histories);
		mListView.setAdapter(adapter);
		Log.v("FURK", "" + histories);

		if (!histories.isEmpty()) {
			// initGraph();

			// Temporarily remove views till History Section is done
			mListView.setVisibility(View.GONE);
			RelativeLayout l = (RelativeLayout) findViewById(R.id.chartViewParent);
			l.setVisibility(View.GONE);

		} else {
			mListView.setVisibility(View.GONE);
			RelativeLayout l = (RelativeLayout) findViewById(R.id.chartViewParent);
			l.setVisibility(View.GONE);

		}

	}

	private void initGraph() {

		LinearSeries ls = new LinearSeries();
		ls.setLineColor(0xff4FBA6F);
		ls.setLineWidth(2);
		Log.v("FURK", "Series created, color and lineWidth set");

		double j = 5;

		for (int i = 0; i < histories.size(); i++) {
			History hist = histories.get(i);
			double d = hist.getDebt();
			ls.addPoint(new LinearPoint(j, d));
			j = j + 5;
		}

		/**
		 * for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) { ls.addPoint(new
		 * LinearPoint(i, Math.sin(i))); }
		 **/
		Log.v("FURK", "For Loop Completed" + ls);
		lineGraph.addSeries(ls);
		Log.v("FURK", "series added to graph");
		lineGraph.setLeftLabelAdapter(new ValueLabelAdapter(
				getApplicationContext(), LabelOrientation.VERTICAL));
		Log.v("FURK", "Left adapter set");
		lineGraph.setBottomLabelAdapter(new ValueLabelAdapter(
				getApplicationContext(), LabelOrientation.HORIZONTAL));
		Log.v("FURK", "Left adapter set");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_debt_menu, menu);

		if (mBitmap != null) {
			if (!isDark(scheme.getBackgroundColor())) {
				// change action bar icons to DARK

				menu.getItem(0).setIcon(R.drawable.ic_action_edit_dark);
				Log.e("FURK", "the color is light as shit yo, change the icon");
			}
		}
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_edit:
			editDebt();
			break;
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void editDebt() {
		Intent intent = new Intent(getApplicationContext(), EditDebt.class);

		intent.putExtra(".model.Debt", debt);

		startActivityForResult(intent, 1003);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case RESULT_OK:
			Bundle b = data.getExtras();
			debt = b.getParcelable(".model.Debt");
			refreshDisplay();
			break;
		case RESULT_CANCELED:
			refreshDisplay();
			break;
		}
	}

	// Experimental Color Scheme code

	private void getColors() throws UnsupportedEncodingException {

		try {
			mBitmap = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), Uri.parse(debt.getAvatar()));
		} catch (FileNotFoundException e) {
			//File Not Found
			e.printStackTrace();
		} catch (IOException e) {
				//IO Exception
			e.printStackTrace();
		}

		// Bitmap bitmap = BitmapFactory.decodeStream(is)
		if (mBitmap != null) {
			// DominantColorCalculator d = new DominantColorCalculator(bitmap);
			// ColorScheme scheme = d.getColorScheme();

			scheme = new ColorArt(mBitmap);

			String hexColor = Integer.toHexString(scheme.getBackgroundColor());
			String newHexColor = "#BF";
			Log.d("FURK Hex Color", hexColor);
			char[] array = hexColor.toCharArray();
			for (int i = 2; i < hexColor.length(); i++) {
				newHexColor = newHexColor + array[i];
			}

			Log.d("FURK New half-transparent Hex Value", newHexColor);

			RelativeLayout layout = (RelativeLayout) findViewById(R.id.debtDetailParentRelativeLayout);
			layout.setBackgroundColor(Color.parseColor(newHexColor));

			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(scheme
					.getBackgroundColor()));
			bar.setDisplayShowTitleEnabled(false);
			bar.setDisplayShowTitleEnabled(true);

			SpannableString title = new SpannableString(getTitle());
			title.setSpan(new ForegroundColorSpan(scheme.getDetailColor()), 0,
					getTitle().length(), 0);
			bar.setTitle(title);

			avatarImageView.setBorderColor(scheme.getBackgroundColor());
			nameTextView.setTextColor(scheme.getPrimaryColor());
			commentsTextView.setTextColor(scheme.getSecondaryColor());
			TextView tv = (TextView) findViewById(R.id.commentsTextTitle);
			tv.setTextColor(scheme.getPrimaryColor());

		}
	}

	public boolean isDark(int color) {
		if (android.R.color.transparent == color)
			return true;

		int[] rgb = { Color.red(color), Color.green(color), Color.blue(color) };

		int brightness = (int) Math.sqrt(rgb[0] * rgb[0] * .241 + rgb[1]
				* rgb[1] * .691 + rgb[2] * rgb[2] * .068);

		Log.v("FURK", "COLOR: " + color + ", BRIGHT: " + brightness);

		// color is dark
		if (brightness <= 130) {
			return true;
		} else {
			return false;
		}

	}

}
