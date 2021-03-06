define(function (require) {
  var Config = require('config');
  var Core = require('core/core');
  var Page = require('core/page');

  // 删除数据字典
  return Page.extend(function () {

    this.renderButton = function (btn, row) {
      if (row.catalogStyle === 'F') {
        return false;
      }

      return this.$findUp('isAdmin') ? true : row.catalogStyle !== 'S';
    };

    this.url = 'system/dictionary/';

    // @override
    this.submit = function (table, data) {
      Core.ajax(Config.ContextPath + this.url + data.catalogCode, {
        type: 'json',
        method: 'post',
        data: {
          _method: 'delete'
        }
      }).then(function () {
        table.datagrid('reload');
      });
    }
  });
});
