import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private String dateSort;

    public RFQRequest(String selectedTab, String rfqFilter, String quoteIdFilter, String lastUpdatedFilter,
                      String senderFilter, String subjectFilter, String productFilter, String percentageFilter,
                      String quantityFilter, String quoteStatusFilter, String marketFilter, String currentPageNo,
                      String itemPerPage, String dateSort){
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
        this.dateSort = dateSort;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public List<String> getRfqFilter() {
        if(rfqFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(rfqFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getQuoteIdFilter() {
        if(quoteIdFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quoteIdFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getLastUpdatedFilter() {
        if(lastUpdatedFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(lastUpdatedFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getSenderFilter() {
        if(senderFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(senderFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getSubjectFilter() {
        if(subjectFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(subjectFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getProductFilter() {
        if(productFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(productFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getPercentageFilter() {
        if(percentageFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(percentageFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getQuantityFilter() {
        if(quantityFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quantityFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getQuoteStatusFilter() {
        if(quoteStatusFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quoteStatusFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public List<String> getMarketFilter() {
        if(marketFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(marketFilter.trim().replaceAll(", ", ",").replaceAll(" ,", ",").split(","));
    }

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public String getItemPerPage() {
        return itemPerPage;
    }

    public String getDateSort() {
        return dateSort;
    }
}
