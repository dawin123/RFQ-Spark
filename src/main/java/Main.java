import static spark.Spark.*;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static String TO_BE_REVIEWED = "TO_BE_REVIEWED";
    public static String IN_REVIEW = "IN_REVIEW";
    public static String REVIEWED = "REVIEWED";
    public static String LEAST_RECENT = "LEAST_RECENT";

    public static void main(String[] args) {
        List<RFQ> rfqList = generateRFQList();
        List<RFQ> quoteList = new ArrayList<>();
        List<RFQ> exceptionList = generateExceptionList();

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
            List<RFQ> selectedList = getList(rfqRequest, rfqList, quoteList, exceptionList);
            Map responseMap = new HashMap();

            //filter List
            List<RFQ> filteredList = filterList(rfqRequest, selectedList);

            //sort list
            List<RFQ> sortedList = sortList(rfqRequest, filteredList);

            //divide by pagination
            List<RFQ> dividedList = divideList(rfqRequest, sortedList);

            responseMap.put("rfqList", dividedList);
            responseMap.put("totalPageNo", countTotalPageNo(rfqRequest, filteredList));
            responseMap.put("totalRFQCount", rfqList.size());
            responseMap.put("totalQuoteCount", quoteList.size());
            responseMap.put("totalExceptionCount", exceptionList.size());

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

    static List<RFQ> getList(RFQRequest rfqRequest, List<RFQ> rfqList, List<RFQ> quoteList, List<RFQ> exceptionList){
        int selectedTab = Integer.parseInt(rfqRequest.getSelectedTab());

        switch (selectedTab){
            case 0:
                return rfqList;
            case 2:
                return exceptionList;
            default:
                return quoteList;
        }
    }

    static List<RFQ> sortList(RFQRequest rfqRequest, List<RFQ> rfqList) {
        List<RFQ> result = rfqList;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        Collections.sort(result, new Comparator<RFQ>() {
            public int compare(RFQ o1, RFQ o2) {
                Date d1 = new Date();
                Date d2 = new Date();

                try {
                    d1 = sdformat.parse(o1.getLastUpdated());
                    d2 = sdformat.parse(o2.getLastUpdated());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(rfqRequest.getDateSort().equals(LEAST_RECENT)){
                    return d1.compareTo(d2);
                } else {
                    return d2.compareTo(d1);
                }
            }
        });

        return result;
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
            result = result.stream().filter(productFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getPercentageFilter().size() > 0){
            result = result.stream().filter(percentageFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getQuantityFilter().size() > 0){
            result = result.stream().filter(quantityFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getQuoteStatusFilter().size() > 0){
            result = result.stream().filter(quoteStatusFilter).collect(Collectors.toList());;
        }
        if(rfqRequest.getMarketFilter().size() > 0){
            result = result.stream().filter(marketFilter).collect(Collectors.toList());;
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
                "RFQ 100", "ABC", "0.50", "100", IN_REVIEW, "SG");
        RFQ r2 = new RFQ("101", "201", "2020-04-10", "jane.doe@gmail.com",
                "RFQ 101", "PQR", "0.10", "10", IN_REVIEW, "SG");
        RFQ r3 = new RFQ("102", "202", "2020-04-12", "kay.doe@gmail.com",
                "RFQ 102", "VWX", "0.03", "200", IN_REVIEW, "US");
        RFQ r4 = new RFQ("103", "232", "2020-04-10", "kay.doe@gmail.com",
                "RFQ 103", "XYZ", "0.30", "150", IN_REVIEW, "SG");
        RFQ r5 = new RFQ("104", "204", "2020-04-08", "john.doe@gmail.com",
                "RFQ 104", "KLM", "0.07", "200", REVIEWED, "EU");
        RFQ r6 = new RFQ("105", "205", "2020-04-07", "jake.doe@gmail.com",
                "RFQ 105", "DEF", "0.08", "42", TO_BE_REVIEWED, "SG");
        RFQ r7 = new RFQ("106", "206", "2020-04-12", "jill.doe@gmail.com",
                "RFQ 106", "GHI", "0.10", "80", TO_BE_REVIEWED, "EU");
        RFQ r8 = new RFQ("107", "207", "2020-04-06", "john.doe@gmail.com",
                "RFQ 107", "ABC", "1.00", "70", REVIEWED, "SG");
        RFQ r9 = new RFQ("108", "208", "2020-04-05", "jill.doe@gmail.com",
                "RFQ 108", "DEF", "0.70", "85", TO_BE_REVIEWED, "SG");
        RFQ r10 = new RFQ("109", "209", "2020-04-01", "julia.doe@gmail.com",
                "RFQ 109", "EFG", "0.02", "55", REVIEWED, "AU");
        RFQ r11 = new RFQ("110", "210", "2020-04-16", "jake.doe@gmail.com",
                "RFQ 110", "TUV", "0.12", "68", REVIEWED, "AU");
        RFQ r12 = new RFQ("111", "211", "2020-04-16", "james.doe@gmail.com",
                "RFQ 111", "OPQ", "0.25", "45", TO_BE_REVIEWED, "SG");
        RFQ r13 = new RFQ("112", "212", "2020-04-13", "julia.doe@gmail.com",
                "RFQ 112", "JKL", "0.05", "1000", IN_REVIEW, "US");
        RFQ r14 = new RFQ("113", "213", "2020-04-15", "james.doe@gmail.com",
                "RFQ 113", "Product CV", "0.87", "850", REVIEWED, "AU");
        RFQ r15 = new RFQ("114", "214", "2020-04-11", "julia.doe@gmail.com",
                "RFQ 114", "Product EM", "0.35", "125", IN_REVIEW, "EU");

        List<RFQ> rfqList = new ArrayList<RFQ>(
                Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15));

        return rfqList;
    }

    static List<RFQ> generateExceptionList(){
        RFQ r1 = new RFQ("210", "300", "2020-04-12", "john.doe@gmail.com",
                "RFQ 210", "MNO", "0.50", "100", IN_REVIEW, "SG");
        RFQ r2 = new RFQ("211", "301", "2020-04-11", "jane.doe@gmail.com",
                "RFQ 211", "RST", "0.10", "10", REVIEWED, "SG");
        RFQ r3 = new RFQ("212", "302", "2020-04-12", "kay.doe@gmail.com",
                "RFQ 212", "UVW", "0.03", "200", TO_BE_REVIEWED, "US");

        List<RFQ> rfqList = new ArrayList<RFQ>(
                Arrays.asList(r1, r2, r3));

        return rfqList;
    }
}
