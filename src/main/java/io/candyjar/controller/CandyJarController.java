package io.candyjar.controller;

import io.candyjar.service.CandyJarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CandyJarController {

	private final CandyJarService candyJarService;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("candy", candyJarService.findAll());
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
