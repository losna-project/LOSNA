package fr.eni.losna.utility;

import static java.util.concurrent.TimeUnit.HOURS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class BatchRunner {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void dailyBatch() {
		final Runnable processBatch = new Runnable() {
			public void run() {
				Batch.batch();
			}
		};

		final ScheduledFuture<?> batchHandle = scheduler.scheduleAtFixedRate(processBatch, 0, 24, HOURS);
		scheduler.schedule(new Runnable() {
			public void run() {
				batchHandle.cancel(true);
			}
		}, 24 * 30, HOURS);
	}
}
