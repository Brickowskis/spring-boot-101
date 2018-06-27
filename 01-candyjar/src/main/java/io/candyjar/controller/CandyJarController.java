package io.candyjar.controller;

import io.candyjar.service.CandyJarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CandyJarController {

    private final CandyJarService candyJarService;


}
