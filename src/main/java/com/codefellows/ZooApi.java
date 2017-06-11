package com.codefellows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

import static spark.Spark.get;
import static spark.Spark.port;

public class ZooApi {
    private static final Logger LOG = LoggerFactory.getLogger(ZooApi.class);
    private static final Connection con = ZooUtils.getConnection();

    private static int getPort() {
        return Integer.parseInt(System.getenv().get("PORT"));
    }

    public static void main(String[] args) {
        int port = getPort();

        LOG.info("Starting API server on port " + port);
        port(port);

        setRoutes();
    }

    private static void setRoutes() {
        get("/hello/:firstName/:lastName", (request, response) ->
                "Hello: " + request.params(":firstName") + " " + request.params(":lastName")
        );

        get("/age/:name", (request, response) ->
                ZooUtils.getPersonAge(con, request.params(":name"))
        );

        get("/age/:name/:newAge", (request, response) -> {
            ZooUtils.setPersonAge(con, request.params(":name"),
                    Integer.parseInt(request.params(":newAge")));

            return ZooUtils.viewTable(con);
        });
    }
}