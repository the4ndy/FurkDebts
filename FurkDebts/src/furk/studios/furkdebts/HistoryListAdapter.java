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
import furk.studios.furkdebts.model.History;

public class HistoryListAdapter extends ArrayAdapter<History> {

	Context context;
	List<History> history;

	public HistoryListAdapter(Context context, List<History> hist) {
		super(context, android.R.id.content, hist);
		this.context = context;
		this.history = hist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		View view = vi.inflate(R.layout.history_list_layout, null);

		History hist = history.get(position);

		TextView tv = (TextView) view.findViewById(R.id.timestampTextView);
		tv.setText(hist.getTimestamp());

		tv = (TextView) view.findViewById(R.id.oldDebtValue);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		tv.setText(nf.format(hist.getDebt()));

		return view;

	}

}
