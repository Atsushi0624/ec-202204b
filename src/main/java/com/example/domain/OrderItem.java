package com.example.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * OrderItemを表すドメイン.
 * 
 * @author atsushi.kikuchi
 *
 */
@Data
public class OrderItem {
	/** ID */
	private Integer id;
	/** アイテムID */
	private Integer itemId;
	/** オーダーID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private String size;
	/** アイテム情報 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderTopping;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<OrderTopping> getOrderTopping() {
		return orderTopping;
	}
	public void setOrderTopping(List<OrderTopping> orderTopping) {
		this.orderTopping = orderTopping;
	}
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderTopping=" + orderTopping + "]";
	}
	
}
