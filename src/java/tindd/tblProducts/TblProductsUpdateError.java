/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblProducts;

/**
 *
 * @author Tin
 */
public class TblProductsUpdateError extends TblProductsAddError {

    private String emptyProductId;
    private String notValidProductId;
    private String notValidCreatedAt;
    private String notFoundBook;

    public TblProductsUpdateError() {
    }

    public TblProductsUpdateError(String emptyProductId, String notValidProductId, String notValidCreatedAt, String notFoundBook) {
        this.emptyProductId = emptyProductId;
        this.notValidProductId = notValidProductId;
        this.notValidCreatedAt = notValidCreatedAt;
        this.notFoundBook = notFoundBook;
    }

    public String getEmptyProductId() {
        return emptyProductId;
    }

    public void setEmptyProductId(String emptyProductId) {
        this.emptyProductId = emptyProductId;
    }

    public String getNotValidProductId() {
        return notValidProductId;
    }

    public void setNotValidProductId(String notValidProductId) {
        this.notValidProductId = notValidProductId;
    }

    public String getNotValidCreatedAt() {
        return notValidCreatedAt;
    }

    public void setNotValidCreatedAt(String notValidCreatedAt) {
        this.notValidCreatedAt = notValidCreatedAt;
    }

    public String getNotFoundBook() {
        return notFoundBook;
    }

    public void setNotFoundBook(String notFoundBook) {
        this.notFoundBook = notFoundBook;
    }

    
}
