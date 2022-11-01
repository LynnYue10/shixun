/**
 * Created by Amy on 2018/8/8.
 */
var userName = getCookie('userName');

var answerName = getCookie('answerName');

var answerId = getCookie('answerId');

var groupname = getCookie('answerGroupname');

$(function () {
    isLoginFun();
    userheader();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    
    $("#answerName").val(answerName);    
	
    $("#ifRemand").css('display','none');
	getGroups();
});

//查看群组
function getGroups() {

    var userName = getCookie("userName");
    var url = '/answer/queryGroups';
    var data = null;
	commonAjaxPost(true, url, data, getGroupsSuccess);
}

// 查看群组成功回调
function getGroupsSuccess(result) {
    if (result.code == "666") {
        var data = result.data;
		console.log(data);
        $("#panel-23802").empty();
		var answerGroup = document.getElementById('answerGroup');
		answerGroup.options.length = 0;
		//var groups = document.createElement("select");
        if (data.length) {
            for (var i = 0; i < data.length; i++) {
				var GroupInfo = data[i];
				var GroupName = GroupInfo.groupName;
				var option = new Option(GroupName)
    			//groups.options[i] = new Option(GroupName,"");	
				answerGroup.options.add(option);
            }
			//groups.size = data.length;
			//answerGroup.appendChild(groups);

        } else {
            layer.msg("暂无可选群组", {icon: 0})
        }

    } else if (result.code == "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'answerManage.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2})
    }

}

//点击“保存修改”，编辑答者
function modifyAnswer() {
    var answerNameInt = $("#answerName").val();
	//var answerGroup = document.getElementById('#answerGroup').value;
    var answerGroup = $("#answerGroup").val();
	console.log(answerGroup);
	//getGroupid();
	//var groupid = getCookie('groupid');
    if (answerNameInt.trim() == '') {
        layer.msg('请填写答者名')
    } else {
        var url = '/answer/modifyAnswer';
        var data = {
            "id": answerId,
            "answername": answerNameInt,
			"groupname":answerGroup,
        };
        commonAjaxPost(true, url, data, modifyAnswerSuccess);
    }
}

//修改答者信息成功
function modifyAnswerSuccess(result) {
    if (result.code == '666') {
        layer.msg('修改成功', {icon: 1});
        setTimeout(function () {
            window.location.href = 'answerManage.html';
        }, 1000);
    } else if (result.code == "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'answerManage.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2});
    }
}


