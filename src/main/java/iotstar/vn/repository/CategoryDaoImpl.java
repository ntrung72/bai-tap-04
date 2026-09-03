package iotstar.vn.repository;

import java.util.List;
import iotstar.vn.config.JPAConfig;
import iotstar.vn.models.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CategoryDaoImpl implements CategoryDao{
    @Override
    public void insert(Category category){
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try{
            trans.begin();
            enma.persist(category);
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
    public void edit(Category category){
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try{
            trans.begin();
            enma.merge(category);
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
            Category category=enma.find(Category.class, id);
            if(category!=null){
                enma.remove(category);
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
    public Category get(int id){
        EntityManager enma=JPAConfig.getEntityManager();
        try{
            return enma.find(Category.class, id);
        }finally{
            enma.close();
        }
    }
    @Override
    public Category get(String name){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT c FROM Category c WHERE c.name = :name";
        try{
            TypedQuery<Category> query=enma.createQuery(jpql, Category.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }finally{
            enma.close();
        }
    }
    @Override
    public List<Category> getAll(){
        EntityManager enma=JPAConfig.getEntityManager();
        try{
            TypedQuery<Category> query=enma.createNamedQuery("Category.findAll", Category.class);
            return query.getResultList();
        }finally{
            enma.close();
        }
    }
    @Override
    public List<Category> search(String keyword){
        EntityManager enma=JPAConfig.getEntityManager();
        String jpql="SELECT c FROM Category c WHERE c.name LIKE :keyword";
        try{
            TypedQuery<Category> query=enma.createQuery(jpql, Category.class);
            query.setParameter("keyword", "%"+keyword+"%");
            return query.getResultList();
        }finally{
            enma.close();
        }
    }
    @Override
    public boolean hasProducts(int categoryId){
        EntityManager enma=JPAConfig.getEntityManager();
        try{
            Long total=enma.createQuery("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId", Long.class).setParameter("categoryId", categoryId).getSingleResult();
            return total>0;
        }finally{
            enma.close();
        }
    }
}