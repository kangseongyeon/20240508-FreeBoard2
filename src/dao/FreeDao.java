package dao;

import java.util.List;

import util.JDBCUtil;
import vo.FreeVo;

public class FreeDao {
	private static FreeDao instance;

	private FreeDao() {

	}

	public static FreeDao getInstance() {
		if (instance == null) {
			instance = new FreeDao();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<FreeVo> freeList() {
		String sql = " SELECT NO, F.NAME, CONTENT, TO_CHAR(REGDATE, 'YYYY.MM.DD') REGDATE, F.MEM_NO, M.NAME WRITER\r\n" + 
					 "FROM JAVA_FREEBOARD F, JAVA_MEMBER M\r\n" + 
					 "WHERE F.MEM_NO = M.MEM_NO \r\n" + 
					 "AND F.DELYN = 'N'\r\n" + 
					 "AND BAN = 'N' ";
		// map 대신에 vo 사용
		return jdbc.selectList(sql, FreeVo.class);
	}

	public FreeVo freeDetail(List<Object> param) {
		String sql ="SELECT * FROM\r\n" + 
				 "(SELECT NO, F.NAME, CONTENT, TO_CHAR(REGDATE, 'YYYY.MM.DD' ) REGDATE, F.MEM_NO , M.NAME writer,\r\n" + 
				 "         LAG(NO) OVER(ORDER BY NO) PREVNO, \r\n" + 
				 "         LEAD(NO) OVER(ORDER BY NO) NEXTNO\r\n" + 
				 "FROM JAVA_FREEBOARD F, JAVA_MEMBER M \r\n" + 
				 "WHERE f.mem_no = m.mem_no\r\n" + 
				 "AND F.DELYN = 'N'\r\n" + 
				 "AND BAN = 'N')\r\n" + 
				 "WHERE NO = ?";
		return jdbc.selectOne(sql, param, FreeVo.class);
	}

	public void freeUpdate(List<Object> param) {
		String sql = "UPDATE JAVA_FREEBOARD\r\n" + 
					 "SET CONTENT = ? \r\n" + 
					 "WHERE NO = ?";
		jdbc.update(sql, param);
	}
}
