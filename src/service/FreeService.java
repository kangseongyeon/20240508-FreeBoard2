package service;

import java.util.List;

import dao.FreeDao;
import vo.FreeVo;

public class FreeService {
	private static FreeService instance;

	private FreeService() {

	}

	public static FreeService getInstance() {
		if (instance == null) {
			instance = new FreeService();
		}
		return instance;
	}
	
	FreeDao freeDao = FreeDao.getInstance();
	
	public List<FreeVo> freeList() {
		return freeDao.freeList();
	}

	public FreeVo freeDetail(List<Object> param) {
		return freeDao.freeDetail(param);
	}

	public void freeUpdate(List<Object> param2) {
		freeDao.freeUpdate(param2);
	}
}
