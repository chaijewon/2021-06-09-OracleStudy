package com.sist.data;
// Jsoup=>웹에서 데이터 읽기
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class FoodManager {
   public static void main(String[] args) {
	  FoodManager fm=new FoodManager();
	  //fm.foodGetData();
	  fm.foodHouseAllData();
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
   // 맛집 저장 
   /*
    *   NO      NOT NULL NUMBER         
		CNO              NUMBER         
		NAME    NOT NULL VARCHAR2(100)  
		SCORE   NOT NULL NUMBER(2,1)    
		ADDRESS NOT NULL VARCHAR2(200)  
		TEL     NOT NULL VARCHAR2(20)   
		TYPE    NOT NULL VARCHAR2(100)  
		PRICE   NOT NULL VARCHAR2(100)  
		PARKING NOT NULL VARCHAR2(100)  
		TIME             VARCHAR2(30)   
		MENU             VARCHAR2(2000) 
		POSTER  NOT NULL VARCHAR2(1000) 
		GOOD             NUMBER         
		SOSO             NUMBER         
		BAD              NUMBER
    */
   public void foodHouseAllData()
   {
	   try
	   {
		   FoodDAO dao=new FoodDAO();
		   ArrayList<FoodCategoryVO> list=dao.foodCategoryListData();
		   for(FoodCategoryVO vo:list)
		   {
			   /*
			   System.out.println("카테고리 번호:"+vo.getCno());
			   System.out.println("제목:"+vo.getTitle());
			   System.out.println("부제목:"+vo.getSubject());
			   System.out.println("이미지:"+vo.getPoster());
			   System.out.println("링크:"+vo.getLink());
			   System.out.println("======================================================");
		      
		      <div class="info">
                        <div class="wannago_wrap">
                          <button class="btn-type-icon favorite wannago_btn "
                                  data-restaurant_uuid="276164"
                                  data-action_id="">
                          </button>
                          <p class="wannago_txt">가고싶다 </p>
                        </div>
                        <span class="title ">
		      */
			   Document doc=Jsoup.connect(vo.getLink()).get();
			   Elements link=doc.select("div.info span.title a");
			   for(int i=0;i<link.size();i++)
			   {
				   //System.out.println(link.get(i).attr("href"));
				   Document doc2=
						Jsoup.connect("https://www.mangoplate.com/"+link.get(i).attr("href")).get();
				   /*
				    *  <div class="restaurant_title_wrap"> 
				    *  <span class="title"> 
				    *  <h1 class="restaurant_name">부성불고기찜닭</h1> 
				    *  <strong class="rate-point "> 
				    *  <span>4.2</span> </strong>
                        
                        figure class="restaurant-photos-item">
                        img class="center-croping
				    */
				   Element title=doc2.selectFirst("span.title h1.restaurant_name");
			       Element score=doc2.selectFirst("strong.rate-point span");
			       // 포스터 
			       /*
			        *  https://mp-seoul-image-production-s3.mangoplate.com/sources/web/restaurants/276164/1862524_1609739956858?fit=around|512:512&amp;crop=512:512;*,*&amp;output-format=jpg&amp;output-quality=80
			        */
			       Elements poster=doc2.select("figure.restaurant-photos-item img.center-croping");
			       String image="";
			       for(int j=0;j<poster.size();j++)
			       {
			    	   image+=poster.get(j).attr("src")+"^"; // , 
			       }
			       image=image.substring(0,image.lastIndexOf("^"));// 마지막 ^를 제거 
			       image=image.replace("&", "#");
			       StringTokenizer st=new StringTokenizer(image,"^");
			       
			       System.out.println("업체명:"+title.text());
			       System.out.println("점수:"+score.text());
			       System.out.println("===========================");
			   }
			   
		   }
	   }catch(Exception ex){}
   }
}











