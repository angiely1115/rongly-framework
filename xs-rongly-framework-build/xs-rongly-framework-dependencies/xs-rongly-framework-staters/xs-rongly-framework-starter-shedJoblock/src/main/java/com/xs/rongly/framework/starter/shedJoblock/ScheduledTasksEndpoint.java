package com.xs.rongly.framework.starter.shedJoblock;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Collection;
import java.util.Set;

/**
 * @Author: lvrongzhuan
 * @Description: 计划任务监控
 * @Date: 2019/1/22 11:56
 * @Version: 1.0
 * modified by:
 */
@Slf4j
@ConditionalOnClass({LockProvider.class, ScheduledTaskRegistrar.class})
@Endpoint(id = "rongly-scheduledtasks")
@Configuration
public class ScheduledTasksEndpoint extends org.springframework.boot.actuate.scheduling.ScheduledTasksEndpoint {
    private  Collection<ScheduledTaskHolder> scheduledTaskHolders;

    private  TaskScheduler taskScheduler;
    public ScheduledTasksEndpoint(Collection<ScheduledTaskHolder> scheduledTaskHolders,TaskScheduler taskScheduler) {
        super(scheduledTaskHolders);
        this.scheduledTaskHolders = scheduledTaskHolders;
        this.taskScheduler = taskScheduler;
    }
    @ReadOperation
    public void triggerScheduledTasksCron(String name) {
        for(ScheduledTaskHolder holder : scheduledTaskHolders) {
            Set<ScheduledTask> tasks = holder.getScheduledTasks();
            for(ScheduledTask task : tasks) {
                if(task.getTask().toString().equals(name)) {
                    log.info("{} task has trigger", name);
                    task.getTask().getRunnable().run();
                }
            }
        }
    }

    @WriteOperation
    public void changeScheduledTasksCron(String name, String cron) {
        for(ScheduledTaskHolder holder : scheduledTaskHolders) {
            Set<ScheduledTask> tasks = holder.getScheduledTasks();
            for(ScheduledTask task : tasks) {
                if(task.getTask().toString().equals(name)) {
                    task.cancel();
                    this.taskScheduler.schedule(task.getTask().getRunnable(), new CronTrigger(cron));
                    log.info("{} task has change to {}", name, cron);
                }
            }
        }
    }

    @DeleteOperation
    public void deleteScheduledTasksCron(String name) {
        for(ScheduledTaskHolder holder : scheduledTaskHolders) {
            Set<ScheduledTask> tasks = holder.getScheduledTasks();
            for(ScheduledTask task : tasks) {
                if(task.getTask().toString().equals(name)) {
                    task.cancel();
                    log.warn("{} task has cancel", name);
                }
            }
        }
    }

}
