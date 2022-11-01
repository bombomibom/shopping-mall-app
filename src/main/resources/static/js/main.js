
/**
 * post 타입으로 백엔드에 데이터를 전달한다.
 * @param targetWrap - 
 * @param url - 
 * @desc 
 */
function requestPostAjax(targetWrap, url){
	//console.log(targetWrap);
	//console.log(url);
	var columnNameList = [];
	var columnValueList = [];
	var dataObject = new Object();
	
	targetWrap.find("input").each(function(){
		if($(this).attr('type') == 'text' || $(this).attr('type') == 'password'){
			//console.log($(this));
			
			var columnName = $(this).attr("id");
			var columnValue = $(this).val();
			
			//console.log(columnName);
			//console.log(columnValue);
			columnNameList.push(columnName);
			columnValueList.push(columnValue);
		}
	})
	
	for(let i = 0; i < columnNameList.length; i++){
		dataObject[columnNameList[i]] = columnValueList[i];
	}
	console.log(dataObject);
	
	// success 부분 수정 예정
	$.ajax({
		url : url,
		type : "POST",
		data : dataObject,
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

/**
 * AJAX로 백엔드에 로그아웃을 요청하고, 백엔드에서는 세션에 저장된 정보를 제거한다.
 * @desc "로그아웃 하시겠습니까?" 경고창과 함께 백엔드로 요청. 성공시 result를 담은 경고창이 뜨고 페이지 이동한다.
 */
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

/**
 * 유저 유형에 따른 폼 내의 아이디 중복 결과를 출력한다.
 * @param checkDuplicateBtn - 사용자가 클릭한 중복확인 버튼
 * @desc  유저 아이디란이 비어있을 경우 경고창이 뜬다. 아닌 경우엔 AJAX POST 방식으로 서버에 데이터 전달 후 중복조회 결과를 출력시킨다.
 */
function checkDuplicateUserId(checkDuplicateBtn){
	//console.log(checkDuplicateBtn);
	//console.log(checkDuplicateBtn.prev("input").val());
	
	if(checkDuplicateBtn.prev("input").val() == ""){
		alert("아이디를 입력해주세요.");
	} else {
		$.ajax({
			url : "/check_duplicate_userid_action",
			type : "POST",
			data : {
				id : checkDuplicateBtn.prev("input").val()
			},
			success : function(result){
				alert(result);
				if(result == "이미 존재하는 아이디입니다."){
					checkDuplicateBtn.prev("input").val("");
				} else {
					return(clickCntDuplicateBtn++);
				}
			},
			error : function(){
				
			}
		});
	}
}


/**
 * 
 * @desc 
 */
function whenInputchange(){
	var requiredFillInputLen = 0;
	var fillInputLen = 0;
	
	$("input").each(function(){
		//console.log($(this));
		if($(this).attr('type') == 'text' || $(this).attr('type') == 'password'){
			requiredFillInputLen++;
			if($(this).val() != ""){
				fillInputLen++;
			}
		}
	})
	
	if(requiredFillInputLen == fillInputLen){
		$(".confirm-btn").addClass('activate-btn');
	} else {
		$(".confirm-btn").removeClass('activate-btn');
	}
}




/**
 * 
 * @param targetWrap - 
 * @param url -
 * @desc 
 */
function confirmInput(targetWrap, url){

	var errorMsgLen = 0;
	
	// step 1 : 정보입력란이 있는 div 내의 input 개수만큼 조회
	targetWrap.find("input").each(function(){
		
		// step 2 : 중복확인 클릭여부 확인
		if($(this).attr("value") == '중복확인'){
			if(clickCntDuplicateBtn == 0){
				errorMsgLen++;
				alert("아이디 중복확인을 해주세요.");
				return false;
			}
		}
		
		// step 3 : 비어있는 입력란 확인
		if($(this).val() == ""){
			errorMsgLen++;
			$(this).css({
				'border':'1px solid red'
			})
			if($(this).siblings(".error-msg").length == 1){
				$(this).siblings(".error-msg").text($(this).attr("placeholder"));
			} else {
				$(this).parent().siblings(".error-msg").text($(this).attr("placeholder"));
			}
			return false;
		} else {
			$(this).css({
				'border':'1px solid #ccc'
			})
			if($(this).siblings(".error-msg").length == 1){
				$(this).siblings(".error-msg").text("");
			} else {
				$(this).parent().siblings(".error-msg").text("");
			}
		}
		
		// step 4 : 최소값 확인
		if($(this).attr('minLength') > $(this).val().length){
			errorMsgLen++;
			//console.log($(this).val().length);
			$(this).css({
				'border':'1px solid red'
			})
			if($(this).siblings(".error-msg").length == 1){
				$(this).siblings(".error-msg").text("최소 " + $(this).attr('minLength') + "자 이상 입력해주세요.");
			} else {
				$(this).parent().siblings(".error-msg").text("최소 " + $(this).attr('minLength') + "자 이상 입력해주세요.");
			}
			return false;
		}
		
	})

	// step 5 : 에러 문구가 하나도 없을 때 ajax 통신
	console.log(errorMsgLen);
	if(errorMsgLen == 0){
		return requestPostAjax(targetWrap, url);
	}

	
}




