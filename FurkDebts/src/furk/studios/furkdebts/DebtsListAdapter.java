package furk.studios.furkdebts;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;

import furk.studios.furkdebts.model.Debt;

public class DebtsListAdapter extends ArrayAdapter<Debt> {

	Context context;
	List<Debt> debts;

	public DebtsListAdapter(Context context, List<Debt> debts) {
		super(context, android.R.id.content, debts);
		this.context = context;
		this.debts = debts;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = vi.inflate(R.layout.list_item_layout, null);

		Debt debt = debts.get(position);

		TextView tv = (TextView) view.findViewById(R.id.debtorsName);
		tv.setText(debt.getName());

		tv = (TextView) view.findViewById(R.id.currentDebt);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		tv.setText(nf.format(debt.getDebt()));

		RoundedImageView civ = (RoundedImageView) view
				.findViewById(R.id.listImageView);
		if (debt.getAvatar().length() > 0) {
			if (debt.getAvatar().contains("default")) {
				int imageResource = context.getResources().getIdentifier(
						debt.getAvatar(), "drawable", context.getPackageName());

				if (imageResource != 0) {
					civ.setImageResource(imageResource);
				}
			} else {
				civ.setImageURI(Uri.parse(debt.getAvatar()));
			}
		}
		return view;

	}

}
