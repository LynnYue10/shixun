/**
 * Created by Amy on 2018/8/6.
 */
var flag = true;
var answerName = '';
var password = '';

$(function () {
    isLoginFun();
    userheader();
    $("#ctl01_lblUserName").text(getCookie('userName'));
});

//点击“立即创建”，创建项目
function createAnswer() {
    answerName = $("#answerName").val();
    password = $("#password").val();
    createAnswerRight();
}

function createAnswerRight() {
    if (flag == true) {
        if (answerName.trim() == '') {
            layer.msg('请填写答者名')
        } else if (password.trim() == '') {
            layer.msg('请填写密码')
        } else {
            var userName = getCookie("userName");         
            var url = '/answer/addAnswer';
            var data = {
	            
                "answername": answerName,
                "password": password,
                "username": userName
                
            };
            commonAjaxPost(false, url, data, function (result) {
                //console.log(result)
                if (result.code == "666") {
                    layer.msg('创建成功');
                    setTimeout(function () {
                        window.location.href = "answerManage.html";
                    }, 700)
                } else if (result.code == "333") {
                    layer.msg(result.message, {icon: 2});
                    setTimeout(function () {
                        window.location.href = 'login.html';
                    }, 1000)
                } else if (result.code == "111") {
                    layer.msg("答者已存在，请重新输入！", {icon: 2});
                }
                else {
                    layer.msg(result.message, {icon: 2})
                }
            });
        }
    }
}
