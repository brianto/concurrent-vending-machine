import java.util.concurrent.TimeUnit;

public class WillieWonka extends Muncher {
	public WillieWonka(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	void setup() {
		for (int day = 0; day < 15; day++) {
			this.schedule(new Runnable() {
				@Override
				public void run() {
					boolean success = WillieWonka.this.getVendingMachine().buyCandies(1);

					if (success)
						System.out.println("The candy man can");
					else
						System.out.println("Violet - you're turning violet");
				}
			}, day * 1000 + 500, TimeUnit.MILLISECONDS);
		}
	}
}
