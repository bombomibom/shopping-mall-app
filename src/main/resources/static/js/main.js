// 로그인
function loginAction(){
	
	console.log($("#userId").val());
	console.log($("#userPw").val());
	
	if($("#userId").val() == ""){
		alert("아이디를 입력해주세요.");
	} else if ($("#userPw").val() == ""){
		alert("비밀번호를 입력해주세요.");
	} else {
		$.ajax({
			url : "/login_action",
			type : "POST",
			data : {
				id : $("#user-id").val(),
				pwd : $("#user-pwd").val()
			},
			success : function(result){
				alert(result);
				if(result == '로그인 성공'){
					location.href = "/main";
				} else {
					location.href = "/";
				}
			},
			error : function(){
				
			}
		});
	}
}

// 로그아웃
function logoutAction(){
	var yesNoQuestion = confirm("로그아웃 하시겠습니까?");
	if(yesNoQuestion){
		$.ajax({
			url : "/logout_action",
			type : "GET",
			success : function(result){
				alert(result);
				location.href = "/";
			},
			error : function(){
				
			}
		});	
	}
}

// 아이디 중복체크
function checkId(){
	console.log($("#user-id").val());
	
	if($("#user-id").val() == ""){
		alert("아이디를 입력해주세요.");
	} else {
		$.ajax({
			url : "/check_id_action",
			type : "POST",
			data : {
				id : $("#user-id").val()
			},
			success : function(result){
				alert(result);
				if(result == "이미 존재하는 아이디입니다."){
					$("#user-id").val("");
				} else {
					
				}
			},
			error : function(){
				
			}
		});
	}
	
}

// 회원가입
function signupAction(){
	//console.log($("input"));
	//console.log($("input").val());
	var thisVal = "";
	$("input").each(function(){
		if($(this).attr("type") != "radio"){
			console.log($(this).val());
			if($(this).val() == ""){
				//console.log($(this));
				thisVal = $(this).attr("id");
				return false;
			}
		}
	})
	console.log(thisVal);
	$("#" + thisVal).css({
		"box-shadow":"red 0 0 2px"
	})
	
	//console.log($("#" + thisVal));
	//console.log($("#" + thisVal).siblings(".error_msg"));
	
	if($("#" + thisVal).siblings(".error_msg").length == 1){
		$("#" + thisVal).siblings(".error_msg").text($("#" + thisVal).attr("placeholder"));
	} else {
		$("#" + thisVal).parent().siblings(".error_msg").text($("#" + thisVal).attr("placeholder"));
	}
	
	
	
}