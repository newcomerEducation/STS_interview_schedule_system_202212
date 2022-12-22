/*プログラム名:　社員情報管理 
このファイルの説明: マッピングを司るcontroller部分です.
作成者: 福本
作成日: 2022/11/16
更新日:2022/12/21 
*/
package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.DepartmentDto;
import com.example.demo.model.EmployeeDepartmentDto;
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

//＜登録＞
	@GetMapping("/employees/new")
	// 初回アクセス時の画面を表示でフォームを紐付けています。
	public String displayInsert(Model model) {
		model.addAttribute("employeeForm", new EmployeeForm());
		List<DepartmentDto> list = employeeRepository.getAllDepartments();
		/*
		 * model.addAttributeメソッドで画面に渡したいデータをModelオブジェクトに追加 【構文】
		 * model.addAttribute("属性名", 渡したいデータ);
		 */
		model.addAttribute("selectedValue", 9);
		model.addAttribute("DepartmentList", list);
		return "employees/new"; // templatesの下のemployeesフォルダの中のnewを表示
	}

	// @PostMapping」を付与すると画面からPOSTメソッドで送られてきた場合の処理ができる。
	// 登録機能 フロント部分で外部キーとオブジェクトが干渉している？ オブジェクトを使えず、@RequestParamを使い個々に値を取ってきている。
	@PostMapping("/inputAll")
	public String insertUp(@RequestParam("employee_code") String str_employee_code,
			@RequestParam("employee_name") String str_employee_name, @RequestParam("password") String str_password,
			@RequestParam("admin_flag") String str_admin_flag,
			@RequestParam("department_id") String str_department_id) {
		// intへ型変更
		int admin_flag = Integer.parseInt(str_admin_flag);
		int department_id = Integer.parseInt(str_department_id);
		// ScheduleFormクラスにsetする
		employeeForm.setEmployee_code(str_employee_code);
		employeeForm.setEmployee_name(str_employee_name);
		employeeForm.setPassword(str_password);
		employeeForm.setAdmin_flag(admin_flag);
		employeeForm.setDepartment_id(department_id);
		// 登録メソッド等呼び出し
		employeeService.insertData(employeeForm);
		// 一覧表示画面にリダイレクト
		return "redirect:/employees/index/entrance";
	}

//<更新＞// 引数には「edit.html」のactionのフォームで設定したaction属性のパスを設定する	
	@GetMapping("/employees/{id}/edit")
	public String displayEdit(@PathVariable Long id, Model model) {
		List<EmployeeDto> edit = employeeRepository.findByIdEdit(id);
		model.addAttribute("EditList", edit);
		return "employees/edit";
	}

	@PostMapping("/employees/update")
	public String editUp(EmployeeForm employeeForm) {
		employeeService.updateData(employeeForm);
		return "redirect:/employees/index/entrance";
	}

//<削除>	
	@GetMapping("/employees/{id}/destroy")
	public String displayViewDestroy(@PathVariable Long id, Model model) {
		employeeRepository.findByIdDestroy(id);
		return "redirect:/employees/index/entrance";
	}

//<一覧表示>
	@GetMapping("/employees/index/entrance")
	public String displayIndex(Model model) {
		List<EmployeeDepartmentDto> record = employeeRepository.getAll();
		// ページ数の検出
		double decimalPointRecord = record.size();
		double decimalPointPage = decimalPointRecord / 15;
		int totalPage = (int) Math.ceil(decimalPointPage);
        //初期接続用ページ
		int page = 1;
		int recordsize = record.size();
		

		model.addAttribute("Page", page);// page＝１
		model.addAttribute("TotalPage", totalPage);// totalPage=全ページ数
		model.addAttribute("TotalRecord", recordsize);// recordsize=全件数
		
		List<EmployeeDepartmentDto> list = employeeRepository.getFirst(recordsize);
		model.addAttribute("EmployeeList", list);// list＝最新レコードから降順で15件
		return "employees/index";
	}

	@RequestMapping("/employees/index")
	// パラメータ（必須）を受け取る
	public String Param1(Model model, @RequestParam int page) {

		
		int maxList = 14;// maxList 表示件数
		List<EmployeeDepartmentDto> record = employeeRepository.getAll();
		int recordsize = record.size();// recordsize 全レコード件数
		double decimalPointRecord = record.size();
		double decimalPointPage = decimalPointRecord / 15;
		int totalPage = (int) Math.ceil(decimalPointPage);// totalPage 全ページ数
		model.addAttribute("TotalPage", totalPage);
		List<EmployeeDepartmentDto> recordFifteen = employeeRepository.findByIdPage(page, maxList, recordsize);
		model.addAttribute("EmployeeList", recordFifteen);// recordFifteen=ページによって異なる、15件のレコード
		model.addAttribute("Page", page);
		model.addAttribute("TotalRecord", recordsize);
		model.addAttribute("TotalPage", totalPage);
		return "employees/index";
	}

//<詳細＞	
	@GetMapping("/employees/{id}/show")

	public String displayView(@PathVariable Long id, Model model) {
		List<EmployeeDepartmentDto> show = employeeRepository.findById(id);
		model.addAttribute("ShowList", show);
		return "employees/show";
	}

}
