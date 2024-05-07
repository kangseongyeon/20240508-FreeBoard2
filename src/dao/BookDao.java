package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BookDao {
	private static BookDao instance;

	private BookDao() {

	}

	public static BookDao getInstance() {
		if (instance == null) {
			instance = new BookDao();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> bookList() {
		String sql = " SELECT BOOK_NO, TITLE, CONTENT, "
				+ "			  TO_CHAR(PUBDATE, 'YYYY.MM.DD') PUBDATE FROM BOOK";
		return jdbc.selectList(sql);
	}

	public void bookUpdate(List<Object> param, int sel) {
		String sql = " UPDATE BOOK "
				   + " SET  ";
		
		if (sel == 1 || sel == 3) {
			sql += " TITLE = ? ";
			if (sel == 3) sql += ", ";
		}
		if (sel == 2 || sel == 3) {
			sql += " CONTENT = ? ";
		}
		
		sql += " WHERE BOOK_NO = ? ";
		
		jdbc.update(sql, param);
	}
	
	public List <Map<String, Object>> holdList(List<Object> param) {
		String sql = "SELECT *\r\n" + 
				"FROM \r\n" + 
				"    (SELECT ROWNUM RN , B.*, H.HOLD_NO\r\n" + 
				"     FROM BOOK B, BOOK_HOLD H\r\n" + 
				"     WHERE B.BOOK_NO = H.BOOK_NO\r\n" + 
				"     ORDER BY B.BOOK_NO)\r\n" + 
				"WHERE (RN >= ? AND RN <= ?)";
		return jdbc.selectList(sql, param);
	}
}
