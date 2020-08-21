package hyn.rpc.registry;

import hyn.rpc.enumeration.RpcError;
import hyn.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HYN
 * 2020/8/20 11:34 下午
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>(16);
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * 注册服务，逻辑：将服务名和提供服务的对象保存在一个CHM中，并用一个并发安全的set保存已经注册的服务(使用服务的完整类名)
     * 服务名默认使用该服务所有接口的完整类名，一个服务可能对应多个服务名，而一个服务名只能对应一个具体服务
     * @param service
     * @param <T>
     */
    @Override
    public <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        //如果已经存在则结束执行
        if (registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        //遍历所有接口，加入CHM
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
