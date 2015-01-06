package com.password.manager.core.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Clemens on 19.12.2014.
 */
public class Query {

    // sample: ORDER_BY username ASCENDING AND SEARCH_FOR <some_string>
    //         ORDER_BY program DESCENDING AND CONTAINS <some_string>
    //         FIND_EXPLICIT <some_string> OR CONTAINS <some_other_string> AND ORDER_BY password
    private String query;

    public Query(String query) {
        this.query = query;
    }

    public static Query build(String... commands) {
        Query query = new Query("");
        StringBuilder stringBuilder = new StringBuilder();
        for (String command : commands) {
            stringBuilder.append(command);
            stringBuilder.append(" ");
        }
        query.setQuery(stringBuilder.toString());
        return query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public <T> List<T> run(List<T> list) throws Exception {
        List<String> params = new LinkedList<String>();
        List<T> tempList = new LinkedList<>(list);
        params.addAll(Arrays.asList(query.split(" " + QueryConnector.AND.toString() + " ")));

        for (String param : params) {
            String[] subParams = param.split(" ");
            if (subParams[0].equals(QueryCommand.CONTAINS.toString())) {
                tempList = contains(tempList, subParams);
            } else if (subParams[0].equals(QueryCommand.ORDER_BY.toString())) {
                tempList = order_by(tempList, subParams);
            }
        }

        return tempList;
    }

    private <T> List<T> contains(List<T> list, String[] subParams) throws Exception {
        List<T> ret = new ArrayList<T>();
        for (T t : list) {
            Field[] fields = t.getClass().getDeclaredFields();
            boolean add = false;
            for (Field f : fields) {
                Object o = f.get(t);
                add |= o.toString().contains(subParams[1]) & !f.toGenericString().contains("static");
            }
            if (add) {
                ret.add(t);
            }
        }
        return ret;
    }

    private <T> List<T> order_by(List<T> list, String[] subParams) throws Exception {
        if(subParams.length == 1) return list;
        if (subParams[2].equals(SortOrder.ASCENDING.toString())) {
            return quick_sort(list, subParams[1], SortOrder.ASCENDING);
        } else if (subParams[2].equals(SortOrder.DESCENDING.toString())) {
            return quick_sort(list, subParams[1], SortOrder.DESCENDING);
        } else {
            return quick_sort(list, subParams[1], SortOrder.DESCENDING);
        }
    }

    private <T> List<T> quick_sort(List<T> list, String field, SortOrder sortOrder) throws Exception {
        Object[] array = list.toArray();

        if (sortOrder.ordinal() == SortOrder.ASCENDING.ordinal())
            QuickSortAscending.quicksort(array, field);
        else QuickSortDescending.quicksort(array, field);

        list.clear();

        for (Object o : array) {
            list.add((T) o);
        }

        return list;
    }

}
