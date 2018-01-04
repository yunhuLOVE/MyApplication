package com.augur.zongyang.model.result;

import com.augur.zongyang.model.Sort;
import com.augur.zongyang.model.TaskDetailInfoModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yunhu on 2017-12-19.
 */

public class TaskListResult implements Serializable {

    private int pageNo;
    private int pageSize;
    private boolean countTotal;
    private boolean firstPage;
    private boolean lastPage;
    private int nextPage;
    private int offset;
    private String orderBy;
    private boolean orderBySetted;
    private String orderDir;
    private int prePage;
    private List<Sort> sort;
    private int totalItems;
    private int totalPages;
    private List<TaskDetailInfoModel> result;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isCountTotal() {
        return countTotal;
    }

    public void setCountTotal(boolean countTotal) {
        this.countTotal = countTotal;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isOrderBySetted() {
        return orderBySetted;
    }

    public void setOrderBySetted(boolean orderBySetted) {
        this.orderBySetted = orderBySetted;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TaskDetailInfoModel> getResult() {
        return result;
    }

    public void setResult(List<TaskDetailInfoModel> result) {
        this.result = result;
    }
}
