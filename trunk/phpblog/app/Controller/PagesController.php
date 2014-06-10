<?php
/**
 * Static content controller.
 *
 * This file will render views from views/pages/
 *
 * PHP 5
 *
 * CakePHP(tm) : Rapid Development Framework (http://cakephp.org)
 * Copyright 2005-2012, Cake Software Foundation, Inc. (http://cakefoundation.org)
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright 2005-2012, Cake Software Foundation, Inc. (http://cakefoundation.org)
 * @link          http://cakephp.org CakePHP(tm) Project
 * @package       app.Controller
 * @since         CakePHP(tm) v 0.2.9
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 */

App::uses ( 'AppController', 'Controller' );

/**
 * Static content controller
 *
 * Override this controller by placing a copy in controllers directory of an
 * application
 *
 * @package app.Controller
 * @link http://book.cakephp.org/2.0/en/controllers/pages-controller.html
 */
class PagesController extends AppController {
	
	/**
	 * Controller name
	 *
	 * @var string
	 */
	public $name = 'Pages';
	var $title_for_layout;
	var $keywords_for_layout;
	var $description_for_layout;
	var $helpers = array (
			'Cache',
			'Html',
			'Number',
			'Time',
			'Text',
			'Form' 
	);
	/**
	 * This controller does not use a model
	 *
	 * @var array
	 */
	public $uses = array (
			"Article",
			"Tag",
			"Link",
			"Preference",
			"ArticleTags" 
	);
	public function beforeFilter() {
		// 查询多的6个tag
		$this->Tag->hasMany = array ();
		$toptags = $this->Tag->find ( "all", array (
				"order" => array (
						"Tag.tagPublishedRefCount" => "DESC" 
				),
				"limit" => 6 
		) );
		$this->set ( compact ( 'toptags' ) );
		$this->findDefaultPreference ();
	}
	
