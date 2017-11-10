layui.config({
	base : "static/js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element(),
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	})

	$.get("article/queryCount.do",
		function(data){
            $(".articleAll span").text(data.count);
		}
	)

	//待处理摄影
	$.get("photograph/queryHandlingCount.do",
		function(data){
			$(".photograph span").text(data.count);
		}
	)

	//待处理摄影评论
	$.get("photograph/comment/queryHandlingCount.do",
		function(data){
			$(".photographComment span").text(data.count);
		}
	)

	//待处理反馈
	$.get("feedback/getHandlingCount.do",
		function(data){
			$(".feedback span").text(data.count);
		}
	)

    //用户总数
    $.get("user/getCount.do",
        function(data){
            $(".userAll span").text(data.count);
        }
    )

    //待处理评论
    $.get("comment/queryHandlingCount.do",
        function(data){
            $(".comment span").text(data.count);
        }
    )

})
