import java.util.concurrent.TimeUnit;

/**
 * Cookie monster.
 * 
 * Attempts to eat 1 cookie and 1 candy evenly spaced out throughout the day.
 * 
 * @author James Kuglics
 * @author Brian To
 */
public class CookieMonster extends Muncher {
	public CookieMonster(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	public void setup() {
		for (int day = 0; day < 15; day++) {
			// Twice a day, buy a cookie and candy if possible
			this.schedule(new BuyCookieAndCandy(), day * 1000 + 250, // Time 1
					TimeUnit.MILLISECONDS);

			this.schedule(new BuyCookieAndCandy(), day * 1000 + 750, // Time 2
					TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * Buy a cookie and candy
	 */
	class BuyCookieAndCandy implements Runnable {
		@Override
		public void run() {
			boolean success = CookieMonster.this.getVendingMachine()
					.buyCookies(1);

			if (success)
				System.out.println("    Me love cookies");
			else
				System.out.println("    Me hungry");
		}
	}
}