package utils;

public class Pagination {
    private int itemsCount;
    public int pageSize = 10;
    private int currentPage = 1;
    private int pagesCount;
    private int pageStartIndex = 1;
    private int pagesGroupSize = 28;

    public int getMinPage(int currentPage) {
	int minPage = currentPage - 5;
	if (minPage < 1) {
	    minPage = 1;
	}
	return minPage;
    }

    public int getMaxPage(int currentPage) {
	int maxPage = currentPage + 5;
	if (maxPage < pagesCount) {
	    maxPage = currentPage + 5;
	} else {
	    maxPage = pagesCount;
	}
	return maxPage;
    }

    public int getPagesGroupSize() {
	return pagesGroupSize;
    }

    public void setPagesGroupSize(int pagesGroupSize) {
	this.pagesGroupSize = pagesGroupSize;
    }

    public Pagination() {
    }

    public Pagination(int pageSize) {
	this.pageSize = pageSize;
    }

    public Pagination(String pageNoFromSession, int itemsCount, int pageSize) {
	if (pageSize > 0)
	    this.pageSize = pageSize;
	this.setItemsCount(itemsCount);
	setPagesCount();
	if (pageNoFromSession == null || pageNoFromSession.length() == 0) {
	    this.currentPage = 1;
	} else {
	    try {
		this.currentPage = Integer.parseInt(pageNoFromSession);
		if (this.currentPage > this.pagesCount)
		    this.currentPage = this.pagesCount;
	    } catch (Exception ex) {
		this.currentPage = 1;
	    }
	}
    }

    @Override
    public String toString() {
	return "Pagination [itemsCount=" + itemsCount + ", pageSize=" + pageSize + ", currentPage=" + currentPage + ", pagesCount=" + pagesCount + "]";
    }

    public int getItemsCount() {
	return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
	this.itemsCount = itemsCount;
    }

    public int getPageSize() {
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public void setPagesCount() {
	if (itemsCount % pageSize == 0)
	    this.pagesCount = itemsCount / pageSize;
	else
	    this.pagesCount = itemsCount / pageSize + 1;
    }

    public int getPagesCount() {
	return this.pagesCount;
    }

    public boolean hasPrevious() {
	return (currentPage > 1);
    }

    public boolean hasNext() {
	return (currentPage < getPagesCount());
    }

    public boolean isCurrentPage(int page) {
	return (this.currentPage == page);
    }

    public int getPageStartIndex() {
	return (this.currentPage - 1) * this.pageSize;
    }

}
