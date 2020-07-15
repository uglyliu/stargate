package com.ppdai.stargate.constant;

public enum InstanceStatusEnum {
    REGISTER_INSTANCE_IN_REGISTRY,
    DEREGISTER_INSTANCE_IN_REGISTRY,
    START_CONTAINER_IN_K8S,
    STOP_CONTAINER_IN_K8S,
    UPDATE_CONTAINER_IN_K8S,
    RESTART_CONTAINER_IN_K8S
}
