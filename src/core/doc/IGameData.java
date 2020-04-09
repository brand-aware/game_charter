package core.doc;

import java.util.ArrayList;

import core.LogData;

public interface IGameData {
	
	/**
	 * Sets compiled log for one game
	 * 
	 * @param ArrayList<LogData> gameLog
	 */
	public void setGameLog(ArrayList<LogData> gameLog);
	
	/**
	 * Used to add new entries into compiled log
	 * 
	 * @param LogData gameLogData
	 */
	public void updateGameLog(LogData gameLogData);
	
	/**
	 * Returns game data is representing
	 * 
	 * @return String name
	 */
	public String getGameName();
	
	/**
	 * Returns specific record of gameplay according to
	 * provided index
	 * 
	 * @param int index
	 * @return LogData entry
	 */
	public LogData getRecord(int index);
	
	/**
	 * Finds the requested index of the associated entry
	 * 
	 * @param int year
	 * @param int month
	 * @param int day
	 * @return int index
	 */
	public int getMatchingDayPosition(int year, int month, int day);
	
	/**
	 * Size of the list (or) number of days with some representation
	 * in the data.
	 * 
	 * @return int size
	 */
	public int getDataSize();

}
