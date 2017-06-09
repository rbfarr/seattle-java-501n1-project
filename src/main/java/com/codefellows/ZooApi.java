package com.codefellows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

public class ZooApi {
    private static final Logger LOG = LoggerFactory.getLogger(ZooApi.class);

    private static int getPort() {
        return Integer.parseInt(System.getenv().get("PORT"));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int port = getPort();

        LOG.info("Starting API server on port " + port);
        port(port);

        get("/hello/:firstName/:lastName", (request, response) -> {
            return "Hello: " + request.params(":firstName") + " " + request.params(":lastName");
        });

        Connection con = ZooUtils.getConnection();
        ZooUtils.viewTable(con);

        get("/age/:name", (request, response) -> {
            return ZooUtils.getPersonAge(con, request.params(":name"));
        });

        put("/age/:name/:newAge", (request, response) -> {
            ZooUtils.setPersonAge(con, request.params(":name"),
                    Integer.parseInt(request.params(":newAge")));
            ZooUtils.viewTable(con);
            return "update age";
        });

        //con.close();
    }
}