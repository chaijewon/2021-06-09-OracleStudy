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
   <td class="check"><input type="checkbox" class="select-check" title="¹Ù¶ó¸¸ º»´Ù" /></td>
     <td class="number">1
               <span class="rank">
     <span class="rank"><span class="rank-none"><span
                      class="hide">À¯Áö</span></span></span>
</span>
</td>
 <td><a href="#" class="cover" onclick="fnViewAlbumLayer('82110804');return false;"><span class="mask"></span><img src="//image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/110/804/82110804_1624611382361_1_140x140.JPG/dims/resize/Q_80,0" onerror="this.src='//image.genie.co.kr/imageg/web/common/blank_68.gif';" alt="MSG¿ö³Êºñ 1Áý" /></a></td>
 <td class="link"><a href="#" class="btn-basic btn-info" onclick="fnViewSongInfo('93619160');return false;">°î Á¦¸ñ Á¤º¸ ÆäÀÌÁö</a></td>
 <td class="info">
  <a href="#" class="title ellipsis" title="Àç»ý" onclick="fnPlaySong('93619160','1');return false;">
  ¹Ù¶ó¸¸ º»´Ù</a>
 <a href="#" class="artist ellipsis" onclick="fnViewArtist('81123111');return false;">MSG¿ö³Êºñ (M.O.M)</a>
  <div class="toggle-button-box" >
  <button type="button" class="btn artist-etc" onclick="fnRelationArtistList('93619160');">¿Ü</button>
   <ul class="list" id="RelationArtist_93619160"></ul>
   </div>
    <i class="bar">|</i>
    <a href="#" class="albumtitle ellipsis" onclick="fnViewAlbumLayer('82110804');return false;">MSG¿ö³Êºñ 1Áý</a>
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
			    Elements title=doc.select("table.list-wrap a.title");//<a class="title">Á¦¸ñ</a>
			    Elements poster=doc.select("table.list-wrap a.cover img");
			    Elements singer=doc.select("table.list-wrap a.artist");
			    Elements album=doc.select("table.list-wrap a.albumtitle");
			    Elements etc=doc.select("table.list-wrap span.rank");
			    for(int j=0;j<title.size();j++)
			    {
			    	try
			    	{
				    	System.out.println("¹øÈ£:"+k);
				    	System.out.println("Á¦¸ñ:"+title.get(j).text());
				    	System.out.println("°¡¼ö¸í:"+singer.get(j).text());
				    	System.out.println("¾Ù¹ü:"+album.get(j).text());
				    	System.out.println("Æ÷½ºÅÍ:"+poster.get(j).attr("src"));
				    	String s=etc.get(j).text();
				    	String state="";
				    	String idcrment="";
				    	if(s.equals("À¯Áö"))
				    	{
				    		state="À¯Áö";
				    		idcrment="0";
				    	}
				    	else
				    	{
				    		state=s.replaceAll("[^°¡-ÆR]", "");
				    		idcrment=s.replaceAll("[^0-9]", "");
				    	}
				    	System.out.println("»óÅÂ:"+state);
				    	System.out.println("µîÆø:"+idcrment);
				    	//System.out.println("Å°:"+youtubeGetKey(idcrment));
				    	
				    	GenieVO vo=new GenieVO();
				    	vo.setNo(k);
				    	vo.setTitle(title.get(j).text());
				    	vo.setSinger(singer.get(j).text());
				    	vo.setAlbum(album.get(j).text());
				    	vo.setPoster(poster.get(j).attr("src"));
				    	vo.setState(state);
				    	vo.setIdcrement(Integer.parseInt(idcrment));
				    	//dao.genieInsert(vo);
				    	System.out.println("Å°:"+youtubeGetKey(vo.getTitle()));
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
			Pattern p=Pattern.compile("/watch\\?v=[^°¡-ÆR]+");
			// . ^ , + , * , ? , | ==> \. \?... ÀÚ¹Ù´Â \\
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
