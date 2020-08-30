package hyn.rpc;


import hyn.rpc.annotation.Service;
import hyn.rpc.annotation.ServiceScan;
import hyn.rpc.enumeration.RpcError;
import hyn.rpc.exception.RpcException;
import hyn.rpc.provider.ServiceProvider;
import hyn.rpc.registry.ServiceRegistry;
import hyn.rpc.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @Author: HYN
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer {

    protected String host;
    protected int port;

    protected ServiceProvider serviceProvider;
    protected ServiceRegistry serviceRegistry;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(ServiceScan.class)) {
                log.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if("".equals(basePackage)) {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for(Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.registerService(serviceName, new InetSocketAddress(host, port));
    }
}
