package io.performia.events;

public @interface InvokeEvent {

    Priority getPriority() default Priority.NORMAL;
}
