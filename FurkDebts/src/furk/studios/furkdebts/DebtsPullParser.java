package furk.studios.furkdebts;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import furk.studios.furkdebts.model.Debt;

public class DebtsPullParser {

	private static final String LOGTAG = "FURKDEBTS";

	private static final String DEBT_ID = "debtId";
	private static final String DEBT_NAME = "debtName";
	private static final String DEBT_DEBT = "debtAmount";
	private static final String DEBT_COM = "comments";

	private Debt currentDebt = null;
	private String currentTag = null;
	List<Debt> debts = new ArrayList<Debt>();

	public List<Debt> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			InputStream stream = context.getResources().openRawResource(R.raw.debts);

			// FileInputStream stream = getApplicationContext.openFileOutput(e,
			// getActivity().MODE_PRIVATE);

			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}
		return debts;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentDebt != null && currentTag != null) {
			if (currentTag.equals(DEBT_ID)) {
				Long id = Long.parseLong(xmlText);
				currentDebt.setId(id);
			} else if (currentTag.equals(DEBT_NAME)) {
				currentDebt.setName(xmlText);
			} else if (currentTag.equals(DEBT_DEBT)) {
				Float debt = Float.valueOf(xmlText);
				currentDebt.setDebt(debt);
			} else if (currentTag.equals(DEBT_COM)) {
				currentDebt.setComments(xmlText);
			}
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("debt")) {
			currentDebt = new Debt();
			debts.add(currentDebt);
		} else {
			currentTag = name;
		}
	}

}
