var main = {
    init : function () {
        var _this = this;
        $('#btn-stock').on('click', function () {
            _this.stock();
        });
    },
    stock : function () {
        var data = {
            stock_name : $('#stock_name').val()
        };
        console.log("stock name : " + data.stock_name);

        $.ajax({
            type : 'POST',
            url : '/api/stock',
            data : data,
            success : function () {
                window.location.href = '/stock/' + data.stock_name;
            }
        });
    }
}

main.init();