package com.ytd.mds.config.redis;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytd.mds.utils.MdsConstants;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching // 启用缓存
public class RedisConfig {

	@Bean("jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大连接数：能够同时建立的“最大链接个数”
		jedisPoolConfig.setMaxTotal(NumberUtils.toInt(MdsConstants.appConfig.getProperty("redis.pool.maxTotal"), 10));
		// 最大空闲数：空闲链接数大于maxIdle时，将进行回收
		jedisPoolConfig.setMaxIdle(NumberUtils.toInt(MdsConstants.appConfig.getProperty("redis.pool.maxIdle"), 5));
		// 最小空闲数：低于minIdle时，将创建新的链接
		jedisPoolConfig.setMinIdle(NumberUtils.toInt(MdsConstants.appConfig.getProperty("redis.pool.minIdle"), 2));
		// 最大等待时间：单位ms
		jedisPoolConfig.setMaxWaitMillis(NumberUtils.toLong(MdsConstants.appConfig.getProperty("redis.pool.maxWait"), 1000));
		// 使用连接时，检测连接是否成功
		jedisPoolConfig
				.setTestOnBorrow(BooleanUtils.toBoolean(MdsConstants.appConfig.getProperty("redis.pool.testOnBorrow")));
		// 返回连接时，检测连接是否成功
		jedisPoolConfig
				.setTestOnReturn(BooleanUtils.toBoolean(MdsConstants.appConfig.getProperty("redis.pool.testOnReturn")));
		return jedisPoolConfig;
	}

	@Bean("jedisConnectionFactory")
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		// 服务主机
		jedisConnectionFactory.setHostName(MdsConstants.appConfig.getProperty("redis.hostName"));
		// 服务端口
		jedisConnectionFactory.setPort(NumberUtils.toInt(MdsConstants.appConfig.getProperty("redis.port"), 6379));
		// 设置dbIndex
		jedisConnectionFactory.setDatabase(NumberUtils.toInt(MdsConstants.appConfig.getProperty("redis.dbIndex")));
		
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.afterPropertiesSet(); // 初始化
		return jedisConnectionFactory;
	}

	@SuppressWarnings("unchecked")
	@Bean("redisTemplate")
	public <K, V> RedisTemplate<K, V> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		Jackson2JsonRedisSerializer<V> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<V>(
				(Class<V>) Object.class);
		ObjectMapper om = new ObjectMapper();
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet(); // 初始化
		return redisTemplate;
	}

}
