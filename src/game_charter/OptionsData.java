package game_charter;

import java.util.ArrayList;

import game_charter.doc.IOptionsData;

public class OptionsData implements IOptionsData{
	
	private Properties properties;
	private ArrayList<String> selectedGames;
	private boolean multiSet;
	private String display;
	
	private String defaultGame;
	
	public OptionsData(Properties p) {
		properties = p;
		multiSet = false;
		defaultGame = "<options not set>";
		selectedGames = new ArrayList<String>();
		selectedGames.add(defaultGame);
		display = "score";
	}
	
	public Properties getProperties() {
		return properties;
	}
	public String getDefaultGame() {
		return defaultGame;
	}
	public ArrayList<String> getSelectedGames(){
		if(selectedGames.size() < 1) {
			selectedGames.add(defaultGame);
		}
		return selectedGames;
	}
	public boolean getMultiSet() {
		return multiSet;
	}
	public String getDisplay() {
		return display;
	}
	
	public void setMultiSet(boolean multi) {
		multiSet = multi;
		if(multiSet == false) {
			int index = selectedGames.size() - 1;
			String selection = selectedGames.get(index);
			selectedGames = new ArrayList<String>();
			selectedGames.add(selection);
		}
	}
	public void setDisplay(String selection) {
		display = selection;
	}
	
	public void clearSelected() {
		selectedGames = new ArrayList<String>();
		selectedGames.add(defaultGame);
	}
	
	public void addSelectedGame(String text) {
		if(selectedGames.size() == 1) {
			if(selectedGames.get(0).compareTo(defaultGame) == 0) {
				selectedGames.set(0, text);
			}else {
				if(multiSet) {
					selectedGames.add(text);
				}else {
					selectedGames.remove(0);
					selectedGames.add(0, text);
				}
			}
		}else {
			if(!isOnList(text)) {
				if(multiSet) {
					selectedGames.add(text);
				}else {
					selectedGames.remove(0);
					selectedGames.add(0, text);
				}
			}else {
				remove(text);
			}
		}
	}
	
	public boolean isOnList(String text) {
		for(int x = 0; x < selectedGames.size(); x++) {
			if(selectedGames.get(x).compareTo(text) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public void remove(String text) {
		for(int x = 0; x < selectedGames.size(); x++) {
			if(selectedGames.get(x).compareTo(text) == 0) {
				selectedGames.remove(x);
				if(selectedGames.size() == 0) {
					selectedGames.add(defaultGame);
				}
				break;
			}
		}
	}
}
