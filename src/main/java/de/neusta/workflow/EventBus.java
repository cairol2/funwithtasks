package de.neusta.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventBus {

    RuntimeService runtimeService;

    @EventListener
    public void onTaskEvent(TaskEvent taskEvent) {
        System.out.println("I am a Delegate Interceptor i intercept Delegates");
    }


    @EventListener
    public void onDelegateEvent(Incident incident) {
        System.out.println("Incident of Type: " + incident.getIncidentType());


    }



    public boolean isRunning(String businessKey) {
        List<Execution> workflowkey = runtimeService
                .createExecutionQuery()
                .processDefinitionKey("workflowkey")
                .active().list();

        for (Execution execution : workflowkey) {
            String processInstanceId = execution.getProcessInstanceId();
            //TODO: Scope
            VariableInstance busineskey = runtimeService.createVariableInstanceQuery().processInstanceIdIn(processInstanceId).variableName("busineskey")
                    .singleResult();
            if(busineskey!=null){
                return true;
            }
        }
        return false;
    }


    @EventListener
    public void asdasd(ExecutionEvent executionEvent) {
        if ("end".equals(executionEvent.getEventName())) {
            if (executionEvent.getProcessInstanceId().equals(executionEvent.getActivityInstanceId())) {

                System.out.println("######Workflow End Event Caught######################");
                System.out.println(executionEvent.getActivityInstanceId());
                System.out.println("#####################################################");
            }
        }
    }
}
