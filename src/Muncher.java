import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implements the common functionality between
 * 
 * <ul>
 * 	<li>Fat Albert</li>
 * 	<li>Willie Wonka</li>
 * 	<li>Cookie Monster</li>
 * </ul>
 * 
 * @author James Kuglics
 * @author Brian To
 */
public abstract class Muncher {
	private final VendingMachine vendor;
	private final ScheduledExecutorService executor;
	private final Set<Event> events;

	/**
	 * Constructs a new muncher with the candy/cookie source (vending machine)
	 * 
	 * @param vendor
	 */
	public Muncher(VendingMachine vendor) {
		this.vendor = vendor;
		this.executor = Executors.newScheduledThreadPool(5);
		this.events = new HashSet<Event>();
	}
	
	/**
	 * Getter for communal vending machine
	 * 
	 * @return vending machine
	 */
	public VendingMachine getVendingMachine() {
		return this.vendor;
	}
	
	/**
	 * Schedules tasks for running
	 * 
	 * Each muncher must implement their own schedule of eating.
	 */
	abstract void setup();
	
	/**
	 * Convenience wrapper for scheduling events to occur
	 * 
	 * @param task a singular buying task
	 * @param start delay of when to start
	 * @param unit time unit of delay
	 */
	public void schedule(Runnable task, long start, TimeUnit unit) {
		this.events.add(new Event(task, start, unit));
	}
	
	/**
	 * Starts running all scheduled buying tasks and stops execution after 15 seconds (3 months).
	 */
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

	/**
	 * Wrapper for shutting down execution
	 */
	public void stop() {
		this.executor.shutdown();
	}
}

/**
 * Wrapper for events (holds task, start time, and time unit of start time delay)
 */
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
