import java.util.concurrent.TimeUnit;

public class FatAlbert extends Muncher {
	public FatAlbert(final VendingMachine vendor) {
		super(vendor);
	}

	@Override
	void setup() {
		for (int day = 0; day < 15; day++) {
			int times = (int) (Math.random() * 3) + 1;

			for (int i = 0; i < times; i++) {
				int dayOffset = day * 1000;
				int timeOffset = 1000 / 3 * i;
				
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
