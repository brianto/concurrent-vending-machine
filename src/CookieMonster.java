import java.util.concurrent.TimeUnit;

public class CookieMonster extends Muncher {
	public CookieMonster(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	public void setup() {
		for (int day = 0; day < 15; day++) {
			this.schedule(new Runnable() {
				@Override
				public void run() {
					boolean success = CookieMonster.this.getVendingMachine().buyCookies(1);
					
					if (success)
						System.out.println("    Me love cookies");
					else
						System.out.println("    Me hungry");
				}
			}, day * 1000 + 250, TimeUnit.MILLISECONDS);
			
			this.schedule(new Runnable() {
				@Override
				public void run() {
					boolean success = CookieMonster.this.getVendingMachine().buyCookies(1);
					
					if (success)
						System.out.println("    Me love cookies");
					else
						System.out.println("    Me hungry");
				}
			}, day * 1000 + 750, TimeUnit.MILLISECONDS);
		}
	}
}