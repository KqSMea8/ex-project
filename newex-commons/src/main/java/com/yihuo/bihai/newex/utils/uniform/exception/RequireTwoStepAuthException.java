package com.yihuo.bihai.newex.utils.uniform.exception;

import com.yihuo.bihai.newex.bean.UserUniformBean;

/**
 * @author <a href="mailto:dragonjackielee@163.com">¿Ó÷«¡˙</a>
 * @since 2018/10/30 …œŒÁ11:10
 */
public class RequireTwoStepAuthException extends RuntimeException {
    private final UserUniformBean userUniformBean;

    public RequireTwoStepAuthException(UserUniformBean userUniformBean) {
        this.userUniformBean = userUniformBean;
    }

    public UserUniformBean getUserUniformBean() {
        return this.userUniformBean;
    }
}
