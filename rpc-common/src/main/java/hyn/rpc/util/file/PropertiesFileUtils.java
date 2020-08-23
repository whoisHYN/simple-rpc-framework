package hyn.rpc.util.file;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: HYN
 */
@Slf4j
public class PropertiesFileUtils {
    private PropertiesFileUtils() {}

    public static Properties readPropertiesFile(String fileName) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rpcConfigPath = rootPath + fileName;
        Properties properties = null;
        try (FileInputStream fileInputStream = new FileInputStream(rpcConfigPath)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]", fileName);
        }
        return properties;
    }
}
