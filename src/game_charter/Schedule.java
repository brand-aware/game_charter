package game_charter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2020
 * 
 */
public class Schedule {
	
	private Properties properties;
	private ArrayList<ScheduleData> sched;
	private String frequency = "daily";
	private ArrayList<String> freqList;
	
	public Schedule(Properties p) throws IOException {
		properties = p;
		sched = new ArrayList<ScheduleData>();
		loadFreqList();
	}
	
	public void addEntry(ScheduleData data) {
		String name = data.getName();
		boolean overwrite = false;
		for(int x = 0; x < sched.size(); x++) {
			String currentName = sched.get(x).getName();
			if(name.compareTo(currentName) == 0) {
				sched.set(x, data);
				overwrite = true;
				break;
			}
		}
		if(!overwrite) {
			sched.add(data);
		}
	}
	
	public ArrayList<ScheduleData> getAllEntries(){
		return sched;
	}
	
	public ArrayList<String> getAllNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(int x = 0; x < sched.size(); x++) {
			String name = sched.get(x).getName();
			names.add(name);
		}
		return names;
	}
	
	public String getValue(String name) {
		String text = "";
		for(int x = 0; x < sched.size(); x++) {
			String value = sched.get(x).getName();
			if(name.compareTo(value) == 0) {
				text = sched.get(x).getValue();
			}
		}
		return text;
	}
	
	public String getOption(String name) {
		String text = "";
		for(int x = 0; x < sched.size(); x++) {
			String value = sched.get(x).getName();
			if(name.compareTo(value) == 0) {
				text = sched.get(x).getOption();
			}
		}
		return text;
	}
	
	public String getUnits(String name) {
		String text = "";
		for(int x = 0; x < sched.size(); x++) {
			String value = sched.get(x).getName();
			if(name.compareTo(value) == 0) {
				text = sched.get(x).getUnits();
			}
		}
		return text;
	}
	
	public void setFrequency(int selection) {
		frequency = freqList.get(selection);
	}
	
	public String getSelectedFrequency() {
		return frequency;
	}
	
	public String[] getFrequencyList(){
		String[] list = new String[freqList.size()];
		for(int x = 0; x < list.length; x++) {
			list[x] = freqList.get(x);
		}
		return list;
	}
	
	public void loadFreqList() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(properties.getFreqListFile()));
		freqList = new ArrayList<String>();
		while(reader.ready()) {
			String line = reader.readLine();
			freqList.add(line);
		}
		reader.close();
	}
}
