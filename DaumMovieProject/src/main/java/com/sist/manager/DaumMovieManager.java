package com.sist.manager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.*;
/*
 *  <div class="poster_info">
      <a href="/moviedb/main?movieId=143722" class="link_story" data-tiara-layer="poster">
     ���� ���� ȸ��� ��ʴϴ� ���̺� �ֽ�ȸ���� ������ ���� ���̺񿡼� �λ� ���� CEO�� �� `�׵�`.���̺��� �� �˾Ҵ� ��ī `Ƽ��`�� �˰� ���� ���̺� �ֽ�ȸ�� �Ҽ� �ӿ����� ��������.�� ���� ���̺� `Ƽ��`�� ���÷� �ٽ� ���̺�� ���ư� `�׵�`�� �� `��`���� �־��� �ð��� 48�ð�!������ ���ϱ� ���� �̼��� ������ �ϼ��� �� ���� ���ΰ�?
      </a>
 */
public class DaumMovieManager {
   public static void main(String[] args) {
	   DaumMovieManager d=new DaumMovieManager();
	   d.movieAllData();
   }
   public void movieAllData()
   {
	   try
	   {
		   Document doc=Jsoup.connect("https://movie.daum.net/ranking/reservation").get(); // HTML�ҽ��� �о� �´� 
	       Elements link=doc.select("a.link_story");
	       Elements story=doc.select("a.link_story");
	       int cno=1;
	       /*
	        *  <div class="info_poster">
                    <a href="#photoId=1423979" class="thumb_img" data-tiara-layer="poster" data-tiara-copy="����_������">
            <span class="bg_img" style="background-image:url(
            https://img1.daumcdn.net/thumb/C408x596/?fname=https%3A%2F%2Ft1.
            daumcdn.net%2Fmovie%2F88c733527983cbd6ab27e4f5f0673358ecfdf9eb)"></span>
            <span class="ico_movie ico_zoom"></span>
            </a>
	        */
	       for(int i=0;i<link.size();i++)
	       {
	    	   System.out.println("��ũ�ּ�:"+link.get(i).attr("href"));
	    	   System.out.println("�ٰŸ�:"+story.get(i).text());
	    	   System.out.println("==================================");
	    	   Document doc2=Jsoup.connect("https://movie.daum.net"+link.get(i).attr("href")).get();
	    	   Element poster=doc2.selectFirst("div.info_poster span.bg_img");
	    	   String img=poster.attr("style");
	    	   img=img.substring(img.indexOf("(")+1,img.lastIndexOf(")"));
	    	   System.out.println("�̹���:"+img);
	    	   /*
	    	    *  <h3 class="tit_movie">
                <span class="txt_tit">
                    ���� ���̺� 2
                </span>
                    <span class="txt_tag">
                        <span class="txt_state">����</span>
                    </span>
                </h3>
	    	    */
	    	   Element title=doc2.selectFirst("h3.tit_movie span.txt_tit");
	    	   System.out.println("����:"+title.text());
	    	   /*
	    	    *  <div class="detail_cont">
                  <div class="inner_cont">
                        <dl class="list_cont">
                                <dt>����</dt>
                            <dd>2021.07.21</dd>
                        </dl>
                    <dl class="list_cont">
                        <dt>�帣</dt>
                        <dd>�ִϸ��̼�/����/��庥ó</dd>
                    </dl>
                    <dl class="list_cont">
                        <dt>����</dt>
                        <dd>�̱�</dd>
                    </dl>
                        <dl class="list_cont">
                        <dt>���</dt>
                        <dd>
                            ��ü������
                        </dd>
                        </dl>
                    <dl class="list_cont">
                    <dt>����Ÿ��</dt>
                    <dd>
                        107��
                    </dd>
                    </dl>
		            </div>
		            <div class="inner_cont">
		                    <dl class="list_cont">
		                    <dt>����</dt>
		                    <dd><span class="ico_movie ico_star"></span>9.6</dd>
		                    </dl>
		                    <dl class="list_cont">
		                        <dt>��������</dt>
		                        <dd>99,422��</dd>
		                    </dl>
	    	    */
	    	   /*
	    	    *   ����
					�帣
					����
					���
					����Ÿ��
					����
					��������
	    	    */
	    	   Elements etc=doc2.select("dl.list_cont dt");
	    	   for(int j=0;j<etc.size();j++)
	    	   {
	    		   //System.out.println(etc.get(j).text());
	    		   String dt=etc.get(j).text();
	    		   if(dt.equals("����"))
	    		   {
	    			   Element regdate=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("������:"+regdate.text());
	    		   }
	    		   else if(dt.equals("�帣"))
	    		   {
	    			   Element genre=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("�帣:"+genre.text());
	    		   }
	    		   else if(dt.equals("����"))
	    		   {
	    			   Element nation=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("����:"+nation.text());
	    		   }
	    		   else if(dt.equals("���"))
	    		   {
	    			   Element grade=doc2.select("dl.list_cont dd").get(j);
    			       System.out.println("���:"+grade.text());
	    			   
	    		   }
	    		   else if(dt.equals("����Ÿ��"))
	    		   {
	    			   Element time=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("�ð�:"+time.text());
	    		   }
	    		   else if(dt.equals("����"))
	    		   {
	    			   Element score=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("����:"+score.text());
	    		   }
	    		   else if(dt.equals("��������"))
	    		   {
	    			   Element user=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("��������:"+user.text());
	    		   }
	    	   }
	       }
	       
	   }catch(Exception ex){}
   }
}






