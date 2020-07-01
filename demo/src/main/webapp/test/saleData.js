var vm, layer;
jsapi.ready(function () {

    var querySaleList = function () {
        jsapi.httpGet(serviceBase + "test/saleList", function (result) {
            if (result != null) {
                layer.msg("获取数据成功", {icon: 1, time: 2000})
                vm.$data.saleCount = result.saleCount;
                vm.$data.gatherSaleCount = result.gatherSaleCount;
                vm.$data.saleList = result.saleList
            }
        }, {})
    }

    vm = new Vue({
        el: "#saleInfos",
        data: {
            saleCount: null,
            gatherSaleCount: null,
            saleList: []
        },
        created: function () {
            async.series({
                layuiInit: function (callback) {
                    layui.use(['layer'], function () {
                        layer = layui.layer;
                        callback();
                    });
                },
                querySaleList: function (callback) {
                    querySaleList();
                    callback();
                }
            })
        },
        methods: {
            querySaleList: function () {
                querySaleList();
            }
        }
    })
})