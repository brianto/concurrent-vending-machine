import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import akka.stm.Atomic;
import akka.stm.Ref;

public class CookieMonster {
	private final VendingMachine vendor;
	private final Ref<Boolean> keepRunning = new Ref<Boolean>(true);
	private final ScheduledExecutorService executor;

	public CookieMonster(final VendingMachine vendor) {
		this.vendor = vendor;
		this.executor = Executors.newScheduledThreadPool(10);
	}
	
	public void scheduleAndRun() {
		for (int day = 0; day < 15; day++) {
			this.executor.schedule(new Runnable() {
				@Override
				public void run() {
					boolean success = CookieMonster.this.vendor.buyCookies(1);
					
					if (success)
						System.out.println("Me love cookies");
					else
						System.out.println("Me hungry");
				}
			}, day * 1000 + 250, TimeUnit.MILLISECONDS);
		}
		
		this.executor.schedule(new Runnable() {
			@Override
			public void run() {
				executor.shutdown();
			}
		}, 15, TimeUnit.SECONDS);
	}

	public void shutdown() {
		new Atomic<Void>() {
			@Override
			public Void atomically() {
				keepRunning.swap(false);
				
				return null;
			}
		}.execute();
	}
}