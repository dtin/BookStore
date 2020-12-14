/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblProducts;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Tin
 */
public class TblProductsDTO implements Serializable {
    private int productId;
    private String productName;
    private int categoryId;
    private String image;
    private String description;
    private long price;
    private String author;
    private int quantity;
    private Date createdAt;
    private boolean status;

    public TblProductsDTO() {
    }

    public TblProductsDTO(int productId, String productName, long price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
    
    public TblProductsDTO(int productId, String productName, int categoryId, String image, String description, long price, String author, int quantity, Date createdAt, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.price = price;
        this.author = author;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.status = status;
    }
    
    public TblProductsDTO(int productId, String productName, int categoryId, String image, String description, long price, String author) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.price = price;
        this.author = author;
    }

    public TblProductsDTO(int productId, String productName, String image, long price) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.price = price;
    }
    
    public TblProductsDTO(String productName, int categoryId, String image, String description, long price, String author, int quantity) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.price = price;
        this.author = author;
        this.quantity = quantity;
    }

    public TblProductsDTO(int productId, String productName, int categoryId, String image, String description, long price, String author, int quantity, Date createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.price = price;
        this.author = author;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
    
    

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}