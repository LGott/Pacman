package mainPackage;

class PausableThread extends Thread {
	private volatile boolean isPaused;

	@Override
	public void run() {
		//isPaused = true;
		while (isPaused ) {
			try {
				waitUntilResumed();
				doAction();
			} catch (InterruptedException e) {
				System.out.println("interrupted. ");
				break;
			}
		}
	}

	public void pauseAction() {
		System.out.println("paused");
		isPaused = true;
	}

	public synchronized void resumeAction() {
		System.out.println("resumed");
		isPaused = false;
		notifyAll();
	}

	// blocks current thread until it is resumed
	private synchronized void waitUntilResumed() throws InterruptedException {
		while (isPaused) {
			wait();
		}
	}

	private void doAction() throws InterruptedException {
		System.out.println("doing something");
		Thread.sleep(1000);
	}
}