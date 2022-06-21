package com.example.domain;

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
	private List<OrderTopping> orderToppingList;
	
	public int getSubTotal() {
		int toppingPrice = 0;
		if(orderToppingList.size() != 0) {
			if("M".equals(size)) {
				toppingPrice = orderToppingList.size() * orderToppingList.get(0).getTopping().getPriceM();				
			}else {
				toppingPrice = orderToppingList.size() * orderToppingList.get(0).getTopping().getPriceL();				
			}
		}
		
		if("M".equals(size)) {
			return (item.getPriceM() + toppingPrice) * quantity;			
		}else {
			return (item.getPriceL() + toppingPrice) * quantity;			
		}
	}
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
	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}
	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}
	
}
