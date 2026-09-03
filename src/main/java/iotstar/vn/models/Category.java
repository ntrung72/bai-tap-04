package iotstar.vn.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="Category", schema="dbo")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cate_id")
    private int id;
    @NotEmpty(message="Không được phép rỗng")
    @Column(name="cate_name", columnDefinition="NVARCHAR(255) NULL")
    private String name;
    @Column(name="icons", columnDefinition="NVARCHAR(255) NULL")
    private String icon;
    @OneToMany(mappedBy="category")
    private List<Product> products=new ArrayList<>();
    public Category(){}
    public Category(int id, String name, String icon){
        this.id=id;
        this.name=name;
        this.icon=icon;
    }
    public Category(String name, String icon){
        this.name=name;
        this.icon=icon;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
        this.icon=icon;
    }
    public List<Product> getProducts(){
        return products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
    }
    public void addProduct(Product product){
        products.add(product);
        product.setCategory(this);
    }
    public void removeProduct(Product product){
        products.remove(product);
        product.setCategory(null);
    }
}