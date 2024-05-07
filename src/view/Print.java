package view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Print {
	
	public void printVar() {
		System.out.println("===================================");
		
	}
	
	public void printLn(int num) {
		for (int i = 0; i < num; i++) System.out.println();
	}
	
	public void printHome() {
		printVar();
		System.out.println("1. 관리자");
		System.out.println("2. 일반 회원");
		printLn(5);
		printVar();
		
	}
	
	public void printAdmin() {
		printVar();
		System.out.println("1. 도서 정보");
		System.out.println("2. 도서 보유");
		printLn(5);
		printVar();
	}
	
	
	public void bookListPrint(List<Map<String, Object>> bookList) {
		printVar();
		for (Map<String,Object> map:bookList) {
			BigDecimal bookNo = (BigDecimal) map.get("BOOK_NO");
			String title = (String)map.get("TITLE");
			String content = (String)map.get("CONTENT");
			String pubdate = (String)map.get("PUBDATE");
			System.out.println(bookNo + "\t" + title + "\t" + content + "\t" + pubdate);
		}
		printVar();
	}
	
	
}
