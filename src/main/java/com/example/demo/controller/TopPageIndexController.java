/*
プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: TopPageIndexController.javaはログイン後に遷移するトップページに関連したマッピングを司るcontroller部分です
更新内容:
作成者: 赤坂
作成日: 2022/12/20
更新日: 2022/12/20
*/
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.ScheduleRepository;

@Controller
public class TopPageIndexController {
	
	// @Autowiredは記述するだけで他のクラスを呼び出すことができる。newを書かなくていい
	@Autowired
	ScheduleRepository scheduleRepository;

	// 自分の面談予定一覧
	@GetMapping("/")
	public String displayList(Model model) {
		return "toppage/topPageIndex"; // templatesのパスを表示
	}
	

}
