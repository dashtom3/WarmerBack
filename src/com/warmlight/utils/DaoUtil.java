package com.warmlight.utils;


public class DaoUtil {

    public static int getTotalPageNumber(int totalItemNum, int numberPerPage) {
        if (totalItemNum % numberPerPage == 0) {
            return (int) totalItemNum / numberPerPage;
        } else {
            return (int) totalItemNum / numberPerPage + 1;
        }
    }

}