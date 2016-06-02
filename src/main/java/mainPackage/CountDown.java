package mainPackage;

public class CountDown {
	private static final double SPEED_UP_FACTOR = 0.15;

	public static void main(String[] args) {
		// CountDownTimer timer = new CountDownTimer(10, 1000);
		// new Thread(timer).start();
	}

	class CountDownTimer implements Runnable {
		final int initialValue;
		final long intervalMillis;

		public CountDownTimer(int initialValue, long intervalMillis) {
			this.initialValue = initialValue;
			this.intervalMillis = intervalMillis;
		}

		public void run() {
			int counter = initialValue;
			double rate = 1;
			while (counter > 0) {
				sleep(Math.round(intervalMillis / rate));
				rate += rate * SPEED_UP_FACTOR;
				System.out.println(--counter);
			}
		}
	}

	private void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
