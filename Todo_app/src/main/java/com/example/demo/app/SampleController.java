package com.example.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 最初のサーバー起動 + 画面表示テスト用のController
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

	@GetMapping("/test")
	public String testPage(Model model) {
		model.addAttribute("title", "SamplePage");
		model.addAttribute("test", "testPage success!!");
		model.addAttribute("greet", "Hello!");
		return "test";
	}

}
