/*プログラム名:　社員情報管理 
このファイルの説明: マッピングを司るcontroller部分です.
作成者: 福本
作成日: 2022/11/16
更新日:2022/12/13 
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

//＜登録＞
	@GetMapping("/employees/new")
	// 初回アクセス時の画面を表示でフォームを紐付けています。
	public String displayInsert(Model model) {
		model.addAttribute("employeeForm", new EmployeeForm());
		return "employees/new";
	}

	// @PostMapping」を付与すると画面からPOSTメソッドで送られてきた場合の処理ができる。
	// 引数には「new.html」のactionのフォームで設定したaction属性のパスを設定する
	@PostMapping("/inputAll")
	public String insertUp(EmployeeForm employeeForm) {
		// 登録メソッド等呼び出し
		employeeService.insertData(employeeForm);
		// newにリダイレクト
		return "redirect:/employees/index";
	}

//<更新＞		
	@GetMapping("/employees/{id}/edit")
	public String displayEdit(@PathVariable Long id, Model model) {
		List<EmployeeDto> edit = employeeRepository.findByIdEdit(id);
		model.addAttribute("EditList", edit);
		return "employees/edit";
	}

	@PostMapping("/employees/update")
	public String editUp(EmployeeForm employeeForm) {
		employeeService.updateData(employeeForm);
		return "redirect:/employees/index";
	}

//<削除>	
	@GetMapping("/employees/{id}/destroy")

	public String displayViewDestroy(@PathVariable Long id, Model model) {
		employeeRepository.findByIdDestroy(id);
		return "redirect:/employees/index";
	}

//<一覧>
	@GetMapping("/employees/index")
	public String disp4(Model model) {
		List<EmployeeDto> list = employeeRepository.getAll();
		model.addAttribute("EmployeeList", list);
		return "employees/index";
	}

//<詳細＞	
	@GetMapping("/employees/{id}/show")

	public String displayView(@PathVariable Long id, Model model) {
		List<EmployeeDto> show = employeeRepository.findById(id);
		model.addAttribute("ShowList", show);
		return "employees/show";
	}

}
