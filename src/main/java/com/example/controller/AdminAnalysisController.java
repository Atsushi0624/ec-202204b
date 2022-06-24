package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/adminAnalysis")
@Controller
public class AdminAnalysisController {
	@Autowired
	private ItemListService itemListService;

	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart() {
		return "pie_chart";
	}

	@RequestMapping("/ranking")
	public String displayRanking(Model model, String ageKey, String genderKey) {
		List<Item> itemList = null;
		if (ageKey == null) {
			itemList = itemListService.findAll();
			model.addAttribute("rankingKey", "10");
		} else if (ageKey.equals("20")) {
			model.addAttribute("rankingKey", "10");
		} else if (ageKey.equals("30")) {
			model.addAttribute("rankingKey", "10");
		}
		int rank = 0;
		model.addAttribute("itemList", itemList);
		return "ranking";
	}
}
