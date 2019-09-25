$(function() {
	$(".treeview-menu li a").click(function () {
		var href = $(this).attr("data-href");
		if(href){
			$(".content-wrapper .content").load(href);
		}
	});
});

