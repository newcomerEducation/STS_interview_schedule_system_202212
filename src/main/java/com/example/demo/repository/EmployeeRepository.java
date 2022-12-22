/*
プログラム名:　社員情報管理
このファイルの説明: Spring BootとJDBCでmysqlを更新を行うはSQLを実行するリポジトリクラスです
作成者: 福本
作成日: 2022/11/25
更新日: 2022/12/13
*/
package com.example.demo.repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.DepartmentDto;
import com.example.demo.model.EmployeeDepartmentDto;
import com.example.demo.model.EmployeeDto;
import lombok.RequiredArgsConstructor;


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
//<登録詳細用>	
	public List<DepartmentDto> getAllDepartments() {
		String sql = "SELECT * FROM departments ORDER BY id DESC;";		
		List<Map<String, Object>> departmentList = jdbcTemplate.queryForList(sql);
		List<DepartmentDto> list = new ArrayList<>();
		for (Map<String, Object> department : departmentList) {
			list.add(new DepartmentDto((int) department.get("id"), //
					(LocalDateTime) department.get("created_at"), //
					(String) department.get("department_code"),
					(String) department.get("department_name"),
					(int) department.get("is_deleted"), //
					(LocalDateTime) department.get("updated_at"))); //
		}
		return list;
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


//	＜全件習得用＞
	public List<EmployeeDepartmentDto> getAll() {
		String sql = "SELECT emp.id id,"
				+ "emp.admin_flag admin_flag,"
				+ "emp.created_at created_at,"
				+ "emp.employee_code employee_code,"
				+ "emp.employee_name employee_name,"
				+ "emp.is_deleted is_deleted,"
				+ "emp.password password,"
				+ "emp.updated_at updated_at,"
				+ "dpt.department_code department_code,"
				+ "dpt.department_name department_name"
				+ " FROM employees emp INNER JOIN departments dpt "
				+ " ON emp.department_id=dpt.id"
				+ " ORDER BY emp.id DESC";
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDepartmentDto> record = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			record.add(new EmployeeDepartmentDto((int) employee.get("id"), //
					(int) employee.get("admin_flag"), //
					(LocalDateTime) employee.get("created_at"), //
					(String) employee.get("employee_code"), (String) employee.get("employee_name"),
					(int) employee.get("is_deleted"), //
					(String) employee.get("password"), //
					(LocalDateTime) employee.get("updated_at"),
					(String) employee.get("department_code"),
					(String) employee.get("department_name")
					));
		}
		return record;
	}
	//＜一覧表示＞※、最新のレコード～（最新のレコード-15）をセレクト
	public List<EmployeeDepartmentDto> getFirst(int recordsize) {
		int lowpage = recordsize -15;
		
		String sql = "SELECT emp.id id,"
				+ "emp.admin_flag admin_flag,"
				+ "emp.created_at created_at,"
				+ "emp.employee_code employee_code,"
				+ "emp.employee_name employee_name,"
				+ "emp.is_deleted is_deleted,"
				+ "emp.password password,"
				+ "emp.updated_at updated_at,"
				+ "dpt.department_code department_code,"
				+ "dpt.department_name department_name"
				+ " FROM employees emp INNER JOIN departments dpt "
				+ " ON emp.department_id=dpt.id"
				+ " WHERE emp.id BETWEEN "+ lowpage+" AND "+recordsize
				+ " ORDER BY emp.id DESC";
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDepartmentDto> list = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			list.add(new EmployeeDepartmentDto((int) employee.get("id"), //
					(int) employee.get("admin_flag"), //
					(LocalDateTime) employee.get("created_at"), //
					(String) employee.get("employee_code"), (String) employee.get("employee_name"),
					(int) employee.get("is_deleted"), //
					(String) employee.get("password"), //
					(LocalDateTime) employee.get("updated_at"),
					(String) employee.get("department_code"),
					(String) employee.get("department_name")
					));
		}
		return list;
	}
	
//	<ページング用＞※パラメーターに該当するレコードをセレクト
		public List<EmployeeDepartmentDto> findByIdPage(int page,int maxList,int recordsize) {
			
           
           
           int maxListPage = maxList*(page-1);
           int lowListPage = maxList* page;
           int startList = recordsize - maxListPage;
           int endList= recordsize - lowListPage ;
           if(page >= 2){
        	   startList = startList - (page-1);
        	   endList = endList -(page-1);
        	   }
           
           
			String sql = "SELECT emp.id id,"
					+ "emp.admin_flag admin_flag,"
					+ "emp.created_at created_at,"
					+ "emp.employee_code employee_code,"
					+ "emp.employee_name employee_name,"
					+ "emp.is_deleted is_deleted,"
					+ "emp.password password,"
					+ "emp.updated_at updated_at,"
					+ "dpt.department_code department_code,"
					+ "dpt.department_name department_name"
					+ " FROM employees emp INNER JOIN departments dpt"
					+ " ON emp.department_id=dpt.id"
					+ " WHERE emp.id BETWEEN "+ endList+" AND "+startList
					+ " ORDER BY emp.id DESC";
			List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
			List<EmployeeDepartmentDto> Page = new ArrayList<>();
			for (Map<String, Object> employee : employeeList) {
				Page.add(new EmployeeDepartmentDto((int) employee.get("id"), //
						(int) employee.get("admin_flag"), //
						(LocalDateTime) employee.get("created_at"), //
						(String) employee.get("employee_code"), (String) employee.get("employee_name"),
						(int) employee.get("is_deleted"), //
						(String) employee.get("password"), //
						(LocalDateTime) employee.get("updated_at"),
						(String) employee.get("department_code"),
						(String) employee.get("department_name")
						));
			}
			return Page;
		}

	


//＜詳細表示＞
	public List<EmployeeDepartmentDto> findById(Long id) {

		String sql ="SELECT emp.id id,"
				+ " emp.admin_flag admin_flag,"
				+ "emp.created_at created_at,"
				+ "emp.employee_code employee_code,"
				+ "emp.employee_name employee_name,"
				+ "emp.is_deleted is_deleted,"
				+ "emp.password password,"
				+ "emp.updated_at updated_at,"
				+ "dpt.department_code department_code,"
				+ "dpt.department_name department_name "
				+ "FROM employees emp INNER JOIN departments dpt "
				+ "ON emp.department_id=dpt.id  WHERE emp.id ="+id;
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		List<EmployeeDepartmentDto> show = new ArrayList<>();
		for (Map<String, Object> employee : employeeList) {
			show.add(new EmployeeDepartmentDto((int) employee.get("id"), //
					(int) employee.get("admin_flag"), //
					(LocalDateTime) employee.get("created_at"), //
					(String) employee.get("employee_code"), (String) employee.get("employee_name"),
					(int) employee.get("is_deleted"), //
					(String) employee.get("password"), //
					(LocalDateTime) employee.get("updated_at"), //
					(String) employee.get("department_code"),
					(String) employee.get("department_name")
					));
		}
		return show;
	}



}

	
