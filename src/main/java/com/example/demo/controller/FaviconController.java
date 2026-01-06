package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FaviconController {

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
//        throw new UnsupportedOperationException();
    }
}