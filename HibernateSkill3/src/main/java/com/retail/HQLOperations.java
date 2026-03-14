package com.retail;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class HQLOperations {

    public void insertProducts() {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(new Product("Laptop", "Electronics", 75000.00, 10));
            session.save(new Product("Mouse", "Electronics", 999.00, 50));
            session.save(new Product("Keyboard", "Electronics", 2500.00, 30));
            session.save(new Product("Monitor", "Electronics", 35000.00, 15));
            session.save(new Product("Headphones", "Electronics", 5000.00, 25));
            session.save(new Product("Pen", "Stationery", 10.00, 200));
            session.save(new Product("Notebook", "Stationery", 50.00, 150));
            session.save(new Product("Printer", "Electronics", 12000.00, 8));
            tx.commit();
            System.out.println("8 Products inserted successfully.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void sortByPriceAscending() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Sort by Price Ascending ---");
            Query<Product> query = session.createQuery(
                "FROM Product ORDER BY price ASC", Product.class);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void sortByPriceDescending() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Sort by Price Descending ---");
            Query<Product> query = session.createQuery(
                "FROM Product ORDER BY price DESC", Product.class);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void sortByQuantityHighestFirst() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Sort by Quantity Highest First ---");
            Query<Product> query = session.createQuery(
                "FROM Product ORDER BY quantity DESC", Product.class);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void paginationFirst3() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Pagination: First 3 Products ---");
            Query<Product> query = session.createQuery(
                "FROM Product", Product.class);
            query.setFirstResult(0);
            query.setMaxResults(3);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void paginationNext3() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Pagination: Next 3 Products ---");
            Query<Product> query = session.createQuery(
                "FROM Product", Product.class);
            query.setFirstResult(3);
            query.setMaxResults(3);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void countTotalProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Count Total Products ---");
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Product p", Long.class);
            Long count = query.uniqueResult();
            System.out.println("Total Products: " + count);
        }
    }

    public void countProductsWithQuantityGreaterThanZero() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Count Products where Quantity > 0 ---");
            Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0", Long.class);
            Long count = query.uniqueResult();
            System.out.println("Products with Quantity > 0: " + count);
        }
    }

    public void countGroupByDescription() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Count Products Grouped by Description ---");
            Query<Object[]> query = session.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class);
            List<Object[]> list = query.list();
            for (Object[] row : list)
                System.out.println("Description: " + row[0] + " | Count: " + row[1]);
        }
    }

    public void minMaxPrice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Min and Max Price ---");
            Query<Object[]> query = session.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p", Object[].class);
            Object[] result = query.uniqueResult();
            System.out.println("Minimum Price: " + result[0]);
            System.out.println("Maximum Price: " + result[1]);
        }
    }

    public void groupByDescription() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- GROUP BY Description ---");
            Query<Object[]> query = session.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class);
            List<Object[]> list = query.list();
            for (Object[] row : list)
                System.out.println("Category: " + row[0] + " | Total: " + row[1]);
        }
    }

    public void filterByPriceRange() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Filter Products WHERE price BETWEEN 1000 AND 40000 ---");
            Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice",
                Product.class);
            query.setParameter("minPrice", 1000.00);
            query.setParameter("maxPrice", 40000.00);
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void likeStartingWith() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- LIKE: Names Starting with 'M' ---");
            Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern", Product.class);
            query.setParameter("pattern", "M%");
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void likeEndingWith() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- LIKE: Names Ending with 'e' ---");
            Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern", Product.class);
            query.setParameter("pattern", "%e");
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void likeContaining() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- LIKE: Names Containing 'oo' ---");
            Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern", Product.class);
            query.setParameter("pattern", "%oo%");
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }

    public void likeExactLength() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- LIKE: Names with Exact 3 Characters ---");
            Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern", Product.class);
            query.setParameter("pattern", "___");
            List<Product> list = query.list();
            for (Product p : list)
                System.out.println(p);
        }
    }
}
