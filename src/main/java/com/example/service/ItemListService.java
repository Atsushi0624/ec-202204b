package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品情報一覧を操作するサービス.
 * 
 * @author ryosuke.moritani
 *
 */
@Service
public class ItemListService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品を全件取得する.
	 * 
	 * @return 商品一覧
	 */
	public List<Item> findAll() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

	/**
	 * 検索された商品を取得する.
	 * 
	 * @param itemName 検索する商品名
	 * @return
	 */
	public List<Item> searchByName(String itemName) {
		List<Item> itemList = itemRepository.findByName(itemName);
		return itemList;
	}
}
