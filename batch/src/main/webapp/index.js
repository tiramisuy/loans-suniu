	var hide;
	var leftWidth;
	var middleLeft;
	var rightLeft;
	var minBodyWidth=1024;	 //最小宽度
	//窗口缩放时处理
	window.onresize=function(){
		ctrMinWidth();
		setCenterHeight();
	}
	
	function clickShowHide(){
		$("#sep-img").click(function(){
			if(hide){
				$('#center-left').animate({width:'230px'},300);
				$('#center-right').animate({'margin-left':'240px'},300);
				//$('#center-middle').animate({'left':'230px'},300);
				$(this).attr("alt","隐藏侧栏");
			}else{	
				$('#center-left').animate({width:'0px'},300);
				$('#center-right').css({'margin-left':'0px'});
				$('#center-middle').animate({'left':'2px'},300);	
				$(this).attr("alt","显示侧栏");
			}
			hide = hide?false:true;			
		});	
	}
	//控制最小宽度
	function ctrMinWidth(){
		var sw=$(window).width();
		
		if(sw<=minBodyWidth){
			$('body').css({
				'width':minBodyWidth+'px',
				'overflow-x':'scroll'
			});
			$('html').css('overflow-x','scroll');
			$('#header').css({
				'width':minBodyWidth+'px'
			});
			$('#footer').css({
				'width':minBodyWidth+'px'
			});
			$('#center').css({
				'width':minBodyWidth+'px'
			});
		}
		else{
			$('body').css({
				'width':'100%',
				'overflow-x':'hidden'
			});
			$('html').css('overflow-x','hidden');
			$('#header').css({
				'width':'100%'
			});
			$('#footer').css({
				'width':'100%'
			});
			$('#center').css({
				'width':'100%'
			});
		}		
	}
	
	//设置中间高度
	function setCenterHeight(){
		var _theH;
		$("#center").height($(window).height()-$("#header").height()-$("#footer").height());
		_theH=$("#center").height();
		$('#leftFrame').css('height',_theH+'px');
		$('#rightFrame').css('height',_theH+'px');
	}
	
	//加载后执行
	$(function(){			
		ctrMinWidth();
		//设置高度
		setCenterHeight();	
		//显示隐藏事件
		clickShowHide();				
	});	