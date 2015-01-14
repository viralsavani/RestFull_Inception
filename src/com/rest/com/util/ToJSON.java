package com.rest.com.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * Created by VIRAL on 1/13/2015.
 */

/*
 * Takes data cursor from database query and converts into JSON format for Rest APIs
 */
public class ToJSON {

    public JSONArray toJSONArray (ResultSet rs){

        JSONArray jsonArray = new JSONArray();
        String temp = null;

        try{
            java.sql.ResultSetMetaData setMetaData = rs.getMetaData();

            while (rs.next()){
                int numCols = setMetaData.getColumnCount();
                JSONObject  jsonObject = new JSONObject();

                for(int i = 1; i < numCols+1; i++){
                    String column_name = setMetaData.getColumnName(i);

                    if(setMetaData.getColumnType(i) == Types.ARRAY){
                        jsonObject.put(column_name, rs.getArray(column_name));
                        System.out.println("ToJSON : ARRAY");
                    }
                    else if(setMetaData.getColumnType(i) == Types.BIGINT){
                        jsonObject.put(column_name, rs.getInt(column_name));
                        System.out.println("ToJSON : BIGINT");
                    }
                    else if(setMetaData.getColumnType(i) == Types.BOOLEAN){
                        jsonObject.put(column_name, rs.getBoolean(column_name));
                        System.out.println("ToJSON : BOOLEAN");
                    }
                    else if(setMetaData.getColumnType(i) == Types.BLOB){
                        jsonObject.put(column_name, rs.getBlob(column_name));
                        System.out.println("ToJSON : BLOB");
                    }
                    else if(setMetaData.getColumnType(i) == Types.DOUBLE){
                        jsonObject.put(column_name, rs.getDouble(column_name));
                        System.out.println("ToJSON : DOUBLE");
                    }
                    else if(setMetaData.getColumnType(i) == Types.FLOAT){
                        jsonObject.put(column_name, rs.getFloat(column_name));
                        System.out.println("ToJSON : FLOAT");
                    }
                    else if(setMetaData.getColumnType(i) == Types.INTEGER){
                        jsonObject.put(column_name, rs.getInt(column_name));
                        System.out.println("ToJSON : INTEGER");
                    }
                    else if(setMetaData.getColumnType(i) == Types.VARCHAR){

                        temp = rs.getString(column_name);

                        // Convert every form to its base version "De-encoded" to its base state
                        temp = ESAPI.encoder().canonicalize(temp);
                        temp = ESAPI.encoder().encodeForHTML(temp);
                        jsonObject.put(column_name, temp);

//                        jsonObject.put(column_name, rs.getString(column_name));
//                        System.out.println("ToJSON : VARCHAR");
                    }
                    else if(setMetaData.getColumnType(i) == Types.TINYINT){
                        jsonObject.put(column_name, rs.getInt(column_name));
                        System.out.println("ToJSON : TINYINT");
                    }
                    else if(setMetaData.getColumnType(i) == Types.SMALLINT){
                        jsonObject.put(column_name, rs.getInt(column_name));
                        System.out.println("ToJSON : SMALLINT");
                    }
                    else if(setMetaData.getColumnType(i) == Types.DATE){
                        jsonObject.put(column_name, rs.getDate(column_name));
                        System.out.println("ToJSON : DATE");
                    }
                    else if(setMetaData.getColumnType(i) == Types.TIMESTAMP){
                        jsonObject.put(column_name, rs.getTimestamp(column_name));
                        System.out.println("ToJSON : TIMESTAMP");
                    }
                    else if(setMetaData.getColumnType(i) == Types.NUMERIC){
                        jsonObject.put(column_name, rs.getBigDecimal(column_name));
                        System.out.println("ToJSON : NUMERIC");
                    }
                    else{
                        jsonObject.put(column_name, rs.getObject(column_name));
                        System.out.println("ToJSON: OBJECT"+column_name);

                    }
                }
                jsonArray.put(jsonObject);
            }

        }catch (Exception e){
            System.out.println("Error in ToJSONArray"+e);
        }

        return jsonArray;
    }
}
