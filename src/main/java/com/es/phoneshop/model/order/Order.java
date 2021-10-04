package com.es.phoneshop.model.order;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Order extends Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal subtotal;
    private BigDecimal deliveryCost;
    private String firstName;
    private String LastName;
    private String address;
    private PaymentMethod paymentMethod;
    private Date deliveryDate;
    private long id;
    private String secureId;

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(subtotal, order.subtotal) && Objects.equals(deliveryCost, order.deliveryCost) && Objects.equals(firstName, order.firstName) && Objects.equals(LastName, order.LastName) && Objects.equals(address, order.address) && paymentMethod == order.paymentMethod && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(secureId, order.secureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtotal, deliveryCost, firstName, LastName, address, paymentMethod, deliveryDate, id, secureId);
    }
}
