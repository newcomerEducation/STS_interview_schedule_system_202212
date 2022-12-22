/*
プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: ScheduleService.javaは登録データの作成、更新、削除を行い、それらのトランザクション処理も請け負うサービスクラスです
更新内容:
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import com.example.demo.model.ScheduleDto;
import com.example.demo.model.ScheduleForm;
import com.example.demo.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;

	@Transactional //この下の処理はすべてトランザクション管理下になり、DB更新で例外が発生したときはロールバックします。
	
	
	
	public void insertScheduleData(ScheduleForm scheform) {
		// 登録データの作成
		List<ScheduleDto> scheduleList = new ArrayList<>();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());// currentTimeで現在の時刻を代入するための記述

		
		scheduleList.add(new ScheduleDto(scheform.getId(), scheform.getEmployee(),
				scheform.getProject_title(),scheform.getPlace(),scheform.getVendor(), 
				scheform.getOrdering_company(),scheform.getSales_employee_id(),scheform.getIntroduce_date(),
				scheform.getProject_overview(),scheform.getNumber_of_interviews(),
				scheform.getPriority(),scheform.getAspiration_situation(),
				scheform.getInterview_situation(),scheform.getPass_fail_status(),
				scheform.getFirst_interview_scheduled_date(),scheform.getFirst_interview_scheduled_time(),
				scheform.getFirst_interview_format(),scheform.getFirst_interview_tool(),
				scheform.getFirst_interview_url(),scheform.getFirst_interview_id(),
				scheform.getFirst_interview_password(),scheform.getFirst_interview_date(),scheform.getFirst_interview_time(),
				scheform.getSecond_interview_scheduled_date(),scheform.getSecond_interview_scheduled_time(),scheform.getSecond_interview_format(),
				scheform.getSecond_interview_tool(),scheform.getSecond_interview_url(),scheform.getSecond_interview_id(), 
				scheform.getSecond_interview_password(),scheform.getSecond_interview_date(),scheform.getSecond_interview_time(),
				scheform.getSupplement(),currentTime,currentTime,0
				));
		
		
		

		scheduleRepository.insertSchedule(scheduleList); // 登録
		}

}
