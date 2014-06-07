<span>
<?php if(!empty($data)){?>
	<?php 
		$tags = explode(",", $data);
		foreach($tags as $ta){
			echo '<a href="/tag/'.$ta.'" title="">'.$ta.'</a> ';
		}
	?>
<?php }?>
</span> 