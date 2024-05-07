package service;

import java.util.List;
import java.util.Map;

import controller.MainController;
import dao.MemberDao;

public class MemberService {
	private static MemberService instance;

	private MemberService() {

	}

	public static MemberService getInstance() {
		if (instance == null) {
			instance = new MemberService();
		}
		return instance;
	}
	
	MemberDao memberDao = MemberDao.getInstance();
	
	public boolean login(List<Object> param, int role) {
		Map<String, Object> member = memberDao.login(param);
		
		// 로그인 실패
		if (member == null) {
			return false;
		}
		// 로그인 성공
		if (role == 1) {
			// 1. 일반 회원 -> member에 넣어줌
			MainController.sessionStorage.put("member", member);
		}
		if (role == 2) {
			// 2. 관리자
			MainController.sessionStorage.put("admin", member);
		}
		return true;
	}
}
