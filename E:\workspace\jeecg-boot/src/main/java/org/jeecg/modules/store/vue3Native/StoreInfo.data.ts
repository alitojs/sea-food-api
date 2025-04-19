import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '店铺名称',
    align: "center",
    dataIndex: 'storeName'
  },
  {
    title: '店铺归属者',
    align: "center",
    dataIndex: 'storeOwner'
  },
  {
    title: '店铺所在地',
    align: "center",
    dataIndex: 'storeAddress'
  },
  {
    title: '店铺图片',
    align: "center",
    dataIndex: 'storePicture'
  },
  {
    title: '店铺类型',
    align: "center",
    dataIndex: 'storeType_dictText'
  },
  {
    title: '是否上线',
    align: "center",
    dataIndex: 'isOnline_dictText'
  },
  {
    title: '是否优选',
    align: "center",
    dataIndex: 'isPreferred_dictText'
  },
];

// 高级查询数据
export const superQuerySchema = {
  storeName: {title: '店铺名称',order: 0,view: 'text', type: 'string',},
  storeOwner: {title: '店铺归属者',order: 1,view: 'text', type: 'string',},
  storeAddress: {title: '店铺所在地',order: 2,view: 'text', type: 'string',},
  storePicture: {title: '店铺图片',order: 3,view: 'text', type: 'string',},
  storeType: {title: '店铺类型',order: 4,view: 'list', type: 'string',dictTable: "tb_store_type_info", dictCode: 'id', dictText: 'store_type_name',},
  isOnline: {title: '是否上线',order: 5,view: 'number', type: 'number',dictCode: 'yn',},
  isPreferred: {title: '是否优选',order: 6,view: 'number', type: 'number',dictCode: 'yn',},
};
