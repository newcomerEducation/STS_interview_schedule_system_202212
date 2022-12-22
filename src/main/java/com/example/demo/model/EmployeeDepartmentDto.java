package com.example.demo.model;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data //@Dataは、getterとsetterの記述を省略できます
@AllArgsConstructor //@AllArgsConstructorは、全項目のコンストラクタを生成します
public class EmployeeDepartmentDto {
	private int id; //ID　（社員番号）
	private int admin_flag; //管理者フラグ
	private LocalDateTime created_at;//作成時間
    private String employee_code;//ログインID （社員番号）
	private String employee_name;//名前
	private int is_deleted;//削除フラグ
	private String password;//パスワード
	private LocalDateTime updated_at;//更新時間
	private String department_code;
	private String department_name;
}
