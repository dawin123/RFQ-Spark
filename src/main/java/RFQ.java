import java.util.Date;

public class RFQ {
    private String rfq;
    private String quoteId;
    private String lastUpdated;
    private String sender;
    private String subject;
    private String product;
    private String percentage;
    private String quantity;
    private String quoteStatus;
    private String market;

    public RFQ(String rfq, String quoteId, String lastUpdated, String sender, String subject,
               String product, String percentage, String quantity, String quoteStatus, String market){
        this.rfq = rfq;
        this.quoteId = quoteId;
        this.lastUpdated = lastUpdated;
        this.sender = sender;
        this.subject = subject;
        this.product = product;
        this.percentage = percentage;
        this.quantity = quantity;
        this.quoteStatus = quoteStatus;
        this.market = market;
    }

    public String getRfq(){
        return this.rfq;
    }

    public String getQuoteId(){
        return this.quoteId;
    }

    public String getLastUpdated(){
        return this.lastUpdated;
    }

    public String getSender(){
        return this.sender;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getProduct(){
        return this.product;
    }

    public String getPercentage(){
        return this.percentage;
    }

    public String getQuantity(){
        return this.quantity;
    }

    public String getQuoteStatus(){
        return this.quoteStatus;
    }

    public String getMarket(){
        return this.market;
    }

}
