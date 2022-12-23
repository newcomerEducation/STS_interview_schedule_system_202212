/*
プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: SchedulesCreateController.javaは面談予定登録に関連したマッピングを司るcontroller部分です
更新内容:
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.EmployeeDto;
import com.example.demo.model.ScheduleForm;
import com.example.demo.model.validators.ScheduleValidator;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SchedulesCreateController {
	// _tokenというログイン情報を受け取るため？の変数を宣言
	// String _token = request.getParameter("_token");

	private final ScheduleService scheduleService;
	private final ScheduleRepository scheduleRepository;
	ScheduleForm scheduleForm = new ScheduleForm();
	LocalDateTime dateTimeNow = LocalDateTime.now();

	@GetMapping("/schedules/new")
	// 初回アクセス時の画面を表示でフォームを紐付けています。
	public String disp1(Model model) {
		model.addAttribute("scheduleForm", new ScheduleForm());

		// 現在の日付を初期値に設定するために画面へ送る
		scheduleForm.setIntroduce_date(new Date(System.currentTimeMillis()));
		model.addAttribute("scheduleForm", scheduleForm);

		// 営業担当のプルダウン表示
		List<EmployeeDto> salesList = scheduleRepository.getAllSalesEmployees();
		model.addAttribute("salesList", salesList);// new.htmlの担当営業欄に渡す

		return "schedules/new";
	}

	@RequestMapping(value = "/createSchedules", method = RequestMethod.POST)
	// @RequestParam("name属性") String フィールドの変数名←格納先の変数名。入力したものを@requestparamに入れる

	// Model modelを入れてのちにformの内容を画面に送られるように。RedirectAttributes
	// redirectAttributesを入れる（フラッシュスコープを使うためのクラス）。
	public String postMethod(@RequestParam("project_title") String str_project_title,
			@RequestParam("place") String str_place, @RequestParam("vendor") String str_vendor,
			@RequestParam("ordering_company") String str_ordering_company,
			@RequestParam("sales_employee_id") String str_sales_employee_id,
			@RequestParam("introduce_date") String str_introduce_date,
			@RequestParam("project_overview") String str_project_overview,
			@RequestParam("number_of_interviews") String str_number_of_interviews,
			@RequestParam("priority") String str_priority,
			@RequestParam("aspiration_situation") String str_aspiration_situation,
			@RequestParam("interview_situation") String str_interview_situation,
			@RequestParam("pass_fail_status") String str_pass_fail_status,
			@RequestParam("first_interview_scheduled_date") String str_first_interview_scheduled_date,
			@RequestParam("first_interview_scheduled_time") String str_first_interview_scheduled_time,
			@RequestParam("first_interview_format") String str_first_interview_format,
			@RequestParam("first_interview_tool") String str_first_interview_tool,
			@RequestParam("first_interview_url") String str_first_interview_url,
			@RequestParam("first_interview_id") String str_first_interview_id,
			@RequestParam("first_interview_password") String str_first_interview_password,
			@RequestParam("first_interview_date") String str_first_interview_date,
			@RequestParam("first_interview_time") String str_first_interview_time,
			@RequestParam("second_interview_scheduled_date") String str_second_interview_scheduled_date,
			@RequestParam("second_interview_scheduled_time") String str_second_interview_scheduled_time,
			@RequestParam("second_interview_format") String str_second_interview_format,
			@RequestParam("second_interview_tool") String str_second_interview_tool,
			@RequestParam("second_interview_url") String str_second_interview_url,
			@RequestParam("second_interview_id") String str_second_interview_id,
			@RequestParam("second_interview_password") String str_second_interview_password,
			@RequestParam("second_interview_date") String str_second_interview_date,
			@RequestParam("second_interview_time") String str_second_interview_time,
			@RequestParam("supplement") String str_supplement, Model model, RedirectAttributes redirectAttributes) {

		// HTMLから渡された値はすべてString型なので、DateやTimeなどの型を持つフィールド変数に格納する前に、いくつかの変数をStringからキャストする処理を行う

		// intに型変換
		int int_sales_employee_id = Integer.parseInt(str_sales_employee_id);

		// Date型に型変換Date.valueOf（変換するString変数）
		Date introduce_date = Date.valueOf(str_introduce_date);

		// int型に空文字やnullが入れられないので未入力時は0を入れるように処理。またintへ型変換
		int int_number_of_interviews = 0;
		if (str_number_of_interviews.equals("")) {
			int_number_of_interviews = 0;
		} else {
			int_number_of_interviews = Integer.parseInt(str_number_of_interviews);
		}

		// int型に空文字やnullが入れられないので未入力時は1を入れるように処理。またintへ型変換
		int int_priority = 1;
		if (str_priority.equals("")) {
			int_priority = 1;
		} else {
			int_priority = Integer.parseInt(str_priority);
		}

		// セレクトボックスは数字で格納するのでintに変換
		int int_aspiration_situation = Integer.parseInt(str_aspiration_situation);

		// セレクトボックスは数字で格納するのでintに変換
		int int_interview_situation = Integer.parseInt(str_interview_situation);

		// セレクトボックスは数字で格納するのでintに変換
		int int_pass_fail_status = Integer.parseInt(str_pass_fail_status);

		// HTMLからもってきた値はDate型に格納できないので型を変換する Date.valueOf（変換するString変数）
		// springMVCは勝手に未入力は空文字で送るが、Date型は空文字拒否でNULLを受け入れるのでNULLを入れる作業が必要
		Date first_interview_scheduled_date = null;

		if (str_first_interview_scheduled_date.equals("")) {
			first_interview_scheduled_date = null;
		} else {
			first_interview_scheduled_date = Date.valueOf(str_first_interview_scheduled_date);
		}

		// HTMLからもってきた値はTime型に格納できないので型を変換する Time.valueOf（変換するString変数）
		// springMVCは勝手に未入力は空文字で送るが、time型は空文字拒否でNULLを受け入れるのでNULLを入れる作業が必要
		// elseif文が無いとバリデーション適用後6文字のデータに再度:00をつけてしまうため注意
		Time first_interview_scheduled_time = null;
		
		if (str_first_interview_scheduled_time.equals("")) {
			first_interview_scheduled_time = null;
		} else if (str_first_interview_scheduled_time.length() == 8) {
			first_interview_scheduled_time = Time.valueOf(str_first_interview_scheduled_time);
		} else {
			first_interview_scheduled_time = Time.valueOf(str_first_interview_scheduled_time + ":00"); // Time型の桁数を追加しないと格納できないから文字列連結。
		}

		// セレクトボックスは数字で格納するのでintに変換
		int int_first_interview_format = Integer.parseInt(str_first_interview_format);

		// Date型に型変換Date.valueOf（変換するString変数）とNULLを入れる処理
		Date first_interview_date = null;

		if (str_first_interview_date.equals("")) {
			first_interview_date = null;
		} else {
			first_interview_date = Date.valueOf(str_first_interview_date);
		}

		// Time型に型変換 Time.valueOf（変換するString変数）とNULLを入れる処理
		// elseif文が無いとバリデーション適用後6文字のデータに再度:00をつけてしまうため注意
		Time first_interview_time = null;
	
		if (str_first_interview_time.equals("")) {
			first_interview_time = null;
		} else if (str_first_interview_time.length() == 8) {
			first_interview_time = Time.valueOf(str_first_interview_time);
		} else {
			first_interview_time = Time.valueOf(str_first_interview_time + ":00"); // Time型の桁数を追加しないと格納できないから文字列連結。
		}

		// Date型に型変換Date.valueOf（変換するString変数）とNULLを入れる処理
		Date second_interview_scheduled_date = null;

		if (str_second_interview_scheduled_date.equals("")) {
			second_interview_scheduled_date = null;
		} else {
			second_interview_scheduled_date = Date.valueOf(str_second_interview_scheduled_date);
		}

		// Time型に型変換 Time.valueOf（変換するString変数）とNULLを入れる処理
		// elseif文が無いとバリデーション適用後6文字のデータに再度:00をつけてしまうため注意
		Time second_interview_scheduled_time = null;
		
		if (str_second_interview_scheduled_time.equals("")) {
			second_interview_scheduled_time = null;
		} else if (str_second_interview_scheduled_time.length() == 8) {
			second_interview_scheduled_time = Time.valueOf(str_second_interview_scheduled_time);
		} else {
			second_interview_scheduled_time = Time.valueOf(str_second_interview_scheduled_time + ":00"); // Time型の桁数を追加しないと格納できないから文字列連結。
		}

		// セレクトボックスは数字で格納するのでintに変換
		int int_second_interview_format = Integer.parseInt(str_second_interview_format);

		// Date型に型変換Date.valueOf（変換するString変数）とNULLを入れる処理
		Date second_interview_date = null;

		if (str_second_interview_date.equals("")) {
			second_interview_date = null;
		} else {
			second_interview_date = Date.valueOf(str_second_interview_date);
		}

		// Time型に型変換 Time.valueOf（変換するString変数）とNULLを入れる処理
		// elseif文が無いとバリデーション適用後6文字のデータに再度:00をつけてしまうため注意
		Time second_interview_time = null;
		
		if (str_second_interview_time.equals("")) {
			second_interview_time = null;
		} else if (str_second_interview_time.length() == 8) {
			second_interview_time = Time.valueOf(str_second_interview_time);
		} else {
			second_interview_time = Time.valueOf(str_second_interview_time + ":00"); // Time型の桁数を追加しないと格納できないから文字列連結。
		}

		// ScheduleFormクラスにsetする
		scheduleForm.setProject_title(str_project_title);
		scheduleForm.setPlace(str_place);
		scheduleForm.setVendor(str_vendor);
		scheduleForm.setOrdering_company(str_ordering_company);
		scheduleForm.setSales_employee_id(int_sales_employee_id);// intにキャストしたもの入れる
		scheduleForm.setIntroduce_date(introduce_date);
		scheduleForm.setProject_overview(str_project_overview);
		scheduleForm.setNumber_of_interviews(int_number_of_interviews);// intにキャストしたもの入れる
		scheduleForm.setPriority(int_priority);// intにキャストしたもの入れる
		scheduleForm.setAspiration_situation(int_aspiration_situation);// intにキャストしたもの入れる
		scheduleForm.setInterview_situation(int_interview_situation);// intにキャストしたもの入れる
		scheduleForm.setPass_fail_status(int_pass_fail_status);// intにキャストしたもの入れる
		scheduleForm.setFirst_interview_scheduled_date(first_interview_scheduled_date);// Dateにキャストしたもの入れる
		scheduleForm.setFirst_interview_scheduled_time(first_interview_scheduled_time);// Timeにキャストしたもの入れる
		scheduleForm.setFirst_interview_format(int_first_interview_format);// intにキャストしたもの入れる
		scheduleForm.setFirst_interview_tool(str_first_interview_tool);
		scheduleForm.setFirst_interview_url(str_first_interview_url);
		scheduleForm.setFirst_interview_id(str_first_interview_id);
		scheduleForm.setFirst_interview_password(str_first_interview_password);
		scheduleForm.setFirst_interview_date(first_interview_date);// Dateにキャストしたもの入れる
		scheduleForm.setFirst_interview_time(first_interview_time);// Timeにキャストしたもの入れる
		scheduleForm.setSecond_interview_scheduled_date(second_interview_scheduled_date);// Dateにキャストしたもの入れる
		scheduleForm.setSecond_interview_scheduled_time(second_interview_scheduled_time);// Timeにキャストしたもの入れる
		scheduleForm.setSecond_interview_format(int_second_interview_format);// intにキャストしたもの入れる
		scheduleForm.setSecond_interview_tool(str_second_interview_tool);
		scheduleForm.setSecond_interview_url(str_second_interview_url);
		scheduleForm.setSecond_interview_id(str_second_interview_id);
		scheduleForm.setSecond_interview_password(str_second_interview_password);
		scheduleForm.setSecond_interview_date(second_interview_date);// Dateにキャストしたもの入れる
		scheduleForm.setSecond_interview_time(second_interview_time);// Timeにキャストしたもの入れる
		scheduleForm.setSupplement(str_supplement);

		// 営業担当のプルダウン表示
		List<EmployeeDto> salesList = scheduleRepository.getAllSalesEmployees();
		model.addAttribute("salesList", salesList);// new.htmlの担当営業欄に渡す

		// 入力値チェック
		// ScheduleForm.javaに入った入力値を引数にScheduleValidator.javaのvalidateメソッドを呼び出す。
		List<String> errors = ScheduleValidator.validate(scheduleForm);// errorsにはエラーメッセージが格納

		if (errors.size() > 0) {
			model.addAttribute("errors", errors);// new.htmlにエラーメッセージを渡す
			model.addAttribute("scheduleForm", scheduleForm);// new.htmlに登録フォームにユーザーが入力していた値を渡す(入力値保持)

			return "/schedules/new";// エラーがあれば面談新規登録画面に戻す
		} else {
			// 登録メソッド呼び出し
			scheduleService.insertScheduleData(scheduleForm);

			// 実際にはセッションに値が格納される。登録完了後index.htmlでメッセージを表示
			redirectAttributes.addFlashAttribute("flush", "登録が完了しました。");
			// 面談一覧画面(index.html)に遷移。リダイレクト
			return "redirect:/schedules/index";

		}
	}

}
