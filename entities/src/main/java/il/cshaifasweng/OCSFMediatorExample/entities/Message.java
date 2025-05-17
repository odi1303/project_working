package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private final String key;
    private final Object payload;
    private final Class<?> requestType;
    private final Class<?> responseType;

    public Message(String key, Object payload, Class<?> requestType, Class<?> responseType) {
        this.key = key;
        this.payload = payload;
        this.requestType = requestType;
        this.responseType = responseType;
    }

    public String getKey() {
        return key;
    }

    public Object getPayload() {
        return payload;
    }

    public Class<?> getRequestType() {
        return requestType;
    }

    public Class<?> getResponseType() {
        return responseType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "key='" + key + '\'' +
                ", requestType=" + requestType.getSimpleName() +
                ", responseType=" + responseType.getSimpleName() +
                ", payload=" + payload +
                '}';
    }
}

