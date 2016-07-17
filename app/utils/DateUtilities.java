package utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtilities {

	public static Date getDateFromString(String dateStr, String format) throws ParseException {
		if (dateStr == null || dateStr.length() == 0)
			return null;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		c.setTime(sdf.parse(dateStr));
		return c.getTime();
	}

	public static String getStringFromDate(Date date, String format) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date getDateFromString(String dateStr) throws ParseException {
		if (dateStr == null || dateStr.length() == 0)
			return null;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		c.setTime(sdf.parse(dateStr));
		// c.setTimeInMillis(Long.parseLong(GenericServices.getTimeStamp(dateStr)));
		// System.out.println(dateStr +
		// "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
		// c.getTime());
		return c.getTime();
	}

	public static boolean isDateBetween(Date fromDate, Date toDate, Date date) {
		if (date.equals(fromDate) == true || date.equals(toDate) == true || (date.after(fromDate) && date.before(toDate)))
			return true;
		else
			return false;
	}

	public static String getTimeStamp(String str_date, String format) throws ParseException {
		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat(format);
		date = (Date) formatter.parse(str_date);
		return "" + date.getTime();
	}

	public static Date getNow() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String dateNow = formatter.format(currentDate.getTime());
		// System.out.println("Now the date is :=>  " + dateNow);
		return currentDate.getTime();
	}

	public static int getDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static Date getTodayDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = formatter.format(currentDate.getTime());
		return currentDate.getTime();
	}

	public static int getDayWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	public static Date setYear(int year, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.YEAR, year);
		date = c.getTime();
		return date;
	}

	public static String getMonthName(int m) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (m >= 0 && m <= 11) {
			month = months[m];
		}
		return month;
	}

	public static List<Date> getDateBetweenTwoDates(Date fromDate, Date toDate) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		while (calendar.getTime().before(toDate)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		dates.add(toDate);
		return dates;
	}

	public static long getMinutesBetweenTwoDates(Date firstDate, Date secondDate) {
		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		long diff = secondDate.getTime() - firstDate.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		return diffMinutes;
	}

	public static long getHoursBetweenTwoDates(Date firstDate, Date secondDate) {
		long diff = secondDate.getTime() - firstDate.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		return diffHours;
	}

	public static float getHoursBetweenTwoDateTime(Date fromDate, Date toDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(fromDate);
		fromCalendar.set(Calendar.YEAR, 1970);
		fromCalendar.set(Calendar.MONTH, 0);
		fromCalendar.set(Calendar.DATE, 1);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(toDate);
		toCalendar.set(Calendar.YEAR, 1970);
		toCalendar.set(Calendar.MONTH, 0);
		toCalendar.set(Calendar.DATE, 1);
		float minutes = (toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (1000 * 60);
		float hours = minutes / 60;
		// System.out.println("Minutes " + minutes + " " + "Hoursss = " +
		// hours);
		return hours;
	}

	public static float getMinutesBetweenTwoDateTime(Date fromDate, Date toDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(fromDate);
		fromCalendar.set(Calendar.YEAR, 1970);
		fromCalendar.set(Calendar.MONTH, 0);
		fromCalendar.set(Calendar.DATE, 1);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(toDate);
		toCalendar.set(Calendar.YEAR, 1970);
		toCalendar.set(Calendar.MONTH, 0);
		toCalendar.set(Calendar.DATE, 1);
		float minutes = (toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (1000 * 60);
		return minutes;
	}

	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}

	public static int getNextMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getPreviousMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) - 1;
	}

	public static Date getDateTime() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// System.out.println(sdf.format(cal.getTime()));
		return DateUtilities.getDateFromString(sdf.format(cal.getTime()), "dd/MM/yyyy HH:mm");
	}

	public static String getTimeFromDate(Date date) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		return timeFormat.format(date);
	}

	public static String getDateWithoutTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	public static Date getOnlyDate(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return DateUtilities.getDateFromString(dateFormat.format(date), "dd/MM/yyyy");
	}

	public static Date concatDateTime(String date, String time) throws ParseException {
		// return DateUtilities.getDateFromString(date + " " + time);
		return DateUtilities.getDateFromString(date + " " + time, "dd/MM/yyyy HH:mm");
	}

	public static Date getFirstDayInMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.YEAR, year);
		calendar.set(calendar.MONTH, month);
		calendar.set(calendar.DATE, 1);
		calendar.set(Calendar.HOUR, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		return calendar.getTime();
	}

	public static Date getLastDayInMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.YEAR, year);
		calendar.set(calendar.MONTH, month);
		calendar.set(calendar.DATE, 1);
		int lastMonthDay = calendar.getActualMaximum(Calendar.DATE);
		Calendar endOfMonthCalendar = Calendar.getInstance();
		endOfMonthCalendar.set(calendar.YEAR, year);
		endOfMonthCalendar.set(calendar.MONTH, month);
		endOfMonthCalendar.set(calendar.DATE, lastMonthDay);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		return endOfMonthCalendar.getTime();
	}

	public static Date addRemoveDay(int year, int month, int day, int daysNbToAddOrRemove) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, 0, 0);
		cal.add(Calendar.DAY_OF_MONTH, daysNbToAddOrRemove);
		return cal.getTime();
	}

	public static Date addRemoveMonth(int year, int month, int monthsNbToAddOrRemove) {
		// System.out.println("addRemoveMonth " + month);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1, 0, 0);
		cal.add(Calendar.MONTH, monthsNbToAddOrRemove);
		return cal.getTime();
	}

	public static Date getNearestDateAfter(Date date, List<Date> datesList) throws ParseException {

		String dateAsString = "01/01/1970 " + DateUtilities.getTimeFromDate(date);
		date = DateUtilities.getDateFromString(dateAsString);
		Date nearDate = date;
		// System.out.println("Date to compare " + date);
		if (datesList != null && datesList.size() > 0) {
			double min = Math.abs(DateUtilities.getHoursBetweenTwoDateTime(date, datesList.get(0)));

			double temp = 0;
			for (Date d : datesList) {

				// System.out.println("fettttttttt  min " + min + "    temp " +
				// temp);
				// System.out.println("fettttttttt  d " + d + "    date " + date
				// + " ;;;" + d.after(date));
				if (d.equals(date) || d.after(date)) {
					temp = Math.abs(DateUtilities.getHoursBetweenTwoDateTime(date, d));

					if (temp <= min) {
						// System.out.println("fet temp<-min");
						min = temp;
						nearDate = d;
					}
				}
			}
		}
		// System.out.println("Date li la2a " + nearDate);
		return nearDate;
	}

	public static Date getNearestDateBefore(Date date, List<Date> datesList) throws ParseException {

		String dateAsString = "01/01/1970 " + DateUtilities.getTimeFromDate(date);
		date = DateUtilities.getDateFromString(dateAsString);
		Date nearDate = date;
		// System.out.println("Date to compare " + date);
		if (datesList != null && datesList.size() > 0) {
			double min = Math.abs(DateUtilities.getHoursBetweenTwoDateTime(date, datesList.get(0)));

			double temp = 0;
			for (Date d : datesList) {

				// System.out.println("fettttttttt  min " + min + "    temp " +
				// temp);
				// System.out.println("fettttttttt  d " + d + "    date " + date
				// + " ;;;" + d.after(date));
				if (d.equals(date) || d.before(date)) {
					temp = Math.abs(DateUtilities.getHoursBetweenTwoDateTime(date, d));

					if (temp <= min) {
						// System.out.println("fet temp<-min");
						min = temp;
						nearDate = d;
					}
				}
			}
		}
		// System.out.println("Date li la2a " + nearDate);
		return nearDate;
	}

	// TODO bi hayde l function fi error btekhod parametre date w ma btesta3mlo
	// !!
	public static String getStringFromDate(Date date) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;
	}

	public static String getStringFromDateWithFormat(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date today = calendar.getTime();
		String reportDate = df.format(today);
		return reportDate;
	}

	public static String getStringFromDateWithoutTime(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(date);
		// System.out.println("dateeeeeeeeee:" + reportDate);
		return reportDate;
	}

	public static int getDayFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonthFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public static int getYearFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static Date getYesterdayDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();

	}

	public static Date getTomorrowDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();

	}

	public static Date addRemoveDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date getNearestSchedule(Date date, List<Date> datesList) {
		Date nearDate = new Date();
		if (datesList != null && datesList.size() > 0) {
			double min = DateUtilities.getHoursBetweenTwoDateTime(date, datesList.get(0));
			double temp = 0;
			for (Date d : datesList) {
				temp = DateUtilities.getHoursBetweenTwoDateTime(date, d);
				if (temp < min) {
					min = temp;
					nearDate = d;
				}
			}
		}
		return nearDate;
	}

	public static List<Date> getDatesBetweenToDate(Date fromDate, Date toDate) {
		// System.out.println("from date" + fromDate);
		// System.out.println("from date" + toDate);
		List<Date> datesList = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.setLenient(false);
		datesList.add(cal.getTime());
		while (cal.getTime().before(toDate)) {
			cal.add(Calendar.DATE, 1);
			datesList.add(cal.getTime());
			// System.out.println(cal.getTime());
		}

		return datesList;
	}

	public static Date getDateFromYMD(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day);
		Date date = c.getTime();
		return date;
	}

	public static Date getDateWithoutYMD(Date date) throws ParseException {
		String timeFromDate = getTimeFromDate(date);
		Date newDate = concatDateTime("01/01/1970", timeFromDate);
		return newDate;
	}

	public static Date replaceYearByCurrentYear(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		return calendar.getTime();
	}

	public static Date getLastDateInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getLastDateInWorkingDay(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (hours == 0) {
			calendar.set(Calendar.HOUR, 11);
			calendar.set(Calendar.AM_PM, Calendar.PM);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		} else {
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR, hours);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
		}
		return calendar.getTime();
	}

	public static Date getFirstDateInWorkingDay(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (hours == 0) {
			calendar.set(Calendar.HOUR, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.AM_PM, Calendar.AM);
		} else {
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.HOUR, hours);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
		}
		return calendar.getTime();
	}

	public static Date getFirstDateInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		return calendar.getTime();
	}

	public static Date getFirstDateInSecondDay(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		if (hours == 0) {
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			return calendar.getTime();
		} else {
			calendar.set(Calendar.HOUR, hours);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
		}
		return calendar.getTime();
	}
}