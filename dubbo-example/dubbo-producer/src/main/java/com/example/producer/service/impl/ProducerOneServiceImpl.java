package com.example.producer.service.impl;

import com.example.common.IProducerOneService;
import org.springframework.stereotype.Service;

@Service("producerOneService")
public class ProducerOneServiceImpl implements IProducerOneService {
    @Override
    public String getMessage() {
        return "我是默认服务提供者";
    }
}
