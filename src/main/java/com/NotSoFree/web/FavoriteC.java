package com.NotSoFree.web;

import com.NotSoFree.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/favoriteC")
public class FavoriteC {
    
    @Autowired
    private FavoriteService favoriteService;
    
}
