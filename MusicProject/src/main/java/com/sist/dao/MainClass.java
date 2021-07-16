package com.sist.dao;
import java.util.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MusicDAO dao=new MusicDAO();
        ArrayList<GenieVO> list=dao.genieAllData();
        for(GenieVO vo:list)
        {
        	System.out.println(vo.getNo()+" "
        			+vo.getTitle()+" "
        			+vo.getSinger()+" "
        			+vo.getAlbum());
        }
	}

}
