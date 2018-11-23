package kafak.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("kafka")
public class TestKafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("send")
    public String send(String msg){
        kafkaTemplate.send("lzl_test", msg);
        return "success";
    }

    public static void main(String[] args) {
        String a = "[";
        for (int i = 0; i < 150; i++) {
            a += i + ",";
        }
        a = a.substring(0, a.length() -1) + "]";
        System.out.println(a);
    }

}
