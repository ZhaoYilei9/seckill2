//存放主要的交互逻辑的js代码
//javascript模块化
//js模块化
var seckill = {
    //封装ajax相关的url
    URL: {
        nowTime: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {

            return "/seckill/" + seckillId + "/exposer";
        },
        kill: function (seckillId, md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true
        } else {
            return false
        }

    },
    handleSeckill: function (seckillId, node) {
        node.hide().html("<button class='btn btn-primary' id='killBtn'>开启秒杀</button>");
        var exposerUrl = seckill.URL.exposer(seckillId);

        $.post(exposerUrl, {}, function (r) {
            if (r && r.success) {
                node.show();
                $("#killBtn").one("click", function () {
                    var seckillUrl = seckill.URL.kill(seckillId, r.data.md5);
                    alert(seckillUrl);
                    $.post(seckillUrl, {}, function (r) {
                        if (r && r.success) {
                            node.hide().html(r.data.stateInfo).show(200);
                        } else {
                            node.hide().html(r.error).show(200);
                        }
                    })
                });

            } else {
                var nowTime = r.data.now;
                var startTime = r.data.start;
                var endTime = r.data.end;
                seckill.countdown(seckillId,startTime,endTime,nowTime);
            }

        })

    },
    countdown: function (seckillId, startTime, endTime, nowTime) {
        var seckillBox = $("#seckill-box");
        if (nowTime > endTime) {
            seckillBox.hide().html('<h2>秒杀结束</h2>').show(400);
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时:%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on("finish.countdown", function () {
                seckill.handleSeckill(seckillId, seckillBox);
            })

        } else {
            seckill.handleSeckill(seckillId, seckillBox);
        }


    },
    //详情页秒杀逻辑
    details: {
        init: function (params) {
            var userPhone = $.cookie("userPhone");

            //验证手机号
            if (!seckill.validatePhone(userPhone)) {
                var phoneModal = $("#myModal");
                phoneModal.modal({
                    show: true,
                    keyboard: false,
                    backdrop: 'static'
                });
                $("#saveBtn").click(function () {
                    var userPhone = $("#killPhone").val();
                    if (seckill.validatePhone(userPhone)) {
                        $.cookie("userPhone", userPhone);
                        window.location.reload(true);
                    } else {
                        $("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号错误</label>").show(300);
                    }
                });
            } else {
                //计时交互
                var startTime = params.startTime;
                var endTime = params.endTime;
                var seckillId = params.seckillId;
                $.getJSON(seckill.URL.nowTime(), {}, function (r) {
                    if (r && r.success) {
                        var nowTime = r.data;
                        seckill.countdown(seckillId, startTime, endTime, nowTime);
                    }
                })
            }
        }
    },
}