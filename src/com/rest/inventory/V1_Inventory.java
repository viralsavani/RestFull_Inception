package com.rest.inventory;

import com.rest.Dao;
import com.rest.com.util.ToJSON;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by VIRAL on 1/13/2015.
 */

@Path("/v1/inventory/*")
public class V1_Inventory {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnAllPcParts() throws SQLException {

        Response httpResponse = null;
        PreparedStatement query;
        String myString = null;
        String returnString = null;
        Connection con = null;

        try {
            con = Dao.ConnectionUtil.getCon();
            query = con.prepareStatement("SELECT * FROM pc_parts");
            ResultSet resultSet = query.executeQuery();

            ToJSON convertor = new ToJSON();
            JSONArray jsonArray = new JSONArray();

            jsonArray = convertor.toJSONArray(resultSet);
            query.close();

            returnString = jsonArray.toString();
            httpResponse = Response.ok(returnString).build();


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }

        return httpResponse;

    }
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
