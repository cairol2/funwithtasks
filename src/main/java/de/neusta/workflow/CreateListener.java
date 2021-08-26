package de.neusta.workflow;

import org.camunda.bpm.engine.delegate.DelegateTask;

public class CreateListener implements org.camunda.bpm.engine.delegate.TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("I am a listener i listen");

    }
}
