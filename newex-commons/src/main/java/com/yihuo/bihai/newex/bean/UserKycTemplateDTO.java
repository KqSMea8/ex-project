//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yihuo.bihai.newex.bean;

import java.util.Date;

public class UserKycTemplateDTO {
    private Long id;
    private Long userId;
    private Integer templateId;
    private Integer siteId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private int level;

    public static UserKycTemplateDTO.UserKycTemplateDTOBuilder builder() {
        return new UserKycTemplateDTO.UserKycTemplateDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Integer getTemplateId() {
        return this.templateId;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public int getLevel() {
        return this.level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UserKycTemplateDTO() {
    }

    private UserKycTemplateDTO(Long id, Long userId, Integer templateId, Integer siteId, Integer status, Date createTime, Date updateTime, int level) {
        this.id = id;
        this.userId = userId;
        this.templateId = templateId;
        this.siteId = siteId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.level = level;
    }

    public static class UserKycTemplateDTOBuilder {
        private Long id;
        private Long userId;
        private Integer templateId;
        private Integer siteId;
        private Integer status;
        private Date createTime;
        private Date updateTime;
        private int level;

        UserKycTemplateDTOBuilder() {
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder templateId(Integer templateId) {
            this.templateId = templateId;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder siteId(Integer siteId) {
            this.siteId = siteId;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public UserKycTemplateDTO.UserKycTemplateDTOBuilder level(int level) {
            this.level = level;
            return this;
        }

        public UserKycTemplateDTO build() {
            return new UserKycTemplateDTO(this.id, this.userId, this.templateId, this.siteId, this.status, this.createTime, this.updateTime, this.level);
        }
    }
}
