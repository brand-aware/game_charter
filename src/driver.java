/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
import java.io.IOException;

import game_charter.ProgressScreen;
import game_charter.Properties;

public class driver {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("java driver <root dir>");
			System.exit(0);
		}
		try {
			Properties properties = new Properties(args[0]);
			ProgressScreen screen = new ProgressScreen(properties);
			screen.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}