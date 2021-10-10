package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "help":
				Menu.displaymenu();
				break;
			
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				System.out.println("제목순 정렬 완료\n");
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				System.out.println("제목 역순 정렬 완료\n");
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				System.out.println("수정 날짜순 정렬 완료\n");
				break;
				
			case "find":
				String word = sc.nextLine().trim();
				TodoUtil.findKeyword(l, word);
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("help에 나와있는 명령어를 입력하세요.");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
