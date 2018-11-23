/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/11/12
 */
public class NommberFormat {
    public static void main(String[] args) {
        String m = "[";
        for (int i = 0; i < 130; i++) {
            m = m + i + ",";
        }
        System.out.println(m.substring(0, m.length() -1) + "]");
    }
}
