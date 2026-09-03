package iotstar.vn.repository;

import java.util.List;
import iotstar.vn.models.Product;

public interface ProductDao{
    void insert(Product product);
    void edit(Product product);
    void delete(int id);
    Product get(int id);
    List<Product> getAll();
    List<Product> getAll(int page, int pageSize);
    List<Product> getLatest(int limit);
    int count();
}