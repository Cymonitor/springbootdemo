/**
 * official_account_send
 *
 * @author ChenYong
 * @date 2018/1/11
 */
import {} from '@/'

var table, vm, form, layer, laydate;
jsapi.ready(function () {
    vm = new Vue({
        el: "#OffcialAtcManagerMain",
        data: {
            queryParam: {
                modelName: "",
                startTime: "",
                endTime: ""
            },
            gridConfig: JSON.stringify({
                url: serviceBase + "test/vehicleInfos",
                page: true,
                id: "offcialSendGrid"
            })
        },
        created: function () {
            async.series({
                initLayUiUse: function (callback) {
                    layui.use(['form', 'table', 'laydate'], function () {
                        form = layui.form;
                        table = layui.table;
                        layer = layui.layer;
                        laydate = layui.laydate;
                        laydate.render({
                            elem: "#startTime",
                            type: "datetime",
                            done: function (value) {
                                vm.$data.queryParam.startTime = value;
                            }
                        });
                        laydate.render({
                            elem: "#endTime",
                            type: "datetime",
                            done: function (value) {
                                vm.$data.queryParam.endTime = value;
                            }
                        });
                        //监听工具条
                        table.on('tool(offcialSendGrid)', function (obj) {
                            var data = obj.data;
                            switch (obj.event) {
                                case 'preview': //预览
                                    window.open(data.url, "_blank");
                                    break;
                                case 'sendmessage':
                                    data.appId = vm.$data.queryParam.appId;
                                    layer.open({
                                        type: 2, //type2表示打开iframe层
                                        title: ['发送信息'],  //false表示不显示标题
                                        area: ['800px', '600px'],
                                        shade: 0.1, //是否显示遮罩层
                                        shadeClose: true, //是否点击遮罩关闭
                                        id: "sendMessage",  //设置id防止重复弹出
                                        moveType: 0, //拖拽模式0或者1
                                        content: kosBase + "page/opm/offcial_detail.html?" + $.param({
                                            title: data.title,
                                            appId: data.appId,
                                            mediaId: data.mediaId
                                        }) //弹出层内容是一个链接
                                    });
                                    break;
                                default:
                                    break;
                            }
                        });
                        form.render();
                        callback();
                    });
                },
                initTable: function () {
                    table.init('offcialSendGrid', {
                        where: vm.$data.queryParam,
                        page: {curr: 1}
                    });
                }
            })
        },
        methods: {
            search: function () {
                vm.$data.queryParam.startTime = $("#startTime").val();
                vm.$data.queryParam.endTime = $("#endTime").val();
                if ($("#startTime").val() > $("#endTime").val()) {
                    layer.msg("开始时间不能大于结束时间", {icon: 2, time: 1000})
                    return
                }
                table.reload('offcialSendGrid', {
                    where: vm.$data.queryParam,
                    page: {curr: 1}
                });
            }
        }
    });
});
