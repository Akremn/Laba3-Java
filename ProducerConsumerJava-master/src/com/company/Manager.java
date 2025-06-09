package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Manager {
    public final Semaphore access;
    public final Semaphore full;
    public final Semaphore empty;
    public final List<String> storage;

    public Manager(int storageSize) {
        access = new Semaphore(1);
        full = new Semaphore(storageSize);
        empty = new Semaphore(0);
        storage = new ArrayList<>();
    }
}