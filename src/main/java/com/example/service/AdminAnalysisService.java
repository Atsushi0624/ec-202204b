package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.OrderItemRepository;

/**
 * 管理者の分析用データを操作するサービス.
 * 
 * @author nao.yamada
 *
 */
@Service
public class AdminAnalysisService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	/**
	 * 年代と性別別に売り上げ順の商品リストを返す.
	 * 
	 * @param age 年代
	 * @param gender 性別
	 * @return 売り上げ順にならんだ商品のリスト
	 */
	public List<Map<Item, Integer>> rankedItemList(String age, String gender){
		return orderItemRepository.rankedItemListByAgeAndGender(age, gender);
	}

}
