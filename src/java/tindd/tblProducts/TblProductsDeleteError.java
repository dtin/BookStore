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
public class TblProductsDeleteError implements Serializable {
    private String notFoundBook;

    public TblProductsDeleteError() {
    }

    public TblProductsDeleteError(String notFoundBook) {
        this.notFoundBook = notFoundBook;
    }

    public String getNotFoundBook() {
        return notFoundBook;
    }

    public void setNotFoundBook(String notFoundBook) {
        this.notFoundBook = notFoundBook;
    }
}
