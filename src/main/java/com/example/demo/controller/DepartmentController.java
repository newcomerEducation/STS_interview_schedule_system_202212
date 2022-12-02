/*プログラム名:　社員情報管理
プログラム説明: Spring BootとJDBCでmysqlを更新し、社員情報（id,name,rubi,created_at,updated_at）を管理します
このファイルの説明: MainController.javaはマッピングを司るcontroller部分です
更新内容:統合版にオブジェクトで値を受け渡す機能を追加
作成者: 赤坂
作成日: 2022/11/16
更新日:2022/11/22
*/
package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.EmployeeDto;
import com.example.demo.model.EmployeeForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import lombok.RequiredArgsConstructor;


public class DepartmentController {}

	

	


