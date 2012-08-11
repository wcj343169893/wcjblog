//By ImMmMm.com
jQuery(document).ready(function($){
var name = "wcj343169893@163.com" //修改成你的Google用户名
var numx = 10; //每页显示相册数量
var top= $('#navi').offset().top-20; //页面刷动到分页，可修改
$body=(window.opera)?(document.compatMode=="CSS1Compat"?$('html'):$('body')):$('html,body');var href="https://picasaweb.google.com/data/feed/api/user/"+name;$.getJSON(href+"?fields=openSearch:totalResults&alt=json&callback=?",function(c){c=parseInt(c.feed.openSearch$totalResults.$t/numx)+1;for(var b=1;b<=c;){$("#navi").append("<a href='"+b+"'> 相册 "+b+" </a> &nbsp; ");b++}$("#navi a").eq(1).addClass("current")});
$("#navi a").live("click",function(){$("#items").fadeOut(500);$(this).addClass("current").siblings().removeClass();var c=$(this).attr("href")-1;$.getJSON(href+"?start-index="+(numx*c+1)+"&max-results="+numx+"&fields=entry(title,gphoto:id,gphoto:numphotos,media:group(media:thumbnail))&alt=json&callback=?",function(b){$("#items").empty();$body.animate({scrollTop:top},400);$(b.feed.entry).each(function(g,d){var f=d.title.$t,e=d.gphoto$id.$t,n=d.gphoto$numphotos.$t,h=d.media$group.media$thumbnail[0].url;$("#items").append("<div class='item'><div class='thumb'><img src='"+
h+"' id='"+e+"'/><br /><span>"+f+" ("+n+")</span></div></div>")});$("#items").fadeIn(400)});return false});$("#navi a").click();
$(".thumb img").live("click",function(){$("#items").fadeOut(500);var c=$(this).attr("id");$.getJSON(href+"/albumid/"+c+"?fields=entry(media:group(media:content,media:title))&alt=json&callback=?",function(b){$("#items").empty();$body.animate({scrollTop:top},400);$(b.feed.entry).each(function(g,d){a=d.media$group;var f=a.media$title.$t,e=a.media$content[0].url;$("#items").append("<div class='item'><div class='thumb-1'><a href='"+e+"?imgmax=800'><img src='"+e+"?imgmax=118'/></a><br /><span>"+f+"</span></div></div>")});
$("#items").fadeIn(500);$(".thumb-1 a:has(img)").slimbox()});return false});
});