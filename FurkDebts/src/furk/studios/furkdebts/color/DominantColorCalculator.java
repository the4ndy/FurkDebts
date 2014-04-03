//This file was created by Chris Banes and is NOT my own work. Modifications may have been
//made to this file but based on my current skill level, most likely not many.

package furk.studios.furkdebts.color;

import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import furk.studios.furkdebts.color.MedianCutQuantizer.ColorNode;

public class DominantColorCalculator {

	private static final String LOG_TAG = DominantColorCalculator.class
			.getSimpleName();

	private static final int NUM_COLORS = 10;

	private static final int PRIMARY_TEXT_MIN_CONTRAST = 135;

	private static final int SECONDARY_MIN_DIFF_HUE_PRIMARY = 120;

	private static final int TERTIARY_MIN_CONTRAST_PRIMARY = 20;
	private static final int TERTIARY_MIN_CONTRAST_SECONDARY = 90;

	private final MedianCutQuantizer.ColorNode[] mPalette;
	private final MedianCutQuantizer.ColorNode[] mWeightedPalette;
	private ColorScheme mColorScheme;

	public DominantColorCalculator(Bitmap bitmap) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();

		final int[] rgbPixels = new int[width * height];
		bitmap.getPixels(rgbPixels, 0, width, 0, 0, width, height);

		final MedianCutQuantizer mcq = new MedianCutQuantizer(rgbPixels,
				NUM_COLORS);

		mPalette = mcq.getQuantizedColors();
		mWeightedPalette = weight(mPalette);

		findColors();
	}

	public ColorScheme getColorScheme() {
		return mColorScheme;
	}

	private void findColors() {
		final ColorNode primaryAccentColor = findPrimaryAccentColor();
		final ColorNode secondaryAccentColor = findSecondaryAccentColor(primaryAccentColor);

		final int tertiaryAccentColor = findTertiaryAccentColor(
				primaryAccentColor, secondaryAccentColor);

		final int primaryTextColor = findPrimaryTextColor(primaryAccentColor);
		final int secondaryTextColor = findSecondaryTextColor(primaryAccentColor);

		mColorScheme = new ColorScheme(primaryAccentColor.getRgb(),
				secondaryAccentColor.getRgb(), tertiaryAccentColor,
				primaryTextColor, secondaryTextColor);
	}

	/**
	 * @return the first color from our weighted palette.
	 */
	private ColorNode findPrimaryAccentColor() {
		return mWeightedPalette[0];
	}

	/**
	 * @return the next color in the weighted palette which ideally has enough
	 *         difference in hue.
	 */
	private ColorNode findSecondaryAccentColor(final ColorNode primary) {
		final float primaryHue = primary.getHsv()[0];

		// Find the first color which has sufficient difference in hue from the
		// primary
		for (ColorNode candidate : mWeightedPalette) {
			final float candidateHue = candidate.getHsv()[0];

			// Calculate the difference in hue, if it's over the threshold
			// return it
			if (Math.abs(primaryHue - candidateHue) >= SECONDARY_MIN_DIFF_HUE_PRIMARY) {
				return candidate;
			}
		}

		// If we get here, just return the second weighted color
		return mWeightedPalette[1];
	}

	/**
	 * @return the first color from our weighted palette which has sufficient
	 *         contrast from the primary and secondary colors.
	 */
	private int findTertiaryAccentColor(final ColorNode primary,
			final ColorNode secondary) {

		// First check that ColorNodes hashCodes are valid int values to catch any errors
		// in the future from the below added fix
		Log.d("FURK findTertiaryAccentColor", "Primary = " + primary.getRgb() + "    "
				+ " Secondary = " + secondary.getRgb());
		
		// this code was added to convert the ColorNode objects into INT objects
		// because there is
		// an error in the For Each loop to follow, as a result the two
		// ColorNode vars have been
		// swapped with the two int vars created from the .hashCode() method of each ColorNode
		int prime = primary.getRgb();
		int second = secondary.getRgb();

		// Find the first color which has sufficient contrast from both the
		// primary & secondary
		for (ColorNode color : mWeightedPalette) {
			if (ColorUtils.calculateContrast(color, prime) >= TERTIARY_MIN_CONTRAST_PRIMARY
					&& ColorUtils.calculateContrast(color, second) >= TERTIARY_MIN_CONTRAST_SECONDARY) {
				return color.getRgb();
			}
		}

		// We couldn't find a colour. In that case use the primary colour,
		// modifying it's brightness
		// by 45%
		return ColorUtils.changeBrightness(secondary.getRgb(), 0.45f);
	}

	/**
	 * @return the first color which has sufficient contrast from the primary
	 *         colors.
	 */
	private int findPrimaryTextColor(final ColorNode primary) {
		
		//See above method code and comments to see how this fix is applied
		Log.d("FURK findPrimaryTextColor", "Primary = " + primary.hashCode());
		
		int prime = primary.hashCode();

		
		// Try and find a colour with sufficient contrast from the primary
		// colour
		for (ColorNode color : mPalette) {
			if (ColorUtils.calculateContrast(color, prime) >= PRIMARY_TEXT_MIN_CONTRAST) {
				return color.getRgb();
			}
		}

		// We haven't found a colour, so return black/white depending on the
		// primary colour's
		// brightness
		return ColorUtils.calculateYiqLuma(primary.getRgb()) >= 128 ? Color.BLACK
				: Color.WHITE;
	}

	/**
	 * @return return black/white depending on the primary colour's brightness
	 */
	private int findSecondaryTextColor(final ColorNode primary) {
		return ColorUtils.calculateYiqLuma(primary.getRgb()) >= 128 ? Color.BLACK
				: Color.WHITE;
	}

	private static ColorNode[] weight(ColorNode[] palette) {
		final MedianCutQuantizer.ColorNode[] copy = Arrays.copyOf(palette,
				palette.length);
		final float maxCount = palette[0].getCount();

		Arrays.sort(copy, new Comparator<ColorNode>() {
			@Override
			public int compare(ColorNode lhs, ColorNode rhs) {
				final float lhsWeight = calculateWeight(lhs, maxCount);
				final float rhsWeight = calculateWeight(rhs, maxCount);

				if (lhsWeight < rhsWeight) {
					return 1;
				} else if (lhsWeight > rhsWeight) {
					return -1;
				}
				return 0;
			}
		});

		return copy;
	}

	private static float calculateWeight(ColorNode node, final float maxCount) {
		return FloatUtils.weightedAverage(
				ColorUtils.calculateColorfulness(node), 2f,
				(node.getCount() / maxCount), 1f);
	}

}