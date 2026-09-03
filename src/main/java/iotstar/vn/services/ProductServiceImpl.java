package iotstar.vn.services;

import java.io.File;
import java.util.List;
import iotstar.vn.models.Product;
import iotstar.vn.repository.ProductDao;
import iotstar.vn.repository.ProductDaoImpl;
import iotstar.vn.utils.Constant;

public class ProductServiceImpl implements ProductService{
    private final ProductDao productDao=new ProductDaoImpl();
    @Override
    public void insert(Product product){
        productDao.insert(product);
    }
    @Override
    public void edit(Product newProduct){
        Product oldProduct=productDao.get(newProduct.getId());
        if(oldProduct==null){
            return;
        }
        String oldImage=oldProduct.getImage();
        oldProduct.setName(newProduct.getName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setCategory(newProduct.getCategory());
        if(newProduct.getImage()!=null && !newProduct.getImage().isBlank()){
            oldProduct.setImage(newProduct.getImage());
        }
        productDao.edit(oldProduct);
        if(newProduct.getImage()!=null && !newProduct.getImage().isBlank() && oldImage!=null && !oldImage.isBlank()){
            deleteImage(oldImage);
        }
    }
    @Override
    public void delete(int id){
        Product product=productDao.get(id);
        if(product==null){
            return;
        }
        productDao.delete(id);
        if(product.getImage()!=null && !product.getImage().isBlank()){
            deleteImage(product.getImage());
        }
    }
    @Override
    public Product get(int id){
        return productDao.get(id);
    }
    @Override
    public List<Product> getAll(){
        return productDao.getAll();
    }
    @Override
    public List<Product> getAll(int page, int pageSize){
        return productDao.getAll(page, pageSize);
    }
    @Override
    public List<Product> getLatest(int limit){
        return productDao.getLatest(limit);
    }
    @Override
    public int count(){
        return productDao.count();
    }
    private void deleteImage(String image){
        File file=new File(Constant.DIR, image.replace("/", File.separator));
        if(file.exists()){
            file.delete();
        }
    }
}