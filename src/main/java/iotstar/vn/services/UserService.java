package iotstar.vn.services;
import iotstar.vn.models.User;
public interface UserService {
    User login(String username, String password);
    User get(String username);
    User getByEmail(String email);
    User getById(int id);
    void insert(User user);
    boolean updatePassword(String email, String newPassword);
    User updateProfile(int id, String fullname, String phone, String avatar);
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    boolean checkExistPhone(String phone, int excludeUserId);
}