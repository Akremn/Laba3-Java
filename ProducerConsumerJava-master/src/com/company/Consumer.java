package com.company;

public class Consumer implements Runnable {
    private final int itemsToConsume;
    private final Manager manager;
    private final int consumerId;

    public Consumer(int consumerId, int itemsToConsume, Manager manager) {
        this.consumerId = consumerId;
        this.itemsToConsume = itemsToConsume;
        this.manager = manager;
        new Thread(this, "Consumer-" + consumerId).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToConsume; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + " чекає, поки з’явиться елемент у сховищі...");
                manager.empty.acquire();
                System.out.println(Thread.currentThread().getName() + " знайшов елемент для споживання.");

                System.out.println(Thread.currentThread().getName() + " очікує доступ до сховища...");
                manager.access.acquire();
                System.out.println(Thread.currentThread().getName() + " отримав доступ до сховища.");

                String item = manager.storage.remove(0);
                System.out.println(Thread.currentThread().getName() + " взяв елемент: " + item);

                manager.access.release();
                System.out.println(Thread.currentThread().getName() + " звільнив доступ до сховища.");

                manager.full.release();
                System.out.println(Thread.currentThread().getName() + " повідомив, що з'явилось вільне місце.");

                System.out.println(Thread.currentThread().getName() + " споживає елемент (спить 300 мс)...");
                Thread.sleep(300);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " було перервано.");
            }
        }
    }
}