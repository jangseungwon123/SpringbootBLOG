package com.tenco.blog.controller;


import com.tenco.blog.model.Board;
import com.tenco.blog.model.repository.BoardNativeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // IoC 대상 - 싱글톤 패턴으로 관리 됨
public class BoardController {

    private BoardNativeRepository boardNativeRepository;
    //DI : 의존성 주입 : 스프링이 자동으로 객체를 주입
    public BoardController(BoardNativeRepository boardNativeRepository){
        this.boardNativeRepository= boardNativeRepository;
    }


    // username, title, content <--- DTO 받는 방법, 기본 데이터 타입 설정
    // form 태그에서 넘어오는 데이터 받기
    // form 태그에 name 속성에에 key 값 동일해야 함
    // http://localhost:8080/board/save
    // 스프링 부트 기본 파싱 전략 - key=value (form)
    @PostMapping("/board/save")
    public String save(@RequestParam("username") String username,
                       @RequestParam("title")String title,
                       @RequestParam("content")String content) {
        System.out.println("title : " + title);
        System.out.println("content : " + content);
        System.out.println("username : " + username);

        boardNativeRepository.save(title, content, username);

        return "redirect:/";



    }


    @GetMapping({"/", "/index"})
    //public String index(Model mdel) {
    public String index(HttpServletRequest request) {
        //    prefix: /templates/
        //    return : index
        //    suffix: .mustache
        //    # 기본 경로를 src/main/resources/templates/index.mustache

        //DB 접근해서 select 결과값을 받아서 머스태치 파일에 데이터 바인딩 처리
        List<Board> boardList = boardNativeRepository.findAll();
        // 뷰에 데이터를 전달 -> Model 사용가능
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveFrom() {
        // /templates//board
        // /templates/board/
        return "board/save-form";
    }

    //

    /**
     * 상세보기 화면 요청
     * board/1
     */
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id) {
        // URL 에서 받은 id 값을 사용해서 특정 게시글 상세보기 조회
        // 실제로는 이 id값으로 데이터베이스에 있는 게시글 조회 하고
        // 머스태치 파일로 데이터를 내려 주어야 함 (Model)
        return "board/detail";
    }

}
