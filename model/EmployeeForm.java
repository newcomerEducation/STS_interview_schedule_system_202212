/*プログラム名:　社員情報管理
このファイルの説明:社員データを格納するmodel部分です
作成者: 福本
作成日: 2022/12/2
*/

package com.example.demo.model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EmployeeForm {
	private int id; //ID　（社員番号）                           
	private int admin_flag; //管理者フラグ
	private LocalDateTime created_at;//作成時間
    private String employee_code;//ログインID （社員番号）
	private String employee_name;//名前                 
	private int is_deleted;//削除フラグ
	private String password;//パスワード
	private LocalDateTime updated_at;//更新時間
	private int department_id;//部署ID
}
	

