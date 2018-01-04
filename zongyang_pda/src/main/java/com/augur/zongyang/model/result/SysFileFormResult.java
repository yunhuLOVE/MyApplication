package com.augur.zongyang.model.result;

import com.augur.zongyang.model.Sort;
import com.augur.zongyang.model.SysFileForm;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yunhu on 2018-01-03.
 */

public class SysFileFormResult implements Serializable {

    private static final long serialVersionUID = 4218822930480985093L;
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
    private List<SysFileForm> result;

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

    public boolean getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(boolean countTotal) {
        this.countTotal = countTotal;
    }

    public List<SysFileForm> getResult() {
        return result;
    }

    public void setResult(List<SysFileForm> result) {
        this.result = result;
    }

    public boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean getLastPage() {
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

    public boolean getOrderBySetted() {
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



}
