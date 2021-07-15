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
	/*
	 * NO     NOT NULL NUMBER         
	POSTER          VARCHAR2(1000) 
	TITLE           VARCHAR2(200)  
	SINGER          VARCHAR2(100)  
	ALBUM           VARCHAR2(200)  
	OK              VARCHAR2(10)   
	KEY             VARCHAR2(100)
	<td><div class="wrap">
	<a href="javascript:melon.link.goAlbumDetail('10638275');" title="MSG¿ö³Êºñ 1Áý" class="image_typeAll">
	<img onerror="WEBPOCIMG.defaultAlbumImg(this);" width="60" height="60" src="https://cdnimg.melon.co.kr/cm2/album/images/106/38/275/10638275_20210625172521_500.jpg/melon/resize/120/quality/80/optimize" alt="MSG¿ö³Êºñ 1Áý - ÆäÀÌÁö ÀÌµ¿"/>
	<span class="bg_album_frame"></span>
	</a>
	</div></td>
	<td><div class="wrap">
		<a href="javascript:melon.link.goSongDetail('33625988');" title="¹Ù¶ó¸¸ º»´Ù °îÁ¤º¸" class="btn button_icons type03 song_info"><span class="none">°îÁ¤º¸</span></a>
		</div></td>
		<td><div class="wrap">
		<div class="wrap_song_info">
								<div class="ellipsis rank01"><span>
	<a href="javascript:melon.play.playSong('19030101',33625988);" title="¹Ù¶ó¸¸ º»´Ù Àç»ý">¹Ù¶ó¸¸ º»´Ù</a>
								</span></div>
								
								<br>
								<div class="ellipsis rank02">
									
									
									<a href="javascript:melon.link.goArtistDetail('2939212');" title="MSG¿ö³Êºñ(M.O.M) - ÆäÀÌÁö ÀÌµ¿" >MSG¿ö³Êºñ(M.O.M)</a><span class="checkEllipsis" style="display:none"><a href="javascript:melon.link.goArtistDetail('2939212');" title="MSG¿ö³Êºñ(M.O.M) - ÆäÀÌÁö ÀÌµ¿" >MSG¿ö³Êºñ(M.O.M)</a></span>
								</div>
								
							</div>
						</div></td>
						<td><div class="wrap">
							<div class="wrap_song_info">
								<div class="ellipsis rank03">
									<a href="javascript:melon.link.goAlbumDetail('10638275');" title="MSG¿ö³Êºñ 1Áý - ÆäÀÌÁö ÀÌµ¿">MSG¿ö³Êºñ 1Áý</a>
								</div>
							</div>
						</div></td>
						<td><div class="wrap">
							<button type="button" class="button_etc like" title="¹Ù¶ó¸¸ º»´Ù ÁÁ¾Æ¿ä" data-song-no="33625988" data-song-menuid="19030101">
								<span class="odd_span">ÁÁ¾Æ¿ä</span>
								<span class="cnt">
									<span class="none">ÃÑ°Ç¼ö</span>
									0
								</span>
							</button>
						</div></td>
	 */
	public void melonAllData()
	{
		try
		{
			MusicDAO dao=new MusicDAO();
			int k=1;
			Document doc=Jsoup.connect("https://www.melon.com/chart/index.htm").get();
			Elements poster=doc.select("a.image_typeAll img");
			Elements title=doc.select("div.rank01 a");
			Elements singer=doc.select("div.rank02 span.checkEllipsis a");
			Elements album=doc.select("div.rank03 a");
			Elements ok=doc.select("span.cnt");
			for(int i=0;i<title.size();i++)
			{
				System.out.println("¹øÈ£:"+k);
				System.out.println("Á¦¸ñ:"+title.get(i).text());
				System.out.println("°¡¼ö:"+singer.get(i).text());
				System.out.println("¾Ù¹ü:"+album.get(i).text());
				System.out.println("ÁÁ¾Æ¿ä:"+ok.get(i).text());
				System.out.println("Æ÷½ºÅÍ:"+poster.get(i).attr("src"));
				//System.out.println("KEY:"+youtubeGetKey(title.get(i).text()));
				System.out.println("===================================");
				MelonVO vo=new MelonVO();
				vo.setNo(k);
				vo.setPoster(poster.get(i).attr("src"));
				vo.setTitle(title.get(i).text());
				vo.setSinger(singer.get(i).text());
				vo.setAlbum(album.get(i).text());
				int r=(int)(Math.random()*10000)+1000000;
				vo.setOk(String.valueOf(r));
				vo.setKey(youtubeGetKey(title.get(i).text()));
				dao.melonInsert(vo);
				k++;
			}
		}catch(Exception ex) {ex.printStackTrace();}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MusicMain m=new MusicMain();
        //m.genieAllData();
        m.melonAllData();
	}

}
