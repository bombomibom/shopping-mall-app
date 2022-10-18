// 로그인 기능
function loginAction(){
	console.log("ddd");
	console.log($("#userId").val());
	
	$.ajax({
		url : "/login_action",
		type : "POST",
		data : {
			userId : $("#userId").val(),
			userPwd : $("#userPwd").val()
		},
		success : function(result){
			alert(result);
			location.href = "/mainList.html";
		},
		error : function(){
			alert(result);
			location.href = "/";
		}
	});
	
}