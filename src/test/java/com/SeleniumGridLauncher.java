package com;

import com.properties.TestProperties;
import com.store.checkout.CheckoutTest;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class SeleniumGridLauncher {

    private static ExecutorService executorService;

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(4);

        TestProperties.setProperty("driver", "grid.chrome");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(CheckoutTest.class))
                .build();
        Launcher launcher = LauncherFactory.create();

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                new SeleniumGridLauncher().run();
            });
        }
        executorService.shutdown();
    }

    private void run() {

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(CheckoutTest.class))
                .build();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();

        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
