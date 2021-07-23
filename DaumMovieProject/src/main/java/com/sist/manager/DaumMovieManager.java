package com.sist.manager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.*;
/*
 *  <div class="poster_info">
      <a href="/moviedb/main?movieId=143722" class="link_story" data-tiara-layer="poster">
     가족 같은 회사로 모십니다 베이비 주식회사의 레전드 보스 베이비에서 인생 만렙 CEO가 된 `테드`.베이비인 줄 알았던 조카 `티나`가 알고 보니 베이비 주식회사 소속 임원으로 밝혀진다.뉴 보스 베이비 `티나`의 지시로 다시 베이비로 돌아간 `테드`와 형 `팀`에게 주어진 시간은 48시간!세상을 구하기 위한 미션을 무사히 완수할 수 있을 것인가?
      </a>
 */
public class DaumMovieManager {
   public static void main(String[] args) {
	   DaumMovieManager d=new DaumMovieManager();
	   d.movieAllData();
   }
   public void movieAllData()
   {
	   /*
	    *  <div class="item_poster">
           <a href="/moviedb/main?movieId=145371" class="thumb_item" data-tiara-layer="poster">
	    */
	   try
	   {
		   Document doc=Jsoup.connect("https://movie.daum.net/premovie/kakaopage").get(); // HTML소스를 읽어 온다 
	       Elements link=doc.select("a.thumb_item");
	       //Elements story=doc.select("a.link_story");
	       int cno=8;
	       /*
	        *  <div class="info_poster">
                    <a href="#photoId=1423979" class="thumb_img" data-tiara-layer="poster" data-tiara-copy="메인_포스터">
            <span class="bg_img" style="background-image:url(
            https://img1.daumcdn.net/thumb/C408x596/?fname=https%3A%2F%2Ft1.
            daumcdn.net%2Fmovie%2F88c733527983cbd6ab27e4f5f0673358ecfdf9eb)"></span>
            <span class="ico_movie ico_zoom"></span>
            </a>
	        */
	       // 데이터베이스에 저장 
	       MovieDAO dao=new MovieDAO();
	       for(int i=0;i<link.size();i++)
	       {
	    	   DaumMovieVO vo=new DaumMovieVO();
	    	   System.out.println("링크주소:"+link.get(i).attr("href"));
	    	   //System.out.println("줄거리:"+story.get(i).text());
	    	   System.out.println("==================================");
	    	   Document doc2=Jsoup.connect("https://movie.daum.net"+link.get(i).attr("href")).get();
	    	   Element poster=doc2.selectFirst("div.info_poster span.bg_img");
	    	   String img=poster.attr("style");
	    	   img=img.substring(img.indexOf("(")+1,img.lastIndexOf(")"));
	    	   System.out.println("이미지:"+img);
	    	   vo.setCno(cno);
	    	   vo.setPoster(img);
	    	   vo.setStory("");
	    	   /*
	    	    *  <h3 class="tit_movie">
                <span class="txt_tit">
                    보스 베이비 2
                </span>
                    <span class="txt_tag">
                        <span class="txt_state">상영중</span>
                    </span>
                </h3>
	    	    */
	    	   Element title=doc2.selectFirst("h3.tit_movie span.txt_tit");
	    	   System.out.println("제목:"+title.text());
	    	   vo.setTitle(title.text());
	    	   /*
	    	    *  <div class="detail_cont">
                  <div class="inner_cont">
                        <dl class="list_cont">
                                <dt>개봉</dt>
                            <dd>2021.07.21</dd>
                        </dl>
                    <dl class="list_cont">
                        <dt>장르</dt>
                        <dd>애니메이션/가족/어드벤처</dd>
                    </dl>
                    <dl class="list_cont">
                        <dt>국가</dt>
                        <dd>미국</dd>
                    </dl>
                        <dl class="list_cont">
                        <dt>등급</dt>
                        <dd>
                            전체관람가
                        </dd>
                        </dl>
                    <dl class="list_cont">
                    <dt>러닝타임</dt>
                    <dd>
                        107분
                    </dd>
                    </dl>
		            </div>
		            <div class="inner_cont">
		                    <dl class="list_cont">
		                    <dt>평점</dt>
		                    <dd><span class="ico_movie ico_star"></span>9.6</dd>
		                    </dl>
		                    <dl class="list_cont">
		                        <dt>누적관객</dt>
		                        <dd>99,422명</dd>
		                    </dl>
	    	    */
	    	   /*
	    	    *   개봉
					장르
					국가
					등급
					러닝타임
					평점
					누적관객
	    	    */
	    	   Elements etc=doc2.select("dl.list_cont dt");
	    	   for(int j=0;j<etc.size();j++)
	    	   {
	    		   //System.out.println(etc.get(j).text());
	    		   String dt=etc.get(j).text();
	    		   if(dt.equals("개봉"))
	    		   {
	    			   Element regdate=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("개봉일:"+regdate.text());
	    			   vo.setRegdate(regdate.text());
	    		   }
	    		   else if(dt.equals("장르"))
	    		   {
	    			   Element genre=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("장르:"+genre.text());
	    			   vo.setGenre(genre.text());
	    		   }
	    		   else if(dt.equals("국가"))
	    		   {
	    			   Element nation=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("국가:"+nation.text());
	    			   vo.setNation(nation.text());
	    		   }
	    		   else if(dt.equals("등급"))
	    		   {
	    			   Element grade=doc2.select("dl.list_cont dd").get(j);
    			       System.out.println("등급:"+grade.text());
	    			   vo.setGrade(grade.text());
	    		   }
	    		   else if(dt.equals("러닝타임"))
	    		   {
	    			   Element time=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("시간:"+time.text());
	    			   vo.setTime(time.text());
	    		   }
	    		   else if(dt.equals("평점"))
	    		   {
	    			   Element score=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("평점:"+score.text());
	    			   vo.setScore(Double.parseDouble(score.text()));
	    		   }
	    		   else if(dt.equals("누적관객"))
	    		   {
	    			   Element user=doc2.select("dl.list_cont dd").get(j);
	    			   System.out.println("누적관객:"+user.text());
	    			   vo.setShowUser(user.text());
	    		   }
	    		   
	    	   }
	    	   
	    	   dao.daumMovieInsert(vo);
	       }
	       System.out.println("데이터베이스 저장 종료!!");
	   }catch(Exception ex){}
   }
}






