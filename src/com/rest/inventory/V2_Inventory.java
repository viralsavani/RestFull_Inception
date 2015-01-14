package com.rest.inventory;

import com.rest.Dao.Schema;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by VIRAL on 1/14/2015.
 * Use of @QueryParam
 * It will look of anything after "?"
 */
@Path("/v2/inventory/*")
public class V2_Inventory {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception{

        String returnString = null;
        JSONArray jsonArray = new JSONArray();

        try{
            if(brand == null || brand.equals("")){
                return Response.status(400).entity("Please enter a brand name as QueryParameter").build();
            }

            Schema newSchema = new Schema();
            jsonArray = newSchema.queryToReturnBrandParts(brand);
            returnString = jsonArray.toString();

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to fulfill your request").build();
        }

        return Response.ok(returnString).build();
    }

    @Path("/{brand}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBrand(@PathParam("brand") String brand) throws Exception{
        String returnString;
        JSONArray jsonArray;

        try{
            Schema newSchema = new Schema();
            jsonArray = newSchema.queryToReturnBrandParts(brand);
            returnString = jsonArray.toString();

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to fulfill your request").build();
        }

        return Response.ok(returnString).build();
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
