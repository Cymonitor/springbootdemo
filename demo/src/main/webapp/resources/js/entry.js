/**
 * @author:  chenyong
 * @date: 2018/8/28
 */
if (!String.prototype.endsWith) {
    String.prototype.endsWith = function (searchString, position) {
        var subjectString = this.toString();
        if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
            position = subjectString.length;
        }
        position -= searchString.length;
        var lastIndex = subjectString.indexOf(searchString, position);
        return lastIndex !== -1 && lastIndex === position;
    };
}
var contentPath = function () {
    var sign = window.location.pathname.indexOf("/test");
    return window.location.pathname.substr(1, sign);
}

var jsBase = null;
var resourcesBase = null;
var contextBase = null;
var parentBase = null;
var cssBase = null;
var commonBase = null;
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
    var scriptSrc = scripts[i].src;
    if (scriptSrc.lastIndexOf('entry.js') > -1) {
        jsBase = scriptSrc.substring(0, scriptSrc.lastIndexOf('entry.js'));
        resourcesBase = jsBase.substring(0, jsBase.lastIndexOf('/js/') + 1);
        contextBase = resourcesBase.substring(0, resourcesBase.length - 1);
        contextBase = contextBase.substring(0, contextBase.lastIndexOf('/') + 1);
        parentBase = contextBase.substring(0, contextBase.length - 1);
        parentBase = parentBase.substring(0, parentBase.lastIndexOf('/') + 1);
        cssBase = resourcesBase + 'css/';
        commonBase = window.location.protocol + "//" + window.location.host + "/" + contentPath();
        break;
    }
}

var importJS = function (path) {
    document.write("<script src='" + jsBase + path + "'></script>");
}

var importCSS = function (path) {
    document.write("<link rel='stylesheet' type='text/css' href='" + cssBase + path + "?" + new Date().getTime() + "'>");
}

var importExJS = function (path) {
    document.write("<script src='" + path + "?" + new Date().getTime() + "'></script>");
}

var importExCSS = function (path) {
    document.write("<link rel='stylesheet' type='text/css' href='" + path + "?" + new Date().getTime() + "'>");
}


importJS('include.js');

importJS('init.js');


//var currentUrl = window.location.href.split('?')[0];
//var pageJSUrl = currentUrl.substring(0, currentUrl.length - 4) + 'js?';
//
//document.write("<script src='" + pageJSUrl + "'></script>");


var currentUrl = window.location.href;
if (currentUrl.indexOf('.html') != -1) {
    var pageJSUrl = currentUrl.substring(0, currentUrl.indexOf('.html') + 1) + 'js?';
    document.write("<script src='" + pageJSUrl + "'></script>");
}
