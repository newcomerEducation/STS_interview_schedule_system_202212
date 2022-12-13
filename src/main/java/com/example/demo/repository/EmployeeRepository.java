/*
プログラム名:　社員情報管理
このファイルの説明: Spring BootとJDBCでmysqlを更新を行うはSQLを実行するリポジトリクラスです
作成者: 福本
作成日: 2022/11/25
更新日: 2022/12/13
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

	// JdbcTemplateのモジュールを使用することによりConnection
	// 等のリソースの取得・破棄といった実際のロジックとは関係のない処理を行う必要がなくなります。
	private final JdbcTemplate jdbcTemplate; // jdbcTempleteはCRUDのメソッドを内臓しているため楽にSQLを読ませることができるようになる?

//＜登録＞
	public void insertEmployee(List<EmployeeDto> employeeList) {
		for (EmployeeDto employee : employeeList) {
			jdbcTemplate.update("INSERT INTO employees"
					+ "(id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id) "
					+ "Values(?,?,?,?,?,?,?,?,?)", employee.getId(), employee.getAdmin_flag(), employee.getCreated_at(),
					employee.getEmployee_code(), employee.getEmployee_name(), employee.getIs_deleted(),
					employee.getPassword(), employee.getUpdated_at(), employee.getDepartment_id());
		}
	}

//<更新>
	public void updateEmployee(List<EmployeeDto> employeeList) {
		for (EmployeeDto employee : employeeList) {
			jdbcTemplate.update(
					"UPDATE employees SET admin_flag = ? , employee_code = ?,employee_name = ? ,is_deleted = ? ,"
							+ "password = ? ,updated_at = ? ,department_id = ?  WHERE id = ?",
					employee.getAdmin_flag(), employee.getEmployee_code(), employee.getEmployee_name(),
					employee.getIs_deleted(), employee.getPassword(), employee.getUpdated_at(),
					employee.getDepartment_id(), employee.getId());
		}
	}

//<更新用、詳細表示>
	public List<EmployeeDto> findByIdEdit(Long id) {
		String sql = "SELECT id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id"
				+ " FROM employees" + " WHERE id =" + id;

		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> edit = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			edit.add(new EmployeeDto((int) employee.get("id"), (int) employee.get("admin_flag"),
					(LocalDateTime) employee.get("created_at"), (String) employee.get("employee_code"),
					(String) employee.get("employee_name"), (int) employee.get("is_deleted"),
					(String) employee.get("password"), (LocalDateTime) employee.get("updated_at"),
					(int) employee.get("department_id")));
		}
		return edit;
	}


//＜削除＞
	public void findByIdDestroy(Long id) {
		jdbcTemplate.update(
				"UPDATE employees SET is_deleted = 1 WHERE id = "+ id );
	
	}

//＜一覧表示＞
	public List<EmployeeDto> getAll() {
		String sql = "SELECT id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id FROM employees ORDER BY id DESC";
		
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> list = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			list.add(new EmployeeDto((int) employee.get("id"), //
					(int) employee.get("admin_flag"), //
					(LocalDateTime) employee.get("created_at"), //
					(String) employee.get("employee_code"),
					(String) employee.get("employee_name"),
					(int) employee.get("is_deleted"), //
					(String) employee.get("password"), //
					(LocalDateTime) employee.get("updated_at"), //
					(int) employee.get("department_id")));
		}
		return list;

	}

//＜詳細表示＞
	public List<EmployeeDto> findById(Long id) {
		String sql = "SELECT id,admin_flag,created_at,employee_code,employee_name,is_deleted,password,updated_at,department_id"
				+ " FROM employees" + " WHERE id =" + id;// 引数 のiｄを使用ししたい

		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> show = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			show.add(new EmployeeDto((int) employee.get("id"), //
					(int) employee.get("admin_flag"), //
					(LocalDateTime) employee.get("created_at"), //
					(String) employee.get("employee_code"), (String) employee.get("employee_name"),
					(int) employee.get("is_deleted"), //
					(String) employee.get("password"), //
					(LocalDateTime) employee.get("updated_at"), //
					(int) employee.get("department_id")));
		}
		return show;
	}



}

	
