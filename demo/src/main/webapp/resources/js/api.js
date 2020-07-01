/**
 * @author:  chenyong
 * @date: 2018/8/28
 */
var jsapi = {};

jsapi.ready = function (callback) {
    $(function () {
        callback();
    });
}

/**
 * ajax get请求
 * @param url       请求地址
 * @param callback  回调函数
 */
jsapi.httpGet = function (url, callback, param) {
    jsapi.__ajax('GET', url, param, callback);
};

/**
 * ajax post 以表单形势提交POST请求
 * @param url       请求地址
 * @param param     请求参数
 * @param callback  回调函数
 */
jsapi.httpPost = function (url, callback, param) {
    jsapi.__ajax('POST', url, param, callback);
};

/**
 * ajax 以restful形势提交POST请求
 * @param url       请求地址
 * @param param     请求参数
 * @param callback  回调函数
 */
jsapi.httpPostJson = function (url, param, callback) {
    jsapi.__ajax('POST', url, JSON.stringify(param), callback, 'application/json;charset=utf-8');
};


jsapi.__ajax = function (method, url, param, successCallback, contentType) {
    var ajaxArgs = {
        type: method,
        url: url,
        data: param,
        dataType: 'json',
        success: successCallback,
        contentType: contentType || "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (xhr, status) {
            if (xhr && xhr.status == '999') {
            }
        },
        error: function (xhr, errorType, error) {
            if (error) {
                alert(error);
            }
        }
    }
    $.ajax(ajaxArgs);
}


/**
 * 从当前url获取指定参数
 * @param name          获取的参数名
 * @param frameId       iframe控件id
 */
jsapi.getUrlParameter = function (name, frameId) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var currentUrl;
    if (frameId) {
        currentUrl = window.parent.document.getElementById(frameId).contentWindow.location;
    } else {
        currentUrl = window.location;
    }
    var r = currentUrl.search.substr(1).match(reg);
    return r ? decodeURI(r[2]) : null;
};


