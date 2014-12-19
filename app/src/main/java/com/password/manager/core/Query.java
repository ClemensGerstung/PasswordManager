package com.password.manager.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Clemens on 19.12.2014.
 */
public class Query {

    // sample: ORDER_BY NAME ASCENDING AND SEARCH_FOR <some_string>
    //         ORDER_BY PROGRAM DESCENDING AND CONTAINS <some_string>
    //         SEARCH_FOR <some_string> OR CONTAINS <some_other_string> AND ORDER_BY PASSWORD
    private String query;

    public Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public <T> List<T> run(List<? extends T> list){
        List<String> params = new LinkedList<String>();
        params.addAll(Arrays.asList(query.split(" AND ")));

        for(String param : params){
            String[] p = param.split(" OR ");
            if(p.length > 1){
                params.remove(param);
                params.addAll(Arrays.asList(p));
            }
        }

        for(String param : params){

        }

        return null;
    }
}
