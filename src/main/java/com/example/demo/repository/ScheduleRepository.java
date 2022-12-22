/*
プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: ScheduleRepository.javaは面談予定に関連したSQLを発行するrepositoryクラスです
更新内容:
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmployeeDto;
import com.example.demo.model.ScheduleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

	// JdbcTemplateのモジュールを使用することによりConnection
	// 等のリソースの取得・破棄といった実際のロジックとは関係のない処理を行う必要がなくなります。
	private final JdbcTemplate jdbcTemplate; // jdbcTempleteはCRUDのメソッドを内臓しているため楽にSQLを読ませることができるようになる?

	public void insertSchedule(List<ScheduleDto> scheduleList) {
		for (ScheduleDto schedule : scheduleList) {
			jdbcTemplate.update("INSERT INTO schedules"
					+ "(id,aspiration_situation,created_at,first_interview_date,first_interview_format,first_interview_id,first_interview_password,first_interview_scheduled_date,first_interview_scheduled_time,first_interview_time,first_interview_tool,first_interview_url,interview_situation,introduce_date,is_deleted,number_of_interviews,ordering_company,pass_fail_status,place,priority,project_overview,project_title,second_interview_date,second_interview_format,second_interview_id,second_interview_password,second_interview_scheduled_date,second_interview_scheduled_time,second_interview_time,second_interview_tool,second_interview_url,supplement,updated_at,vendor,employee_id,sales_employee_id) "
					+ "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", // プレースホルダでSQLインジェクション対策
					schedule.getId(), schedule.getAspiration_situation(), schedule.getCreated_at(),
					schedule.getFirst_interview_date(), schedule.getFirst_interview_format(),
					schedule.getFirst_interview_id(), schedule.getFirst_interview_password(),
					schedule.getFirst_interview_scheduled_date(), schedule.getFirst_interview_scheduled_time(),
					schedule.getFirst_interview_time(), schedule.getFirst_interview_tool(),
					schedule.getFirst_interview_url(), schedule.getInterview_situation(), schedule.getIntroduce_date(),
					schedule.getIs_deleted(), schedule.getNumber_of_interviews(), schedule.getOrdering_company(),
					schedule.getPass_fail_status(), schedule.getPlace(), schedule.getPriority(),
					schedule.getProject_overview(), schedule.getProject_title(), schedule.getSecond_interview_date(),
					schedule.getSecond_interview_format(), schedule.getSecond_interview_id(),
					schedule.getSecond_interview_password(), schedule.getSecond_interview_scheduled_date(),
					schedule.getSecond_interview_scheduled_time(), schedule.getSecond_interview_time(),
					schedule.getSecond_interview_tool(), schedule.getSecond_interview_url(), schedule.getSupplement(),
					schedule.getUpdated_at(), schedule.getVendor(), schedule.getEmployee(),
					schedule.getSales_employee_id());

		}
	}

	// 面談予定登録画面で営業担当のプルダウン表示を行うために削除されていない部署コードが営業の社員情報をselectしてリストに格納後、コントローラーに送る。
	public List<EmployeeDto> getAllSalesEmployees() {
		// SQL文を作成
		// String sql = "SELECT e FROM Employee WHERE e.is_deleted = 0 AND e.department.department_name like '営業%' ORDER BY e.id DESC";
		String sql = "SELECT * FROM employees INNER JOIN departments ON employees.department_id = departments.id WHERE employees.is_deleted = 0 AND department_name like '営業%' ORDER BY employees.id DESC";

		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDto> salesList = new ArrayList<>();
		// salesListオブジェクトに格納する。
		// 1行のデータを1インスタンスとしてリストにセット
		for (Map<String, Object> employee : employeeList) {
			salesList.add(new EmployeeDto((int) employee.get("id"), (int) employee.get("admin_flag"),
					(LocalDateTime) employee.get("created_at"), (String) employee.get("employee_code"),
					(String) employee.get("employee_name"), (int) employee.get("is_deleted"),
					(String) employee.get("password"), (LocalDateTime) employee.get("updated_at"),
					(int) employee.get("department_id")));
		}
		// salesListとしてscheduleController.javaに返す
		return salesList;

	}


}
