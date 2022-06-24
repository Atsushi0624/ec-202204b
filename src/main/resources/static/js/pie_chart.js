/**
 *
 */ //値をグラフに表示させる
Chart.plugins.register({
  afterDatasetsDraw: function (chart, easing) {
    var ctx = chart.ctx;

    chart.data.datasets.forEach(function (dataset, i) {
      var meta = chart.getDatasetMeta(i);
      if (!meta.hidden) {
        meta.data.forEach(function (element, index) {
          // 値の表示
          ctx.fillStyle = 'rgb(0, 0, 0,0.8)'; //文字の色
          var fontSize = 12; //フォントサイズ
          var fontStyle = 'normal'; //フォントスタイル
          var fontFamily = 'Arial'; //フォントファミリー
          ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);

          var dataString = dataset.data[index].toString();

          // 値の位置
          ctx.textAlign = 'center'; //テキストを中央寄せ
          ctx.textBaseline = 'middle'; //テキストベースラインの位置を中央揃え

          var padding = 5; //余白
          var position = element.tooltipPosition();
          ctx.fillText(
            dataString,
            position.x,
            position.y - fontSize / 2 - padding
          );
        });
      }
    });
  },
});

//=========== 円グラフ ============//
$('#age-graph').on('inview', function (event, isInView) {
  //画面上に入ったらグラフを描画
  if (isInView) {
    var ctx = document.getElementById('age-graph'); //グラフを描画したい場所のid
    var chart = new Chart(ctx, {
      type: 'pie', //グラフのタイプ
      data: {
        //グラフのデータ
        labels: ['IT', '営業', '不動産', '医療'], //データの名前
        datasets: [
          {
            label: '職種別比率', //グラフのタイトル
            backgroundColor: ['#BB5179', '#FAFF67', '#58A27C', '#3C00FF'], //グラフの背景色
            data: ['20', '30', '10', '40'], //データ
          },
        ],
      },

      options: {
        //グラフのオプション
        maintainAspectRatio: false, //CSSで大きさを調整するため、自動縮小をさせない
        legend: {
          display: true, //グラフの説明を表示
        },
        tooltips: {
          //グラフへカーソルを合わせた際の詳細表示の設定
          callbacks: {
            label: function (tooltipItem, data) {
              return (
                data.labels[tooltipItem.index] +
                ': ' +
                data.datasets[0].data[tooltipItem.index] +
                '%'
              ); //%を最後につける
            },
          },
        },
        title: {
          //上部タイトル表示の設定
          display: true,
          fontSize: 10,
          text: '単位：%',
        },
      },
    });

    var ctx = document.getElementById('gender-graph'); //グラフを描画したい場所のid
    var chart = new Chart(ctx, {
      type: 'pie', //グラフのタイプ
      data: {
        //グラフのデータ
        labels: ['IT', '営業', '不動産', '医療'], //データの名前
        datasets: [
          {
            label: '職種別比率', //グラフのタイトル
            backgroundColor: ['#BB5179', '#FAFF67', '#58A27C', '#3C00FF'], //グラフの背景色
            data: ['20', '30', '10', '40'], //データ
          },
        ],
      },

      options: {
        //グラフのオプション
        maintainAspectRatio: false, //CSSで大きさを調整するため、自動縮小をさせない
        legend: {
          display: true, //グラフの説明を表示
        },
        tooltips: {
          //グラフへカーソルを合わせた際の詳細表示の設定
          callbacks: {
            label: function (tooltipItem, data) {
              return (
                data.labels[tooltipItem.index] +
                ': ' +
                data.datasets[0].data[tooltipItem.index] +
                '%'
              ); //%を最後につける
            },
          },
        },
        title: {
          //上部タイトル表示の設定
          display: true,
          fontSize: 10,
          text: '単位：%',
        },
      },
    });
  }
});
