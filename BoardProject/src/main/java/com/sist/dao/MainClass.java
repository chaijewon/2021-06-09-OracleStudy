package com.sist.dao;
import java.util.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Scanner scan=new Scanner(System.in);
        System.out.print("페이지 입력:");
        int page=scan.nextInt();
        //데이터베이스 읽기
        BoardDAO dao=new BoardDAO();
        ArrayList<BoardVO> list=dao.boardListData(page);
        for(BoardVO vo:list)
        {
        	System.out.println(vo.getNo()); // 1page => 11~2  2page 1~
        }
	}

}
