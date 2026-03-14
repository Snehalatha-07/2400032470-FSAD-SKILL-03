package com.retail;

public class Main {
    public static void main(String[] args) {

        HQLOperations hql = new HQLOperations();

        System.out.println("INSERTING PRODUCTS ");
        hql.insertProducts();

        System.out.println("\n  SORTING BY PRICE ");
        hql.sortByPriceAscending();
        hql.sortByPriceDescending();

        System.out.println("\n SORT BY QUANTITY ");
        hql.sortByQuantityHighestFirst();

        System.out.println("\n  PAGINATION ");
        hql.paginationFirst3();
        hql.paginationNext3();

        System.out.println("\n  AGGREGATE FUNCTIONS ");
        hql.countTotalProducts();
        hql.countProductsWithQuantityGreaterThanZero();
        hql.countGroupByDescription();
        hql.minMaxPrice();

        System.out.println("\n GROUP BY ");
        hql.groupByDescription();

        System.out.println("\n  WHERE FILTER ");
        hql.filterByPriceRange();

        System.out.println("\n LIKE QUERIES ");
        hql.likeStartingWith();
        hql.likeEndingWith();
        hql.likeContaining();
        hql.likeExactLength();

        System.out.println("\n ALL HQL OPERATIONS COMPLETED ");

        HibernateUtil.shutdown();
    }
}
