package kafak.destory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/11/22
 */
@Service
@Slf4j
public class CacheServiceTest {
    private Integer lastProcessTime = 0;
    private Integer needSave() {
        Integer l = lastProcessTime++;
        log.info("lastProcessTime : " + l);
        return l;
    }

    public void loadEngine() {
        Integer b = needSave();
        if (b > 0) {
            log.info("b > 0");
            return;
        }
        log.info("<<<<<<<<<<<<<<<我又被销毁了>>>>>>>>>>>>>>>>");
    }
}
