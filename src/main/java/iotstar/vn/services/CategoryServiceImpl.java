package iotstar.vn.services;

import java.io.File;
import java.util.List;
import iotstar.vn.models.Category;
import iotstar.vn.repository.CategoryDao;
import iotstar.vn.repository.CategoryDaoImpl;
import iotstar.vn.utils.Constant;

public class CategoryServiceImpl implements CategoryService{
    private final CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public void insert(Category category){
        categoryDao.insert(category);
    }
    @Override
    public void edit(Category newCategory){
        Category oldCategory=categoryDao.get(newCategory.getId());
        if(oldCategory==null){
            return;
        }
        String oldIcon=oldCategory.getIcon();
        oldCategory.setName(newCategory.getName());
        if(newCategory.getIcon()!=null && !newCategory.getIcon().isBlank()){
            oldCategory.setIcon(newCategory.getIcon());
        }
        categoryDao.edit(oldCategory);
        if(newCategory.getIcon()!=null && !newCategory.getIcon().isBlank() && oldIcon!=null && !oldIcon.isBlank()){
            deleteImage(oldIcon);
        }
    }
    @Override
    public void delete(int id){
        Category category=categoryDao.get(id);
        if(category==null){
            return;
        }
        if(categoryDao.hasProducts(id)){
            throw new IllegalStateException("Không thể xóa danh mục đang chứa sản phẩm!");
        }
        String icon=category.getIcon();
        categoryDao.delete(id);
        if(icon!=null && !icon.isBlank()){
            deleteImage(icon);
        }
    }
    @Override
    public Category get(int id){
        return categoryDao.get(id);
    }
    @Override
    public Category get(String name){
        return categoryDao.get(name);
    }
    @Override
    public List<Category> getAll(){
        return categoryDao.getAll();
    }
    @Override
    public List<Category> search(String keyword){
        return categoryDao.search(keyword);
    }
    private void deleteImage(String image){
        File file=new File(Constant.DIR, image.replace("/", File.separator));
        if(file.exists()){
            file.delete();
        }
    }
}