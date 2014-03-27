package furk.studios.furkdebts;

import java.text.NumberFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import furk.studios.furkdebts.ValueLabelAdapter.LabelOrientation;
import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;
import com.makeramen.RoundedImageView;

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
	}

	private void bindViews(){
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

		Log.v("FUFK AVATAR", debt.getAvatar());
		Log.v("FURK AVATAR LENGTH", debt.getAvatar().length() + "");
		
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
		Log.v("FURK", ""+histories);
		
		if (!histories.isEmpty()){
			initGraph();	
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
			j = j+5;
		}
		
		/**
		for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) {
			ls.addPoint(new LinearPoint(i, Math.sin(i)));
		}
		**/
		Log.v("FURK", "For Loop Completed" + ls);
		lineGraph.addSeries(ls);
		Log.v("FURK", "series added to graph");
		lineGraph.setLeftLabelAdapter(new ValueLabelAdapter(getApplicationContext(), LabelOrientation.VERTICAL));
		Log.v("FURK", "Left adapter set");
		lineGraph.setBottomLabelAdapter(new ValueLabelAdapter(getApplicationContext(), LabelOrientation.HORIZONTAL));
		Log.v("FURK", "Left adapter set");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_debt_menu, menu);
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

		switch (resultCode){
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
}
