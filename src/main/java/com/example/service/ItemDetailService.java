package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品詳細を操作するサービス.
 * 
 * @author ryosuke.moritani
 *
 */
@Service
public class ItemDetailService {
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ToppingRepository toppingRepository;

  /**
   * 商品情報を取得する.
   * 
   * @param id 商品id
   * @return 商品情報
   */
  public Item showDetail(Integer id) {
    Item item = itemRepository.load(id);
    List<Topping> toppingList = toppingRepository.findAll();
    item.setToppingList(toppingList);
    return item;
  }
}
