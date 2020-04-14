import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/hello", "application/json", (request, response) -> {
            return new MyMessage("Hello World");
        }, new JsonTransformer());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
