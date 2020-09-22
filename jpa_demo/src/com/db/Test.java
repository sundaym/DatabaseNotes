package com.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        // 1.创建EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        // 2.创建EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4.进行持久化
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setEmail("xxx@gmail.com");
        customer.setLastName("Tom");
        entityManager.persist(customer);
        // 5.提交事务
        transaction.commit();
        // 6.关闭EntityManager
        entityManager.clear();
        // 7.关闭EntityManagerFactory
        entityManagerFactory.close();
    }
}
