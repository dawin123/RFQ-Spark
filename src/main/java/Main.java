import static spark.Spark.*;
import com.google.gson.Gson;
import spark.Filter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static String TO_BE_REVIEWED = "TO_BE_REVIEWED";
    public static String IN_REVIEW = "IN_REVIEW";
    public static String REVIEWED = "REVIEWED";

    public static void main(String[] args) {
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
        });

        get("/hello", "application/json", (request, response) -> {
            return new MyMessage("Hello World");
        }, new JsonTransformer());

        post("/getRFQList", "application/json", (request, response) -> {
            System.out.println(request.body());
            RFQRequest rfqRequest = new Gson().fromJson(request.body(), RFQRequest.class);

            // get list based on selected tab
            List<RFQ> rfqList = getList(rfqRequest);
            Map responseMap = new HashMap();

            //filter List
            List<RFQ> filteredList = filterList(rfqRequest, rfqList);

            //divide by pagination
            List<RFQ> dividedList = divideList(rfqRequest, filteredList);

            responseMap.put("rfqList", dividedList);
            responseMap.put("totalPageNo", countTotalPageNo(rfqRequest, filteredList));

            return responseMap;
        }, new JsonTransformer());
    }

    static int countTotalPageNo(RFQRequest rfqRequest, List<RFQ> filteredList){
        if(filteredList.size() < 1){
            return 1;
        }
        int a = filteredList.size();
        int b = Integer.parseInt(rfqRequest.getItemPerPage());
        int n = a / b + ((a % b == 0) ? 0 : 1);

        return n;
    }

    static List<RFQ> getList(RFQRequest rfqRequest){
        int selectedTab = Integer.parseInt(rfqRequest.getSelectedTab());

        switch (selectedTab){
            case 0:
                return generateRFQList();
            case 2:
                return generateQuoteList();
            default:
                return new ArrayList<>();
        }
    }

    static List<RFQ> divideList(RFQRequest rfqRequest, List<RFQ> rfqList){
        List<RFQ> result = new ArrayList<>();
        int currentPageNo = Integer.parseInt(rfqRequest.getCurrentPageNo());
        int itemPerPage = Integer.parseInt(rfqRequest.getItemPerPage());
        int startIndex = currentPageNo * itemPerPage - itemPerPage;
        int remainingElement = rfqList.size() - startIndex;
        int maxIndex = Math.min(startIndex + itemPerPage, startIndex+remainingElement);

        for(int i=startIndex;i<maxIndex;i++){
            result.add(rfqList.get(i));
        }
        return result;
    }

    static List<RFQ> filterList(RFQRequest rfqRequest, List<RFQ> rfqList){
        List<RFQ> result = rfqList;

        //filters
        Predicate<RFQ> rfqFilter = r -> rfqRequest.getRfqFilter().contains(r.getRfq());
        Predicate<RFQ> quoteIdFilter = r -> rfqRequest.getQuoteIdFilter().contains(r.getQuoteId());
        Predicate<RFQ> lastUpdatedFilter = r -> rfqRequest.getLastUpdatedFilter().contains(r.getLastUpdated());
        Predicate<RFQ> senderFilter = r -> rfqRequest.getSenderFilter().contains(r.getSender());
        Predicate<RFQ> subjectFilter = r -> rfqRequest.getSubjectFilter().contains(r.getSubject());
        Predicate<RFQ> productFilter = r -> rfqRequest.getProductFilter().contains(r.getProduct());
        Predicate<RFQ> percentageFilter = r -> rfqRequest.getPercentageFilter().contains(r.getPercentage());
        Predicate<RFQ> quantityFilter = r -> rfqRequest.getQuantityFilter().contains(r.getQuantity());
        Predicate<RFQ> quoteStatusFilter = r -> rfqRequest.getQuoteStatusFilter().contains(r.getQuoteStatus());
        Predicate<RFQ> marketFilter = r -> rfqRequest.getMarketFilter().contains(r.getMarket());

        if(rfqRequest.getRfqFilter().size() > 0){
            result = result.stream().filter(rfqFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getQuoteIdFilter().size() > 0){
            result = result.stream().filter(quoteIdFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getLastUpdatedFilter().size() > 0){
            result = result.stream().filter(lastUpdatedFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getSenderFilter().size() > 0){
            result = result.stream().filter(senderFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getSubjectFilter().size() > 0){
            result = result.stream().filter(subjectFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getProductFilter().size() > 0){
            result = rfqList.stream().filter(productFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getPercentageFilter().size() > 0){
            result = rfqList.stream().filter(percentageFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getQuantityFilter().size() > 0){
            result = rfqList.stream().filter(quantityFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getQuoteStatusFilter().size() > 0){
            result = rfqList.stream().filter(quoteStatusFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getMarketFilter().size() > 0){
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

    static List<RFQ> generateRFQList(){
        RFQ r1 = new RFQ("100", "200", "2020-04-14", "john.doe@gmail.com",
                "RFQ 100", "ABC", "0.5", "100", IN_REVIEW, "SG");
        RFQ r2 = new RFQ("101", "201", "2020-04-10", "jane.doe@gmail.com",
                "RFQ 101", "PQR", "0.1", "10", IN_REVIEW, "SG");
        RFQ r3 = new RFQ("102", "202", "2020-04-12", "kay.doe@gmail.com",
                "RFQ 102", "VWX", "0.03", "200", IN_REVIEW, "US");
        RFQ r4 = new RFQ("103", "232", "2020-04-10", "kay.doe@gmail.com",
                "RFQ 103", "XYZ", "0.3", "150", IN_REVIEW, "SG");
        RFQ r5 = new RFQ("104", "204", "2020-04-08", "john.doe@gmail.com",
                "RFQ 104", "KLM", "0.07", "200", REVIEWED, "EU");
        RFQ r6 = new RFQ("105", "205", "2020-04-07", "jake.doe@gmail.com",
                "RFQ 105", "DEF", "0.08", "42", TO_BE_REVIEWED, "SG");
        RFQ r7 = new RFQ("106", "206", "2020-04-12", "jill.doe@gmail.com",
                "RFQ 106", "GHI", "0.1", "80", TO_BE_REVIEWED, "EU");
        RFQ r8 = new RFQ("107", "207", "2020-04-06", "john.doe@gmail.com",
                "RFQ 107", "ABC", "1.0", "70", REVIEWED, "SG");
        RFQ r9 = new RFQ("108", "208", "2020-04-05", "jill.doe@gmail.com",
                "RFQ 108", "DEF", "0.7", "85", TO_BE_REVIEWED, "SG");
        RFQ r10 = new RFQ("109", "209", "2020-04-01", "julia.doe@gmail.com",
                "RFQ 109", "EFG", "0.02", "55", REVIEWED, "AU");

        List<RFQ> rfqList = new ArrayList<RFQ>(
                Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10));

        return rfqList;
    }

    static List<RFQ> generateQuoteList(){
        RFQ r1 = new RFQ("110", "200", "2020-04-12", "john.doe@gmail.com",
                "RFQ 110", "MNO", "0.5", "100", IN_REVIEW, "SG");
        RFQ r2 = new RFQ("111", "201", "2020-04-11", "jane.doe@gmail.com",
                "RFQ 111", "RST", "0.1", "10", REVIEWED, "SG");
        RFQ r3 = new RFQ("112", "202", "2020-04-12", "kay.doe@gmail.com",
                "RFQ 112", "UVW", "0.03", "200", TO_BE_REVIEWED, "US");

        List<RFQ> rfqList = new ArrayList<RFQ>(
                Arrays.asList(r1, r2, r3));

        return rfqList;
    }
}
