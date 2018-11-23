package com.yihuo.bihai.newex.security.jwt.model;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:44
 */
public class JwtConsts {
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_DELETED = -1;
    public static final int STATUS_SUCCESS = 10;
    public static final int STATUS_PASS_STAGE1 = 1;
    public static final String TOKEN_SESSIONID = "sid";
    public static final String CURRENT_USER = "currentUser";
    public static final String TOKEN_MEMBER_SESSIONID = "sessionId";
    public static final String USER_LOGINNAME = "user_loginName";
    public static final String USER_BEHAVIOR = "user_behavior";
    public static final String CAPTCHA_KEY = "captcha_";
    public static final String TOKEN_MEMBER_ID = "tokenId";
    public static final String TOKEN_MEMBER_PASSTIME = "accessPassTime";
    public static final String DARM_ROOM_KICKOUT_USER = "OKDARM_KICKOUT_USER";
    public static final String DARM_ROOM_DISCARD_TOKEN = "OKDARM_DISCARD_TOKEN";

    public JwtConsts() {
    }
}
