package game_charter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Properties extends PropertiesTools {
	
	public Properties(String dir) throws IOException {
		root = dir;
		imgDir = root + File.separator + "img";
		company = imgDir + File.separator + "company.png";
		company_iframe = imgDir + File.separator + "company_iframe.png";
		logo = imgDir + File.separator + "logo.png";
		background = imgDir + File.separator + "background.png";
		completePic = imgDir + File.separator + "complete.png";
		incompletePic = imgDir + File.separator + "not_complete.png";
		
		settingsDir = root + File.separator + "settings";
		selector = settingsDir + File.separator + "selector_options.txt";
		freqListFile = settingsDir + File.separator + "freq_list.txt";
		settings = settingsDir + File.separator + "reg_games.txt";
		createSettingsDir();
		
		profiles = settingsDir + File.separator + "profiles.txt";
		createLoadProfile();
		
		lastUse = settingsDir + File.separator + "last_use.txt";
		File file = new File(lastUse);
		if(!file.exists()) {
			file.createNewFile();
		}
		
		//lists for standard selection options
		stdRegGameList = loadRegGames(new File(settings));
		gamePaths = new HashMap<String, String>();
		regGameList = new ArrayList<String>();
	}
	
	public String getLastUseFile() {
		return lastUse;
	}
	public String getProfiles() {
		return profiles;
	}
	public String getUsername() {
		return username;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public ArrayList<String> getStandardGameList(){
		ArrayList<String> games = new ArrayList<String>();
		for(int x = 0; x < stdRegGameList.size(); x++) {
			String[] data = stdRegGameList.get(x).split(",");
			games.add(data[0]);
		}
		return games;
	}
	public ArrayList<String> getRegGames(){
		return regGameList;
	}
	public String getSelectorList() {
		return selector;
	}
	public String getFreqListFile() {
		return freqListFile;
	}
	public String[] getProfileList(){
		return profileList;
	}
	public String getCompany() {
		return company;
	}
	public String getCompanyIframe() {
		return company_iframe;
	}
	public String getLogo() {
		return logo;
	}
	public String getBackground() {
		return background;
	}
	public String getCompletePic() {
		return completePic;
	}
	public String getIncompletePic() {
		return incompletePic;
	}
	public String genIconPath(String path) {
		return path + File.separator + "img" + File.separator + "product.png";
	}
	public String genLaunchPath(String path) {
		return path + File.separator + "launcher.bat";
	}
	public void setSchedule(Schedule schd) {
		schedule = schd;
	}
	public Schedule getSchedule() {
		return schedule;
	}

}
