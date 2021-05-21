package nl.fedex.aggregation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GuavaTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {

        List<String> executions = new ArrayList<>();
        System.out.println(LocalDateTime.now());
        for(int i = 0; i < 10; i++) {
            executions.add( i + " Done executing");
            System.out.println(LocalDateTime.now());
            if(executions.size() == 5) {
                scheduleRequests(executions);
                executions.removeAll(executions);
                System.out.println("new list");
            }
        }
    }

    private Object scheduleRequests(List<String> executions) throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture =
                scheduledExecutorService.schedule(new Callable() {
                                                      public Object call() throws Exception {
                                                          executions.forEach(item -> {
                                                              System.out.println("item finding it");
                                                          });
                                                          return "the result of the executions";
                                                      }
                                                  },
                        5,
                        TimeUnit.SECONDS);

        return scheduledFuture.get();
    }

}
