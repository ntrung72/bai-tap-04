package iotstar.vn.repository;
import iotstar.vn.config.JPAConfig;
import iotstar.vn.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
public class UserDaoImpl implements UserDao {
    @Override
    public User get(String username) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            return enma.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }
    @Override
    public User getByEmail(String email) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            return enma.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }
    @Override
    public User getById(int id) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            return enma.find(User.class, id);
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean insert(User user) {
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
            trans.commit();
            return true;
        } catch(Exception e) {
            if(trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean updatePassword(String email, String newPassword) {
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try {
            trans.begin();
            int updated=enma.createQuery("UPDATE User u SET u.passWord = :password WHERE u.email = :email")
                    .setParameter("password", newPassword)
                    .setParameter("email", email)
                    .executeUpdate();
            trans.commit();
            return updated>0;
        } catch(Exception e) {
            if(trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            enma.close();
        }
    }
    @Override
    public User updateProfile(int id, String fullname, String phone, String avatar) {
        EntityManager enma=JPAConfig.getEntityManager();
        EntityTransaction trans=enma.getTransaction();
        try {
            trans.begin();
            User user=enma.find(User.class, id);
            if(user==null) {
                trans.rollback();
                return null;
            }
            user.setFullName(fullname);
            user.setPhone(phone);
            user.setAvatar(avatar);
            trans.commit();
            return user;
        } catch(Exception e) {
            if(trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean checkExistEmail(String email) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            Long count=enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count>0;
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean checkExistUsername(String username) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            Long count=enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.userName = :username", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count>0;
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            Long count=enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
            return count>0;
        } finally {
            enma.close();
        }
    }
    @Override
    public boolean checkExistPhone(String phone, int excludeUserId) {
        EntityManager enma=JPAConfig.getEntityManager();
        try {
            Long count=enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.phone = :phone AND u.id <> :id", Long.class)
                    .setParameter("phone", phone)
                    .setParameter("id", excludeUserId)
                    .getSingleResult();
            return count>0;
        } finally {
            enma.close();
        }
    }
}