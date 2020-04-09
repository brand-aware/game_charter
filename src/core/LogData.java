package core;

import core.doc.ILogData;

public class LogData implements ILogData{
	
	private int year;
	private int month;
	private int date;
	private double timespent;
	private double score;
	private double totalScore;
	private int plays;
	
	public LogData(int y, int m, int d) {
		year = y;
		month = m;
		date = d;
		timespent = 0;
		totalScore = 0;
		plays = 0;
	}
	
	public boolean matchingDay(int yr, int mth, int dt) {
		if(yr == year) {
			if(mth == month) {
				if(dt == date) {
					return true;
				}
			}
		}
		return false;
	}
	public String formattedDate() {
		String monthString = "";
		String dateString = "";
		if(month < 10) {
			monthString = "0" + month;
		}else {
			monthString = "" + month;
		}
		
		if(date < 10) {
			dateString = "0" + date;
		}else {
			dateString = "" + date;
		}
		
		return year + " - " + monthString + " - " + dateString;
	}
	
	public void addEntry(double newTime, double newScore) {
		plays++;
		totalScore += newScore;
		score = totalScore / plays; 
		timespent += newTime;
	}
	
	public int getNumPlays() {
		return plays;
	}
	public double getAvgScore() {
		return score;
	}
	public double getTotalTimeSpent() {
		return timespent;
	}
	public double getTotalScore() {
		return totalScore;
	}

}