	private function findDefaultPreference() {
		// 读取默认的博客信息，并放入缓存
		$pref = $this->Preference->find ( "first", array (
				"conditions" => array (
						"Preference.oId" => "preference" 
				) 
		) );
		$this->title_for_layout = $title_for_layout = $pref ["Preference"] ["blogTitle"];
		$this->keywords_for_layout = $keywords_for_layout = $pref ["Preference"] ["metaKeywords"];
		$this->description_for_layout = $description_for_layout = $pref ["Preference"] ["metaDescription"];
		$auth_for_layout = $pref ["Preference"] ["adminEmail"];
		$this->set ( compact ( 'title_for_layout', 'keywords_for_layout', 'description_for_layout', 'auth_for_layout' ) );
	}
	/**
	 * Displays a view
	 *
	 * @param
	 *        	mixed What page to display
	 * @return void
	 */
	public function display() {
		$path = func_get_args ();
		
		$count = count ( $path );
		if (! $count) {
			$this->redirect ( '/' );
		}
		$title_for_layout = null;
		$page = 1;
		
		if (! empty ( $path [0] )) {
			$page = intval ( $path [0] );
		}
		// $this->render(implode('/', $path));
		$this->findBlogByPage ( $page );
	}
	public function crawl(){
		$url=$this->request->data("url");
		$content = file_get_contents($url);
		echo $content;
		die();
	}
	/**
	 * /articles/2013/08/20/1376965022626.html
	 * /blog/123456
	 */
	public function article( $id = 0) {
		if (! empty ( $id )) {
			$id = h ( $id );
			$article = $this->Article->find ( "first", array (
					"conditions" => array (
							"Article.oId" => $id 
					) 
			) );
			$tag = "";
			if (! empty ( $article )) {
				// 增加浏览次数
				$this->Article->id = $id;
				$this->Article->saveField ( "articleViewCount", $article ["Article"] ["articleViewCount"] + 1 );
				// 查询相同标签的内容，6条数据
				$tag = $article ["Article"] ["articleTags"];
			}
			$otherArticle = $this->findArticleByTag ( $tag, $id );
			$this->set ( compact ( 'article', 'otherArticle' ) );
			
			$title_for_layout = $article ["Article"] ["articleTitle"] . " -- " . $this->title_for_layout;
			$keywords_for_layout = $article ["Article"] ["articleTags"];
			App::uses ( "String", "Utility" );
			$description = String::excerpt ( strip_tags ( $article ["Article"] ["articleAbstract"] ), null, 190 );
			$description_for_layout = $description;
			$this->set ( compact ( 'title_for_layout', 'keywords_for_layout', 'description_for_layout' ) );
		}
	}
	private function findArticleByTag($tag, $oid) {
		$sql = "";
		if (! empty ( $tag )) {
			$tags = explode ( ",", $tag );
			$tag_count = count ( $tags );
			foreach ( $tags as $k => $ta ) {
				$sql .= " Article.articleTags like '%{$ta}%'";
				if ($k < $tag_count-1) {
					$sql .= " or ";
				}
			}
		}
		$tag_sql = ! empty ( $sql ) ? "(" . $sql . ")" : "";
		$articles = $this->Article->find ( "all", array (
				"conditions" => array (
						"Article.oId <>" => $oid,
						"Article.articleIsPublished" => 1,
						$tag_sql 
				),
				"limit" => "6" 
		) );
		return $articles;
	
	}
	/**
	 * 友情链接
	 */
	public function link() {
		$links = $this->Link->find ( "all", array (
				"order" => array (
						"Link.linkOrder" => "ASC" 
				) 
		) );
		$title_for_layout = "友情链接 -- " . $this->title_for_layout;
		$this->set ( compact ( 'links', 'title_for_layout' ) );
	}
	public function about() {
		$title_for_layout = "关于我 -- " . $this->title_for_layout;
		$this->set ( compact ( 'title_for_layout' ) );
	}
	/**
	 * 查询所有标签
	 */
	public function tags() {
		$alltag = $this->Tag->find ( "all" );
		$this->set ( compact ( 'alltag' ) );
	}
	public function tag() {
// 		$path = func_get_args ();
		
// 		$count = count ( $path );
		$title_for_layout = null;
		$tag=!empty($_REQUEST["name"])?h($_REQUEST["name"]):"";
		$page=!empty($_REQUEST["p"])?intval($_REQUEST["p"]):1;
		if (empty($tag)) {
			$this->redirect ( '/' );
		}
		$title_for_layout = Inflector::humanize ( $tag ) . " -- " . $this->title_for_layout;
		$this->set ( compact ( 'tag', 'title_for_layout' ) );
		$this->findBlogByTagePage ( $page, $tag ,10);
	}
	/**
	 * 分页查询文章
	 */
	private function findBlogByPage($page, $pageSize = 5) {
		if ($page < 1) {
			$page = 1;
		}
		$options = array (
				"conditions" => array (
						"Article.articleIsPublished" => 1 
				),
				"order" => array (
						"Article.articlePutTop" => "DESC",
						"Article.oId" => "DESC" 
				) 
		);
		$this->Article->hasMany = array ();
		$sumcount = $this->Article->find ( "count", $options );
		$data = array ();
		$sumPage = 0;
		if ($sumcount > 0) {
			$sumPage = ceil ( $sumcount / $pageSize );
			$limit = ($page - 1) * $pageSize . "," . $pageSize;
			$options ["limit"] = $limit;
			$options ["fields"] = array (
					"Article.oId",
					"Article.articleTitle",
					"Article.articleAbstract",
					"Article.articleTags",
					"Article.articlePermalink",
					"Article.articleCreateDate",
					"Article.articleUpdateDate",
					"Article.articleCommentable",
					"Article.articleViewPwd" 
			);
			$data = $this->Article->find ( "all", $options );
		}
		$this->set ( compact ( 'sumcount', 'page', 'sumPage', 'data', 'pageSize' ) );
	}
	/**
	 * 根据tag分页查询
	 */
	private function findBlogByTagePage($page, $tag, $pageSize = 5) {
		$options = array (
				"conditions" => array (
						"Tag.tagTitle" => $tag 
				) 
		);
		$sumcount = $this->ArticleTags->find ( "count", $options );
		$data = array ();
		$sumPage = 0;
		if ($sumcount > 0) {
			$sumPage = ceil ( $sumcount / $pageSize );
			$limit = ($page - 1) * $pageSize . "," . $pageSize;
			$options ["limit"] = $limit;
			$options ["fields"] = array (
					"Article.oId",
					"Article.articleTitle",
					"Article.articleAbstract",
					"Article.articleTags",
					"Article.articlePermalink",
					"Article.articleCreateDate",
					"Article.articleUpdateDate",
					"Article.articleCommentable",
					"Article.articleViewPwd" 
			);
			$data = $this->ArticleTags->find ( "all", $options );
		}
		$this->set ( compact ( 'sumcount', 'page', 'sumPage', 'data', 'pageSize' ) );
	}
}
