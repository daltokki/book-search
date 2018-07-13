var service = {
  isEmpty: function (val) {
    return !val || val.length === 0 || val === '';
  },
  cleanSearchForm: function (form_name) {
    var $form = $('form').find(form_name);
    $form[0].reset();
  },
  initDatePicker: function () {
    $('input[data-date-picker="on"]').datepicker({
      format: 'yyyy-mm-dd',
      titleformat: 'yyyy MM',
      language: '{{locale}}',
      todayBtn: true,
      autoclose: true
    });
    $('input[data-month-picker="on"]').datepicker({
      format: 'yyyy-mm',
      titleformat: 'yyyy MM',
      viewMode: "months",
      minViewMode: "months",
      language: '{{locale}}',
      todayBtn: true,
      autoclose: true
    });
  }
};