package de.neusta.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.Random;

public class ExceptionTask extends LogginTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ExceptionTask.class.getCanonicalName());

        @Autowired
        RuntimeService runtimeService;

        @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        log(delegateExecution,log);

            if( new Random().nextBoolean())
        throw new RuntimeException();

    }
}
