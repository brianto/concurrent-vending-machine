/**
 * Excerpted from "Programming Concurrency on the JVM",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/vspcon for more book information.
 * 
 * @author James Kuglics
 * @author Brian To
 */
import akka.stm.Ref;
import akka.stm.Atomic;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VendingMachine {
	private final long MAX_COOKIES = 6;
	private final long MAX_CANDIES = 6;

	final Ref<Long> cookies = new Ref<Long>(MAX_COOKIES);
	final Ref<Long> candies = new Ref<Long>(MAX_CANDIES);

	private static final ScheduledExecutorService replenishTimer = Executors
			.newScheduledThreadPool(10);

	private VendingMachine() {
		
	}

	public void init() {
		replenishTimer.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("Replenish!");
				replenish();
			}
		}, 0, 3, TimeUnit.SECONDS);
		
		replenishTimer.schedule(new Runnable() {
			public void run() {
				replenishTimer.shutdown();
			}
		}, 15, TimeUnit.SECONDS);
	}

	public static VendingMachine create() {
		final VendingMachine energySource = new VendingMachine();
		return energySource;
	}

	public long getCookiesAvailable() {
		return cookies.get();
	}
	
	public long getCandiesAvailable() {
		return candies.get();
	}

	public boolean buyCookies(final long units) {
		return new Atomic<Boolean>() {
			public Boolean atomically() {
				long currentCookies = cookies.get();
				
				if (units > 0 && currentCookies >= units) {
					cookies.swap(currentCookies - units);
					return true;
				} else {
					return false;
				}
			}
		}.execute();
	}

	public boolean buyCandies(final long units) {
		return new Atomic<Boolean>() {
			public Boolean atomically() {
				long currentCandies = candies.get();
				
				if (units > 0 && currentCandies >= units) {
					candies.swap(currentCandies - units);
					return true;
				} else {
					return false;
				}
			}
		}.execute();
	}

	private void replenish() {
		new Atomic<Void>() {
			public Void atomically() {
				cookies.swap(MAX_COOKIES);
				candies.swap(MAX_CANDIES);
				
				return null;
			}
		}.execute();
	}
}