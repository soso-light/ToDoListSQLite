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
				+ "< �� �� �߰� >\n"
				+ "�з�: ");
		category = sc.next();
		
		System.out.println("���� : ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		sc.nextLine();
		System.out.println("���� : ");
		desc = sc.nextLine();
		
		System.out.println("������(��/��/��): ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(list.addItem(t)>0) {
			System.out.println("�� �� ��Ͽ� �߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "< �� �� ���� >\n"
				+ "������ �� ���� ��ȣ�� �Է��ϼ��� : ");
		int num = sc.nextInt();
		/*
		if(num > l.size()) {
			System.out.println("�������� �ʴ� ��ȣ�Դϴ�."); 
			return;
		}
*/
			if (l.deleteItem(num)>0) {
				System.out.println("�� �� ��Ͽ��� �����Ǿ����ϴ�.\n");
			}
		
	}

	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "< �� �� ���� >\n"
				+ "������ �� ���� ��ȣ�� �Է��ϼ��� : ");
		int num = sc.nextInt();
/*
		if(num > l.size()) {
			System.out.println("�������� �ʴ� ��ȣ�Դϴ�."); 
			return;
		}
	*/	
		System.out.println("�� ���� �� ī�װ����� �Է��ϼ��� : ");
		new_category = sc.next().trim();
		
		System.out.println("�� ���� �� ������ �Է��ϼ��� : ");
		new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		sc.nextLine();
		System.out.println("���ο� ������ �Է��ϼ��� : ");
		new_desc = sc.nextLine().trim();
		
		System.out.println("�� ���� �� �������� �Է��ϼ���(��/��/��): ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_category,new_title, new_desc, new_due_date);
		t.setId(num);
		if(l.editItem(t)>0) {
				System.out.println("�� ���� �����Ǿ����ϴ�.\n");
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("<�� �� ���>");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void loadList(TodoList l, String filename) {
		File file = new File(filename); // File��ü ���� 
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
			System.out.println(i+"���� �� ���� ������");
		}
		else System.out.println("todolist.txt ������ �������� ����.");
		
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
			System.out.println("**��� ������ �����.**");
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
		System.out.println("�� " + num+ "���� �� ���� ã�ҽ��ϴ�.\n");
	}
}