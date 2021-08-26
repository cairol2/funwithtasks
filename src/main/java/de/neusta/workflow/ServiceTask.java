package de.neusta.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceTask extends  LogginTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ServiceTask.class.getCanonicalName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        log(delegateExecution,log);


    }
}
