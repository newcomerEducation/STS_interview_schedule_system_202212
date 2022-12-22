/*プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: ScheduleDto.javaは面談予定データを格納するmodel部分です。
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor //@AllArgsConstructorは、全項目のコンストラクタを生成します
public class ScheduleDto {

	private Integer id;

	private EmployeeDto employee;

	private String project_title;

	private String place;

	private String vendor;

	private String ordering_company;

	private Integer sales_employee_id;//EmployeeDto sales_employee→Integer sales_employee_id に変更

	private Date introduce_date;

	private String project_overview;

	private Integer number_of_interviews;

	private Integer priority;

	private Integer aspiration_situation;

	private Integer interview_situation;

	private Integer pass_fail_status;

	private Date first_interview_scheduled_date;

	private Time first_interview_scheduled_time;

	private Integer first_interview_format;

	private String first_interview_tool;

	private String first_interview_url;

	private String first_interview_id;

	private String first_interview_password;

	private Date first_interview_date;

	private Time first_interview_time;

	private Date second_interview_scheduled_date;

	private Time second_interview_scheduled_time;

	private Integer second_interview_format;

	private String second_interview_tool;

	private String second_interview_url;

	private String second_interview_id;

	private String second_interview_password;

	private Date second_interview_date;

	private Time second_interview_time;

	private String supplement;

	private Timestamp created_at;

	private Timestamp updated_at;

	private Integer is_deleted;

}
