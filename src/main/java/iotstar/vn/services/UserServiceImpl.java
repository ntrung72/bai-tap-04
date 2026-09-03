package iotstar.vn.services;
import iotstar.vn.models.User;
import iotstar.vn.repository.UserDao;
import iotstar.vn.repository.UserDaoImpl;
public class UserServiceImpl implements UserService {
    private final UserDao userDao=new UserDaoImpl();
    @Override
    public User login(String username, String password) {
        User user=this.get(username);
        if(user!=null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }
    @Override
    public User get(String username) {
        return userDao.get(username);
    }
    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }
    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if(userDao.checkExistUsername(username) || userDao.checkExistEmail(email) || userDao.checkExistPhone(phone)) {
            return false;
        }
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        return userDao.insert(new User(email, username, fullname, password, null, 5, phone, date));
    }
    @Override
    public boolean updatePassword(String email, String newPassword) {
        return userDao.updatePassword(email, newPassword);
    }
    @Override
    public User updateProfile(int id, String fullname, String phone, String avatar) {
        return userDao.updateProfile(id, fullname, phone, avatar);
    }
    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }
    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }
    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }
    @Override
    public boolean checkExistPhone(String phone, int excludeUserId) {
        return userDao.checkExistPhone(phone, excludeUserId);
    }
    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
}