/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.product;

import java.io.Serializable;
import tindd.tblProducts.TblProductsDTO;

/**
 *
 * @author Tin
 */
public class ProductSearch implements Serializable {

    private TblProductsDTO productsDTO;
    private String categoryName;

    public ProductSearch() {
    }

    public ProductSearch(TblProductsDTO productsDTO, String categoryName) {
        this.productsDTO = productsDTO;
        this.categoryName = categoryName;
    }

    public TblProductsDTO getProductsDTO() {
        return productsDTO;
    }

    public void setProductsDTO(TblProductsDTO productsDTO) {
        this.productsDTO = productsDTO;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
