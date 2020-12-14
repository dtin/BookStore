/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.history;

import java.io.Serializable;
import tindd.tblOrders.TblOrdersDTO;

/**
 *
 * @author Tin
 */
public class OrderHistory implements Serializable {
    private TblOrdersDTO ordersDTO;
    private long finalPrice;

    public OrderHistory() {
    }

    public OrderHistory(TblOrdersDTO ordersDTO, long finalPrice) {
        this.ordersDTO = ordersDTO;
        this.finalPrice = finalPrice;
    }

    public TblOrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(TblOrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }
}
