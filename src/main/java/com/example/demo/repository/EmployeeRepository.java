/*
プログラム名:　社員情報管理
プログラム説明: Spring BootとJDBCでmysqlを更新し、社員情報（id,name,rubi,created_at,updated_at）を管理します
このファイルの説明: EmployeeRepository.javaはSQLを実行するレポジトリクラスです
作成者: 赤坂
作成日: 2022/11/16
*/
package com.example.demo.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmployeeDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
	
	//  JdbcTemplateのモジュールを使用することによりConnection 等のリソースの取得・破棄といった実際のロジックとは関係のない処理を行う必要がなくなります。
	private final JdbcTemplate jdbcTemplate; // jdbcTempleteはCRUDのメソッドを内臓しているため楽にSQLを読ませることができるようになる?
	
	
	public void insertEmployee(List<EmployeeDto> employeeList) {
		for (EmployeeDto employee : employeeList) {
			jdbcTemplate.update(
			"INSERT INTO employees"
					+ "(id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id) "
					+ "Values(?,?,?,?,?,?,?,?,?)",
					employee.getId(), employee.getAdmin_flag(),employee.getCreated_at(),
					employee.getEmployee_code(),employee.getEmployee_name(),employee.getIs_deleted(),
					employee.getPassword(),employee.getUpdated_at(),employee.getDepartment_id());
		}
	}
//	"INSERT INTO employee"
//	+ "(id,namae,myoji,sex,status_deleted,created_at,updated_at) "
//	+ "Values(?,?,?,?,?,?,?)",
	//
//	public void updateEmployee(List<EmployeeDto> employeeList) {
//		for (EmployeeDto employee : employeeList) {
//		jdbcTemplate.update(
//				"UPDATE employee SET namae = ? ,myoji = ?,sex = ?,updated_at = ? WHERE id = ?",
//				employee.getNamae(),employee.getMyoji(),employee.getSex(),employee.getUpdatedAt(),employee.getId());
//		}
//	}
	//public void updateEmployee() {
	//	jdbcTemplate.update(
	//			"UPDATE employee SET namae = ? ,myoji = ?,sex = ?,updated_at = ? where id = ?",
	//			"testNamae", "testMyoji","testSex","testUpdated_at", 2);
	//}

//	public void deleteEmployee(EmployeeDto employeeList) {
//		jdbcTemplate.update("DELETE FROM employee where id = ?" ,
//				employeeList.getId());
//	}
	
	// 一覧表示機能のメソッドgetAll()
	public List<EmployeeDto> getAll() {
		String sql = "SELECT id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id FROM employees";
		//String sql = "SELECT employee_code,employee_name,department_id FROM employees";
		
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> list = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			list.add(new EmployeeDto(
					(int) employee.get("id"),           //
					(int) employee.get("admin_flag"),   //
					(LocalDateTime) employee.get("created_at"),  //
					(String) employee.get("employee_code"),  
					(String) employee.get("employee_name"),
					(int) employee.get("is_deleted"),  //
					(String) employee.get("password"),     //
					(LocalDateTime) employee.get("updated_at"),  //
					(int) employee.get("department_id")));
		}
		return list;
	}
/**	public List<EmployeeDto> findById(int id) {
		String sql = "SELECT id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id FROM employees";
		//String sql = "SELECT employee_code,employee_name,department_id FROM employees";
		
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> aaa = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			aaa.add(new EmployeeDto(
					(int) employee.get("id"),           //
					(int) employee.get("admin_flag"),   //
					(LocalDateTime) employee.get("created_at"),  //
					(String) employee.get("employee_code"),  
					(String) employee.get("employee_name"),
					(int) employee.get("is_deleted"),  //
					(String) employee.get("password"),     //
					(LocalDateTime) employee.get("updated_at"),  //
					(int) employee.get("department_id")));
		}
		return aaa;
	

}*/
	}

	
