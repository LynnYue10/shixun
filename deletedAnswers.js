/**
 * Created by Amy on 2018/8/9.
 */
var userName = getCookie('userName');

$(function () {
    isLoginFun();
    userheader();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    var oTable = new TableInit();
    oTable.Init();
});

//回车事件
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        getDeletedAnswerList();
		//getSearchAnswerList();
    }
});

/*$('#answerManager').on("keydown", function (event) {
    var keyCode = event.keyCode || event.which;
    if (keyCode == "13") {
        //console.log("1111")
        event.preventDefault();
    }
});*/

function getDeletedAnswerList() {
    $("#answerTable").bootstrapTable('refresh');
}


function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#answerTable').bootstrapTable({
            url: httpRequestUrl + '/answer/queryDeletedAnswerList',         //请求后台的URL（*）
			method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
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
                    field: 'id',
                    title: '答者id',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'answerName',
                    title: '答者账号',
                    align: 'center'
                }, {
                    field: 'groupName',
                    title: '群组名',
                    align: 'center'
                }, {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                console.log(res);
                if(res.code == "666"){
                    var answerInfo = res.data.list;
                    var NewData = [];
                    if (answerInfo.length) {
                        for (var i = 0; i < answerInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                "answerName": '',
                                "groupName": ''
                            };
                            dataNewObj.id = answerInfo[i].id;
                            dataNewObj.answerName = answerInfo[i].answerName;
                            dataNewObj.groupName = answerInfo[i].groupName;
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
        var answerName = $("#keyWord").val();
        //console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            answerName: answerName,
			//userName:getCookie('userName')
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}


window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        id = row.id;
        $.cookie('answerId', id);
		$.cookie('groupName', groupname);
    }

	
};


// 表格中按钮
function addFunctionAlty(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"findbackAnswer(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">恢复</button>&nbsp;&nbsp;";
	
    btnText += "<button type=\"button\" id=\"btn_stop\" onclick=\"finnaldeleteAnswer(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";
	//btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteAnswer(" + "'" + row.id + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";

    return btnText;
}

//彻底删除答者
function finnaldeleteAnswer(id) {
	//var answerId = getCookie('answerId');
	layer.confirm('您确认要删除此项目吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/answer/finnaldeleteAnswerById';
        var data = {
            "id": id
			//"groupName": groupname
        };
        commonAjaxPost(true, url, data, function (result) {
            // //console.log(result);
            if (result.code == "666") {
                layer.msg(result.message, {icon: 1});
                $("#answerTable").bootstrapTable('refresh');
            } else if (result.code == "333") {
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

//恢复答者
function findbackAnswer(id) {
	//var answerId = getCookie('answerId');
	layer.confirm('您确认要恢复此项目吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/answer/findbackAnswerById';
        var data = {
            "id": id
			//"groupName": groupname
        };
        commonAjaxPost(true, url, data, function (result) {
            // //console.log(result);
            if (result.code == "666") {
                layer.msg(result.message, {icon: 1});
                $("#answerTable").bootstrapTable('refresh');
            } else if (result.code == "333") {
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

