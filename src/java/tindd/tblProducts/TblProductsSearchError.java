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
public class TblProductsSearchError implements Serializable {
    private String minValueNumberic;
    private String maxValueNumberic;
    private String categoryNumberic;

    public TblProductsSearchError() {
    }

    public TblProductsSearchError(String minValueNumberic, String maxValueNumberic, String categoryNumberic) {
        this.minValueNumberic = minValueNumberic;
        this.maxValueNumberic = maxValueNumberic;
        this.categoryNumberic = categoryNumberic;
    }

    public String getMinValueNumberic() {
        return minValueNumberic;
    }

    public void setMinValueNumberic(String minValueNumberic) {
        this.minValueNumberic = minValueNumberic;
    }

    public String getMaxValueNumberic() {
        return maxValueNumberic;
    }

    public void setMaxValueNumberic(String maxValueNumberic) {
        this.maxValueNumberic = maxValueNumberic;
    }

    public String getCategoryNumberic() {
        return categoryNumberic;
    }

    public void setCategoryNumberic(String categoryNumberic) {
        this.categoryNumberic = categoryNumberic;
    }
}
