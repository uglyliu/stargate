package com.ppdai.stargate.job.task.handler.instance;

import com.ppdai.stargate.constant.InstanceStatusEnum;
import com.ppdai.stargate.constant.JobTaskTypeEnum;
import com.ppdai.stargate.job.JobInfo;
import com.ppdai.stargate.job.task.AbstractTaskHandler;
import com.ppdai.stargate.job.task.TaskInfo;
import com.ppdai.stargate.po.ApplicationEntity;
import com.ppdai.stargate.po.InstanceEntity;
import com.ppdai.stargate.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DeregistryOneInRegistryTaskHandler extends AbstractTaskHandler {

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private TrafficService trafficService;

    @Override
    public String getName() {
        return JobTaskTypeEnum.DEREGISTRY_ONE.name();
    }

    @Override
    public void execute(TaskInfo taskInfo) throws Exception {
        JobInfo jobInfo = taskInfo.getJobInfo();

        log.info("开始向注册中心注销实例信息: instanceId={}, taskId={}, jobId={}",
                taskInfo.getInstanceId(), taskInfo.getId(), jobInfo.getId());

        InstanceEntity instanceEntity = getInstance(taskInfo);

        ApplicationEntity applicationEntity = getApplication(instanceEntity.getAppId());


        trafficService.deregisterOne(instanceEntity.getEnv(), applicationEntity.getCmdbAppId(), applicationEntity.getName(),
                getAppDomain(applicationEntity, instanceEntity.getEnv()), instanceEntity);

        log.info("向注册中心注销实例信息成功: instance={}, taskId={}, jobId={}",
                instanceEntity.getName(), taskInfo.getId(), jobInfo.getId());

        instanceEntity.setStatus(InstanceStatusEnum.DEREGISTER_INSTANCE_IN_REGISTRY.name());
        instanceService.saveInstance(instanceEntity);
        log.info("保存实例到数据库成功: instance={}", instanceEntity.getName());
    }
}
