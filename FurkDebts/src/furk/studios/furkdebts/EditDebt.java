package furk.studios.furkdebts;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.makeramen.RoundedImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import furk.studios.furkdebts.color.ColorArt;
import furk.studios.furkdebts.color.FadingImageView;
import furk.studios.furkdebts.db.DebtsDataSource;
import furk.studios.furkdebts.model.Debt;

public class EditDebt extends Activity {

	private static final int ADD_TO_DEBT = 1001;
	private static final int SUB_FROM_DEBT = 1002;

	Debt debt;
	FadingImageView mImageView;
	RoundedImageView rImageView;
	EditText mEditDebt;
	TextView mNameView;
	Button clearDebt;
	Button addToDebt;
	Button subFromDebt;
	NumberFormat nf;
	DebtsDataSource datasource;
	Float debtAtStart;
	ColorArt cs;
	
	
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
	RelativeLayout mLayout;

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
		try {
			setColors();
		} catch (NotFoundException e) {
			Log.v("FURKception", "NotFoundException : " + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.v("FURKception", "IOException : " + e);
			e.printStackTrace();
		}
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
				if (!mEditDebt.getText().toString().isEmpty()) {
					saveAndFinish(ADD_TO_DEBT);
				}
			}
		});
		subFromDebt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mEditDebt.getText().toString().isEmpty()) {
					saveAndFinish(SUB_FROM_DEBT);
				}
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
			debt.setDebt(debtAtStart + f);
			datasource.updateEntry(debt, debt.getId());
			intent.putExtra(".model.Debt", debt);
			setResult(RESULT_OK, intent);
			finish();

			break;
		case SUB_FROM_DEBT:
			datasource.logDebtHistory(debt, debtAtStart);

			debt.setDebt(debtAtStart - f);
			datasource.updateEntry(debt, debt.getId());
			intent.putExtra(".model.Debt", debt);
			setResult(RESULT_OK, intent);
			finish();

			break;
		}

	}

	private void bindViews() {

		nf = NumberFormat.getCurrencyInstance(Locale.getDefault());

		mLayout = (RelativeLayout) findViewById(R.id.editDebtParentLayout);

		mImageView = (FadingImageView) findViewById(R.id.editDebtImageView);
		rImageView = (RoundedImageView) findViewById(R.id.editDebtRoundImageView);
		
		mNameView = (TextView) findViewById(R.id.editDebtName);

		mEditDebt = (EditText) findViewById(R.id.editDebt1);

		clearDebt = (Button) findViewById(R.id.clear_debt_btn);

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

		refreshDisplay();
	}

	private void refreshDisplay() {

		mNameView.setText(debt.getName());

		if (debt.getAvatar().length() > 0) {
			if (debt.getAvatar().contains("default")) {
				int imageResource = getResources().getIdentifier(
						debt.getAvatar(), "drawable", getPackageName());

				if (imageResource != 0) {
					mImageView.setVisibility(View.GONE);
					rImageView.setVisibility(View.VISIBLE);
					rImageView.setImageResource(imageResource);
				}
			} else {
				rImageView.setVisibility(View.GONE);
				mImageView.setVisibility(View.VISIBLE);
				mImageView.setImageURI(Uri.parse(debt.getAvatar()));
			}
		}

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

	public void initBtnColor(ColorArt scheme) {

		Button[] array = { one, two, three, four, five, six, seven, eight,
				nine, zero, dblZero, clearDebt, addToDebt, subFromDebt, decimal };

		ImageView[] arrayImages = {
				(ImageView) findViewById(R.id.clearImageView),
				(ImageView) findViewById(R.id.addImageView),
				(ImageView) findViewById(R.id.subImageView),
				(ImageView) findViewById(R.id.oneImageView),
				(ImageView) findViewById(R.id.twoImageView),
				(ImageView) findViewById(R.id.threeImageView),
				(ImageView) findViewById(R.id.fourImageView),
				(ImageView) findViewById(R.id.fiveImageView),
				(ImageView) findViewById(R.id.sixImageView),
				(ImageView) findViewById(R.id.sevenImageView),
				(ImageView) findViewById(R.id.eightImageView),
				(ImageView) findViewById(R.id.nineImageView),
				(ImageView) findViewById(R.id.zeroImageView),
				(ImageView) findViewById(R.id.dblZeroImageView),
				(ImageView) findViewById(R.id.decimalImageView) };

		for (int i = 0; i < array.length; i++) {
			int resId = array[i].getId();
			Log.v("FURK", "" + resId);
			TextView tv = (TextView) findViewById(resId);
			tv.setTextColor(scheme.getPrimaryColor());
		}

		for (int i = 0; i < arrayImages.length; i++) {
			arrayImages[i].setBackgroundColor(scheme.getDetailColor());
		}

	}

	public void setColors() throws NotFoundException, IOException {
	
		if (debt.getAvatar().contains("default_contact")){			
			mImageView.setVisibility(View.GONE);
			rImageView.setVisibility(View.VISIBLE);
		} else {
		if (getColorScheme(debt.getAvatar()) != null) {

			cs = getColorScheme(debt.getAvatar());
			initBtnColor(cs);

			mEditDebt.setHintTextColor(halfTransparentColor(cs
					.getSecondaryColor()));
			mEditDebt.setTextColor(cs.getSecondaryColor());
			TextView t = (TextView) findViewById(R.id.dollaSign);
			t.setTextColor(cs.getPrimaryColor());
			mNameView.setTextColor(cs.getPrimaryColor());

			mImageView.setBackgroundColor(cs.getBackgroundColor(),
					FadingImageView.FadeSide.CIRCLE);

			mLayout.setBackgroundColor(cs.getBackgroundColor());

			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(cs.getBackgroundColor()));
			bar.setDisplayShowTitleEnabled(false);
			bar.setDisplayShowTitleEnabled(true);

			SpannableString title = new SpannableString(getTitle());
			title.setSpan(new ForegroundColorSpan(cs.getDetailColor()), 0,
					getTitle().length(), 0);
			bar.setTitle(title);

		} 
		}
	}

	public ColorArt getColorScheme(String uri) throws IOException {

		Bitmap bitmap = null;

		bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
				Uri.parse(debt.getAvatar()));

		if (bitmap != null) {

			ColorArt color = new ColorArt(bitmap);
			/**
			 * 
			 * Test the color scheme
			 * 
			 * for (int i = 0; i < 4; i++){ ImageView iv; switch (i) { case 0:
			 * iv = (ImageView) findViewById(R.id.imageColor0);
			 * iv.setBackgroundColor(color.getPrimaryColor()); break; case 1: iv
			 * = (ImageView) findViewById(R.id.imageColor1);
			 * iv.setBackgroundColor(color.getSecondaryColor()); break; case 2:
			 * iv = (ImageView) findViewById(R.id.imageColor2);
			 * iv.setBackgroundColor(color.getBackgroundColor()); break; case 3:
			 * iv = (ImageView) findViewById(R.id.imageColor3);
			 * iv.setBackgroundColor(color.getDetailColor()); break; } }
			 */

			return color;

		} else {
			return null;
		}
	}

	public int halfTransparentColor(int color) {
		String hexColor = Integer.toHexString(color);
		String newHexColor = "#80";
		Log.d("FURK Hex Color", hexColor);
		char[] array = hexColor.toCharArray();
		for (int i = 2; i < hexColor.length(); i++) {
			newHexColor = newHexColor + array[i];
		}
		int newColor = Color.parseColor(newHexColor);
		return newColor;

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