import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '仓库id',
    align: "center",
    dataIndex: 'warehouseId'
  },
  {
    title: '店铺id',
    align: "center",
    dataIndex: 'storeId'
  },
];

// 高级查询数据
export const superQuerySchema = {
  warehouseId: {title: '仓库id',order: 0,view: 'text', type: 'string',},
  storeId: {title: '店铺id',order: 1,view: 'text', type: 'string',},
};
