/**
 * @author James Kuglics
 * @author Brian To
 */
public class Main {

	/**
	 * Run the simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		VendingMachine vendor = VendingMachine.create();
		
		CookieMonster monster = new CookieMonster(vendor);
		WillieWonka wonka = new WillieWonka(vendor);
		FatAlbert albert = new FatAlbert(vendor);
		
		vendor.init();
		monster.start();
		wonka.start();
		albert.start();
	}
}
