package iotstar.vn.models;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="Products", schema="dbo")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p ORDER BY p.id DESC")
public class Product implements Serializable{
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    @NotEmpty(message="Không được phép rỗng")
    @Column(name="product_name", columnDefinition="NVARCHAR(255) NOT NULL")
    private String name;
    @Column(name="images", columnDefinition="NVARCHAR(255) NULL")
    private String image;
    @Column(name="price", precision=18, scale=2, nullable=false)
    private BigDecimal price;
    @Column(name="description", columnDefinition="NVARCHAR(MAX) NULL")
    private String description;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cate_id", nullable=false)
    private Category category;
    public Product(){}
    public Product(int id, String name, String image, BigDecimal price, String description, Category category){
        this.id=id;
        this.name=name;
        this.image=image;
        this.price=price;
        this.description=description;
        this.category=category;
    }
    public Product(String name, String image, BigDecimal price, String description, Category category){
        this.name=name;
        this.image=image;
        this.price=price;
        this.description=description;
        this.category=category;
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
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price=price;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public Category getCategory(){
        return category;
    }
    public void setCategory(Category category){
        this.category=category;
    }
}