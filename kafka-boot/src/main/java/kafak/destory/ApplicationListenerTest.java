package kafak.destory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/11/22
 */
@Component
public class ApplicationListenerTest implements ApplicationListener {
    @Autowired
    private CacheServiceTest cacheServiceTest;
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ApplicationReadyEvent) {
            System.out.println("<<<<<<<<<<<<<<<我又启动了>>>>>>>>>>>>>>>>");
        }else if(applicationEvent instanceof ContextClosedEvent) {
            cacheServiceTest.loadEngine();
        }
    }
}
