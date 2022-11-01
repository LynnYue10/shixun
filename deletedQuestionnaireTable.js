var username = getCookie('userName')
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}


$(function () {
    isLoginFun();
    userheader();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    var oTable = new relatedQuestionnaireTableInit();
    oTable.Init();
});

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        getQuestionnaireList();
    }
});

/*$('#questionnaireManager').on("keydown", function (event) {
    var keyCode = event.keyCode || event.which;
    if (keyCode == "13") {
        //console.log("1111")
        event.preventDefault();
    }
});*/

function getQuestionnaireList() {
    $('#questionnaireTable').bootstrapTable('refresh');
}

window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        id = row.id;
        $.cookie('questionId', id);
    }
};

function addFunctionAlty(value, row, index) {
    // console.log(row);
    var btnText = '';

    if (row.questionStop === "0") {
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeQuestionnaireStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">关闭</button>&nbsp;&nbsp;";
    } else if (row.questionStop === "5"){
        btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"changeQuestionnaireStatus(" + "'" + row.id + "'" + ")\" class=\"btn btn-success-g ajax-link\">开启</button>&nbsp;&nbsp;"
    }

    btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteQuestionnaireCompletely(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";

	btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"RecoverDeletedQuestionnaire(" + "'" + row.id + "'" + ")\" class=\"btn btn-success-g ajax-link\">恢复</button>&nbsp;&nbsp;";

    return btnText;
}

function relatedQuestionnaireTableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#questionnaireTable').bootstrapTable({
            url: httpRequestUrl + '/showAllDeletedQuestionnaireInfo',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "desc",                   //排序方式
            sortable: true,
            sortName: "creation_date",
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列

            columns: [{
                checkbox: true,
                visible: false
            }, {
                field: 'id',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'questionName',
                    title: '试卷名称',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'questionContent',
                    title: '问卷描述',
                    align: 'center'
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                console.log(res.data.list);
                if (res.code == "666") {
                    var userInfo = res.data.list;
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                "questionName": '',
								 'questionContent': '',
                                'startTime': ''
                            };
                            dataNewObj.id = userInfo[i].id;
                            dataNewObj.questionName = userInfo[i].questionName;
							dataNewObj.questionContent = userInfo[i].questionContent;
                            /*dataNewObj.releaseTime = userInfo[i].releaseTime;*/
                            dataNewObj.questionStop = userInfo[i].questionStop;
/*                            dataNewObj.status =dataNewObj.status = getStatus(
                                userInfo[i].questionStop, userInfo[i].startTime,
                                userInfo[i].endTime, userInfo[i].releaseTime
                            );*/
							dataNewObj.status = userInfo[i].groupname;
                            NewData.push(dataNewObj);
                        }
                        //console.log(NewData)
                    }
                    var data = {
                        total: res.data.total,
                        rows: NewData
                    };

                    return data;
                }

            }

        });
    };

    // 得到查询的参数
    function queryParams(params) {
        // console.log(params);
        var questionnaireName = $("#keyWord").val();
        //console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
			questionnaireName:$("#keyWord").val()
            // orderBy: params.sortName,
            // sort: params.sortOrder
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}



//删除问卷(修改状态为已被删除)

function RecoverDeletedQuestionnaire(questionnaireId){
/*	    if(isQuestionnaireOpenOrReleased(questionnaireId)) {
        return;
    }*/


		layer.confirm('您确认要恢复此问卷吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
 commonAjaxPost(false, "/queryDeletedQuestionnaireById", {id: questionnaireId}, function (result) {
        if (result.code === "666") {
            var data = result.data;
			var da ={
				"id" : data.id,
				"questionName":data.questionName,
				"questionContent":data.questionContent,
				"startTime":data.startTime,
				"endTime":data.endTime,
				"status":data.status,
				"projectId":data.projectId,
				"releaseTime":data.releaseTime,
				"dataId":data.dataId,
				"question":data.question,
				"questionTitle":data.questionTitle,
				"isdelete":'0',
				"groupname":data.groupname,
				"username":data.username
			};
			commonAjaxPost(true,'/RecoverQuestionnaire',da,function(result){
			if (result.code == '666') {
       				 layer.msg(result.message, {icon: 1});
     			     $("#questionnaireTable").bootstrapTable('refresh');
    			} else if (result.code == "333") {
      				  layer.msg(result.message, {icon: 2});
       				setTimeout(function () {
        			window.location.href = 'login.html';
       				 }, 1000)
   			 } else {
        				alert("问卷已发布，无法更改！");
   		 		}
			});
			
        } else {
            layer.msg(result.msg);
        }
    });
    }, function () {
    });

   
}


// 彻底删除问卷
function deleteQuestionnaireCompletely(questionnaireId) {
/*    if(isQuestionnaireOpenOrReleased(questionnaireId)) {
        return;
    }
*/
    layer.confirm('您确认要永久删除此问卷吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/deleteQuestionnaireInfoCompletely';
        commonAjaxPost(true, url, {'id': questionnaireId}, function (result) {
            if (result.code === "666") {
                layer.msg(result.message, {icon: 1});
                $("#questionnaireTable").bootstrapTable('refresh');
            } else if (result.code === "333") {
                layer.msg(result.message, {icon: 2});
                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 1000);
            } else {
                layer.msg(result.message, {icon: 2});
            }
        });
    }, function () {
    });

}



//创建问卷
function createQuestionnaire() {
    //alert("创建问卷")
/*	deleteCookie("projectId");
	deleteCookie("projectName");
    setCookie("projectId", id);
	setCookie("projectName", name);*/
	setCookie('username',username);
    window.location.href = "createQuestionnaire.html"
}

//查询问卷
function queryQuestionnaireByName(){
	
	
}
