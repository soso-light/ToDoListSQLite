package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Locale.Category;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "< 할 일 추가 >\n"
				+ "분류: ");
		category = sc.next();
		
		System.out.println("제목 : ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다.");
			return;
		}
		sc.nextLine();
		System.out.println("내용 : ");
		desc = sc.nextLine();
		
		System.out.println("마감일(년/월/일): ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(list.addItem(t)>0) {
			System.out.println("할 일 목록에 추가되었습니다.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "< 할 일 삭제 >\n"
				+ "삭제할 할 일의 번호을 입력하세요 : ");
		int num = sc.nextInt();
		/*
		if(num > l.size()) {
			System.out.println("존재하지 않는 번호입니다."); 
			return;
		}
*/
			if (l.deleteItem(num)>0) {
				System.out.println("할 일 목록에서 삭제되었습니다.\n");
			}
		
	}

	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "< 할 일 수정 >\n"
				+ "수정할 할 일의 번호를 입력하세요 : ");
		int num = sc.nextInt();
/*
		if(num > l.size()) {
			System.out.println("존재하지 않는 번호입니다."); 
			return;
		}
	*/	
		System.out.println("할 일의 새 카테고리를 입력하세요 : ");
		new_category = sc.next().trim();
		
		System.out.println("할 일의 새 제목을 입력하세요 : ");
		new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		sc.nextLine();
		System.out.println("새로운 내용을 입력하세요 : ");
		new_desc = sc.nextLine().trim();
		
		System.out.println("할 일의 새 마감일을 입력하세요(년/월/일): ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_category,new_title, new_desc, new_due_date);
		t.setId(num);
		if(l.editItem(t)>0) {
				System.out.println("할 일이 수정되었습니다.\n");
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("<할 일 목록>");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void loadList(TodoList l, String filename) {
		File file = new File(filename); // File객체 생성 
		if(file.exists()){ 
			BufferedReader r;
			int i=0;
			try {
				r = new BufferedReader(new FileReader(file));
			 	String line = null; 
			 	try {
					while ((line = r.readLine()) != null){ 
						StringTokenizer st = new StringTokenizer(line, "##");
						String category = st.nextToken();
						String title = st.nextToken();
						String desc = st.nextToken();
						String due_date = st.nextToken();
						String current_date = st.nextToken();
						TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
						l.addItem(t);
						i++;
					}
					r.close(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(i+"개의 할 일이 존재함");
		}
		else System.out.println("todolist.txt 파일이 존재하지 않음.");
		
	}
	
	public static void saveList(TodoList l, String filename) {
		File file = new File(filename); 
		FileWriter w;
		try {
			w = new FileWriter(file, false);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("**모든 데이터 저장됨.**");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void findKeyword(TodoList l, String key) {
		int num=0;
		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(key) || item.getDesc().contains(key)) {
				System.out.println((l.indexOf(item)+1) + ". " + item.toString());
				num++;
			}
		}
		System.out.println("총 " + num+ "개의 할 일을 찾았습니다.\n");
	}
}