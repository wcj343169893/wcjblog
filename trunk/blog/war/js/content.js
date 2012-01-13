var editor_id = 'content';
KE.init({
	id : editor_id,
	width : '600px',
	height : '300px'
});
KE.event.ready(function(){
	var textarea = document.createElement('textarea');
	var default_content=document.getElementById("vito-content-title");
	textarea.innerHTML=default_content? "我觉得“"+default_content.innerHTML+"”":"  ";
	textarea.id = editor_id;
	textarea.name = "content";
	document.getElementById('content-div').appendChild(textarea);
	KE.create(editor_id);
});