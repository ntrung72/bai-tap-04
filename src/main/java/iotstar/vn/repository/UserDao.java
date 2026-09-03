package iotstar.vn.repository;
import iotstar.vn.models.User;
public interface UserDao {
    User get(String username);
    User getByEmail(String email);
    User getById(int id);
    boolean insert(User user);
    boolean updatePassword(String email, String newPassword);
    User updateProfile(int id, String fullname, String phone, String avatar);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    boolean checkExistPhone(String phone, int excludeUserId);
}