package com.example.demo;

import javafx.application.Platform;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.CountDownLatch;

public class JavaFXBaseTesting implements BeforeAllCallback {

    private static boolean javafxInitialized = false;

    @Override
    public void beforeAll(ExtensionContext context) throws InterruptedException {
        if (!javafxInitialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {});
            Platform.runLater(latch::countDown);
            latch.await();
            javafxInitialized = true;
        }
    }
}
