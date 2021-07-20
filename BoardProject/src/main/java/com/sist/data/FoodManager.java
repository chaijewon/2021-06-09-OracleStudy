package com.sist.data;
// Jsoup=>웹에서 데이터 읽기
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class FoodManager {
   public static void main(String[] args) {
	  FoodManager fm=new FoodManager();
	  fm.foodGetData();
   }
   public void foodGetData()
   {
	   FoodDAO dao=new FoodDAO();
	   try
	   {
		   Document doc=Jsoup.connect("https://www.mangoplate.com/").get();
		   Elements title=doc.select("ul.list-toplist-slider span.title");
		   Elements subject=doc.select("ul.list-toplist-slider p.desc");
		   Elements poster=doc.select("ul.list-toplist-slider img.center-croping");
		   Elements link=doc.select("ul.list-toplist-slider a");
		   /*
		    *   <a href="aaa">bbb</a> ==> text()
		    *           ===== attr()
		    *   <a href="bbb"><span>ccc</span></a>
		    *                 ================ => html()
		    *   <javascript> => data()
		    *   <img src="포스터 주소">
		    *        
		    */
		   for(int i=0;i<title.size();i++)
		   {
			   System.out.println(title.get(i).text());
			   System.out.println(subject.get(i).text());
			   System.out.println(poster.get(i).attr("data-lazy"));
			   System.out.println(link.get(i).attr("href"));
			   System.out.println("====================================");
			   FoodCategoryVO vo=new FoodCategoryVO();
			   vo.setTitle(title.get(i).text());
			   vo.setSubject(subject.get(i).text());
			   vo.setPoster(poster.get(i).attr("data-lazy"));
			   vo.setLink(link.get(i).attr("href"));
			   dao.foodCategoryInsert(vo);
		   }
	   }catch(Exception ex){}
   }
}



