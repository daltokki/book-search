$(function () {
  var elements ={},
    service = {
      search: function (num) {
        var page = $('#page').val(num || 1).val(), size = parseInt($('#size').val() || 10);
        $.ajax({
          url: '/bookmark/get',
          data: {
            page: page,
            size: size,
            dateSearchType: elements.$activeSearchType.attr('id')
          },
          type: 'get',
          success: function (res) {
            elements.$divResult.empty().append(res);
            $('.badge').css('padding', '8px 15px');
          },
          error: function () {
            var div = '<div class="alert alert-secondary" role="alert"><i class="fa fa-info-circle"></i> 조회 정보를 불러오는데 실패하였습니다.</div>';
            elements.$divResult.empty().append(div);
          }
        });
      },
      filterEvent: function ($filterSelector) {
        elements.$divSearch.find('button').removeClass('active');
        $filterSelector.addClass('active');
        service.search($('#page').val() || 1);
      },
      removeBookmark: function (key) {
        $.ajax({
          url: '/bookmark/remove',
          type: 'post',
          data: { bookmarkId: key },
          success: function (result) {
            if (result.success) {
              alert(result.message);
              location.reload();
            } else {
              alert(result.message);
            }
          }
        });
      }
    };

  elements['$divSearch'] = $('#div-search');
  elements['$divResult'] = $('#div-result');
  elements['$activeSearchType'] = $('button[name=dateSearchType].active');

  elements.$divSearch.find('.card-header').on('click', '.btn', function () {
    service.filterEvent($(this));
  });
  $('button[name=remove]').on('click', function () {
    service.removeBookmark($(this).data('key'));
  });
  elements.$divResult.on('click', '.page-link',function () {
    service.search($(this).data('page'));
  });
  $('button[name=dateSearchType]:last').trigger('click');
});