package com.sist.dao;
import java.util.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Scanner scan=new Scanner(System.in);
        System.out.print("������ �Է�:");
        int page=scan.nextInt();
        //�����ͺ��̽� �б�
        BoardDAO dao=new BoardDAO();
        ArrayList<BoardVO> list=dao.boardListData(page);
        for(BoardVO vo:list)
        {
        	System.out.println(vo.getNo()); // 1page => 11~2  2page 1~
        }
	}

}
