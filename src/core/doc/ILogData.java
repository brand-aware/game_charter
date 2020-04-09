package core.doc;

/**
 * Object used to represent exactly ONE calendar day for exactly
 * one registered game.
 * 
 * @author mike802
 *
 */
public interface ILogData {
	
	/**
	 * Determines if this object can be used to add new entries
	 * 
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return boolean matchingDay
	 */
	public boolean matchingDay(int yr, int mth, int dt);
	
	/**
	 * Creates a cumulative total for the current calendar day
	 * 
	 * @param double newTime
	 * @param double newScore
	 */
	public void addEntry(double newTime, double newScore);
	
	/**
	 * Returns formatted representation of current date entry in
	 * the form of yyyy - mm - dd
	 * 
	 * @return String date
	 */
	public String formattedDate();

	/**
	 * Gets the number of times the game was played (full log
	 * cycle)
	 * 
	 * @return int plays
	 */
	public int getNumPlays();
	
	/**
	 * Gets the daily average point total (should be ~st_dev)
	 * 
	 * @return double score
	 */
	public double getAvgScore();
	
	/**
	 * Gets the daily accumulated time spent playing
	 * 
	 * @return double time
	 */
	public double getTotalTimeSpent();
	
}
