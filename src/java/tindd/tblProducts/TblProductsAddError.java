/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblProducts;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblProductsAddError implements Serializable {

    private String emptyProductName;
    private String emptyCategoryId;
    private String notValidCategoryId;
    private String emptyImage;
    private String emptyDescription;
    private String emptyQuantity;
    private String notValidQuantity;
    private String emptyPrice;
    private String notValidPrice;
    private String emptyAuthor;
    private String failToAdd;

    public TblProductsAddError() {
    }

    public TblProductsAddError(String emptyProductName, String emptyCategoryId, String notValidCategoryId, String emptyImage, String emptyDescription, String emptyQuantity, String notValidQuantity, String emptyPrice, String notValidPrice, String emptyAuthor, String failToAdd) {
        this.emptyProductName = emptyProductName;
        this.emptyCategoryId = emptyCategoryId;
        this.notValidCategoryId = notValidCategoryId;
        this.emptyImage = emptyImage;
        this.emptyDescription = emptyDescription;
        this.emptyQuantity = emptyQuantity;
        this.notValidQuantity = notValidQuantity;
        this.emptyPrice = emptyPrice;
        this.notValidPrice = notValidPrice;
        this.emptyAuthor = emptyAuthor;
        this.failToAdd = failToAdd;
    }

    public String getEmptyProductName() {
        return emptyProductName;
    }

    public void setEmptyProductName(String emptyProductName) {
        this.emptyProductName = emptyProductName;
    }

    public String getEmptyCategoryId() {
        return emptyCategoryId;
    }

    public void setEmptyCategoryId(String emptyCategoryId) {
        this.emptyCategoryId = emptyCategoryId;
    }

    public String getNotValidCategoryId() {
        return notValidCategoryId;
    }

    public void setNotValidCategoryId(String notValidCategoryId) {
        this.notValidCategoryId = notValidCategoryId;
    }

    public String getEmptyImage() {
        return emptyImage;
    }

    public void setEmptyImage(String emptyImage) {
        this.emptyImage = emptyImage;
    }

    public String getEmptyDescription() {
        return emptyDescription;
    }

    public void setEmptyDescription(String emptyDescription) {
        this.emptyDescription = emptyDescription;
    }

    public String getEmptyQuantity() {
        return emptyQuantity;
    }

    public void setEmptyQuantity(String emptyQuantity) {
        this.emptyQuantity = emptyQuantity;
    }

    public String getNotValidQuantity() {
        return notValidQuantity;
    }

    public void setNotValidQuantity(String notValidQuantity) {
        this.notValidQuantity = notValidQuantity;
    }

    public String getEmptyPrice() {
        return emptyPrice;
    }

    public void setEmptyPrice(String emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public String getNotValidPrice() {
        return notValidPrice;
    }

    public void setNotValidPrice(String notValidPrice) {
        this.notValidPrice = notValidPrice;
    }

    public String getEmptyAuthor() {
        return emptyAuthor;
    }

    public void setEmptyAuthor(String emptyAuthor) {
        this.emptyAuthor = emptyAuthor;
    }

    public String getFailToAdd() {
        return failToAdd;
    }

    public void setFailToAdd(String failToAdd) {
        this.failToAdd = failToAdd;
    }

}
