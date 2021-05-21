package nl.fedex.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {
    public static final String KEY = "messageKey";
    public static final String EXECUTION_KEY = "executionKey";

    @Bean
    Config config() {
        Config config = new Config();

        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(300);
        config.getMapConfigs().put(KEY, mapConfig);
        config.getMapConfigs().put(EXECUTION_KEY, mapConfig);
        return config;
    }
}
