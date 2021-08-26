package de.neusta.workflow;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableProcessApplication
public class Application extends SpringBootProcessApplication {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);

  }


  public TaskListener getTaskListener() {
    return new TaskListener() {
      public void notify(DelegateTask delegateTask) {
        // handle all Task Events from Invoice Process

        System.out.println("");

      }
    };

  }



}