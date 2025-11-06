package com.back.domain.wiseSaying.controller;


//하는 일 : 명언 등록, 명언 리스트 반환, 명언 제거, 명언 수정, Json 빌드 명령

import com.back.domain.wiseSaying.entity.QueryParse;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class WiseSayingController {
    private WiseSayingService wiseSayingService;
    private Scanner scanner;

    @Autowired
    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }
    
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void write(){
        System.out.print("Content : ");
        String content = scanner.nextLine();
        System.out.print("Author : ");
        String author = scanner.nextLine();

        int id = wiseSayingService.write(content,author);
        System.out.println("No."+id+ "is written.");
    }

    public void remove(String command){
        String numbersOnly = command.replaceAll("[^0-9]", "");
        int id = Integer.parseInt(numbersOnly);
        if(wiseSayingService.remove(id)){
            System.out.println("No." + id + "is removed.");
        }
        else{
            System.out.println("No." + id+ "is not exist.");
        }
    }

    public void modify(String command){
        String numbersOnly = command.replaceAll("[^0-9]", "");
        int id = Integer.parseInt(numbersOnly);
        WiseSaying w = wiseSayingService.read(id);

        if(w != null){
            System.out.println("Content (before) :" + w.getContent());
            System.out.print("Content (after) : ");
            String content = scanner.nextLine();


            System.out.println("Author (before):  " + w.getAuthor());
            System.out.print("Author (after) : ");
            String author = scanner.nextLine();

            wiseSayingService.modify(id,content,author);
        }
        else{
            System.out.println("No." + id+ "is not exist.");
        }
    }

    public void build(){
        wiseSayingService.build();
    }


    public void list(String command) {
        QueryParse q = wiseSayingService.queryParser(command);
        if(q.getPage() > 0) {
            Map<Integer, List<WiseSaying>> result = wiseSayingService.search(q.getPage(), q.getKeywordType(), q.getKeyword());
            if (result == null) {
                System.out.println("error request");
            } else {
                result.forEach((k, v) -> {
                    System.out.println("No / Author / Content");
                    System.out.println("----------------------");
                    for (WiseSaying w : v) {
                        System.out.println(w.getIdAsInt() + " / " + w.getAuthor() + " / " + w.getContent());
                    }
                    System.out.println("----------------------");
                    System.out.print("page : ");
                    for (int i = 1; i <= k; i++) {
                        if (i != 1) {
                            System.out.print(" / ");
                        }
                        if (i == q.getPage()) {
                            System.out.print("[" + i + "]");
                        } else {
                            System.out.print(i);
                        }
                    }
                    System.out.println();
                });
            }
        }
    }

    public void dummyData() {
        List<WiseSaying> wsList = wiseSayingService.list();
        if (wsList == null || wsList.isEmpty()) {
            for(int i=1;i<11;i++){
                String content= "dummy" + i;
                String author = "dummyAuthor" + i ;
                wiseSayingService.write(content,author);
            }
        }
    }
}
