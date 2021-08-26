package de.neusta.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;

public abstract class LogginTask {

    public void log(DelegateExecution delegateExecution, Logger log){


        String subproces=delegateExecution.getId()+" ";


        log.info(subproces+subproces+"########################################################");
        log.info(subproces+"ExceptionTask: " + delegateExecution.getCurrentActivityName());

        log.info(subproces+"Variable Scope: " + delegateExecution.getVariableScopeKey());
        log.info(subproces+"ProcessInstance: " + delegateExecution.getProcessInstanceId());
        log.info(subproces+"CurrentActivitiy ID: " + delegateExecution.getCurrentActivityId());
        log.info(subproces+"Activity Instance ID: " + delegateExecution.getActivityInstanceId());
        log.info(subproces+"Parent ID: " + delegateExecution.getParentId());
        log.info(subproces+"Current Transition ID: " + delegateExecution.getCurrentTransitionId());
        log.info(subproces+"ID: " + delegateExecution.getId());
        log.info(subproces+"ScopeVariable: " + delegateExecution.getVariable("scope"));


            Integer scoped = (Integer) delegateExecution.getVariable("scoped");
            log.info(subproces + "Scoped Variable is: " + scoped);
        if(delegateExecution.getParentId()!=null) {
            if(scoped==null){
                scoped=0;
            }


            delegateExecution.setVariable("scoped", scoped+1, "SubProcess");
        }else{
            log.info("Anzahl Variablen: "+delegateExecution.getVariables().size());
        }


        Object variable = delegateExecution.getVariable(delegateExecution.getCurrentActivityId());
        delegateExecution.setVariable(delegateExecution.getCurrentActivityId(), delegateExecution.getActivityInstanceId());
        log.info(subproces+"Variable ist: " + variable);


        Object shared = delegateExecution.getVariable("shared");
//        if (delegateExecution.getId() != null) {
//
//            if(delegateExecution.getParentId()!=delegateExecution.getProcessInstanceId()){
//                shared = delegateExecution.getVariable(delegateExecution.getParentId() + "shared");
//                delegateExecution.setVariable(delegateExecution.getParentId() + "shared", delegateExecution.getActivityInstanceId());
//            }else{
        shared = delegateExecution.getVariable(delegateExecution.getParentId() + "shared");
        delegateExecution.setVariable(delegateExecution.getParentId() + "shared", delegateExecution.getActivityInstanceId());
        delegateExecution.setVariable(delegateExecution.getParentId() + "exceptionvariable", delegateExecution.getActivityInstanceId());
//            }
//
//
//        } else {
//            delegateExecution.setVariable("shared", delegateExecution.getActivityInstanceId());
//        }
        log.info(subproces+"Shared Variable ist: " + shared);


//        delegateExecution.getVariables().values().stream().forEach(e->
//                {
//                    System.out.print(e.toString()+" ");
//                }
//        );


        log.info(subproces+"########################################################");


    }


}
