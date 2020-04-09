package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import game_charter.Properties;
import game_charter.Schedule;
import game_charter.ScheduleData;

public class DateCheck {
	
	Properties properties;
	ArrayList<Boolean> scheduleItemsComplete;
	DateTool dateTool;
	ProgressIO progressIO;
	
	public DateCheck(ProgressIO pio, Properties p) throws IOException {
		properties = p;
		progressIO = pio;
		scheduleItemsComplete = new ArrayList<Boolean>();
		dateTool = new DateTool(progressIO);
		prepAllData();
	}
	
	public boolean checkComplete(Schedule schedule) {
		ArrayList<ScheduleData> scheduleEntries = schedule.getAllEntries();
		for(ScheduleData entry : scheduleEntries) {
			String name = entry.getName();
			dateTool.setName(name);
			String range = schedule.getSelectedFrequency();
			String category = entry.getOption();
			String goal = entry.getValue();
			boolean done = dateTool.runCheck(range, category, goal);
			scheduleItemsComplete.add(done);
		}
		
		return isDone();
	}
	
	public boolean isDone() {
		for(Boolean item : scheduleItemsComplete) {
			if(!item) {
				return false;
			}
		}
		if(scheduleItemsComplete.size() > 0) {
			return true;
		}
		return false;
	}
	
	public void prepAllData() throws IOException {
		ArrayList<String> gameList = properties.getRegGames();
		for(String game : gameList) {
			String path = properties.getGamePath(game);
			path += File.separator + "logs";
			if(!progressIO.isRead(game)) {
				progressIO.readLog(path, game);
			}
			if(!progressIO.isCondense(game)) {
				progressIO.condenseData(game);
			}
		}
	}
}
