package de.neusta.workflow;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class ExceptionTask extends LogginTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log(delegateExecution, log);

        if ((Boolean) delegateExecution.getVariable("throwexception")) {
            throw new CustomException();
        }
    }
}
