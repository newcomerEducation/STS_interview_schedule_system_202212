/*プログラム名:　社員情報管理
プログラム説明: Spring BootとJDBCでmysqlを更新し、社員情報（id,name,rubi,created_at,updated_at）を管理します
このファイルの説明: MainController.javaはマッピングを司るcontroller部分です
更新内容:統合版にオブジェクトで値を受け渡す機能を追加
作成者: 赤坂
作成日: 2022/11/16
更新日:2022/11/22
*/
package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.EmployeeDto;
import com.example.demo.model.EmployeeForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	EmployeeForm employeeForm = new EmployeeForm();
	
	 /**
	   * ユーザー情報詳細画面を表示
	   * @param id 表示するユーザーID
	   * @param model Model
	   * @return ユーザー情報詳細画面
	   */
	
	
	
	
	
	//@GetMapping("/new")
//	public String disp1(Model model) {
//		model.addAttribute("employeeForm", new EmployeeForm());
//		return "employees/new";
//	}
//＜インサート＞
	//@PostMapping」を付与すると画面からPOSTメソッドで送られてきた場合の処理ができる。
	//引数には「new.html」のactionのフォームで設定したaction属性のパスを設定する			
		@PostMapping("/inputAll")
		public String disp2(EmployeeForm employeeForm) {
			// 登録メソッド等呼び出し
			employeeService.insertData(employeeForm);
			// newにリダイレクト
			return "redirect:/new";//
		}
		
			
//＜アップデート＞		
	// @PostMapping」を付与すると画面からPOSTメソッドで送られてきた場合の処理ができる。引数には「index.html」のaction
	// のフォームで設定したaction属性のパスを設定する	
//		@PostMapping("/update")
//		public String disp3(EmployeeForm employeeForm) {
			// 登録メソッド等呼び出し
//			employeeService.updateData(employeeForm);
			// test1にリダイレクト
//			return "redirect:/test1";
//		}
	
	
	// <全件取得　　社員情報管理画面　一覧　（index）>
	@GetMapping("/test1") // URLがtest1の時うごく？
	public String disp4(Model model) {
		List<EmployeeDto> list = employeeRepository.getAll();
		/*
		 * model.addAttributeメソッドで画面に渡したいデータをModelオブジェクトに追加 【構文】
		 * model.addAttribute("属性名", 渡したいデータ);
		 */
		model.addAttribute("EmployeeList", list);
		return "employees/index"; // templatesの下のemployeesフォルダの中のindexを表示?
	}
		
		
	
		//XXXXXXXXXXX仮　詳細コントローラー 詳細
		@GetMapping("/test2") // URLがtest2の時うごく？//テスト用　変更するのわすれるな
		public String disp5(Model model) {
			List<EmployeeDto> list = employeeRepository.getAll();
			/*
			 * model.addAttributeメソッドで画面に渡したいデータをModelオブジェクトに追加 【構文】
			 * model.addAttribute("属性名", 渡したいデータ);
			*/ 
			model.addAttribute("EmployeeList", list);
			return "employees/edit"; // templatesの下のtest1フォルダの中のindex2を表示?
			}

		/**
		   * ユーザー情報詳細画面を表示
		   * @param id 表示するユーザーID
		   * @param model Model
		   * @return ユーザー情報詳細画面
		   */
		//  @GetMapping("/user/{id}")
		//  public String displayView(@PathVariable Long id, Model model) {
		  //  User user = userService.findById(id);
		    //model.addAttribute("userData", user);
		    //return "user/view";
		  //}
	

}
