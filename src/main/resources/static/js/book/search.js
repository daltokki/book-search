$(function () {
  var elements = {},
    service = {
      search: function (num) {
        var page = $('#page').val(num || 1).val(), size = parseInt($('#size').val() || 10);
        $.ajax({
          url: elements.$formSearch.attr('action'),
          data: {
            page: page,
            size: size,
            query: $('#query').val(),
            category: parseInt($('#subCategory').val()) || null,
            target: $('[name=bookSearchType].active').attr('id') || null,
            sort: $('#bookSortType').val() || null
          },
          type: 'get',
          success: function (res) {
            elements.$divResult.empty().append(res);
          },
          error: function () {
            var div = '<div class="alert alert-secondary" role="alert"><i class="fa fa-info-circle"></i> 조회 정보를 불러오는데 실패하였습니다.</div>';
            elements.$divResult.empty().append(div);
          }
        });
      },
      bookMarking: function (idx) {
        var detailInfo = detail_map[idx];
        $.ajax({
          url: '/bookmark/add',
          type: 'post',
          dataType: 'json',
          contentType: 'application/json',
          data: JSON.stringify(detailInfo),
          success: function (result) {
            if (result.success) {
              alert(result.message);
            } else {
              alert(result.message);
            }
          }
        });
      },
      dateFormatter: function(date) {
        var dateString = (date.getFullYear() + '-'
          + ('0' + (date.getMonth() + 1)).slice(-2)
          + '-' + ('0' + (date.getDate())).slice(-2));
        return dateString;
      },
      filterEvent: function ($filterSelector) {
        elements.$divSearch.find('button').removeClass('active');
        $filterSelector.addClass('active');
      },
      drawCategoryOptions: function (subCategoryList) {
        var sub_options = [];
        sub_options.push('<option value="">중분류</option>');
        $.each(subCategoryList, function (idx, category) {
          sub_options.push('<option value="' + category['categoryId'] + '">' + category['subCategory'] + '</option>');
        });
        return sub_options;
      },
      init: function () {
        elements.$divSearch.find('button#ALL').addClass('active');
        elements.$divSearch.find('#mainCategory').trigger('change');
      }
    };

  elements['$divSearch'] = $('div#div-search');
  elements['$divResult'] = $('div#div-result');
  elements['$formSearch'] = $('form#book-search-form');

  elements.$divSearch.find('.card-header').on('click', '.btn', function () {
    service.filterEvent($(this));
  });
  elements.$divSearch.on('change', '#mainCategory', function () {
    var $selector = $(this);
    var categoryMapElement = category_map[$selector.val()];
    elements.$divSearch.find('#subCategory').empty().append(service.drawCategoryOptions(categoryMapElement));
  });
  $('#search-btn').on('click', function () {
    if (util.isEmpty($('#query').val())) {
      alert('검색 내용을 입력해주세요.');
      return;
    }
    if (util.isNotEmpty($('#mainCategory').val()) && util.isEmpty($('#subCategory').val())) {
      alert('중분류를 선택해 주세요!');
      return
    }
    service.search();
  });
  elements.$divResult.on('click', 'tr', function () {
    var idx = $(this).data('idx');
    var detail = detail_map[idx];
    var $detailModal = $('#detailModal');
    $detailModal.find('#detailIdx').val(idx);
    $detailModal.find('div#img').empty().append('<img src="' + detail['thumbnail'] + '">');
    $detailModal.find('p#title').empty().append(detail['title']);
    $detailModal.find('p#contents').empty().append(detail['contents']);
    $detailModal.find('p#price').empty().append('도서정가 : ' + detail['price'].format() + '(원) / 판매가 : ' + detail['sale_price'].format() + '(원)');
    $detailModal.find('p#authors').empty().append(detail['authors']);
    $detailModal.find('p#translators').empty().append(detail['translators']);
    $detailModal.find('p#publisher').empty().append(detail['publisher']);
    $detailModal.find('p#category').empty().append(detail['category']);
    $detailModal.find('p#datetime').empty().append(service.dateFormatter(new Date(detail['datetime'])));
    $detailModal.find('p#status').empty().append(detail['status']);
    $detailModal.find('label').css('font-weight', 'bold');
    $detailModal.show();
  });
  $('#bookmark').on('click', function () {
    var detail_idx = $('#detailModal').find('#detailIdx').val();
    service.bookMarking(detail_idx);
  });
  elements.$divResult.on('click', '.page-link',function () {
    service.search($(this).data('page'));
  });
  service.init();
});
