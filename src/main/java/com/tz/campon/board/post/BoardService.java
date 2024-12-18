package com.tz.campon.board.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository dao;

    public List<Board> getBoard(int currentPage, int size){
        System.out.println("current Page"+currentPage);
        System.out.println("size"+size);

        return dao.selectAllPage(currentPage, size);
    }

    public int getTotal(){
        return dao.countAll();
    }
}
