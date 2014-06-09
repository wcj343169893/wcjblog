<div id="article_list">
<!--logo end-->
<aside class="list_area f_right" id="down">
   	<?php if(!empty($sumcount)){?>
   	<?php foreach ($data as $article){?>
    <section class="list relative item"><?php $link="/blog/".$article["Article"]["oId"];?>
		<h2 class="t"><?php echo $this->Html->link($article["Article"]["articleTitle"],$link)?></h2>
         <blockquote class="cont">
         	<?php echo $article["Article"]["articleAbstract"];?>
         	<?php echo $this->Html->link("查看全文",$link);?>
         </blockquote>
         <footer class="article">
         	<span>Tags:</span>
             <?php echo $this->element("article_tags",array("data"=>$article["Article"]["articleTags"]))?>
         </footer>
		<time class="time"><?php echo $this->Time->format("Y-m-d",$article["Article"]["articleCreateDate"])?></time>
     </section>
     <?php }?>
     <?php }?>
</aside>
<div id="page_nav" style="display:none;">
	<?php if($page<$sumPage){
		$nextPage=$page+1;
			echo $this->Html->link($nextPage,array('controller' => 'pages', 'action' => 'display',"ext"=>"html",$nextPage));
	}?>
</div>
</div>
<script type="text/javascript" src="/js/jquery.masonry.js"></script>
<script type="text/javascript" src="/js/jquery.infinitescroll.min.js"></script>
<script type="text/javascript">
$(function(){
    var speed = 1000;
    $("#down").masonry({
       singleMode: true,
       columnWidth: 590,
       itemSelector: '.item',
       animate: false,
       animationOptions: {
           duration: 500,
           easing: 'linear',
           queue: false
       }
   });
   
   $("#down").infinitescroll({
	   loading: {
	        finished: undefined,
	        finishedMsg: "<em>加载完成</em>",
	        img: "/img/003.gif",
	        msg: null,
	        msgText: "<em>正在努力加载中...</em>",
	        selector: null,
	        speed: 'fast',
	        start: undefined
	    },
       navSelector : '#page_nav', // selector for the paged navigation
       nextSelector : '#page_nav a', // selector for the NEXT link (to page 2)
       itemSelector : '.item', // selector for all items you'll retrieve
       debug: true,
       behavior: 'local',
       binder: $("#article_list"),
       errorCallback: function() {
       // fade out the error message after 2 seconds
       //$('#infscr-loading').animate({opacity: .8},2000).fadeOut('normal');
       }},
       // call masonry as a callback.
       function( newElements ) { $(this).masonry({ appendedContent: $(newElements) });
        }
   );
   setTimeout("initScroll()",500);
  
});
function initScroll(){
	 $("#article_list").niceScroll({cursorcolor:"#1cbdc5",cursorwidth:"4px",cursorborder:"0"});
}
</script>