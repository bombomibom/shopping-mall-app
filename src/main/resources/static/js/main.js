// 로그인 기능
function loginAction(){
	console.log("ddd");
	console.log($("#userId").val());
	
	$.ajax({
		url : "/login_action",
		type : "POST",
		data : {
			userId : $("#userId").val(),
			userPw : $("#userPw").val()
		},
		success : function(result){
			alert(result);
			
			if(result == '로그인 성공'){
				location.href = "/mainList";
			} else {
				location.href = "/";
			}
		},
		error : function(){
			
		}
	});
	
}