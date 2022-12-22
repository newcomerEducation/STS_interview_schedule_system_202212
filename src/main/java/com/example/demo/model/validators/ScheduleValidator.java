/*プログラム名:　面談予定管理システム
プログラム説明: Spring BootとJDBCでmysqlを更新し、面談情報を管理します
このファイルの説明: SchedulesValidator.javaは面談予定登録時のValidator部分です
更新内容:
作成者: 赤坂
作成日: 2022/11/22
更新日: 2022/12/19
*/
package com.example.demo.model.validators;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.ScheduleForm;

public class ScheduleValidator {
    public static List<String> validate(ScheduleForm scheduleForm) {
        List<String> errors = new ArrayList<String>();

        String project_title_error = validateProjectTitle(scheduleForm.getProject_title());
        if(!project_title_error.equals("")) {
            errors.add(project_title_error);
        }

        String ordering_company_error = validateOrderingCompany(scheduleForm.getOrdering_company());
        if(!ordering_company_error.equals("")) {
            errors.add(ordering_company_error);
        }

        return errors;
    }

    private static String validateProjectTitle(String project_title) {
        if(project_title == null || project_title.equals("")) {
            return "・案件名を入力してください";
        }
        return "";
    }

    private static String validateOrderingCompany(String ordering_company) {
        if(ordering_company == null || ordering_company.equals("")) {
            return "・上位会社（発注元企業）を入力してください";
        }
        return "";
    }

}
