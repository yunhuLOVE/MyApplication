package com.augur.zongyang.model;

import java.io.Serializable;
import java.util.List;

/**
 * 项目分类数据
 * Created by yunhu on 2018-01-09.
 */

public class ProjectTypeModel implements Serializable{

    private String itemCode;
    private Long itemId;
    private String itemMemo;
    private String itemName;
    private Long itemSortno;
    private Long itemStatus;
    private List<String> sysCodeItemFormList;
    private List<String> sysCodeTypeFormList;
    private String typeCode;
    private String typeGrade;
    private Long typeId;
    private String typeMemo;
    private String typeName;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemMemo() {
        return itemMemo;
    }

    public void setItemMemo(String itemMemo) {
        this.itemMemo = itemMemo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemSortno() {
        return itemSortno;
    }

    public void setItemSortno(Long itemSortno) {
        this.itemSortno = itemSortno;
    }

    public Long getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    public List<String> getSysCodeItemFormList() {
        return sysCodeItemFormList;
    }

    public void setSysCodeItemFormList(List<String> sysCodeItemFormList) {
        this.sysCodeItemFormList = sysCodeItemFormList;
    }

    public List<String> getSysCodeTypeFormList() {
        return sysCodeTypeFormList;
    }

    public void setSysCodeTypeFormList(List<String> sysCodeTypeFormList) {
        this.sysCodeTypeFormList = sysCodeTypeFormList;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeGrade() {
        return typeGrade;
    }

    public void setTypeGrade(String typeGrade) {
        this.typeGrade = typeGrade;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeMemo() {
        return typeMemo;
    }

    public void setTypeMemo(String typeMemo) {
        this.typeMemo = typeMemo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
