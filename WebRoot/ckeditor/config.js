/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	//字体.

	config.font_names = '宋体;楷体_GB2312;新宋体;黑体;隶书;幼圆;微软雅黑;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana;' ;

	//工具按钮.
	config.filebrowserWindowWidth = '640';
	config.filebrowserWindowHeight = '480';
	config.toolbar_A =
		[
			['Source'],
			['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
			['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
			'/',
			['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
			['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			['Link','Unlink','Anchor'],
			['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
			'/',
			['Styles','Format','Font','FontSize'],
			['TextColor','BGColor'],
			['Maximize', 'ShowBlocks']
		];
	config.toolbar = 'A';
/*
	config.toolbar=

	[

	['Save','NewPage','Preview','-','Templates'],

	['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print','SpellChecker','Scayt'],

	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],

	['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
	
	'/',

	['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],

	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],

	['Link','Unlink'],

	['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar',

	'PageBreak'],

	'/',

	['Styles','Format','Font','FontSize'],

	['TextColor','BGColor'],

	['Maximize','ShowBlocks']

	];*/

	//宽度

	config.width ="800px";

	//高度

	config.height ="200px";

	//皮肤
	config.skin="office2003";
	
	//不能改变输入框的大小
	config.resize_enabled = false;
	//字体默认大小 plugins/font/plugin.js
	//config.fontSize_defaultLabel = '20px';

	//对应的表情图片 plugins/smiley/plugin.js
/*    config.smiley_images = [
	    '001.gif','002.gif','003.gif','004.gif','005.gif','024.gif',
	    '006.gif','007.gif','008.gif','009.gif','010.gif','011.gif',
	    '012.gif','013.gif','014.gif','015.gif','016.gif','017.gif',
	    '018.gif','019.gif','020.gif','021.gif','022.gif','023.gif'];*/

	//表情的地址 plugins/smiley/plugin.js
	//config.smiley_path = 'http://localhost/bbs/ckeditor/plugins/smiley/custom/';
	//使用的工具栏 plugins/toolbar/plugin.js
	//config.toolbar = 'Basic';
	//界面编辑框的背景色 plugins/dialog/plugin.js
	//config.dialog_backgroundCoverColor = 'rgb(255,0, 180)'; 
	//当回车时是产生BR标记还是P或者DIV标记
	//UseBROnCarriageReturn=true;
};
