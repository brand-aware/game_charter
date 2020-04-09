package core;

import java.util.ArrayList;

import core.doc.IGameData;

public class GameData implements IGameData{
	
	private String gameName;
	private ArrayList<LogData> gameLogList;
	
	
	public GameData(String name) {
		gameName = name;
		gameLogList = new ArrayList<LogData>();
	}
	
	public void setGameLog(ArrayList<LogData> gameLog) {
		gameLogList = gameLog;
	}
	
	public void updateGameLog(LogData gameLogData) {
		gameLogList.add(gameLogData);
		sortRecords();
	}
	public String getGameName() {
		return gameName;
	}
	public LogData getRecord(int index) {
		return gameLogList.get(index);
	}
	
	public int getMatchingDayPosition(int year, int month, int day) {
		for(int x = 0; x < gameLogList.size(); x++) {
			if(gameLogList.get(x).matchingDay(year, month, day)) {
				return x;
			}
		}
		return -1;
	}
	public int getDataSize() {
		return gameLogList.size();
	}
	
	public void sortRecords() {
		for(int x = 0; x < gameLogList.size(); x++) {
			for(int y = 0; y < gameLogList.size(); x++) {
				LogData game1 = gameLogList.get(x);
				LogData game2 = gameLogList.get(y);
				if(game1.formattedDate().compareTo(game2.formattedDate()) < 0) {
					gameLogList.set(x, game2);
					gameLogList.set(y, game1);
				}
			}
		}
	}

}
