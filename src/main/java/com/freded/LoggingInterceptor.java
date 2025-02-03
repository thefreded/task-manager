package com.freded;

import jakarta.annotation.Priority;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Priority(1000)
@CustomLog
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethod(InvocationContext context) throws Exception {
        // Before the method is invoked
        System.out.println("Invoking method: " + context.getMethod().getName());

        // Proceed with the method invocation
        Object result = context.proceed();

        // After the method is invoked
        System.out.println("Method executed: " + context.getMethod().getName());

        return result;
    }
}
