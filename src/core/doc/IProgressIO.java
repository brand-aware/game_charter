package core.doc;

import java.io.IOException;
import java.util.ArrayList;

import org.jfree.data.category.DefaultCategoryDataset;

public interface IProgressIO {

	/**
	 * Given path the registered game root directory, reads all logs
	 * currently on file into memory.
	 * 
	 * @param String path
	 * @param String gameName
	 * @throws IOException
	 */
	public void readLog(String path, String gameName) throws IOException;
	
	/**
	 * For the given registered game, prepares raw log data to be
	 * loaded into a chart-consumable data set.
	 * 
	 * @param String gameName
	 */
	public void condenseData(String gameName);
	
	/**
	 * Helps to determine what state current data set is in.
	 * 
	 * @param String[] entry
	 * @param String gameName
	 * @return int position
	 */
	public int isInDataList(String[] entry, String gameName);
	
	/**
	 * On request, takes given parameters and creates a DefaultCategoryDataset
	 * consumable by the JFreeChart library for use with GUI chart display.
	 * 
	 * @param ArrayList<String> names
	 * @param String series
	 * @return DefaultCategoryDataset dataset
	 */
	public DefaultCategoryDataset createDataset(ArrayList<String> names, String series);
	
}
