package com.example.consumer.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.example.common.IProducerOneService;
import com.example.consumer.service.IConsumerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConsumerServiceImpl implements IConsumerService {

    @Resource
    private IProducerOneService producerOneService;


    @Override
    public String getMessage() {
        //隐式传参
        RpcContext.getContext().setAttachment("dubbo.version","1.0");
        return producerOneService.getMessage();
    }
}
