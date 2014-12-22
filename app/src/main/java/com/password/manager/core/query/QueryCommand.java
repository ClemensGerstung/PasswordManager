package com.password.manager.core.query;

/**
 * Created by Gerstung on 22.12.2014.
 */
public enum QueryCommand {
    ORDER_BY,
    CONTAINS,   // almost the same as find or search...
                // implements starts_with, ends_with and find_explicit
    STARTS_WITH,
    ENDS_WITH,
    FIND_EXPLICIT

}
