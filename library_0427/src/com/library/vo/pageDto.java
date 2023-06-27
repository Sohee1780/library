package com.library.vo;

public class pageDto {
	
	/* pageNo : 요청한 페이지번호(기본값1)
	 * amount : 페이지 당 게시물 수 <- amount를 사용해 게시물의 끝 페이지를 알 수 있음
	 * => 예) 55/10 = 5.5 = 6 -> 더블타입으로 계산 후 올림
	 * 페이지블럭
	 * <앞으로가기> 시작번호 ... 끝번호 <뒤로가기>
	 * 
	 * */

	int startNo;						// 페이지블럭 시작번호
	int endNo;							// 페이지블럭 끝번호
	int realEnd;						// 게시물의 끝 페이지 번호 (<< 끝페이지로 이동 >>)
	boolean prev, next;					// 이전, 다음 버튼(true : 보여주기)
	// 페이지 블럭을 생성하기 위해 필요한 요소 
	int total;							// 총 게시물의 수
	Criteria criteria = new Criteria();	// 페이지 번호, 페이지당 게시물수
	
	
	public pageDto(int total, Criteria criteria) {
		super();
		this.total = total;
		this.criteria = criteria;
		
		// 페이지 블럭의 끝 번호 구하기
		// 7페이지 요청 : 올림 (7/10.0)*10
		// 11페이지 요청 : 올림 (11/10.0)*10
		// 10을 고치면 페이지블록수 변경 가능
		endNo = (int)Math.ceil(criteria.pageNo/10.0)*10;
		startNo = endNo-(10-1);
		
		// 끝 페이지 번호
		// 55/10 = 5 -> 더블타입으로 계산 후 올림
		realEnd = (int)Math.ceil((total*1.0)/criteria.getAmount());
		
		// 페이지 블럭의 끝번호를 설정
		// 게시물의 끝페이지보다 큰경우, 게시물의 끝페이지로 설정
		endNo = realEnd<endNo?realEnd:endNo;
		
		// 앞으로 이동/ 뒤로 이동 설정
		prev = startNo>1?true:false;
		next = endNo!=realEnd?true:false;
		
	}


	public pageDto() {
		// TODO Auto-generated constructor stub
	}


	public int getStartNo() {
		return startNo;
	}


	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}


	public int getEndNo() {
		return endNo;
	}


	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}


	public int getRealEnd() {
		return realEnd;
	}


	public void setRealEnd(int realEnd) {
		this.realEnd = realEnd;
	}


	public boolean isPrev() {
		return prev;
	}


	public void setPrev(boolean prev) {
		this.prev = prev;
	}


	public boolean isNext() {
		return next;
	}


	public void setNext(boolean next) {
		this.next = next;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public Criteria getCriteria() {
		return criteria;
	}


	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
