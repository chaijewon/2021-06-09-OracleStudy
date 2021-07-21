package com.sist.data;
// Jsoup=>������ ������ �б�
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
		    *   <img src="������ �ּ�">
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
   // ���� ���� 
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
			   System.out.println("ī�װ� ��ȣ:"+vo.getCno());
			   System.out.println("����:"+vo.getTitle());
			   System.out.println("������:"+vo.getSubject());
			   System.out.println("�̹���:"+vo.getPoster());
			   System.out.println("��ũ:"+vo.getLink());
			   System.out.println("======================================================");
		      
		      <div class="info">
                        <div class="wannago_wrap">
                          <button class="btn-type-icon favorite wannago_btn "
                                  data-restaurant_uuid="276164"
                                  data-action_id="">
                          </button>
                          <p class="wannago_txt">����ʹ� </p>
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
				    *  <h1 class="restaurant_name">�μ��Ұ�����</h1> 
				    *  <strong class="rate-point "> 
				    *  <span>4.2</span> </strong>
                        
                        figure class="restaurant-photos-item">
                        img class="center-croping
				    */
				   Element title=doc2.selectFirst("span.title h1.restaurant_name");
			       Element score=doc2.selectFirst("strong.rate-point span");
			       // ������ 
			       /*
			        *  https://mp-seoul-image-production-s3.mangoplate.com/sources/web/restaurants/276164/1862524_1609739956858?fit=around|512:512&amp;crop=512:512;*,*&amp;output-format=jpg&amp;output-quality=80
			        */
			       Elements poster=doc2.select("figure.restaurant-photos-item img.center-croping");
			       String image="";
			       for(int j=0;j<poster.size();j++)
			       {
			    	   image+=poster.get(j).attr("src")+"^"; // , 
			       }
			       image=image.substring(0,image.lastIndexOf("^"));// ������ ^�� ���� 
			       image=image.replace("&", "#");
			       StringTokenizer st=new StringTokenizer(image,"^");
			       int k=1;
			       while(st.hasMoreTokens())
			       {
			    	   System.out.println(k+"."+st.nextToken());
			    	   k++;
			       }
			       // �Ѱ� => Element , ������ => Elements
			       /*
			        *   <table class="info no_menu "> 
                        <tr class="only-desktop"> 
				      <th>�ּ�</th> 
				      <td>������ ������ ������ 50<br/> 
				      <span class="Restaurant__InfoAddress--Rectangle">����</span> 
				      <span class="Restaurant__InfoAddress--Text">
				         ������ ������ ��õ�� 219-11</span> 
				      </td> 
				    </tr>     <tr class="only-desktop"> 
				      <th>��ȭ��ȣ</th> 
				      <td>033-641-3601</td> 
      					</tr>
			        */
			       Element address=doc2.select("table.info td").get(0);
			       String addr=address.text();
			       Element tel=doc2.select("table.info td").get(1);
			       String phone=tel.text();
			       Element type=doc2.select("table.info td").get(2);
			       String tp=type.text();
			       // 3,4,5,6
			       ////////////////////////////////////////////////////
			       String pr="";
			       try
			       {
			          Element price=doc2.select("table.info th").get(3);
			          String s=price.text();
			          if(s.equals("���ݴ�"))
			          {
			        	  Element pp=doc2.select("table.info td").get(3);
			        	  pr=pp.text();
			          }
			       }catch(Exception ex)
			       {
			    	      pr="none";
			       }
			       
			       //Element parking=doc2.selectFirst("");
			       //Element time=doc2.selectFirst("");
			       //Element menu=doc2.selectFirst("");
			       ////////////////////////////////////////////////////
			       System.out.println("��ü��:"+title.text());
			       System.out.println("����:"+score.text());
			       System.out.println("�ּ�:"+addr);
			       System.out.println("��ȭ:"+phone);
			       System.out.println("��������:"+tp);
			       System.out.println("���ݴ�:"+pr);
			       System.out.println("===========================");
			   }
			   
		   }
	   }catch(Exception ex){}
   }
}











