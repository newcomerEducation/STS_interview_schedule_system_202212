package com.example.demo.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.LoginDto;
import com.example.demo.repository.LoginRepository;
import com.example.demo.utils.EncryptUtil;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class LoginController {
	private final LoginRepository loginRepository;
	// ＜ログイン＞
	@GetMapping("/login")
	public String displayLogin(Model model) {
		return "login/login";
	}
	@PostMapping("/login")
	public String login(@RequestParam("employee_code") String str_employee_code,
			@RequestParam("password") String str_password) {
		//HTMLから受け取ったパスワードをハッシュ化
		String pass_hash = EncryptUtil.getPasswordEncrypt(str_password, "null");
		// リポジトリのfindByEmployeeCodeメソッドから社員番号とパスワードを受け取る
		List<LoginDto> logindata = loginRepository.findByEmployeeCode(str_employee_code);
		//loginCheckにlist型の0番目を取得（一件のみしかデータがないので0番のみの取得で可）
		LoginDto loginCheck = logindata.get(0);
		// データベースの社員番号とパスワードと入力された社員番号とパスワードの正誤を判定する
		if (loginCheck.getEmployee_code().equals(str_employee_code) && loginCheck.getPassword().equals(pass_hash)) {
			return "redirect:/";
		} else {
			return "redirect:/login/login";
		}
	}
}