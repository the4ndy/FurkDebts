/*
 * MainActivity.java
 * 
 * Copyright (C) 2013 6 Wunderkinder GmbH.
 * 
 * @author      Jose L Ugia - @Jl_Ugia
 * @author      Antonio Consuegra - @aconsuegra
 * @author      Cesar Valiente - @CesarValiente
 * @author      Benedikt Lehnert - @blehnert
 * @author      Timothy Achumba - @iam_timm
 * @version     1.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package furk.studios.furkdebts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import furk.studios.furkdebts.db.DebtsDataSource;
import furk.studios.furkdebts.model.Debt;

public class MainActivity extends Activity {

	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int MENU_DELETE_ID = 1002;
	private static final String LIST_ANIMATION_STYLE = "pref_animation_style";
	private static final String SHOW_LIST_ANIM = "pref_show_anim";
	private static final String SORT_OPTIONS = "pref_default_filter";
	private int currentFilter = 0;
	private ListView mListView;
	private int currentDebtorId;
	DebtsDataSource datasource;
	private SharedPreferences settings;
	List<Debt> debts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		settings = PreferenceManager.getDefaultSharedPreferences(this);

		mListView = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(mListView);
		mListView.setEmptyView(findViewById(R.id.empty_state));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long id) {

				Debt debt = debts.get(position);

				Intent intent = new Intent(getApplicationContext(),
						DebtDetail.class);

				intent.putExtra(".model.Debt", debt);

				startActivity(intent);

			}
		});

		datasource = new DebtsDataSource(this);
		datasource.openDB();
		Log.v("FURK", "OnCreate pre RD");

		refreshDisplay();

	}

	private void refreshDisplay() {

		Log.v("FURK", "Pre-Switch");
		switch (currentFilter) {
		case 0:
			debts = datasource.findAll();
			break;
		case 1:
			debts = datasource.findFiltered(null, "name ASC");
			break;
		case 2:
			debts = datasource.findFiltered(null, "name DESC");
			break;
		case 3:
			debts = datasource.findFiltered(null, "debt DESC");
			break;
		case 4:
			debts = datasource.findFiltered(null, "debt ASC");
			break;

		}

		ArrayAdapter<Debt> adapter = new DebtsListAdapter(this, debts);

		// Maybe Im dumb, but comparing strings doesn't work with ==,
		// must use .contentEquals();
		// boolean b = settings.getString(LIST_ANIMATION_STYLE,
		// null).contentEquals("From Right");

		if (settings.getBoolean(SHOW_LIST_ANIM, false)) {
			// Create and Instantiate new ListView Animation Adapter
			if (settings.getString(LIST_ANIMATION_STYLE, "Alpha")
					.contentEquals("From Right")) {
				SwingRightInAnimationAdapter animAdapter = new SwingRightInAnimationAdapter(
						adapter);
				// animAdapter.setAbsListView(mListView);
				// mListView.setAdapter(animAdapter);
				setAdapters(animAdapter);
			} else if (settings.getString(LIST_ANIMATION_STYLE, "Alpha")
					.contentEquals("From Left")) {
				SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(

						adapter);
				setAdapters(animAdapter);
			} else if (settings.getString(LIST_ANIMATION_STYLE, "Alpha")
					.contentEquals("From Bottom")) {
				SwingBottomInAnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
						adapter);
				setAdapters(animAdapter);
			} else if (settings.getString(LIST_ANIMATION_STYLE, "Alpha")
					.contentEquals("Scale")) {
				ScaleInAnimationAdapter animAdapter = new ScaleInAnimationAdapter(
						adapter);
				setAdapters(animAdapter);
			} else {
				AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(
						adapter);
				setAdapters(animAdapter);
				Log.v("FURK", "Else, Alpha");
			}

		} else {
			Log.v("FURK", "Show List Anim Else aka false");
			mListView.setAdapter(adapter);
		}
	
	}

	private void setAdapters(AnimationAdapter animAdapter) {
		// Set the AnimAdapt to the ListView and the ListView to the
		// AnimAdapt
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_create:
			createDebt();
			break;
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		case R.id.menu_sort_AtoZ:
			currentFilter = 1;
			refreshDisplay();
			break;
		case R.id.menu_sort_ZtoA:
			currentFilter = 2;
			refreshDisplay();
			break;
		case R.id.menu_sort_HighToLow:
			currentFilter = 3;
			refreshDisplay();
			break;
		case R.id.menu_sort_LowToHigh:
			currentFilter = 4;
			refreshDisplay();
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentDebtorId = (int) info.id;
		// Log.i("FURK", "" + currentDebtorId);
		menu.add(0, MENU_DELETE_ID, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getItemId() == MENU_DELETE_ID) {
			Debt debt = debts.get(currentDebtorId);
			datasource.deleteEntry(debt.getId());
			refreshDisplay();
		}

		return super.onContextItemSelected(item);
	}

	private void createDebt() {

		Intent intent = new Intent(this, NewDebtor.class);
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
			refreshDisplay();
		} else if (resultCode == RESULT_CANCELED) {
			refreshDisplay();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.openDB();
		refreshDisplay();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// datasource.closeDB();
	}

}
