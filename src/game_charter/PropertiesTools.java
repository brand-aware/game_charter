package game_charter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import game_charter.doc.IPropertiesTools;
/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
public abstract class PropertiesTools extends CommonProperties implements IPropertiesTools{
	
	private boolean regGames = false;
	private boolean regDirs = false;
		
	public void createSettingsDir() throws IOException {
		File folder = new File(settingsDir);
		if(!folder.exists()) {
			folder.mkdir();
			folder = new File(settingsDir + File.separator + "users");
			folder.mkdir();
		}
		File file = new File(settings);
		if(!file.exists()) {
			file.createNewFile();
		}
	}
	
	public void createLoadProfile() throws IOException {
		File file = new File(profiles);
		if(!file.exists()) {
			file.createNewFile();
			profileList = null;
		}else {
			profileList = loadProfiles(file);
		}
	}
	
	public ArrayList<String> loadRegGames(File file) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while(reader.ready()) {
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		return list;
	}
	
	public String[] loadProfiles(File file) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while(reader.ready()) {
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		
		String[] loadedList = new String[list.size()];
		for(int x = 0; x < list.size(); x++) {
			loadedList[x] = list.get(x);
		}
		return loadedList;
	}
	
	public void setUsername(String name) {
		regGameList = new ArrayList<String>();
		gamePaths = new HashMap<String, String>();
		username = name;
		userProfile = settingsDir + File.separator
				+ "users" + File.separator + "user_" + username;
	}
	
	public boolean createUsername(String name) throws IOException {
		if(!validUsername(name)) {
			return false;
		}
		setUsername(name);
		File file = new File(userProfile);
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedReader reader = new BufferedReader(new FileReader(getProfiles()));
		String buffer = "";
		while(reader.ready()) {
			String line = reader.readLine();
			buffer += line + "\n";
		}
		buffer += name;
		reader.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(getProfiles()));
		writer.write(buffer);
		writer.close();
				
		return true;
	}
	
	public boolean validUsername(String name) {
		if(name.length() > 20  || name.length() < 1) {
			return false;
		}
		char[] nameChars = name.toCharArray();
		for(int x = 0; x < nameChars.length; x++) {
			String nameString = nameChars[x] + "";
			if(!Pattern.matches("[a-zA-Z]+", nameString)) {
				return false;
			}
		}
		return true;
	}
	
	public void loadUserProfile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(userProfile));
		regGameList = new ArrayList<String>();
		gamePaths = new HashMap<String, String>();
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.compareTo("###reg_games") == 0) {
				regGames = true;
				regDirs = false;
			}else if(line.compareTo("###reg_dirs") == 0) {
				regGames = false;
				regDirs = true;
			}else if(line.compareTo("###schedule") == 0) {
				break;
			}else {
				if(regGames) {
					regGameList.add(line);
				}else if(regDirs) {
					String[] gameDir = line.split(",");
					String name = gameDir[0];
					String path = gameDir[1];
					gamePaths.put(name, path);
				}
			}
		}
		regGames = false;
		regDirs = false;
		reader.close();
	}
	
	public boolean registerGame(Schedule schedule, String game) throws IOException {
		String name = searchStdRegList(game);
		if(name != null) {
			String path = getPath(name);
			if(path != null) {
				regGameList.add(game);
				gamePaths.put(game, path);
				saveProfile(schedule);
				return true;
			}
		}
		return false;
	}
	
	public String searchStdRegList(String game) {
		for(int x = 0; x < stdRegGameList.size(); x++) {
			String[] split = stdRegGameList.get(x).split(",");
			if(split[0].compareTo(game) == 0) {
				return split[1];
			}
		}
		return null;
	}
	
	public String getPath(String name) {
		String path = "";
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "executable(.exe)", "exe");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
		    if(!path.contains(name)) {
		    	JOptionPane.showMessageDialog(
		    			null, 
		    			"incorrect file selected", 
		    			"register game not successful", 
		    			JOptionPane.INFORMATION_MESSAGE);
		    		return null;
		    	}
		    }
		path = path.substring(0, path.lastIndexOf(File.separator));
		return path;
	}
	
	public void saveProfile(Schedule schedule) throws IOException {
		String buffer = "";
		BufferedWriter writer = new BufferedWriter(new FileWriter(userProfile));
		buffer += "###reg_games\n";
		for(int x = 0; x < regGameList.size(); x++) {
			buffer += regGameList.get(x) + "\n";
		}
		buffer += "###reg_dirs\n";
		Iterator<String> names = gamePaths.keySet().iterator();
		while(names.hasNext()) {
			String name = names.next();
			buffer += name + "," + gamePaths.get(name) + "\n";
		}
		buffer += "###schedule\n";
		ArrayList<String> nameList = schedule.getAllNames();
		for(String name : nameList) {
			String value = schedule.getValue(name);
			String option = schedule.getOption(name);
			String unit = schedule.getUnits(name);
			buffer += name + "," + option + "," + value + "," + unit + "\n";			
		}
		buffer += schedule.getSelectedFrequency();
		buffer += ",no";
		
		writer.write(buffer);
		writer.close();
	}
	
	public ArrayList<String> removeDuplicates(ArrayList<String> stdReg){
		ArrayList<String> notUnion = new ArrayList<String>();
		boolean add = true;
		for(int x = 0; x < stdReg.size(); x++) {
			String stdName = stdReg.get(x).split(",")[0];
			for(int y = 0; y < regGameList.size(); y++) {
				String regName = regGameList.get(y);
				if(regName.compareTo(stdName) == 0) {
					add = false;
					break;
				}
			}
			if(add) {
				notUnion.add(stdName);
			}
			add = true;
		}
		if(notUnion.size() < 1) {
			notUnion.add("<that's all we have>");
		}
		return notUnion;
	}
	
	public String getGamePath(String name) {
		return gamePaths.get(name);
	}

}
