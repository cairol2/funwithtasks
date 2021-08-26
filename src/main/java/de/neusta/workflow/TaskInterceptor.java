package de.neusta.workflow;

import org.camunda.bpm.engine.impl.delegate.DelegateInvocation;
import org.camunda.bpm.engine.impl.interceptor.DelegateInterceptor;
import org.springframework.stereotype.Component;

@Component
public class TaskInterceptor implements DelegateInterceptor {
    @Override
    public void handleInvocation(DelegateInvocation delegateInvocation) throws Exception {

        System.out.println("I am a Delegate Interceptor i intercept Delegates");

    }
}
