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

    public List<String> getRfqFilter() {
        if(rfqFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(rfqFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getQuoteIdFilter() {
        if(quoteIdFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quoteIdFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getLastUpdatedFilter() {
        if(lastUpdatedFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(lastUpdatedFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getSenderFilter() {
        if(senderFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(senderFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getSubjectFilter() {
        if(subjectFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(subjectFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getProductFilter() {
        if(productFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(productFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getPercentageFilter() {
        if(percentageFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(percentageFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getQuantityFilter() {
        if(quantityFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quantityFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getQuoteStatusFilter() {
        if(quoteStatusFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(quoteStatusFilter.replaceAll(" ", "").split(","));
    }

    public List<String> getMarketFilter() {
        if(marketFilter.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.asList(marketFilter.replaceAll(" ", "").split(","));
    }

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public String getItemPerPage() {
        return itemPerPage;
    }
}
