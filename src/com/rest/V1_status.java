package com.rest;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.naming.NamingException;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Root Path
@Path("/V1_status/*")


/**
 * Created by VIRAL on 1/13/2015.
 */
public class V1_status {

    private static final String version_api = "0.0.1";

    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.TEXT_HTML)
    public String getClichedMessage() {
        // Return some cliched textual content
        return "TRY NEW";
    }

    @Path("/version")
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.TEXT_HTML)
    public String getMessage() {
        // Return some cliched textual content
        return "<h1>TRY NEW More</h1>"+version_api;
    }

    @Path("/databaseStatus")
    @GET
    @Produces("text/html")
    public String returnDatabaseStatus() throws SQLException {

        PreparedStatement query;
        String myString = null;
        String returnString = null;
        Connection con = null;

        try {
            con = Dao.ConnectionUtil.getCon();
            //query = con.prepareStatement("SELECT Name FROM temp WHERE idtemp = 1");
            query = con.prepareStatement("SELECT NOW()");
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()){
                System.out.println("THERE");
                myString = resultSet.getString(1);
            }
            query.close();

            returnString = "<h4>Database is Connected</h4> \n <h4>Current TimeStamp "+myString+"</h4>";

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }

        return "<h2>DATABASE STATUS</h2>" + returnString;

    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/V1_status");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}

