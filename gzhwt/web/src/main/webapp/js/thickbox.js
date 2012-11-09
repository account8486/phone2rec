/*
 * Thickbox 3.1 - One Box To Rule Them All.
 * By Cody Lindley (http://www.codylindley.com)
 * Copyright (c) 2007 cody lindley
 * Licensed under the MIT License: http://www.opensource.org/licenses/mit-license.php
 */



/*!!!!!!!!!!!!!!!!! edit below this line at your own risk !!!!!!!!!!!!!!!!!!!!!!!*/

//on page load call tb_init

$(document).ready(function() {

    tb_init('a.thickbox, area.thickbox, input.thickbox');//pass where to apply thickbox

});



//add thickbox to href & area elements that have a class of .thickbox
function tb_init(domChunk) {
    $(domChunk).click(function() {
        var t = this.title || this.name || null;
        var a = this.href || this.alt;
        var g = this.rel || false;


        tb_show(t, a, g);

        this.blur();
        return false;
    });
}

function tb_show(caption, url, imageGroup) {//function called when the user clicks on a thickbox link
    if (caption === null) {
        caption = "";
    }



        if (document.getElementById("TB_window") === null) {
            $("body").append("<div id='TB_window'></div>");
//                $("#TB_overlay").click(tb_remove);
        }



    var baseURL;
    if (url.indexOf("?") !== -1) { //ff there is a query string involved
        baseURL = url.substr(0, url.indexOf("?"));
    } else {
        baseURL = url;
    }

    var urlString = /\.jpg$|\.jpeg$|\.png$|\.gif$|\.bmp$/;
    var urlType = baseURL.toLowerCase().match(urlString);


    var queryString = url.replace(/^[^\?]+\??/, '');
    var params = tb_parseQuery(queryString);

    TB_WIDTH = (params['width'] * 1) + 30 || 630; //defaults to 630 if no paramaters were added to URL
    TB_HEIGHT = (params['height'] * 1) + 40 || 440; //defaults to 440 if no paramaters were added to URL
    ajaxContentW = TB_WIDTH - 30;
    ajaxContentH = TB_HEIGHT - 45;

    if (url.indexOf('TB_iframe') != -1) {// either iframe or ajax window
        urlNoQuery = url.split('TB_');
        $("#TB_iframeContent").remove();
        if (params['modal'] != "true") {//iframe no modal
            $("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>" + caption + "</div></div><iframe frameborder='0' hspace='0' src='" + urlNoQuery[0] + "' id='TB_iframeContent' name='TB_iframeContent" + Math.round(Math.random() * 1000) + "' onload='tb_showIframe()' style='width:" + (ajaxContentW) + "px;height:" + (ajaxContentH) + "px;' > </iframe>");
        } else {//iframe modal
//            $("#TB_overlay").unbind();
            $("#TB_window").append("<iframe frameborder='0' hspace='0' src='" + urlNoQuery[0] + "' id='TB_iframeContent' name='TB_iframeContent" + Math.round(Math.random() * 1000) + "' onload='tb_showIframe()' style='width:" + (ajaxContentW) + "px;height:" + (ajaxContentH ) + "px;'> </iframe>");
        }
    } else {// not an iframe, ajax
        if ($("#TB_window").css("display") != "block") {
            if (params['modal'] != "true") {//ajax no modal
                $("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>" + caption + "</div><div id='TB_closeAjaxWindow'><a href='#' id='TB_closeWindowButton'>点此关闭</a> 或者按 Esc键退出</div></div><div id='TB_ajaxContent' style='width:" + ajaxContentW + "px;height:" + ajaxContentH + "px'></div>");
            } else {//ajax modal
//                $("#TB_overlay").unbind();
                $("#TB_window").append("<div id='TB_ajaxContent' class='TB_modal' style='width:" + ajaxContentW + "px;height:" + ajaxContentH + "px;'></div>");
            }
        } else {//this means the window is already up, we are just loading new content via ajax
            $("#TB_ajaxContent")[0].style.width = ajaxContentW + "px";
            $("#TB_ajaxContent")[0].style.height = ajaxContentH + "px";
            $("#TB_ajaxContent")[0].scrollTop = 0;
            $("#TB_ajaxWindowTitle").html(caption);
        }
    }


    $("#dialog:ui_dialog").dialog("destroy");


    $("#TB_window").dialog({
            width: TB_WIDTH,
            height: TB_HEIGHT,
            title: caption,
            modal: true,
            close: function(event, ui) {

            }
        }
    );

}

function reload_frame(id) {
     document.getElementById(id).src = document.getElementById(id).src;
}

//helper functions below
function tb_showIframe() {
    $("#TB_load").remove();
    $("#TB_window").css({display:"block"});
}


function tb_remove() {
     $("#TB_window").dialog("close");
}


function tb_parseQuery(query) {
    var Params = {};
    if (! query) {
        return Params;
    }// return empty object
    var Pairs = query.split(/[;&]/);
    for (var i = 0; i < Pairs.length; i++) {
        var KeyVal = Pairs[i].split('=');
        if (! KeyVal || KeyVal.length != 2) {
            continue;
        }
        var key = unescape(KeyVal[0]);
        var val = unescape(KeyVal[1]);
        val = val.replace(/\+/g, ' ');
        Params[key] = val;
    }
    return Params;
}




