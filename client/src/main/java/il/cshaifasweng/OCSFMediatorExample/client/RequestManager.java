package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class RequestManager {
    private static final RequestManager INSTANCE = new RequestManager();

    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> responses = new ConcurrentHashMap<>();

    private RequestManager() {} // Prevent outside instantiation

    public static RequestManager getInstance() {
        return INSTANCE;
    }

    public <T> T sendAndWait(Object requestPayload, long timeoutMillis, Class<?> requestType, Class<T> responseType) throws Exception {
        String key = UUID.randomUUID().toString();
        Object lock = new Object();
        locks.put(key, lock);

        Message message = new Message(key, requestPayload, requestType, responseType);
        SimpleClient.getClient().sendToServer(message);

        synchronized (lock) {
            lock.wait(timeoutMillis);
        }

        locks.remove(key);
        Object response = responses.remove(key);
        return responseType.cast(response);
    }

    public void setResponse(String key, Object response) {
        Object lock = locks.get(key);
        if (lock != null) {
            responses.put(key, response);
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}

