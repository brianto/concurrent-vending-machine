import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akka.stm.Atomic;
import akka.stm.Ref;

public class CookieMonster {
	private final VendingMachine vendor;
	private final Ref<Boolean> keepRunning = new Ref<Boolean>(true);
	private final Timer timer;

	public CookieMonster(final VendingMachine vendor) {
		this.vendor = vendor;
		this.timer = new Timer();
	}
	
	public void scheduleAndRun() {
		for (int day = 0; day < 15; day++) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					boolean success = CookieMonster.this.vendor.buyCookies(1);
					
					if (success)
						System.out.println("Me love cookies");
					else
						System.out.println("Me hungry");
				}
			}, day * 1000 + 250);
		}
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
			}
		}, 15 * 1000);
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