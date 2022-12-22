/*
プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: SchedulesIndexController.javaは面談予定一覧画面に関連したマッピングを司るcontroller部分です
更新内容:
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controllerをつけないと以下メソッドが動かない
@Controller
public class SchedulesIndexController {
// 画面表示
	@GetMapping("/schedules/index")
	public String displayList(Model model) {
		return "schedules/index"; // templatesのパスを表示
	}

}
