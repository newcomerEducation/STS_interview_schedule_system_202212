/*
プログラム名:　社員情報管理
このファイルの説明: Dtoクラスへ値を格納
作成者: 福本
作成日: 2022/11/16
更新日: 2022/12/20
*/
package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.EmployeeDto;
import com.example.demo.model.EmployeeForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.utils.EncryptUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	@Transactional // この下の処理はすべてトランザクション管理下になり、DB更新で例外が発生したときはロールバックします。

	

// <登録>
	public void insertData(EmployeeForm eform) {
		List<EmployeeDto> employeeList = new ArrayList<>();
		LocalDateTime dateTimeNow = LocalDateTime.now();
		// パスワードハッシュ化　インスタンス化部分
		String pass_hash = EncryptUtil.getPasswordEncrypt(eform.getPassword(), "null");
		employeeList.add(new EmployeeDto(eform.getId(), eform.getAdmin_flag(), dateTimeNow, eform.getEmployee_code(),
				eform.getEmployee_name(), eform.getIs_deleted(), pass_hash, dateTimeNow, eform.getDepartment_id()));
		employeeRepository.insertEmployee(employeeList); 
		
	}
// <更新>
	public void updateData(EmployeeForm eform) {
		List<EmployeeDto> employeeList = new ArrayList<>();
		LocalDateTime dateTimeNow = LocalDateTime.now();
		String pass_hash = EncryptUtil.getPasswordEncrypt(eform.getPassword(), "null");
		employeeList.add(new EmployeeDto(eform.getId(), eform.getAdmin_flag(), eform.getCreated_at(),
				eform.getEmployee_code(), eform.getEmployee_name(), eform.getIs_deleted(), pass_hash,
				dateTimeNow, eform.getDepartment_id()));
		employeeRepository.updateEmployee(employeeList); 
		
	}
}
