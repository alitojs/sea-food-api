import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '仓库名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '仓库所在地',
    align:"center",
    dataIndex: 'address',
   },
   {
    title: '仓库管理员',
    align:"center",
    dataIndex: 'warehouseOwner'
   },
   {
    title: '管理员手机号',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '仓库管理员账号id',
    align:"center",
    dataIndex: 'ownerUserId_dictText'
   },
   {
    title: '商家id',
    align:"center",
    dataIndex: 'merchantId_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '仓库名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入仓库名称!'},
          ];
     },
  },
  {
    label: '仓库所在地',
    field: 'address',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入仓库所在地!'},
          ];
     },
  },
  {
    label: '仓库管理员',
    field: 'warehouseOwner',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入仓库管理员!'},
          ];
     },
  },
  {
    label: '管理员手机号',
    field: 'phone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入管理员手机号!'},
                 { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ];
     },
  },
  {
    label: '仓库管理员账号id',
    field: 'ownerUserId',
    component: 'JSelectUser',
    componentProps:{
        labelKey:'realname',
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入仓库管理员账号id!'},
          ];
     },
  },
  {
    label: '商家id',
    field: 'merchantId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"tb_merchant_info,name,id",
        type: "radio"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入商家id!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  name: {title: '仓库名称',order: 0,view: 'text', type: 'string',},
  address: {title: '仓库所在地',order: 1,view: 'pca', type: 'string',},
  warehouseOwner: {title: '仓库管理员',order: 2,view: 'text', type: 'string',},
  phone: {title: '管理员手机号',order: 3,view: 'text', type: 'string',},
  ownerUserId: {title: '仓库管理员账号id',order: 4,view: 'sel_user', type: 'string',},
  merchantId: {title: '商家id',order: 5,view: 'radio', type: 'string',dictTable: "tb_merchant_info", dictCode: 'id', dictText: 'name',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}