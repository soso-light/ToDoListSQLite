package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println(" add\t : 할 일 추가");
        System.out.println(" del\t : 할 일 삭제");
        System.out.println(" edit\t : 할 일 수정");
        System.out.println(" ls\t : 할 일 목록");
        System.out.println(" ls_name_asc\t : 제목순 정렬하기");
        System.out.println(" ls_name_desc\t : 제목 역순 정렬하기");
        System.out.println(" ls_date\t : 수정 날짜순 정렬하기");
        System.out.println(" find '??'\t : 제목과 내용에서 ?? 찾기");
        System.out.println(" exit\t\t : 종료");
    }
    
    public static void prompt() {
        System.out.println("명령어 입력 >> ");
    }
}
