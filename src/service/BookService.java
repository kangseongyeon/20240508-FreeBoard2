package service;

import java.util.List;
import java.util.Map;

import dao.BookDao;

public class BookService {
	private static BookService instance;

	private BookService() {

	}

	public static BookService getInstance() {
		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	BookDao bookDao = BookDao.getInstance();
	
	public List<Map<String, Object>> bookList() {
		return bookDao.bookList();
	}

	public void bookUpdate(List<Object> param, int sel) {
		bookDao.bookUpdate(param, sel);
	}
	
	public List<Map<String, Object>> holdList(List<Object> param) {
		return bookDao.holdList(param);
	}
}
