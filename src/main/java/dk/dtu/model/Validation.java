package main.java.dk.dtu.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	/**
	 * 
	 * @param input
	 * @return true if the input i a positive integer
	 */
	public static boolean isPositiveInteger(String input) {
		try {
			long i = Long.parseLong(input);
			if (i >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to validate if an ID is a valid choice.
	 * @param ID
	 * @return true if the ID is valid
	 */
	public static boolean isValidID(String ID) {
		if (isPositiveInteger(ID)) {
			int i = Integer.parseInt(ID);
			if(i < 11 || i > 99) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Method to validate if a user name is a valid choice.
	 * @param userName
	 * @return true if the user name is valid
	 */
	public static boolean isValidUserName(String userName) {
		//TODO kan valideres yderligere
		if(userName.length() < 2 || userName.length() > 20) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method to validate if a set of initials is a valid choice.
	 * @param initials
	 * @return true if the initials are valid
	 */
	public static boolean isValidInitials(String initials) {
		//TODO kan valideres yderligere
		if(initials.length() < 2 || initials.length() > 4) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method to validate if a cpr is a valid.
	 * @param cpr
	 * @return true if the cpr is valid
	 */
	public static boolean isValidCpr(String cpr) {
		if (isPositiveInteger(cpr)) {
			int month = Integer.parseInt(cpr.substring(2, 4));
			// Checks if month is valid
			if (month > 0 && month < 13) {
				for (int i = 1900; i < 2100; i += 100) {
					int day = Integer.parseInt(cpr.substring(0, 2));
					int year = i + Integer.parseInt(cpr.substring(4, 6));
					// Creates a calendar object and sets year and month
					Calendar cprDate = new GregorianCalendar(year, month-1, 1);
					// Get the number of days in that month
					int daysInMonth = cprDate.getActualMaximum(Calendar.DAY_OF_MONTH);
					// Checks if day is valid
					if (day > 0 && day <= daysInMonth) {
						// check if the date later than todays date
						cprDate.set(year, month-1, day);
						Calendar currentDate = new GregorianCalendar();
						if (cprDate.compareTo(currentDate) <= 0) {
							// check if full cpr is valid
							int CprProductSum = 0;
							int[] multiplyBy = {4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
							for (int j = 0; j < cpr.length(); j++) {
								CprProductSum += Integer.parseInt(cpr.substring(j, j+1)) * multiplyBy[j];
							}
							if (CprProductSum % 11 == 0) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Method can validate if a chosen password is allowed or not, based on the following requirements:
	 * minimum 2 symbols
	 * minimum 2 upper case characters
	 * minimum 2 lower case characters
	 * (username not present in password string)
	 * @param password
	 * @return true if the password is valid.
	 */
	public static boolean isValidPassword(String password) {
		//(?!.*" + hashMap.get("userName").toString()+")
		Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * Method to validate if a role is a valid choice.
	 * @param role
	 * @return true if the role is valid
	 */
	public static boolean isValidRole(String role) {
		ArrayList<String> validRoles = new ArrayList<>();
		validRoles.add("admin");
		validRoles.add("pharmacist");
		validRoles.add("foreman");
		validRoles.add("operator");
		role = role.toLowerCase();
		// if its a valid role
		if (validRoles.contains(role)) {
			return true;
		} else {
			return false;
		}
	}
}
