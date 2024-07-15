package experimental.dynamic_steps.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class CustomTaskExecutor implements TaskExecutor {
private static final Logger log = LoggerFactory.getLogger(CustomTaskExecutor.class);
    @Override
    public void execute(Runnable task) {
        Thread thread = Thread.ofVirtual().start(task);
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error("Virtual thread of task executor was interrupted. Cause: {}", e.getCause());
        }
    }

}
