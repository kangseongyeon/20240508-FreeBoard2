// map 대신에 vo 사용
// map	: 단) type과 컬럼을 정확히 알고 있어야 함
// vo	: 장) 오타 x / TYPE을 DB에 가지 않고도 JAVA에서 볼 수 있음 
// 18분 설명 
package vo;

import lombok.Data;

// getter / setter 자동으로 만들어줌

@Data
public class FreeVo {
	private int no;
	private String name;
	private String content;
	private int mem_no;
	private String regdate;
	private String delyn;
	private String ban;
	
	// DB에는 없지만 사용할 컬럼 추가할 수 있음
	private String writer;	
	
	// 이전글 번호, 다음글 번호 필요
	private int preno;
	private int nextno;
}
