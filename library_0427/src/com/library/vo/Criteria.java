package com.library.vo;

public class Criteria {
	
	private String searchField;
	private String searchWord="";
	
	int pageNo = 1; // 페이지 번호
	int amount = 10; // 한페이지당 보여질 게시물 수
	
	int startNo = 1; 
	int endNo = 10; 
	
	
	public Criteria() {
		// TODO Auto-generated constructor stub
	}

	public Criteria(int pageNo) {
		// 페이지 계산
		super();
		if(pageNo>0) {			
			this.pageNo = pageNo;
			// 마지막 : 1 *10 =10
			// 시작 : 10-9=1
			endNo = pageNo * amount;
			startNo = pageNo * amount - (amount -1);
		}
	}

	public Criteria(String searchField, String searchWord, int pageNo) {
		this.searchField = searchField;
		this.searchWord = searchWord;
		if(pageNo > 0) {
			this.pageNo = pageNo;
			endNo = pageNo * amount;
			startNo = pageNo * amount - (amount - 1);
		}
	}

	public Criteria(String searchField, String searchWord, String pageNoStr) {
		if(searchWord != null) {
			this.searchField = searchField;
			this.searchWord = searchWord;			
		}
		
		if(pageNoStr != null
				&& !pageNoStr.equals("")) {
			pageNo = Integer.parseInt(pageNoStr);
			
			if(pageNo > 0) {
				endNo = pageNo * amount;
				startNo = pageNo * amount - (amount - 1);
			}else {
				pageNo = 1;
			}
		}
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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

}
