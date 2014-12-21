package com.password.manager.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Clemens on 19.12.2014.
 */
public class Query {

    // TODO: query commands into own enum

    // sample: ORDER_BY username ASCENDING AND SEARCH_FOR <some_string>
    //         ORDER_BY program DESCENDING AND CONTAINS <some_string>
    //         SEARCH_FOR <some_string> OR CONTAINS <some_other_string> AND ORDER_BY password
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

    public <T> List<T> run(List<? extends T> list) throws Exception {
        List<String> params = new LinkedList<String>();
        params.addAll(Arrays.asList(query.split(" AND ")));

        for (String param : params) {
            String[] subParams = param.split(" ");
            if (subParams[0].equals("ORDER_BY")) {
                for (T t : list) {
                    if (subParams[2].equals("ASCENDING")) {
                        return quick_sort(list, subParams[1], true);
                    } else if (subParams[2].equals("DESCENDING")) {
                        return quick_sort(list, subParams[1], false);
                    } else {
                        return quick_sort(list, subParams[1], true);
                    }

                }
            }
        }


        return null;
    }

    private <T, E> List<T> quick_sort(List<? extends T> list, String field, boolean ascending) throws Exception {
        List<E> n_list = toFieldList(list, field);



        return null;
    }

    private <T, E> List<T> toFieldList(List<E> list, String field) throws Exception{
        List<T> ret = new LinkedList<T>();

        for (E e : list){
            ret.add((T)e.getClass().getDeclaredField(field).get(e));
        }

        return ret;
    }
}
