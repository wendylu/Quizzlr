package quizzlr.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

	/**
	 * Gets a SHA1 message digest object
	 * @return
	 */
	public static MessageDigest getSHA1MessageDigest() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		return md;
	}

	/**
	 * Gets the SHA1 hash as a hexadecimal string
	 * @param message
	 * @return
	 */
	public static String getSHA1Hash(String message) {
		MessageDigest md = getSHA1MessageDigest();
		return hexToString(md.digest(message.getBytes()));
	}


	private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mmaaa");
	/**
	 * Formats the date into a prettier format
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return formatter.format(date);
	}

	/**
	 * Pretty prints a duration into hh:mm:ss.xxxx
	 * @param time
	 * @return
	 */
	public static String formatDuration(long time) {
		int milliseconds = (int)(time % 1000);
		int seconds = (int)(time / 1000) % 60;
		int minutes = (int)(time / (60*1000)) % 60;
		int hours = (int)(time / (60*60*1000));

		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(2);
		String result = hours + ":" + nf.format(minutes) + ":" + nf.format(seconds);
		nf.setMinimumIntegerDigits(4);
		result += "." + nf.format(milliseconds);

		return result;
	}

	/**
	 * Escapes a string to be HTML safe
	 * @param text
	 * @return
	 */
	public static String escapeHTML(String text) {
		text = text.replace("&", "&amp;");
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace("\"", "&quot;");
		text = text.replace("'", "&#39;");
		return text;
	}
	
	public static String strip(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("[^a-z0-9]", "");
		return s;
	}
}
