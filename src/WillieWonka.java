import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author James Kuglics
 * @author Brian To
 */
public class WillieWonka extends Muncher {
	public WillieWonka(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	void setup() {
		Random rand = new Random(); 
		for (int day = 0; day < 15; day++) {
			this.schedule(new Runnable() {
				@Override
				public void run() {
					boolean success = WillieWonka.this.getVendingMachine().buyCandies(1);

					if (success)
						System.out.println("        The candy man can");
					else
						System.out.println("        Violet - you're turning violet");
				}
			}, day * 1000 + rand.nextInt(1000), TimeUnit.MILLISECONDS);
			// Every day, at some random point in the day
		}
	}
}
