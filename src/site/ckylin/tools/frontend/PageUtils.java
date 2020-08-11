/**
 * @Package: site.ckylin.tools.frontend
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-06 11:47
 */
package site.ckylin.tools.frontend;

/**
 * 分页工具类
 */
public class PageUtils {
    private int currentPage = 1;
    private int virtualCurrentPage = 1;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;

    /**
     * 初始化分页工具类
     *
     * @param totalItems   总条目数
     * @param itemsPerPage 每页条目数
     */
    public PageUtils(int totalItems, int itemsPerPage) {
        setItemsPerPage(itemsPerPage, false);
        setTotalItems(totalItems);
    }

    /**
     * 获取上一页页码，若无上一页则返回null
     *
     * @return the prev page
     */
    public Integer getPrevPage() {
        if (currentPage == 1) return null;
        return currentPage - 1;
    }

    /**
     * 获取下一页页码，若无下一页则返回null
     *
     * @return the next page
     */
    public Integer getNextPage() {
        if (currentPage == totalPages) return null;
        return currentPage + 1;
    }

    /**
     * 获取指定条目所杂的页码
     *
     * @param index the index
     * @return the int
     */
    public int getContainedPageForItem(int index) {
        if (index <= 0) index = 1;
        int page = index / itemsPerPage;
        if (index % itemsPerPage != 0) page++;
        return page;
    }

    /**
     * 获取当前页起始条目
     *
     * @return the int
     */
    public int getStartIndex() {
        return getPageStartIndex(currentPage);
    }

    /**
     * 获取当前页<b>所包含的</b>最后一项条目
     *
     * @return the int
     */
    public int getEndIndex() {
        return currentPage * itemsPerPage;
    }

    /**
     * 获取虚拟当前页起始条目
     *
     * @return the int
     */
    public int getVirtualStartIndex() {
        return getPageStartIndex(virtualCurrentPage);
    }

    /**
     * 获取虚拟当前页<b>所包含的</b>最后一项条目
     *
     * @return the int
     */
    public int getVirtualEndIndex() {
        return virtualCurrentPage * itemsPerPage;
    }

    /**
     * 返回当前页是否是首页
     *
     * @return the boolean
     */
    public boolean isFirstPage() {
        return currentPage == 1;
    }

    /**
     * 返回当前页是否是尾页
     *
     * @return the boolean
     */
    public boolean isLastPage() {
        return currentPage == totalPages;
    }

    /**
     * 获取某页起始条目
     *
     * @param page the page
     * @return the int
     */
    public int getPageStartIndex(int page) {
        return (page - 1) * itemsPerPage + 1;
    }

    /**
     * 获取某页<b>所包含的</b>最后一项条目
     *
     * @param page the page
     * @return the int
     */
    public int getPageEndIndex(int page) {
        return page * itemsPerPage;
    }

    /**
     * 获取虚拟当前页
     *
     * @return the virtual current page
     */
    public int getVirtualCurrentPage() {
        return virtualCurrentPage;
    }

    /**
     * 设置虚拟当前页
     *
     * @param virtualCurrentPage the virtual current page
     */
    public void setVirtualCurrentPage(int virtualCurrentPage) {
        this.virtualCurrentPage = virtualCurrentPage;
    }

    /**
     * 使虚拟当前页生效
     */
    public void applyVirtualCurrentPage() {
        setCurrentPage(virtualCurrentPage);
    }

    /**
     * 获取当前页码
     *
     * @return the current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页码。页码将自动被归并到 0~最大页码 的范围内
     *
     * @param currentPage the current page
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage <= 0) currentPage = 1;
        else if (currentPage > totalPages) currentPage = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * 获取总条目数
     *
     * @return the total items
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * 设置总条目数。这将重新计算分页数据和当前页码。
     *
     * @param totalItems the total items
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        calculateTotalPages();
    }

    /**
     * 获取总页数
     *
     * @return the total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 设置每页条数。这将重新计算分页数据和当前页码。
     *
     * @param itemsPerPage the items per page
     */
    public void setItemsPerPage(int itemsPerPage) {
        setItemsPerPage(itemsPerPage, true);
    }

    /**
     * 设置每页条数。若<code>recalculate</code>为真，将重新计算分页数据和当前页码。
     *
     * @param itemsPerPage the items per page
     * @param recalculate  trigger recalculate or not
     */
    public void setItemsPerPage(int itemsPerPage, boolean recalculate) {
        if (itemsPerPage <= 0) itemsPerPage = 1;
        this.itemsPerPage = itemsPerPage;
        if (recalculate) calculateTotalPages();
    }

    /**
     * 获取每页条目数
     *
     * @return the items per page
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    private void calculateTotalPages() {
        if (totalItems == 0) {
            totalPages = 1;
        } else {
            totalPages = totalItems / itemsPerPage;
            if (totalPages == 0) totalPages = 1;
            else if (totalItems % itemsPerPage != 0) totalPages++;
        }
        if (currentPage > totalPages) currentPage = totalPages;
    }
}
