package com.tz.campon.board.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService service;

    @GetMapping("/list")
    public String getboard(@RequestParam(required = false, defaultValue = "1", name = "currentPage") Integer currentPage, Model model){
        int size = 2;
        int grpSize = 5;
        int totalCount = 0;

        List<Board> list = service.getBoard(currentPage, size);
        totalCount = service.getTotal();

        System.out.println(totalCount);
        PageHandler handler = new PageHandler(currentPage, totalCount, size, grpSize);

        model.addAttribute("members", list);
        model.addAttribute("pageHandler", handler);

        return "memberlist";
    }

    @GetMapping("/test")
    public String test(String test ){
        System.out.println( test);
        return "memberlist";
    }

}
