package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.FreeService;
import service.MemberService;
import util.ScanUtil;
import util.View;
import view.Print;
import vo.FreeVo;
import vo.MemberVo;
//
public class MainController extends Print {
	
	static public Map<String, Object> sessionStorage = new HashMap<>();
	
	FreeService freeService = FreeService.getInstance();	
	MemberService memberService = MemberService.getInstance();
	
	
	public static void main(String[] args) {
		new MainController().start();
	}

	private void start() {
		View view = View.HOME;
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break;
			case FREE_LIST:
				view = freeList();
				break;
			case FREE_DETAIL:
				view = freeDetail();
			case FREE_UPDATE:
				view = freeUpdate();
				break;
			case FREE_DELETE:
				view = freeList();
				break;
			case MEMBER_LOGIN:
				view = memberLogin();
				break;
			default:
				break;
			}
		}
	}


	
	private View memberLogin() {
		
		String id  = ScanUtil.nextLine("ID : ");
		String pw  = ScanUtil.nextLine("PW : ");
		
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(pw);
		param.add(1);
		boolean loginChk = memberService.login(param, 1);
		if (!loginChk) {
			// 로그인 실패 로직
			System.out.println("1. 재로그인");
			System.out.println("2. 회원 가입");
			System.out.println("3. 홈");
			return View.MEMBER_LOGIN;
		}
		// 로그인 성공
		// 해당 페이지에서 로그인 시도했다면 해당 페이지로 이동
		View view = (View) sessionStorage.get("view");
		if (view == null) return View.FREE_LIST;
		else return view;
	}

	
	private View freeUpdate() {
		if (!sessionStorage.containsKey("member")) {
			// 업데이트를 진행하는 곳에서 현재 페이지 주소를 보내줌
			sessionStorage.put("view", View.FREE_UPDATE);
			return View.MEMBER_LOGIN;
		}
		
		int boardNo = (int) sessionStorage.get("boardNo");
		List<Object> param = new ArrayList();
		param.add(boardNo);
		
		FreeVo freeVo = freeService.freeDetail(param);		
		MemberVo member = (MemberVo) sessionStorage.get("member");
		
		// 해당 권한이 없다면 (같지 않을 경우) 글 작성 x
		if (freeVo.getMem_no() != member.getMem_no()) {
			System.out.println("해당 게시글 변경 권한이 없습니다.");
			
			return View.FREE_DETAIL;
		}
		// 권한이 있다면 게시판 변경 진행
		
		String content = ScanUtil.nextLine("내용: ");
		List<Object> param2 = new ArrayList();
		param2.add(content);
		param2.add(boardNo);
		freeService.freeUpdate(param2);
		return View.FREE_DETAIL;
	}

	
	private View freeDetail() {
		int boardNo = (int) sessionStorage.get("boardNo");
		List<Object> param = new ArrayList();
		param.add(boardNo);
		
		FreeVo freeVo = freeService.freeDetail(param);
		System.out.println(freeVo);
		
		System.out.println("<이전글 \t 다음글>");
		System.out.println("1. 게시판 변경");
		System.out.println("2. 게시판 삭제");
		System.out.println("3. 전체 게시판 조회");
		String sel = ScanUtil.nextLine("메뉴 : ");
		if (sel.equals("1")) return View.FREE_UPDATE;
		else if (sel.equals("2")) return View.FREE_DELETE;
		else if (sel.equals("3")) return View.FREE_LIST;
		else if (sel.equals("<")) {
			boardNo = freeVo.getPreno();
			if (boardNo == 0) {
				System.out.println("이전 게시글 없습니다");
			} else {
				sessionStorage.put("boardNo", boardNo);
			}
			return View.FREE_DETAIL;
		}
		else if (sel.equals(">")) {
			boardNo = freeVo.getNextno();
			if (boardNo == 0) {
				System.out.println("다음 게시글 없습니다");
			} else {
				sessionStorage.put("boardNo", boardNo);
			}
			return View.FREE_DETAIL;
		}
		else return View.FREE_DETAIL;
	}

	
	private View freeList() {
		// 기존 : Map 사용 -> 현재 : Vo 사용
		// Vo : 타입 & 컬럼명 미리 java에 적어둠 -> 타입을 명확하게 알 수 있는 장점 
		// 	  : but, 컬럼이 계속 바뀔 경우 get/set 만들 때 불편 -> lombok으로 해결 가능
		List<FreeVo> freeList = freeService.freeList();
		for (FreeVo freeVo : freeList) {
			int no = freeVo.getNo();
			String name = freeVo.getName();
			String content = freeVo.getContent();
			String writer = freeVo.getWriter();
			// 날짜 : String 타입이기 때문에 TO_CHAR로 변환 필요
			// if) Date 타입으로 바꾸고 싶다면? -> FreeVo에서 String을 Date로 바꾸면 됨
			String regdate = freeVo.getRegdate();
			
			System.out.println(no + "\t" + name + "\t" + content + "\t" + writer + "\t" + regdate);
		}
		System.out.println("1. 게시판 상세 조회");
		System.out.println("2. 홈");
		int sel = ScanUtil.menu();
		if (sel == 1) {
			int boardNo = ScanUtil.nextInt("게시판 번호 입력");
			sessionStorage.put("boardNo", boardNo);
			return View.FREE_DETAIL;
		}
		else if (sel == 2) return View.HOME;
		else return View.FREE_LIST;
	}

	
	private View home() {
		// 시스템 관리자 페이지 -> 노출 X
		System.out.println("1. 전체 게시판 조회");
		System.out.println("2. 게시판 등록");
		
		int sel = ScanUtil.menu();
		if (sel == 1)  return View.FREE_LIST;
		else if (sel == 2) return View.FREE_INSERT;
		else if (sel == 0) return View.ADMIN;
		else return View.HOME;
	}

}
