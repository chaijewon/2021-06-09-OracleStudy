package com.sist.main;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.*;
/*
 *  <tr class="list" songid="93619160">
   <td class="check"><input type="checkbox" class="select-check" title="바라만 본다" /></td>
     <td class="number">1
               <span class="rank">
     <span class="rank"><span class="rank-none"><span
                      class="hide">유지</span></span></span>
</span>
</td>
 <td><a href="#" class="cover" onclick="fnViewAlbumLayer('82110804');return false;"><span class="mask"></span><img src="//image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/110/804/82110804_1624611382361_1_140x140.JPG/dims/resize/Q_80,0" onerror="this.src='//image.genie.co.kr/imageg/web/common/blank_68.gif';" alt="MSG워너비 1집" /></a></td>
 <td class="link"><a href="#" class="btn-basic btn-info" onclick="fnViewSongInfo('93619160');return false;">곡 제목 정보 페이지</a></td>
 <td class="info">
  <a href="#" class="title ellipsis" title="재생" onclick="fnPlaySong('93619160','1');return false;">
  바라만 본다</a>
 <a href="#" class="artist ellipsis" onclick="fnViewArtist('81123111');return false;">MSG워너비 (M.O.M)</a>
  <div class="toggle-button-box" >
  <button type="button" class="btn artist-etc" onclick="fnRelationArtistList('93619160');">외</button>
   <ul class="list" id="RelationArtist_93619160"></ul>
   </div>
    <i class="bar">|</i>
    <a href="#" class="albumtitle ellipsis" onclick="fnViewAlbumLayer('82110804');return false;">MSG워너비 1집</a>
    </td>
                                
 */
public class MusicMain {
    // https://www.genie.co.kr/chart/top200?ditc=D&ymd=20210715&hh=14&rtm=Y&pg=2
	public void genieAllData()
	{
		MusicDAO dao=new MusicDAO();
		try
		{
			int k=1;
			for(int i=1;i<=4;i++)
			{
				Document doc=Jsoup.connect("https://www.genie.co.kr/chart/top200?ditc=D&ymd=20210715&hh=14&rtm=Y&pg="+i).get();
			    Elements title=doc.select("table.list-wrap a.title");//<a class="title">제목</a>
			    Elements poster=doc.select("table.list-wrap a.cover img");
			    Elements singer=doc.select("table.list-wrap a.artist");
			    Elements album=doc.select("table.list-wrap a.albumtitle");
			    Elements etc=doc.select("table.list-wrap span.rank");
			    for(int j=0;j<title.size();j++)
			    {
			    	try
			    	{
				    	System.out.println("번호:"+k);
				    	System.out.println("제목:"+title.get(j).text());
				    	System.out.println("가수명:"+singer.get(j).text());
				    	System.out.println("앨범:"+album.get(j).text());
				    	System.out.println("포스터:"+poster.get(j).attr("src"));
				    	String s=etc.get(j).text();
				    	String state="";
				    	String idcrment="";
				    	if(s.equals("유지"))
				    	{
				    		state="유지";
				    		idcrment="0";
				    	}
				    	else
				    	{
				    		state=s.replaceAll("[^가-힣]", "");
				    		idcrment=s.replaceAll("[^0-9]", "");
				    	}
				    	System.out.println("상태:"+state);
				    	System.out.println("등폭:"+idcrment);
				    	//System.out.println("키:"+youtubeGetKey(idcrment));
				    	
				    	GenieVO vo=new GenieVO();
				    	vo.setNo(k);
				    	vo.setTitle(title.get(j).text());
				    	vo.setSinger(singer.get(j).text());
				    	vo.setAlbum(album.get(j).text());
				    	vo.setPoster(poster.get(j).attr("src"));
				    	vo.setState(state);
				    	vo.setIdcrement(Integer.parseInt(idcrment));
				    	//dao.genieInsert(vo);
				    	System.out.println("키:"+youtubeGetKey(vo.getTitle()));
				    	System.out.println("=========================================");
				    	
				    	k++;
			    	}catch(Exception ex){}
			    }
			}
		}catch(Exception ex){}
	}
	public String youtubeGetKey(String title)
	{
		String key="";
		try
		{
			Document doc=Jsoup.connect("https://www.youtube.com/results?search_query="+title).get();
			Pattern p=Pattern.compile("/watch\\?v=[^가-힣]+");
			// . ^ , + , * , ? , | ==> \. \?... 자바는 \\
			Matcher m=p.matcher(doc.toString());
			while(m.find())
			{
				String s=m.group();
				System.out.println(s);
				key=s.substring(s.indexOf("=")+1,s.indexOf("\""));
				break;
			}
		}catch(Exception ex) {}
		return key;
	}
	public void melonAllData()
	{
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MusicMain m=new MusicMain();
        m.genieAllData();
	}

}
