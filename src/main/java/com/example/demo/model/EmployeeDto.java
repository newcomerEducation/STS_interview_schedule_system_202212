/*プログラム名:　社員情報管理
プログラム説明: Spring BootとJDBCでmysqlを更新し、社員情報（id,name,rubi,created_at,updated_at）を管理します
このファイルの説明: Emloyee.javaは社員データを格納するmodel部分です
作成者: 赤坂
作成日: 2022/11/16
*/
package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //@Dataは、getterとsetterの記述を省略できます
@AllArgsConstructor //@AllArgsConstructorは、全項目のコンストラクタを生成します
public class EmployeeDto {
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
