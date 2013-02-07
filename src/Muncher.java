import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Muncher {
	private final VendingMachine vendor;
	private final ScheduledExecutorService executor;
	private final Set<Event> events;

	public Muncher(VendingMachine vendor) {
		this.vendor = vendor;
		this.executor = Executors.newScheduledThreadPool(5);
		this.events = new HashSet<Event>();
	}
	
	public VendingMachine getVendingMachine() {
		return this.vendor;
	}
	
	abstract void setup();
	
	public void schedule(Runnable task, long start, TimeUnit unit) {
		this.events.add(new Event(task, start, unit));
	}
	
	public void start() {
		this.setup();
		
		this.schedule(new Runnable() {
			@Override
			public void run() {
				Muncher.this.stop();
			}
		}, 15, TimeUnit.SECONDS);
		
		for (Event e : this.events)
			this.executor.schedule(e.getTask(), e.getStart(), e.getTimeUnit());
	}
	
	public void stop() {
		this.executor.shutdown();
	}
}

class Event {
	final private Runnable task;
	final private long start;
	final private TimeUnit unit;
	
	public Event(Runnable task, long start, TimeUnit unit) {
		this.task = task;
		this.start = start;
		this.unit = unit;
	}
	
	public Runnable getTask() { return this.task; }
	public long getStart() { return this.start; }
	public TimeUnit getTimeUnit() { return this.unit; }
}
