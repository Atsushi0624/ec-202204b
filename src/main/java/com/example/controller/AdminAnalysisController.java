package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author nao.yamada
 *
 */

import com.example.domain.Item;
import com.example.service.AdminAnalysisService;
import com.example.service.ItemListService;

@RequestMapping("/adminAnalysis")
@Controller
public class AdminAnalysisController {

	/** 顧客登録時に選択できる年代の数 */
	private static final int AGE_NUM = 6;

	@Autowired
	private AdminAnalysisService adminAnalysisService;

	@Autowired
	private ItemListService itemListService;

	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart(Integer itemId, Model model) {
		List<Item> allItemList = itemListService.findAll();
		model.addAttribute("itemList", allItemList);
		if (itemId == null) {
			itemId = 1;
		}
		model.addAttribute("selectedItemId", itemId);

		// Integer[] agePercentArray;
		// Integer[] genderPercentArray;
		List<String> agePercentList = new ArrayList<>(2);
		List<String> genderPercentList = new ArrayList<>(AGE_NUM);

		// Map<male, 男性の数> Map<age20, 20代の数>のように値が格納されている
		Map<String, Integer> ageGenderCountMap = adminAnalysisService.getAgeGenderMapByItem(itemId);
		if (ageGenderCountMap == null) {
			// agePercentArray = new Integer[AGE_NUM];
			// genderPercentArray = new Integer[AGE_NUM];
		} else {
			// mapから「数」のみのListに変換する
			List<Integer> ageCountList = new ArrayList<>(
					Arrays.asList(ageGenderCountMap.get("male"), ageGenderCountMap.get("female")));
			List<Integer> genderCountList = new ArrayList<>();
			for (int i = 1; i <= AGE_NUM; i++) {
				String keyName = "age" + String.valueOf(i * 10);
				genderCountList.add(ageGenderCountMap.get(keyName));
			}

			// Listの要素を数から%に変換する
			agePercentList = toPercent(ageCountList);
			genderPercentList = toPercent(genderCountList);

			// agePercentArray = (Integer[])agePercentList.toArray();
			// genderPercentArray = (Integer[])genderPercentList.toArray();

			System.out.println(agePercentList.toString());
			System.out.println(genderPercentList.toString());

		}
		model.addAttribute("agePercentList", agePercentList);
		model.addAttribute("genderPercentList", genderPercentList);

		return "pie_chart";
	}

	@RequestMapping("/ranking")
	public String displayRanking(Model model, String age, String gender) {
		List<Map<Item, Integer>> itemList = adminAnalysisService.rankedItemList(age, gender);
		List<String> items = new ArrayList<>();
		List<Integer> sales = new ArrayList<>();
		for (Map<Item, Integer> itemMap : itemList) {
			Set<Item> keySet = itemMap.keySet();
			Iterator iterator = keySet.iterator();
			Item item = (Item) iterator.next();
			Integer sale = itemMap.get(item);
			items.add(item.getName());
			sales.add(sale);
		}
		model.addAttribute("items", items);
		model.addAttribute("sales", sales);

		// model.addAttribute("itemList", itemList);
		return "ranking";
	}

	/**
	 * Listの要素の値を、その値がList要素合計に占める割合（%）に変換します.
	 * 
	 * @param list
	 */
	public List<String> toPercent(List<Integer> list) {
		System.out.println("======================");
		List<String> percentList = new ArrayList<>();
		System.out.println(list);
		int total = 0;
		for (int elem : list) {
			total += elem;
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(percentList);
			percentList.add(String.valueOf((int) (((double) list.get(i) / total) * 100)));
			System.out.println(percentList);
		}

		System.out.println(percentList);
		System.out.println("======================");
		return percentList;
	}
}
