import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '商品id',
    align: "center",
    dataIndex: 'productId_dictText'
  },
  {
    title: '店铺id',
    align: "center",
    dataIndex: 'storeId_dictText'
  },
];

// 高级查询数据
export const superQuerySchema = {
  productId: {title: '商品id',order: 0,view: 'list', type: 'string',dictTable: "tb_product_info", dictCode: 'id', dictText: 'product_name',},
  storeId: {title: '店铺id',order: 1,view: 'list', type: 'string',dictTable: "tb_store_info", dictCode: 'id', dictText: 'store_name',},
};
