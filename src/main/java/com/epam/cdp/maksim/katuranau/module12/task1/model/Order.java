package com.epam.cdp.maksim.katuranau.module12.task1.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class Order {
    @Min(value = 0, message = "identifier should not be negative")
    private Long orderId;
    @NotNull(message = "status object can't be null")
    @Valid
    private Status status;
    @NotNull(message = "list of goods should not be null")
    private List<Goods> goodsList;
    private Integer totalPrice;

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Order(final Long orderId, final Status status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(final List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(status, order.status) &&
                Objects.equals(goodsList, order.goodsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, goodsList);
    }
}
