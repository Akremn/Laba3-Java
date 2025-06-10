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
                System.out.println(Thread.currentThread().getName() + " чекає, поки з’явиться місце в сховищі...");
                manager.full.acquire();
                System.out.println(Thread.currentThread().getName() + " знайшов місце в сховищі.");

                System.out.println(Thread.currentThread().getName() + " очікує доступ до сховища...");
                manager.access.acquire();
                System.out.println(Thread.currentThread().getName() + " отримав доступ до сховища.");

                String item = "Producer-" + producerId + "_item-" + i;
                manager.storage.add(item);
                System.out.println(Thread.currentThread().getName() + " додав елемент: " + item);

                manager.access.release();
                System.out.println(Thread.currentThread().getName() + " звільнив доступ до сховища.");

                manager.empty.release();
                System.out.println(Thread.currentThread().getName() + " повідомив, що елемент готовий для споживання.");

                System.out.println(Thread.currentThread().getName() + " спить 100 мс (симуляція виробництва)...");
                Thread.sleep(100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " було перервано.");
            }
        }
    }
}
