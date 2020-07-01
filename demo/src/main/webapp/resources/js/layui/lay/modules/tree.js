/** layui-v1.0.3 LGPL license By www.layui.com */
;layui.define("jquery", function (e) {
    "use strict";
    var o = layui.jquery,
        a = layui.hint(),
        r = "layui-tree-enter",
        i = function (e) {
            //options就是传递进来的数据
            this.options = e
        },
        t = {
            arrow: ["&#xe623;", "&#xe625;"],
            checkbox: ["&#xe626;", "&#xe627;"],
            radio: ["&#xe62b;", "&#xe62a;"],
            branch: ["&#xe622;", "&#xe624;"],
            leaf: "&#xe621;"
        };
    i.prototype.init = function (e) {
        var o = this;
        e.addClass("layui-box layui-tree"),
            o.tree(e),
            o.on(e)
    },
        i.prototype.tree = function (e, a) {//生成树
            var r = this,
                i = r.options,
                n = a || i.nodes;
            layui.each(n, function (a, n) {

                if (n.children) {//如果当前节点有子节点，则将子节点的pid设置成当前节点的id
                    layui.each(n.children, function (index, item) {
                        item.pid = n.id;
                    });
                }
                var l = n.children && n.children.length > 0,
                    //生成ul
                    c = o('<ul class="' + (n.spread ? "layui-show" : "") + '"></ul>'),
                    //生成节点
                    s = o(
                        [
                            "<li " + (n.spread ? 'data-spread="' + n.spread + '"' : "") + ">",
                            function () {
                                return l ? '<i class="layui-icon layui-tree-spread">' + (n.spread ? t.arrow[1] : t.arrow[0]) + '</i>' : '<i style="paddling-left: 28px;"></i>';
                            }(),
                            function () {

                                //如果用户选择的风格是checkbox则生成checkbox
                                var eleStr = i.check && i.check == "checkbox" ? '<input type="checkbox" lay-skin="primary" lay-filter="treeChoose" name="' + i.checkboxName + '" ' + ((n.checked && n.checked == true) ? 'checked="checked"' : "") + (n.checkboxValue ? ('value="' + n.checkboxValue + '"') : "") + 'data-parent-id="' + n.pId + '"' + 'id="' + n.id + '"' : '';
                                if (eleStr.length > 0) {
                                    //给元素添加data-xxx属性
                                    if (n.data && Object.prototype.toString.call(n.data) == "[object Object]") {
                                        for (var attr in n.data) {
                                            eleStr += ' data-' + attr + '=' + n.data[attr];
                                        }
                                    }
                                    eleStr += ' />';
                                }

                                return eleStr;
                            }(),
                            function () {
                                //a链接是否可点击
                                return '<a href="' + (n.href || "javascript:;") + '" ' + (i.target && n.href ? 'target="' + i.target + '"' : "") + ">" + ('<i class="layui-icon layui-tree-' + (l ? "branch" : "leaf") + '">' + (l ? n.spread ? t.branch[1] : t.branch[0] : t.leaf) + "</i>") + ("<cite>" + (n.name || "未命名") + "</cite></a>")
                            }(),
                            "</li>"
                        ].join("")
                    );
                l && (s.append(c), r.tree(c, n.children)),
                    e.append(s),
                "function" == typeof i.click && r.click(s, n),
                    r.spread(s, n)
            })
        },
        i.prototype.click = function (e, o) {
            var a = this,
                r = a.options;
            var $ = layui.jquery;
            e.children("a").on("click", function (f) {
                if (this.className == "active") {
                    this.className = ""
                } else {
                    $(r.elem).find('li a').removeClass("active");
                    this.className = "active"
                }
                layui.stope(f),
                    r.click(o)
            })
        },
        i.prototype.spread = function (e, o) {
            //这个方法的功能是展开子节点
            var a = this,
                //r就是小箭头
                r = (a.options, e.children(".layui-tree-spread")),
                i = e.children("ul"),
                n = e.children("a"),
                l = function () {
                    //e.data("spread")为true，表示当前节点是展开的
                    e.data("spread") ? (e.data("spread", null), i.removeClass("layui-show"), r.html(t.arrow[0]), n.find(".layui-icon").html(t.branch[0])) : (e.data("spread", !0), i.addClass("layui-show"), r.html(t.arrow[1]), n.find(".layui-icon").html(t.branch[1]))
                };
            //单击小箭头或双击整个节点都会展开会折起
            i[0] && (r.on("click", l), n.on("dblclick", l))
        },
        i.prototype.on = function (e) {
            var a = this,
                i = a.options,
                t = "layui-tree-drag";
            e.find("i").on("selectstart", function (e) {
                return !1
            }),
            i.drag && o(document).on("mousemove", function (e) {
                var r = a.move;
                if (r.from) {
                    var i = (r.to, o('<div class="layui-box ' + t + '"></div>'));
                    e.preventDefault(),
                    o("." + t)[0] || o("body").append(i);
                    var n = o("." + t)[0] ? o("." + t) : i;
                    n.addClass("layui-show").html(r.from.elem.children("a").html()),
                        n.css({
                            left: e.pageX + 10,
                            top: e.pageY + 10
                        })
                }
            }).on("mouseup", function () {
                var e = a.move;
                e.from && (e.from.elem.children("a").removeClass(r), e.to && e.to.elem.children("a").removeClass(r), a.move = {}, o("." + t).remove())
            })
        },
        i.prototype.move = {},
        e("tree", function (e) {
            var r = new i(e = e || {}),
                t = o(e.elem);
            return t[0] ? void r.init(t) : a.error("layui.tree 没有找到" + e.elem + "元素");
        })
})