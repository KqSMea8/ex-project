package com.yihuo.bihai.newex.auth;

import org.apache.commons.lang3.math.NumberUtils;

import java.time.Instant;
/**
 * @author <a href="mailto:dragonjackielee@163.com">¿Ó÷«¡˙</a>
 * @since 2018/10/30 …œŒÁ10:32
 */
public class UserUniformQuery {
    private final Long userId;
    private final String phone;
    private final String email;
    private final int offset;
    private final int pageSize;
    private final Instant startTime;
    private final Instant endTime;

    public UserUniformQuery(Long userId, String phone, String email, int offset, int pageSize, Instant startTime, Instant endTime) {
        if (userId != null) {
            checkArgument(userId > 0L, "userId is %s but expected positive", userId);
        }

        if (phone != null) {
            checkArgument(NumberUtils.isDigits(phone), "phone is %s but expected digits", phone);
        }

        checkArgument(offset >= 0, "offset is %s but expected positive", offset);
        checkArgument(offset <= 500, "offset is %s but expected small than %s", offset, 500);
        checkArgument(pageSize > 0, "pageSize is %s but expected positive", pageSize);
        checkArgument(pageSize <= 50, "pageSize is %s but expected small than %s", pageSize, 50);
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.offset = offset;
        this.pageSize = pageSize;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static UserUniformQuery.UserUniformQueryBuilder builder() {
        return new UserUniformQuery.UserUniformQueryBuilder();
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public static class UserUniformQueryBuilder {
        private Long userId;
        private String phone;
        private String email;
        private int offset;
        private int pageSize;
        private Instant startTime;
        private Instant endTime;

        UserUniformQueryBuilder() {
        }

        public UserUniformQuery.UserUniformQueryBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder startTime(Instant startTime) {
            this.startTime = startTime;
            return this;
        }

        public UserUniformQuery.UserUniformQueryBuilder endTime(Instant endTime) {
            this.endTime = endTime;
            return this;
        }

        public UserUniformQuery build() {
            return new UserUniformQuery(this.userId, this.phone, this.email, this.offset, this.pageSize, this.startTime, this.endTime);
        }

        @Override
        public String toString() {
            return "UserUniformQuery.UserUniformQueryBuilder(userId=" + this.userId + ", phone=" + this.phone + ", email=" + this.email + ", offset=" + this.offset + ", pageSize=" + this.pageSize + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ")";
        }
    }
}
