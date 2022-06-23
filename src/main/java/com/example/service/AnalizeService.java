package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AnalizeData;
import com.example.domain.Customer;
import com.example.domain.Item;
import com.example.repository.AnalizeRepository;
import com.example.repository.ItemRepository;

/**
 * おすすめ商品特定用の分析データを操作するサービス.
 * 
 * @author nao.yamada
 *
 */
@Service
public class AnalizeService {

	@Autowired
	private AnalizeRepository analizeRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private HttpSession session;

	/**
	 * 分析データからおすすめ商品３つをを取得します.
	 * 
	 * @return
	 */
	public List<Item> getRecommendItems() {
		if (session.getAttribute("customer") == null) {
			return null;
		}
		// ログイン中の顧客を取得
		Customer logedinCustomer = (Customer) session.getAttribute("customer");
		logedinCustomer.getId();
		List<Double> logedInCustomerRates = null;

		int itemNum = itemRepository.getItemNum();
		analizeRepository.setItemNum(itemNum);

		// Map<顧客, 商品への評価のベクトル>
		Map<Customer, List<Double>> analizeDataMap = new HashMap<>();

		for (AnalizeData data : analizeRepository.getdata()) {
			Customer customer = data.getCustomer();
			List<Double> customerRateList = new ArrayList<>();
			for (Item item : data.getItemList()) {
				customerRateList.add(item.getAverageRate());
			}
			analizeDataMap.put(customer, customerRateList);
			
			// ログイン中の顧客の商品評価のベクトルを取得
			if (customer.getId().equals(logedinCustomer.getId())) {
				logedInCustomerRates = List.copyOf(customerRateList);
			}
		}
		
		// 最も評価の似ている評価ベクトルを取得
		List<Double> mostSimilerList = null;
		double minDist = 100.0;
		for (Entry<Customer, List<Double>> data : analizeDataMap.entrySet()) {
			Double dist = calcEuclideanDist(logedInCustomerRates, data.getValue());
			if (dist < minDist) {
				minDist = dist;
				mostSimilerList = data.getValue();
			}
		}
		
		// 評価ベクトルから値の大きい評価値上位３つを取得する
		Map<Integer, Double> rateAndItemIdMap = new HashMap<>();
		for (int i=0; i<mostSimilerList.size(); i++ ) {
			rateAndItemIdMap.put(i, mostSimilerList.get(i));
		}
		List<Entry<Integer, Double>> rateAndItemIdList = new ArrayList<>(rateAndItemIdMap.entrySet());
		rateAndItemIdList.sort(Entry.comparingByValue());
		Collections.reverse(rateAndItemIdList);
		
		// おすすめ商品をListに入れる
		int firstItemId = rateAndItemIdList.get(0).getKey();
		int secondItemId = rateAndItemIdList.get(1).getKey();
		int thirdItemId = rateAndItemIdList.get(2).getKey();
		List<Item> recommendItemList = new ArrayList<>();
		recommendItemList.add(itemRepository.load(firstItemId));
		recommendItemList.add(itemRepository.load(secondItemId));
		recommendItemList.add(itemRepository.load(thirdItemId));
		System.out.println(recommendItemList);
		
		return recommendItemList;
	}
	
	/**
	 * ユークリッド距離を計算する.
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public Double calcEuclideanDist(List<Double> list1, List<Double> list2) {
		Double sum = 0.0;
		for (int i=0; i<list1.size(); i++) {
			sum += Math.pow(list1.get(i) - list2.get(i), 2);
		}
		return Math.sqrt(sum);
	}

}
