package kafak.message;

import kafak.service.MessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("send")
@Slf4j
public class TestMessageSendController {

    @RequestMapping("getMessage")
    public String send(String code){

        String result = "error.code.biz.";
        String message = MessageSourceUtil.getMessage(result + code, null);
        return message;
    }


}
