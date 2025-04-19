import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '店铺分类名称',
    align: 'left',
    dataIndex: 'storeTypeName'
  },
  {
    title: '店铺分类编码',
    align: 'center',
    dataIndex: 'storeTypeCode'
  },
  {
    title: '父级id',
    align: 'center',
    dataIndex: 'parentId_dictText'
  },
];

// 高级查询数据
export const superQuerySchema = {
  storeTypeName: {title: '店铺分类名称',order: 0,view: 'text', type: 'string',},
  storeTypeCode: {title: '店铺分类编码',order: 1,view: 'text', type: 'string',},
  parentId: {title: '父级id',order: 2,view: 'radio', type: 'string',dictTable: "tb_store_type_info", dictCode: 'id', dictText: 'store_type_name',},
};
