<?php 
	if(!empty($article)){
		
	}else{
		
	}
?>
<aside class="detailed f_right" id="down">
	<?php if(!empty($article)){?>
 		<h3 class="d_t"  id="top"><?php echo $article["Article"]["articleTitle"]?></h3>
        <section class="info">
			<p><time><?php echo $this->Time->format("Y-m-d",$article["Article"]["articleCreateDate"])?></time> 
			<span>超过<a><?php echo $article["Article"]["articleViewCount"]?></a>位围观</span></p>
        </section>
        <section class="entry w90">
        	<?php echo $article["Article"]["articleContent"]?>
        </section>
	<?php }?>
        <section class="w90 share">
            <!-- Baidu Button BEGIN -->
            <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare">
            <a class="bds_qzone"></a>
            <a class="bds_tsina"></a>
            <a class="bds_tqq"></a>
            <a class="bds_renren"></a>
            <a class="bds_t163"></a>
            <span class="bds_more"></span>
            <a class="shareCount"></a>
            </div>
            <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6657706" ></script>
            <script type="text/javascript" id="bdshell_js"></script>
            <script type="text/javascript">
            document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
            </script>
            <!-- Baidu Button END -->
        </section>
        <section class="w90 related">
        	<dl>
            	<dt class="r_t">您可能也感兴趣的：</dt>
            	<?php if(!empty($otherArticle)){foreach ($otherArticle as $article){?><?php $link="/blog/".$article["Article"]["oId"];?>
                    <dd><?php echo $this->Html->link($article["Article"]["articleTitle"],$link)?></dd>
                    <?php }}?>
            </dl>
        </section>
        <section class="comments w90 t-20">
            <!-- Duoshuo Comment BEGIN -->
            <div class="ds-thread"></div>
            <script type="text/javascript">
            var duoshuoQuery = {short_name:"r-yang"};
                (function() {
                    var ds = document.createElement('script');
                    ds.type = 'text/javascript';ds.async = true;
                    ds.src = 'http://static.duoshuo.com/embed.js';
                    ds.charset = 'UTF-8';
                    (document.getElementsByTagName('head')[0] 
                    || document.getElementsByTagName('body')[0]).appendChild(ds);
                })();
                </script>
            <!-- Duoshuo Comment END -->
        </section>
    </aside>
<script type="text/javascript">
$(function(){
$("#down").niceScroll({cursorcolor:"#1cbdc5",cursorwidth:"4px",cursorborder:"0"});
});
</script>