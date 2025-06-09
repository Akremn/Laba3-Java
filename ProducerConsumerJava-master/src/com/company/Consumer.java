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
                manager.empty.acquire();  // Очікуємо, поки з'явиться елемент
                manager.access.acquire(); // Блокуємо доступ до сховища

                String item = manager.storage.remove(0);
                System.out.println(Thread.currentThread().getName() + " took " + item);

                manager.access.release();
                manager.full.release();   // Повідомляємо, що звільнилось місце

                Thread.sleep(300); // Симуляція часу споживання

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}