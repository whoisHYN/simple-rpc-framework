package hyn.rpc.enumeration;

/**
 * @Author: HYN
 */
public enum RpcConfigProperties {
    /**
     * 配置文件路径和zk地址
     */
    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;


    RpcConfigProperties(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
