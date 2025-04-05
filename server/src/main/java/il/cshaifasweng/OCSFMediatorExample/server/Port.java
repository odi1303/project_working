package il.cshaifasweng.OCSFMediatorExample.server;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({PARAMETER, FIELD, METHOD})
public @interface Port {
    int value();
}