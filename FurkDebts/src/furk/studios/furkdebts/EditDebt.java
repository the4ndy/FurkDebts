package furk.studios.furkdebts;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makeramen.RoundedImageView;

import furk.studios.furkdebts.db.DebtsDataSource;
import furk.studios.furkdebts.model.Debt;

public class EditDebt extends Activity {

	private static final int ADD_TO_DEBT = 1001;
	private static final int SUB_FROM_DEBT = 1002;

	Debt debt;
	RoundedImageView mImageView;
	EditText mEditDebt;
	TextView mNameView;
	Button clearDebt;
	Button addToDebt;
	Button subFromDebt;
	NumberFormat nf;
	DebtsDataSource datasource;
	Float debtAtStart;

	Button one;
	Button two;
	Button three;
	Button four;
	Button five;
	Button six;
	Button seven;
	Button eight;
	Button nine;
	Button zero;
	Button dblZero;
	Button decimal;

	boolean hasDecimal = false;
	int numAfterDecimal = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_debt_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		datasource = new DebtsDataSource(this);

		Bundle b = getIntent().getExtras();
		debt = b.getParcelable(".model.Debt");

		datasource.openDB();

		debtAtStart = debt.getDebt();

		bindViews();
		setClickListeners();
		refreshDisplay();

	}

	private void setClickListeners() {

		clearDebt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditDebt.setText("");
				numAfterDecimal = 0;
				hasDecimal = false;
			}
		});
		
		addToDebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveAndFinish(ADD_TO_DEBT);
			}
		});
		subFromDebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveAndFinish(SUB_FROM_DEBT);
			}
		});

		one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "1");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "1");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "2");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "2");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "3");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "3");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "4");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "4");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		five.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "5");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "5");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		six.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "6");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "6");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		seven.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "7");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "7");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		eight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "8");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "8");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		nine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "9");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "9");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		zero.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "0");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "0");
					numAfterDecimal = numAfterDecimal + 1;
				}

			}
		});
		dblZero.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hasDecimal && numAfterDecimal == 2) {
					refreshDisplay();
				} else if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + "00");
				} else if (hasDecimal && numAfterDecimal != 2) {
					mEditDebt.setText(mEditDebt.getText() + "00");
					numAfterDecimal = numAfterDecimal + 2;
				}

			}
		});
		decimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!hasDecimal) {
					mEditDebt.setText(mEditDebt.getText() + ".");
					hasDecimal = true;
				} else {
					refreshDisplay();
				}

			}
		});

	}

	private void saveAndFinish(int i) {

		Log.v("FURK", "Save and Finish");

		float f = Float.parseFloat(mEditDebt.getText().toString());
		
		Intent intent = new Intent();
				
		switch (i) {
		case ADD_TO_DEBT:
			datasource.logDebtHistory(debt, debtAtStart);
			debt.setDebt(debtAtStart+f);
			datasource.updateEntry(debt, debt.getId());
			intent.putExtra(".model.Debt", debt);
			setResult(RESULT_OK, intent);
			finish();

			break;
		case SUB_FROM_DEBT:
			datasource.logDebtHistory(debt, debtAtStart);
			
			debt.setDebt(debtAtStart-f);
			datasource.updateEntry(debt, debt.getId());
			intent.putExtra(".model.Debt", debt);
			setResult(RESULT_OK, intent);
			finish();

			break;
		}

	}

	private void bindViews() {

		nf = NumberFormat.getCurrencyInstance(Locale.getDefault());

		mImageView = (RoundedImageView) findViewById(R.id.editDebtImageView);

		mNameView = (TextView) findViewById(R.id.editDebtName);

		mEditDebt = (EditText) findViewById(R.id.editDebt1);

		clearDebt = (Button) findViewById(R.id.clear_debt_button);

		addToDebt = (Button) findViewById(R.id.addToDebt);
		subFromDebt = (Button) findViewById(R.id.subFromDebt);

		one = (Button) findViewById(R.id.one_btn);
		two = (Button) findViewById(R.id.two_btn);
		three = (Button) findViewById(R.id.three_btn);
		four = (Button) findViewById(R.id.four_btn);
		five = (Button) findViewById(R.id.five_btn);
		six = (Button) findViewById(R.id.six_btn);
		seven = (Button) findViewById(R.id.seven_btn);
		eight = (Button) findViewById(R.id.eight_btn);
		nine = (Button) findViewById(R.id.nine_btn);
		decimal = (Button) findViewById(R.id.decimal_btn);
		zero = (Button) findViewById(R.id.zero_btn);
		dblZero = (Button) findViewById(R.id.dbl_zero_btn);

	}

	private void refreshDisplay() {
		// mEditDebt.setText(nf.format(debt.getDebt()).replace("$", ""));
		mNameView.setText(debt.getName());
		mImageView.setImageURI(Uri.parse(debt.getAvatar()));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		setResult(RESULT_CANCELED);
		finish();

	}

}
