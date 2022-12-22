package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor //@AllArgsConstructorは、全項目のコンストラクタを生成します
public class LoginDto {
	private String employee_code;//ログインID （社員番号）
	private String password;//パスワード
}
