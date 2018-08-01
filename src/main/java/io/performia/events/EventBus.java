package io.performia.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Used to register/post events so they can be invoked from any class
 *
 * @author Kevin Brewster
 * @since 1.0
 */
public class EventBus {

    public static EventBus INSTANCE = new EventBus();
    private HashMap<Class<?>, CopyOnWriteArrayList<EventSubscriber>> subscriptions = new HashMap<>();

    private EventBus() {

    }

    /**
     * Registers all methods of a class into the event system with
     * the {@link package me.kbrewster.blazeapi.api.event.InvokeEvent} annotation
     *
     * @param obj An instance of the class which you would like to register as an event
     */
    public void register(Object obj) {
        Class<?> clazz = obj.getClass();
        // iterates though all the methods in the class
        for (Method method : clazz.getDeclaredMethods()) {
            // all the informaton and error checking before the method is added such
            // as if it even is an event before the element even touches the hashmap
            if (method.getAnnotation(InvokeEvent.class) == null) {
                continue;
            }
            if (method.getParameterCount() != 1) {
                throw new IllegalArgumentException("Couldn't find parameter inside of ${method.name}!");
            }
            Class<?> event = method.getParameterTypes()[0];
            Priority priority = method.getAnnotation(InvokeEvent.class).getPriority();
            method.setAccessible(true);

            // where the method gets added to the event key inside of the subscription hashmap
            // the arraylist is either sorted or created before the element is added
            if (subscriptions != null) {
                if (subscriptions.containsKey(event)) {
                    // sorts array on insertion
                    subscriptions.get(event).add(new EventSubscriber(obj, method, priority));
                    subscriptions.get(event).sort((eventSubscriber, subscriber) -> subscriber.getPriority().getValue());
                } else {
                    // event hasn't been added before so it creates a new instance
                    // sorting does not matter here since there is no other elements to compete against
                    subscriptions.put(event, new CopyOnWriteArrayList<>());
                    subscriptions.get(event).add(new EventSubscriber(obj, method, priority));
                }
            }
        }
    }

    /**
     * Registers all methods of each class in the array into the event system with
     * the {@link package me.kbrewster.blazeapi.api.event.InvokeEvent} annotation
     *
     * @param obj An instance of the class which you would like to register as an event
     */
    public void register(Object... obj) {
        for (Object object : obj) {
            this.register(object);
        }
    }

    /**
     * Unregisters all methods of the class instance from the event system
     * inside of {@link #subscriptions}
     *
     * @param obj An instance of the class which you would like to register as an event
     */
    public void unregister(Object obj) {
        this.subscriptions.forEach((aClass, subscriber) -> subscriber.removeIf(it -> it.getInstance() == obj));
    }

    /**
     * Unregisters all methods of the class from the event system
     * inside of {@link #subscriptions}
     *
     * @param clazz An instance of the class which you would like to register as an event
     */
    public void unregister(Class<?> clazz) {
        this.subscriptions.forEach((aClass, subscriber) -> subscriber.removeIf(it -> it.getInstance().getClass() == clazz));
    }

    /**
     * Invokes all of the methods which are inside of the classes
     * registered to the event
     *
     * @param event Event that is being posted
     */
    public void post(Object event) {
        CopyOnWriteArrayList<EventSubscriber> subs = this.subscriptions.getOrDefault(event.getClass(), null);
        if (subs != null) {
            subs.forEach(sub -> {
                try {
                    sub.getMethod().invoke(sub.getInstance(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}