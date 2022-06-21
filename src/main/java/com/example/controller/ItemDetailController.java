package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemDetailService;

/**
 * 商品詳細を操作するコントローラ.
 * 
 * @author ryosuke.moritani
 *
 */
@Controller
@RequestMapping("")
public class ItemDetailController {
	@Autowired
	private ItemDetailService itemDetailService;

	/**
	 * 商品詳細を表示する.
	 * 
	 * @param model モデル
	 * @param id    商品id
	 * @return 商品詳細ページ
	 */
	@RequestMapping("/detail")
	public String showDetail(Model model, Integer id) {
		Item item = itemDetailService.showDetail(id);
		model.addAttribute("item", item);
		return "item_detail";
	}
}
