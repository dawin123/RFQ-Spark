import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static String IN_PROCESS = "IN_PROCESS";

    public static void main(String[] args) {
        RFQ r1 = new RFQ("100", "200", "2020-04-14", "john.doe@gmail.com",
                "RFQ 100", "Product 1", "0.5", "100", IN_PROCESS, "SG");
        RFQ r2 = new RFQ("101", "201", "2020-04-10", "jane.doe@gmail.com",
                "RFQ 101", "Product 2", "0.1", "10", IN_PROCESS, "SG");
        RFQ r3 = new RFQ("102", "202", "2020-04-12", "kay.doe@gmail.com",
                "RFQ 102", "Product 3", "0.03", "200", IN_PROCESS, "SG");

        List<RFQ> rfqList = new ArrayList<RFQ>(
                Arrays.asList(r1, r2, r3));

        get("/hello", "application/json", (request, response) -> {
            return new MyMessage("Hello World");
        }, new JsonTransformer());

        post("/getRFQList", "application/json", (request, response) -> {
            System.out.println(request.body());
            RFQRequest rfqRequest = new Gson().fromJson(request.body(), RFQRequest.class);
            //process list here
            return filterList(rfqRequest, rfqList);
        }, new JsonTransformer());
    }

    static List<RFQ> filterList(RFQRequest rfqRequest, List<RFQ> rfqList){
        List<RFQ> result = rfqList;

        //filters
        Predicate<RFQ> rfqFilter = r -> r.getRfq().equals(rfqRequest.getRfqFilter());
        Predicate<RFQ> quoteIdFilter = r -> r.getQuoteId().equals(rfqRequest.getQuoteIdFilter());
        Predicate<RFQ> lastUpdatedFilter = r -> r.getLastUpdated().equals(rfqRequest.getLastUpdatedFilter());
        Predicate<RFQ> senderFilter = r -> r.getSender().equals(rfqRequest.getSenderFilter());
        Predicate<RFQ> subjectFilter = r -> r.getSubject().equals(rfqRequest.getSubjectFilter());
        Predicate<RFQ> productFilter = r -> r.getProduct().equals(rfqRequest.getProductFilter());
        Predicate<RFQ> percentageFilter = r -> r.getPercentage().equals(rfqRequest.getPercentageFilter());
        Predicate<RFQ> quantityFilter = r -> r.getQuantity().equals(rfqRequest.getQuantityFilter());
        Predicate<RFQ> quoteStatusFilter = r -> r.getQuoteStatus().equals(rfqRequest.getQuoteStatusFilter());
        Predicate<RFQ> marketFilter = r -> r.getMarket().equals(rfqRequest.getMarketFilter());

        System.out.println("rfqFilter: "+rfqRequest.getRfqFilter());
        if(!rfqRequest.getRfqFilter().isEmpty()){
            result = result.stream().filter(rfqFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getQuoteIdFilter().isEmpty()){
            result = result.stream().filter(quoteIdFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getLastUpdatedFilter().isEmpty()){
            result = result.stream().filter(lastUpdatedFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getSenderFilter().isEmpty()){
            result = result.stream().filter(senderFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getSubjectFilter().isEmpty()){
            result = result.stream().filter(subjectFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getProductFilter().isEmpty()){
            result = rfqList.stream().filter(productFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getPercentageFilter().isEmpty()){
            result = rfqList.stream().filter(percentageFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getQuantityFilter().isEmpty()){
            result = rfqList.stream().filter(quantityFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getQuoteStatusFilter().isEmpty()){
            result = rfqList.stream().filter(quoteStatusFilter).collect(Collectors.toList());;
        }
        if(!rfqRequest.getMarketFilter().isEmpty()){
            result = rfqList.stream().filter(marketFilter).collect(Collectors.toList());;
        }

        return result;
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
