package com.password.manager.core.query;

import java.util.List;

/**
 * Created by Gerstung on 22.12.2014.
 */
public class Quick {
    public static <T> void sort(List<Comparable> fieldValueList, List<T> objectList, SortOrder sortOrder) {
        if (fieldValueList.size() != objectList.size()) {
            throw new IllegalArgumentException("The tow lists must have the same size!");
        }

        sort(fieldValueList, objectList, sortOrder, fieldValueList.size() / 2, 0, fieldValueList.size() - 1);
    }

    private static <T> void sort(List<Comparable> fieldValueList, List<T> objectList, SortOrder sortOrder, int pivotPos, int l, int r) {
        Comparable left = fieldValueList.get(l);
        Comparable right = fieldValueList.get(r);
        Comparable pivot = fieldValueList.get(pivotPos);
        if(sortOrder.equals(SortOrder.ASCENDING)){
            orderAscending(fieldValueList, objectList, l, r, left, right, pivot);
        }
        else if(sortOrder.equals(SortOrder.DESCENDING)){
            orderDescending(fieldValueList, objectList, l, r, left, right, pivot);
        }

    }

    private static <T> void orderAscending(List<Comparable> fieldValueList, List<T> objectList, int l, int r, Comparable left, Comparable right, Comparable pivot) {
        int i = l, j = r;
        while (i <= j) {
            while (left.compareTo(pivot) == 1) {
                i++;
            }

            while (right.compareTo(pivot) == -1) {
                j--;
            }

            if (i <= j) {
                Comparable temp = fieldValueList.get(i);
                fieldValueList.set(i, fieldValueList.get(j));
                fieldValueList.set(j, temp);

                T tmp = objectList.get(i);
                objectList.set(i, objectList.get(j));
                objectList.set(j, tmp);

                i++;
                j--;
            }
        }

        // TODO: set as parameter new left, right and pivot
        if (l < j)
            orderAscending(fieldValueList, objectList, l, j, left, right, pivot);
        if (r > i)
            orderAscending(fieldValueList, objectList, i, r, left, right, pivot);
    }

    private static <T> void orderDescending(List<Comparable> fieldValueList, List<T> objectList, int l, int r, Comparable left, Comparable right, Comparable pivot) {
        int i = l, j = r;
        while (i <= j) {
            while (left.compareTo(pivot) == -1) {
                i++;
            }

            while (right.compareTo(pivot) == 1) {
                j--;
            }

            if (i <= j) {
                Comparable temp = fieldValueList.get(i);
                fieldValueList.set(i, fieldValueList.get(j));
                fieldValueList.set(j, temp);

                T tmp = objectList.get(i);
                objectList.set(i, objectList.get(j));
                objectList.set(j, tmp);

                i++;
                j--;
            }
        }

        // TODO: set as parameter new left, right and pivot
        if (l < j)
            orderDescending(fieldValueList, objectList, l, j, left, right, pivot);
        if (r > i)
            orderDescending(fieldValueList, objectList, i, r, left, right, pivot);
    }
}
