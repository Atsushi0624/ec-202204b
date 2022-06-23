package com.example.domain;

import java.util.List;

import lombok.Data;

/**
 * おすすめ商品を特定するための分析データドメイン.
 * 
 * @author nao.yamada
 *
 */
@Data
public class AnalizeData {
	/** 顧客情報 */
	private Customer customer;
	/**
	 * 商品情報（id,rateのみ入る）
	 * ただしrateはcustomerごとの評価の平均値が入る
	 */
	private List<Item> itemList;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "AnalizeData [customer=" + customer + ", itemList=" + itemList + "]";
	}

}
