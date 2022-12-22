package com.example.demo.model;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data // @Dataは、getterとsetterの記述を省略できます
@AllArgsConstructor // @AllArgsConstructorは、全項目のコンストラクタを生成します
public class DepartmentDto {
	private int id;
	private LocalDateTime created_at;
	private String department_code;
	private String department_name;
	private int is_deleted;
	private LocalDateTime updated_at;
}