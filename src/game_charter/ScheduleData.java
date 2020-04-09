package game_charter;

/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2020
 * 
 */
public class ScheduleData {
	
	private String name;
	private String option;
	private String value;
	private String units;
	
	public ScheduleData(String nm, String opt, String val, String unt) {
		name = nm;
		option = opt;
		value = val;
		units = unt;
	}
	
	public String getName() {
		return name;
	}
	public String getOption() {
		return option;
	}
	public String getValue() {
		return value;
	}
	public String getUnits() {
		return units;
	}

}
