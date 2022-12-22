package com.example.demo.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.LoginDto;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class LoginRepository {
	private final JdbcTemplate jdbcTemplate; // jdbcTempleteはCRUDのメソッドを内臓しているため楽にSQLを読ませることができるようになる?
	//データベースから社員番号が一致するデータの社員番号とパスワードを取得するメソッド
		public List<LoginDto> findByEmployeeCode(String employee_code){
			String sql="SELECT employee_code,password from employees WHERE employee_code = "+employee_code;
			
			List<Map<String, Object>> loginList = jdbcTemplate.queryForList(sql);
			List<LoginDto> logindata = new ArrayList<>();
			for (Map<String, Object> login : loginList) {
				logindata.add(new LoginDto((String) login.get("employee_code"),
						(String) login.get("password")
						));
			}
			return logindata;
		}
}