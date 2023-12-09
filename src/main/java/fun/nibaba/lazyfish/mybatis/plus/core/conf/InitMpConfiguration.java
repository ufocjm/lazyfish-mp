package fun.nibaba.lazyfish.mybatis.plus.core.conf;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import fun.nibaba.lazyfish.mybatis.plus.core.injector.LazySqlInjector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化配置
 *
 * @author chenjiamin
 * @date 2021/5/27 4:53 下午
 */
@Configuration
public class InitMpConfiguration {

    @ConditionalOnMissingBean(DefaultSqlInjector.class)
    @Bean
    public ISqlInjector sqlInjector() {
        return new LazySqlInjector();
    }
}
