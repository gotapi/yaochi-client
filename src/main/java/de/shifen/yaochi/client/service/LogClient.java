package de.shifen.yaochi.client.service;

import com.google.gson.Gson;
import de.shifen.yaochi.client.model.AmendTarget;
import de.shifen.yaochi.client.model.AttributeModifiedInAmendent;
import de.shifen.yaochi.client.model.Operation;
import de.shifen.yaochi.client.model.Operator;
import de.shifen.yaochi.client.task.LogAttributesTask;
import de.shifen.yaochi.client.task.LogEventTask;
import de.shifen.yaochi.client.task.LogObjectTask;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ms404 <yaochi.github@404.ms>
 */
public class LogClient implements InitializingBean {
    private static final int DEFAULT_THREAD_KEEP_ALIVE_TIME = 360;

    @Setter
    AutowireCapableBeanFactory capableBeanFactory;



    OperationSenderFactory.DefaultAsyncThreadFactory factory = new OperationSenderFactory.DefaultAsyncThreadFactory();
    ExecutorService executorService;
    public LogClient() {
    }


    /**
     * Get a deep copy of the input object
     *
     * @param originalObject Original object
     * @param <T> Type of original object
     * @return Deep copy of the original object
     */
    public <T> T deepCopy(T originalObject) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(originalObject), (Class<T>)originalObject.getClass());
    }

    public void logObject(String uuid, Operator operator, Operation operation, AmendTarget amendTarget,
                          Object oldObject, Object newObject, Long operateAt) {
        try {
            LogObjectTask logObjectTask = new LogObjectTask(uuid,
                    operation, operator,amendTarget, oldObject, newObject, operateAt);
            capableBeanFactory.autowireBean(logObjectTask);
            executorService.execute(logObjectTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void logAttributes(String uuid,Operator operator,Operation operation,AmendTarget amendTarget,
                              List<AttributeModifiedInAmendent> baseAttributeModelList,Long operateAt) {
        try {

            LogAttributesTask logAttributesTask = new LogAttributesTask(uuid,operator, operation,amendTarget, baseAttributeModelList, operateAt);
            capableBeanFactory.autowireBean(logAttributesTask);
            executorService.execute(logAttributesTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void logEvent(String uuid,Operator operator,Operation operation, Long operateAt) {
        try {
            LogEventTask logEventTask = new LogEventTask(uuid,operator, operation, operateAt);
            capableBeanFactory.autowireBean(logEventTask);
            executorService.execute(logEventTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (executorService == null) {
            int maxRequests = 100;
            executorService = new ThreadPoolExecutor(8, maxRequests, DEFAULT_THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(10000),
                    factory);
        }
    }
}
