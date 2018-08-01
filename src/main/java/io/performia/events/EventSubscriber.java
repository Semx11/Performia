package io.performia.events;

import java.lang.reflect.Method;

public class EventSubscriber {

    private Object instance;

    private Method method;

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public Priority getPriority() {
        return priority;
    }

    private Priority priority;

    public EventSubscriber(Object instance, Method method, Priority priority) {
        this.instance = instance;
        this.method = method;
        this.priority = priority;
    }


}