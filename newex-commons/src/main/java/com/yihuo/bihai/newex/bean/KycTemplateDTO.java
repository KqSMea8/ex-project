package com.yihuo.bihai.newex.bean;

import java.util.Date;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:51
 */
public class KycTemplateDTO {
    private Integer templateId;
    private String templateName;
    private String countryId;
    private Integer siteId;
    private Integer level;
    private Integer type;
    private String fields;
    private Integer delFlag;
    private Date createTime;
    private Date updateTime;

    public Integer getTemplateId() {
        return this.templateId;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getType() {
        return this.type;
    }

    public String getFields() {
        return this.fields;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public KycTemplateDTO() {
    }

    private KycTemplateDTO(Integer templateId, String templateName, String countryId, Integer siteId, Integer level, Integer type, String fields, Integer delFlag, Date createTime, Date updateTime) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.countryId = countryId;
        this.siteId = siteId;
        this.level = level;
        this.type = type;
        this.fields = fields;
        this.delFlag = delFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
