public class RFQRequest {
    private String selectedTab;
    private String rfqFilter;
    private String quoteIdFilter;
    private String lastUpdatedFilter;
    private String senderFilter;
    private String subjectFilter;
    private String productFilter;
    private String percentageFilter;
    private String quantityFilter;
    private String quoteStatusFilter;
    private String marketFilter;
    private String currentPageNo;
    private String itemPerPage;

    public RFQRequest(String selectedTab, String rfqFilter, String quoteIdFilter, String lastUpdatedFilter,
                      String senderFilter, String subjectFilter, String productFilter, String percentageFilter,
                      String quantityFilter, String quoteStatusFilter, String marketFilter, String currentPageNo,
                      String itemPerPage){
        this.selectedTab = selectedTab;
        this.rfqFilter = rfqFilter;
        this.quoteIdFilter = quoteIdFilter;
        this.lastUpdatedFilter = lastUpdatedFilter;
        this.senderFilter = senderFilter;
        this.subjectFilter = subjectFilter;
        this.productFilter = productFilter;
        this.percentageFilter = percentageFilter;
        this.quantityFilter = quantityFilter;
        this.quoteStatusFilter = quoteStatusFilter;
        this.marketFilter = marketFilter;
        this.currentPageNo = currentPageNo;
        this.itemPerPage = itemPerPage;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public String getRfqFilter() {
        return rfqFilter;
    }

    public String getQuoteIdFilter() {
        return quoteIdFilter;
    }

    public String getLastUpdatedFilter() {
        return lastUpdatedFilter;
    }

    public String getSenderFilter() {
        return senderFilter;
    }

    public String getSubjectFilter() {
        return subjectFilter;
    }

    public String getProductFilter() {
        return productFilter;
    }

    public String getPercentageFilter() {
        return percentageFilter;
    }

    public String getQuantityFilter() {
        return quantityFilter;
    }

    public String getQuoteStatusFilter() {
        return quoteStatusFilter;
    }

    public String getMarketFilter() {
        return marketFilter;
    }

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public String getItemPerPage() {
        return itemPerPage;
    }
}
