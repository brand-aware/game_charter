package core;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * Utility designed to check listed schedule requirements against log data 
 * currently loaded in memory to determine if requirements have been met
 * during the listed timeframe.
 * 
 * @author mike802
 * 
 * brand_aware
 * ??? - 2020
 *
 */
public class DateTool {
	
	private final int DAYS_IN_WEEK = 7;
	ArrayList<String> totals;
	ProgressIO progressIO;
	String name;
	
	public DateTool(ProgressIO pio) {
		totals = new ArrayList<String>();
		progressIO = pio;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public boolean runCheck(String range, String category, String goal) {
		ArrayList<String> targetLogs = new ArrayList<String>();
		String total = "0";
		if(range.compareTo("daily") == 0) {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DATE);
			targetLogs.add(year + "/" + month + "/" + day);
		}else if(range.compareTo("weekly") == 0) {
			String target = findLogDays(0);
			targetLogs = buildValidDates(target);
		}else if(range.compareTo("monthly") == 0) {
			String target = findLogDays(1);
			targetLogs = buildValidDates(target);
		}
		for(int x = 0; x < targetLogs.size(); x++) {
			String[] date = targetLogs.get(x).split("/");
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			System.out.println("date check: " + year + "-" + month + "-" + day);
			if(progressIO.hasLog(
					year, 
					month, 
					day, 
					name)) {
				String data = progressIO.getDataFromLog(year, month, day, name, category);
				System.out.println("   ## data: " + name + " - " + data);
				if(category.compareTo("playtime") == 0) {
					Double newTotal = Double.parseDouble(total) + Double.parseDouble(data);
					total = newTotal + "";
				}else if(category.compareTo("score") == 0) {
					Double newTotal = Double.parseDouble(total) + Double.parseDouble(data);
					total = newTotal + "";
				}else if(category.compareTo("total score") == 0) {
					Double newTotal = Double.parseDouble(total) + Double.parseDouble(data);
					total = newTotal + "";
				}else if(category.compareTo("playcount") == 0) {
					Integer newTotal = Integer.parseInt(total) + Integer.parseInt(data);
					total = newTotal + "";
				}
			}
		}
		System.out.println("total: " + total);
		if(goal.compareTo(total) > 0) {
			return false;
		}
		System.out.println(" - green");
		return true;
	}
	
	private final String findLogDays(int selection){
		boolean weekFlag = false;
		boolean monthFlag = false;
		String finishedDate = "";
		
		if(selection == 0) {
			weekFlag = true;
		}else if(selection == 1) {
			monthFlag = true;
		}
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);		
		
		if(weekFlag) {
			int date = -1;
			if(day > DAYS_IN_WEEK) {
				date = day - DAYS_IN_WEEK;
				finishedDate = year + "/" + month + "/" + date;
			}else {
				if(month == 1) {
					year--;
					month = 12;
				}else {
					month--;
				}
				int prevMonthDays = getMonthDays(year, month);
				int diff = DAYS_IN_WEEK - day;
				int weekBackDay = prevMonthDays - (diff - 1);
				finishedDate = year + "/" + month + "/" + weekBackDay;
			}
		}else if(monthFlag) {
			if(month == 1) {
				year--;
				month = 12;
			}else {
				month--;
			}
			if(day == 1) {
				if(month == 1) {
					year--;
					month = 12;
				}else {
					month--;
				}
			}else {
				day--;
			}
			
			finishedDate = year + "/" + month + "/" + day;
		}
		return finishedDate;		
	}
	
	public final int getMonthDays(int year, int month){
		int days = -1;
		if(month == 1 || 
				month == 3 || 
				month == 5 || 
				month == 7 || 
				month == 8 || 
				month == 10 || 
				month == 12){
			days = 31;
		}else if(month == 4 ||
				month == 6 ||
				month == 9 ||
				month == 11){
			days = 30;
		}else if(month == 2){
			if(year % 4 == 0){
				days = 29;
			}else{
				days = 28;
			}
		}
		return days;
	}
	
	public ArrayList<String> buildValidDates(String begin){
		ArrayList<String> list = new ArrayList<String>();
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		
		//format: year / month / day
		String[] data = begin.split("/");
		Integer[] date = new Integer[3];
		date[0] = Integer.parseInt(data[0]);
		date[1] = Integer.parseInt(data[1]);
		date[2] = Integer.parseInt(data[2]);
		
		int lastDayOfMonth = getMonthDays(date[0], date[1]);
		
		boolean done = false;
		while(!done) {
			if(year == date[0] &&
				month == date[1] &&
				day == date[2]) {
				done = true;
			}else {
				String finishedDate = date[0] + "/" + date[1] + "/" + date[2];
				list.add(finishedDate);
				if(date[2] < lastDayOfMonth) {
					// increment day
					date[2]++;
				}else {
					// increment month
					date[2] = 1;
					date[1]++;
					if(date[1] == 12) {
						// increment year
						date[2] = 1;
						date[1] = 1;
						date[0]++;
					}
				}				
			}
		}
		String finishedDate = year + "/" + month + "/" + day;
		list.add(finishedDate);
		
		return list;
	}
}