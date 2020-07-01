var vm, layer
jsapi.ready(function () {
    vm = new Vue({
        el: 'body',
        data: {
            username: "",
            password: "",
            actionUrl: serviceBase + "j_security_check"
        },
        created: function () {
            async.series({
                initLayUi: function (callback) {
                    layui.use(['layer'], function () {
                        layer = layui.layer;
                        callback();
                    });
                }
            })
        },
        methods: {
            login: function () {
                var username = $("#username").val();
                var password = $("#password").val();
                if (username == "" || username == null) {
                    layer.msg("用户名不能为空", {icon: 2, time: 1000});
                    return;
                }
                if (password == "" || password == null) {
                    layer.msg("密码不能为空", {icon: 2, time: 1000});
                    return;
                }
                jsapi.httpPost(serviceBase + "login/userlogin", function (result) {
                    //成功
                    if (result.code == 1) {
                        window.location.href = contextBase + "test/saleData.html"
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1000})
                    }
                }, {username: username, password: password})
                /*                var url=serviceBase+"login/userlogin";
                                $.ajax({
                                    url:url,
                                    type:'post',
                                    data:{username:username,password:password},
                                    dataType:'json',
                                    success:function (data) {
                                        //成功
                                        if(data.code==1){
                                            window.open(commonBase+"test/saleData.html");
                                        }else{
                                            window.open(commonBase+"test/offcial_act_send.html");
                                        }
                                    }
                                })*/

            }
        }
    });
})