package com.thenextmediagroup.dsod.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ValidationUtil {

	public static final String REGEX_USERACCOUNT = "";

	public static final String REGEX_PHONENUMBER = "^1[3,4,5,7,8]\\d{9}$";

	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	public static final String REGEX_PASSWORD = "^(?:(?=.*[A-Z])(?=.*[0-9])).{8,16}$";

	public static final String REGEX_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5_.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

	public static final String REGEX_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

	public static final String REGEX_NUMBER = "^[0-9]*[1-9][0-9]*$";

	/**
	 * isBlank
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(final String cs) {
		if (cs == null || "".equals(cs.trim()))
			return true;
		else
			return false;
	}

	/**
	 * isNotBlank
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return StringUtils.isNotBlank(cs);
	}

	/**
	 * isDigits
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isDigits(final CharSequence cs) {
		return StringUtils.isNumeric(cs);
	}

	/**
	 * isNumber
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return NumberUtils.isParsable(str);
	}

	/**
	 * isPossiveNumber
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPossiveNumber(String str) {
		if (!isNumber(str)) {
			return false;
		}
		return Double.valueOf(str) > 0;
	}

	/**
	 * isNegativeNumber
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNegativeNumber(String str) {
		if (!isNumber(str)) {
			return false;
		}
		return Double.valueOf(str) < 0;
	}

	/**
	 * isAlpha
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlpha(String str) {
		return StringUtils.isAlpha(str);
	}

	/**
	 * isUrl
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUrl(String str) {
		return match(REGEX_URL, str);
	}

	/**
	 * isPhoneNumber
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String str) {
		return match(REGEX_PHONENUMBER, str);
	}

	/**
	 * isUserAccountValid
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUserAccountValid(String str) {
		return match(REGEX_USERACCOUNT, str);
	}

	/**
	 * isPasswordValid
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPasswordValid(String str) {
		if (isBlank(str))
			return false;
		else
			return match(REGEX_PASSWORD, str);
	}

	/**
	 * isPhoneValid
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneValid(String str) {
		return match(REGEX_NUMBER, str);
	}

	/**
	 * isEmail
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		if (isBlank(str))
			return false;
		boolean isEmail = match(REGEX_EMAIL, str);
		if (isEmail)
			return str.length() <= 100;
		else
			return isEmail;
	}

	/**
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	private static boolean match(String regex, String str) {
		return Pattern.compile(regex).matcher(str).matches();
	}

}
