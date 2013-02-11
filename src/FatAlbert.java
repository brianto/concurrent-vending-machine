import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author James Kuglics
 * @author Brian To
 */
public class FatAlbert extends Muncher {
	public FatAlbert(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	void setup() {
		Random rand = new Random();
		
		for (int day = 0; day < 15; day++) {
			// Between 2 and 4 times throughout the day
			int times = rand.nextInt(3) + 2;

			for (int i = 0; i < times; i++) {
				int dayOffset = day * 1000; // Which day
				int timeOffset = 1000 / 3 * i; // Which part of the day (evenly spaced)
				
				this.schedule(new Runnable() {
					@Override
					public void run() {
						boolean cookie = FatAlbert.this
								.getVendingMachine().buyCookies(1);
						boolean candy = FatAlbert.this
								.getVendingMachine().buyCandies(1);

						if (cookie && candy)
							System.out.println("            Hey, hey, hey!");
						else if (cookie)
							System.out.println("            At least I got cookie");
						else if (candy)
							System.out.println("            At least I got candy");
						else
							System.out.println("            No food for me today");
					}
				}, dayOffset + timeOffset, TimeUnit.MILLISECONDS);
			}
		}
	}
}
