<span>
<?php if(!empty($data)){?>
	<?php 
		$tags = explode(" ", $data);
		foreach($tags as $ta){
			echo '<a href="/tag/'.urlencode($ta).'.html" title="">'.$ta.'</a> ';
		}
	?>
<?php }?>
</span> 