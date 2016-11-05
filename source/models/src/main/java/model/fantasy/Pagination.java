package model.fantasy;

public class Pagination {
    private int currentPage;
    private int pageSize;
    private int pages;
    private long totalElements;

    public Pagination() {
    }
    public Pagination(int currentPage, int pageSize, int pages, long totalElements) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pages = pages;
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

}
