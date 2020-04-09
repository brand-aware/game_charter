package core;

public class LogProgress {
	
	String gameName;
	String logDirPath;
	String lastLogFilePath;
	String lastEntry;
	
	public LogProgress(String game, String dir) {
		gameName = game;
		logDirPath = dir;
	}
	
	public String getGameName() {
		return gameName;
	}
	public String getLogDirPath() {
		return logDirPath;
	}
	public String getLastLogFilePath() {
		return lastLogFilePath;
	}
	public String getLastEntry() {
		return lastEntry;
	}
	
	public void setLastLogFile(String path) {
		lastLogFilePath = path;
	}
	public void setLastEntry(String entry) {
		lastEntry = entry;
	}

}
