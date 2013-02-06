/**
 * 
 */

/**
 * @author bxt5647
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VendingMachine vendor = VendingMachine.create();
		
		CookieMonster monster = new CookieMonster(vendor);
		
		vendor.init();
		monster.scheduleAndRun();
	}

}
