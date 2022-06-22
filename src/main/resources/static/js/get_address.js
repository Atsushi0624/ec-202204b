"use strict";
$(function () {
  $(document).on("click", "#get_address", function () {
    $.ajax({
      url: "https://zipcloud.ibsnet.co.jp/api/search",
      dataType: "jsonp",
      data: {
        zipcode: $("#zipcode").val(),
      },
      async: true,
    })
      .done(function (data) {
        console.log(data);
        console.dir(JSON.stringify(data));
        $("#address").val(
          data.results[0].address1 +
            data.results[0].address2 +
            data.results[0].address3
        );
        $("#address").focus();
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert("正しい結果を得られませんでした。");
        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
