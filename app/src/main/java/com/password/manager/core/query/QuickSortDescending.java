package com.password.manager.core.query;

/**
 * Created by Gerstung on 23.12.2014.
 * For more information see com.password.manager.core.query.QuickSortAscending
 */
public class QuickSortDescending {
    /**
     * Sort the first size entries in a.
     */
    public static void quicksort(Object[] a, String field) throws Exception {
        quicksort(a, 0, a.length - 1, field);
    }

    /**
     * Sort the entries in a between left and right inclusive.
     */
    public static void quicksort(Object[] a, int left, int right, String field) throws Exception {
        int size = right - left + 1;
        switch (size) {
            case 0:
            case 1:
                break;
            case 2:
                if (compare(a[left], a[right], field) < 0) swap(a, left, right);
                break;
            case 3:
                if (compare(a[left], a[right - 1], field) < 0) swap(a, left, right - 1);
                if (compare(a[left], a[right], field) < 0) swap(a, left, right);
                if (compare(a[left + 1], a[right], field) < 0) swap(a, left + 1, right);
                break;
            default:
                int median = median(a, left, right, field);
                int partition = partition(a, left, right, median, field);
                quicksort(a, left, partition - 1, field);
                quicksort(a, partition + 1, right, field);
        }
    }

    private static int compare(Object a, Object b, String field) throws Exception {
        if (a == null) {
            return b == null ? 0 : -1;
        } else if (b == null) {
            return +1;
        } else {
            Object aF = a.getClass().getDeclaredField(field).get(a);
            Object bF = b.getClass().getDeclaredField(field).get(b);
            return ((Comparable) aF).compareTo(bF);
        }
    }

    private static void swap(Object[] a, int left, int right) {
        Object t = a[left];
        a[left] = a[right];
        a[right] = t;
    }

    private static int median(Object[] a, int left, int right, String field) throws Exception {
        int center = (left + right) / 2;
        if (compare(a[left], a[center], field) < 0) swap(a, left, center);
        if (compare(a[left], a[right], field) < 0) swap(a, left, right);
        if (compare(a[center], a[right], field) < 0) swap(a, center, right);
        swap(a, center, right - 1);
        return right - 1;
    }

    private static int partition(Object[] a, int left, int right, int pivotIndex, String field) throws Exception {
        int leftIndex = left;
        int rightIndex = right - 1;
        while (true) {
            while (compare(a[++leftIndex], a[pivotIndex], field) > 0);
            while (compare(a[--rightIndex], a[pivotIndex], field) < 0);
            if (leftIndex >= rightIndex) {
                break; // pointers cross so partition done
            } else {
                swap(a, leftIndex, rightIndex);
            }
        }
        swap(a, leftIndex, right - 1);         // restore pivot
        return leftIndex;                 // return pivot location
    }
}
