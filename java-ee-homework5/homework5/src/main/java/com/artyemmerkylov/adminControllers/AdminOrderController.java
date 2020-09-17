package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.*;
import com.artyemmerkylov.models.Purchase;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@SessionScoped
@Named
public class AdminOrderController implements Serializable {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

//    public List<Purchase> getOrderPurchase(Order order) {
//        return purchaseRepository.getPurchaseListByOrderId(order.getId());
//    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public String editOrder(Order order) {
        this.order = order;
        return "/admin/order.xhtml?faces-redirect=true";
    }

//    public void deleteProduct(Order order, Product product) {
//        orderRepository.deleteProductFromOrder(order, product);
//
//        List<Purchase> purchaseList = order.getPurchaseList();
//        Iterator<Purchase> iter = purchaseList.iterator();
//
//        while(iter.hasNext())
//            if (iter.next().getProduct().getId() == product.getId()) iter.remove();
//
//        order.setPurchaseList(purchaseList);
//    }

    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order.getId());
    }

    public String saveOrder() {
        try {
            if (order.getId() != null) {
                orderRepository.updateOrder(order);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return "/admin/orders.xhtml?faces-redirect=true";
    }
}
