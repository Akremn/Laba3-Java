package com.company;

public class Producer implements Runnable {
    private final int itemsToProduce;
    private final Manager manager;
    private final int producerId;

    public Producer(int producerId, int itemsToProduce, Manager manager) {
        this.producerId = producerId;
        this.itemsToProduce = itemsToProduce;
        this.manager = manager;
        new Thread(this, "Producer-" + producerId).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            try {
                manager.full.acquire();   // Очікуємо, поки буде місце
                manager.access.acquire(); // Блокуємо доступ до сховища

                String item = "Producer-" + producerId + "_item-" + i;
                manager.storage.add(item);
                System.out.println(Thread.currentThread().getName() + " added " + item);

                manager.access.release();
                manager.empty.release();  // Повідомляємо, що є новий елемент

                Thread.sleep(100); // Симуляція часу виробництва

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
