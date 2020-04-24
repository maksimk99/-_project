function addGoodsToBasket(goodsId) {
    $.ajax({
        type: "POST",
        url: "/basket?goodsId=" + goodsId,
        dataType: "html",
        success: function (response) {
            location.reload();
        },
        error: function (response) {
            var parsed = $.parseHTML(response.responseText);
            $('#content').replaceWith($(parsed).filter("#errorContent"));
        }
    });
}

function removeGoodsFromBasket(goodsId) {
    $.ajax({
        type: "DELETE",
        url: "/basket?goodsId=" + goodsId,
        success: function (response) {
            location.reload();
        },
        error: function (response) {
            var parsed = $.parseHTML(response.responseText);
            $('#content').replaceWith($(parsed).filter("#errorContent"));
        }
    });
}

function removeGoods(goodsId) {
    $.ajax({
        type: "DELETE",
        url: "/goods?goodsId=" + goodsId,
        success: function (response) {
            location.reload();
        },
        error: function (error) {
            var parsed = $.parseHTML(response.responseText);
            $('#content').replaceWith($(parsed).filter("#errorContent"));
        }
    });
}

function removeOrder(orderId) {
    $.ajax({
        type: "DELETE",
        url: "/order?orderId=" + orderId,
        success: function (response) {
            location.reload();
        },
        error: function (error) {
            var parsed = $.parseHTML(response.responseText);
            $('#content').replaceWith($(parsed).filter("#errorContent"));
        }
    });
}

function bs_input_file() {
    $(".input-file").before(
        function () {
            if (!$(this).prev().hasClass('input-ghost')) {
                var element = $("<input type='file' accept='.jpg,.png,.jpeg,.webp' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name", $(this).attr("name"));
                element.change(function () {
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function () {
                    element.click();
                });
                $(this).find("button.btn-reset").click(function () {
                    element.val(null);
                    $(this).parents(".input-file").find('input').val('');
                });
                $(this).find('input').css("cursor", "pointer");
                $(this).find('input').mousedown(function () {
                    $(this).parents('.input-file').prev().click();
                    return false;
                });
                return element;
            }
        }
    );
}
