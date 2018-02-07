package com.augur.zongyang.model;

/**
 * 按钮信息
 * Created by yunhu on 2018-01-29.
 */

public class ButtonModel {
    private Long id;
    private String elementCode;
    private String elementName;
    private Long parentId;
    private String elementType;
    private String elementGroup;
    private String isActive;
    private String memo;
    private String isPublic;
    private String elementTip;
    private String smallImgPath;
    private String middleImgPath;
    private String bigImgPath;
    private String elementInvoke;
    private Integer elementSortNo;
    private Integer groupSortNo;
    private String elementInvokeMode;
    private Long templateId;
    private String templateName;
    private String parentElementCode;
    private boolean hidden;
    private boolean locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementGroup() {
        return elementGroup;
    }

    public void setElementGroup(String elementGroup) {
        this.elementGroup = elementGroup;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getElementTip() {
        return elementTip;
    }

    public void setElementTip(String elementTip) {
        this.elementTip = elementTip;
    }

    public String getSmallImgPath() {
        return smallImgPath;
    }

    public void setSmallImgPath(String smallImgPath) {
        this.smallImgPath = smallImgPath;
    }

    public String getMiddleImgPath() {
        return middleImgPath;
    }

    public void setMiddleImgPath(String middleImgPath) {
        this.middleImgPath = middleImgPath;
    }

    public String getBigImgPath() {
        return bigImgPath;
    }

    public void setBigImgPath(String bigImgPath) {
        this.bigImgPath = bigImgPath;
    }

    public String getElementInvoke() {
        return elementInvoke;
    }

    public void setElementInvoke(String elementInvoke) {
        this.elementInvoke = elementInvoke;
    }

    public Integer getElementSortNo() {
        return elementSortNo;
    }

    public void setElementSortNo(Integer elementSortNo) {
        this.elementSortNo = elementSortNo;
    }

    public Integer getGroupSortNo() {
        return groupSortNo;
    }

    public void setGroupSortNo(Integer groupSortNo) {
        this.groupSortNo = groupSortNo;
    }

    public String getElementInvokeMode() {
        return elementInvokeMode;
    }

    public void setElementInvokeMode(String elementInvokeMode) {
        this.elementInvokeMode = elementInvokeMode;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getParentElementCode() {
        return parentElementCode;
    }

    public void setParentElementCode(String parentElementCode) {
        this.parentElementCode = parentElementCode;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
