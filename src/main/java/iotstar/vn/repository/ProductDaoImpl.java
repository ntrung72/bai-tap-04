package iotstar.vn.repository;

import java.util.List;
import iotstar.vn.config.JPAConfig;
import iotstar.vn.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ProductDaoImpl implements ProductDao{
    @Override
    public void insert(Product product){
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try{
            trans.begin();
            enma.persist(product);
            trans.commit();
        }catch(RuntimeException e){
            e.printStackTrace();
            if(trans.isActive()){
                trans.rollback();
            }
            throw e;
        }finally{
            enma.close();
        }
    }
    @Override
    public void edit(Product product){
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try{
            trans.begin();
            enma.merge(product);
            trans.commit();
        }catch(RuntimeException e){
            e.printStackTrace();
            if(trans.isActive()){
                trans.rollback();
            }
            throw e;
        }finally{
            enma.close();
        }
    }
    @Override
    public void delete(int id){
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try{
            trans.begin();
            Product product=enma.find(Product.class, id);
            if(product!=null){
                enma.remove(product);
            }
            trans.commit();
        }catch(RuntimeException e){
            e.printStackTrace();
            if(trans.isActive()){
                trans.rollback();
            }
            throw e;
        }finally{
            enma.close();
        }
    }
    @Override
    public Product get(int id){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id";
        try{
            TypedQuery<Product> query=enma.createQuery(jpql, Product.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }finally{
            enma.close();
        }
    }
    @Override
    public List<Product> getAll(){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
        try{
            TypedQuery<Product> query=enma.createQuery(jpql, Product.class);
            return query.getResultList();
        }finally{
            enma.close();
        }
    }
    @Override
    public List<Product> getAll(int page, int pageSize){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
        try{
            TypedQuery<Product> query=enma.createQuery(jpql, Product.class);
            query.setFirstResult((page-1)*pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        }finally{
            enma.close();
        }
    }
    @Override
    public List<Product> getLatest(int limit){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
        try{
            TypedQuery<Product> query=enma.createQuery(jpql, Product.class);
            query.setMaxResults(limit);
            return query.getResultList();
        }finally{
            enma.close();
        }
    }
    @Override
    public int count(){
        EntityManager enma=JPAConfig.getEntityManager();
        try{
            Long total=enma.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
            return total.intValue();
        }finally{
            enma.close();
        }
    }
}