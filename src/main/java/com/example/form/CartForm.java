package com.example.form;

import java.util.List;

/**
 * カートに入れる商品情報を受け取るフォーム.
 * 
 * @author atsushi.kikuchi
 *
 */
public class CartForm {
	
	/** アイテムID */
	private Integer itemId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private String size;
	/** トッピングIDリスト */
	private List<Integer> toppingIdList;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	public List<Integer> getToppingIdList() {
		return toppingIdList;
	}
	public void setToppingIdList(List<Integer> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}
	
	@Override
	public String toString() {
		return "CartForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size + ", toppingIdList="
				+ toppingIdList + "]";
	}
	
	
}