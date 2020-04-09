package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.jfree.data.category.DefaultCategoryDataset;

import core.doc.IProgressIO;
import game_charter.Properties;

public class ProgressIO implements IProgressIO{
	
	private ArrayList<String[]> currentEntries;
	private ArrayList<GameData> dataList;
	//private ArrayList<LogProgress> logProgress;
	
	private ConcurrentHashMap<String, Boolean> readLog;
	private ConcurrentHashMap<String, Boolean> condense;
	
	//private final int PLAYER_NUM = 0;
	private final int SCORE_NUM = 1;
	private final int YEAR_NUM = 2;
	private final int MONTH_NUM = 3;
	private final int DATE_NUM = 4;
	private final int TIME_NUM = 5;
	
	public ProgressIO(Properties properties) {
		currentEntries = new ArrayList<String[]>();
		dataList = new ArrayList<GameData>();
		//logProgress = new ArrayList<LogProgress>();
		
		readLog = new ConcurrentHashMap<String, Boolean>();
		condense = new ConcurrentHashMap<String, Boolean>();
		ArrayList<String> gameList = properties.getRegGames();
		for(int x = 0; x < gameList.size(); x++) {
			String name = gameList.get(x);
			readLog.put(name, false);
			condense.put(name, false);
		}
	}
	
	public boolean isRead(String name) {
		return readLog.get(name);
	}
	
	public void readLog(String path, String gameName) throws IOException {
		File logDir = new File(path);
		File[] dirList = logDir.listFiles();
		currentEntries = new ArrayList<String[]>();
		if(dirList != null) {
			for(int x = 0; x < dirList.length; x++) {
				if(dirList[x].getAbsolutePath().contains("log.txt")) {
					BufferedReader reader = new BufferedReader(new FileReader(dirList[x]));
					while(reader.ready()) {
						String line = reader.readLine();
						String[] data = line.split(",");
						currentEntries.add(data);
					}
					
					reader.close();
				}
			}
		}
		readLog.put(gameName, true);
	}
	
	public boolean isCondense(String name) {
		return condense.get(name);
	}
	
	public void condenseData(String gameName) {
		ArrayList<LogData> gameDataList = new ArrayList<LogData>();
		GameData gameData = new GameData(gameName);
		gameData.setGameLog(gameDataList);
		dataList.add(gameData);		
		for(int x = 0; x < currentEntries.size(); x++) {
			String[] entry = currentEntries.get(x);
			int position = isInDataList(entry, gameName);
			if(position != -1) {
				gameDataList.get(position).addEntry(
						Double.parseDouble(entry[TIME_NUM]), 
						Double.parseDouble(entry[SCORE_NUM]));
			}else {
				LogData logData = new LogData(
						Integer.parseInt(entry[YEAR_NUM]), 
						Integer.parseInt(entry[MONTH_NUM]), 
						Integer.parseInt(entry[DATE_NUM]));
				logData.addEntry(
						Double.parseDouble(entry[TIME_NUM]), 
						Double.parseDouble(entry[SCORE_NUM]));
				gameDataList.add(logData);
			}
		}
		condense.put(gameName, true);
	}
	
	public int isInDataList(String[] entry, String gameName) {
		int position = -1;
		for(int x = 0; x < dataList.size(); x++) {
			String name  = dataList.get(x).getGameName();
			if(name.compareTo(gameName) == 0) {
				position = x;
				break;
			}
		}
		int matchingDayPosition = -1;
		if(position != -1) {
			 matchingDayPosition = dataList.get(position).getMatchingDayPosition(
						Integer.parseInt(entry[YEAR_NUM]), 
						Integer.parseInt(entry[MONTH_NUM]), 
						Integer.parseInt(entry[DATE_NUM]));
		}
		return matchingDayPosition;
	}
	
	public int findGame(String name) {
		int index = -1;
		for(int x = 0; x < dataList.size(); x++) {
			GameData data = dataList.get(x);
			String gameName = data.getGameName();
			if(gameName.compareTo(name) == 0) {
				index = x;
				break;
			}
		}
		return index;
	}
	
	public DefaultCategoryDataset createDataset(ArrayList<String> names, String series) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ArrayList<GameData> selectedGames = new ArrayList<GameData>();
		for(int x = 0; x < names.size(); x++) {
			int gameIndex = findGame(names.get(x));
			GameData game = dataList.get(gameIndex);
			selectedGames.add(game);
			String gameName = game.getGameName();
			
			int dataSize = game.getDataSize();
			for(int y = 0; y < dataSize; y++) {
				LogData data = game.getRecord(y);
				if(series.compareTo("playtime") == 0) {
					dataset.addValue(
							data.getTotalTimeSpent(), 
							gameName, 
							data.formattedDate());
				}else if(series.compareTo("score") == 0) {
					dataset.addValue(data.getAvgScore(), gameName, data.formattedDate());
				}else if(series.compareTo("playcount") == 0) {
					dataset.addValue(data.getNumPlays(), gameName, data.formattedDate());
				}else if(series.compareTo("total score") == 0) {
					dataset.addValue(data.getTotalScore(), gameName, data.formattedDate());
				}else{
					System.exit(0);
				}
			}
		}
		return dataset;
	}
	
	public String createTextOutput(ArrayList<String> names) {
		DecimalFormat formatter = new DecimalFormat(".##");
		String outputBuffer = "";
		ArrayList<GameData> selectedGames = new ArrayList<GameData>();
		for(int x = 0; x < names.size(); x++) {
			int gameIndex = findGame(names.get(x));
			if(gameIndex != -1) {
				GameData game = dataList.get(gameIndex);
				selectedGames.add(game);
				String gameName = game.getGameName();
				
				int dataSize = game.getDataSize();
				for(int y = 0; y < dataSize; y++) {
					LogData data = game.getRecord(y);
					outputBuffer += gameName + ": " + data.formattedDate() + ": ";
					outputBuffer += formatter.format(data.getTotalTimeSpent()) + " seconds, ";
					outputBuffer += formatter.format(data.getAvgScore()) + " score, ";
					outputBuffer += data.getNumPlays() + " plays, ";
					outputBuffer += data.getTotalScore() + " total score";
					outputBuffer += "\n";
				}
			}
		}
		if(outputBuffer.compareTo("") != 0) {
			outputBuffer = outputBuffer.substring(0, outputBuffer.lastIndexOf("\n"));
		}
		return outputBuffer;
	}
	
	public boolean hasLog(int year, int month, int day, String game) {
		int index = findGame(game);
		if(index > -1) {
			GameData gameData = dataList.get(index);
			
			int dataSize = gameData.getDataSize();
			for(int x = 0; x < dataSize; x++) {
				LogData data = gameData.getRecord(x);
				if(data.matchingDay(year, month, day)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getDataFromLog(int year, int month, int day, String game, String category) {
		String data = "";
		int index = findGame(game);
		GameData gameData = dataList.get(index);
		
		int dataSize = gameData.getDataSize();
		for(int x = 0; x < dataSize; x++) {
			LogData record = gameData.getRecord(x);
			if(record.matchingDay(year, month, day)) {
				if(category.compareTo("score") == 0) {
					data = "" + record.getAvgScore();
				}else if(category.compareTo("timespent") == 0) {
					data = "" + record.getTotalTimeSpent();
				}else if(category.compareTo("totalscore") == 0) {
					data = "" + record.getTotalScore();
				}else if(category.compareTo("playcount") == 0) {
					data = "" + record.getNumPlays();
				}
			}
		}
		return data;
	}
}
