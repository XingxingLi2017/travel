package domain;

import java.util.List;

/**
 * object represents the content in one page of pagination bar
 * @param <T>
 */
public class PageBean<T> {
    private int totalCount;     // total count of T's records in database
    private int totalPage;      // total pages of T's records in database
    private int currentPage;    // current page number
    private int pageSize;       // how many entries of T need to be show in current page
    // list of records
    private List<T> list;       // entries needed to be show in current page

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getList() {
        return list;
    }
}
