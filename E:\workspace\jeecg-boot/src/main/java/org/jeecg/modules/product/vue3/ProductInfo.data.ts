import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '商品名称',
    align:"center",
    sorter: true,
    dataIndex: 'productName'
   },
   {
    title: '商品分类',
    align:"center",
    sorter: true,
    dataIndex: 'productCategory_dictText'
   },
   {
    title: '是否限购',
    align:"center",
    sorter: true,
    dataIndex: 'isLimited_dictText'
   },
   {
    title: '限购数量',
    align:"center",
    sorter: true,
    dataIndex: 'limitedQuantity'
   },
   {
    title: '是否上架',
    align:"center",
    sorter: true,
    dataIndex: 'isListing_dictText'
   },
   {
    title: '商品主图',
    align:"center",
    dataIndex: 'mainPicture',
    customRender:render.renderImage,
   },
   {
    title: '商家id',
    align:"center",
    dataIndex: 'merchantId_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "商品名称",
      field: "productName",
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "商品分类",
      field: "productCategory",
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"product_category"
      },
      //colProps: {span: 6},
 	},
	{
      label: "是否限购",
      field: "isLimited",
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
	{
      label: "限购数量",
      field: "limitedQuantity",
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "是否上架",
      field: "isListing",
      component: 'JDictSelectTag',
      componentProps:{
          dictCode:"yn"
      },
      //colProps: {span: 6},
 	},
	{
      label: "商品主图",
      field: "mainPicture",
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '商品名称',
    field: 'productName',
    component: 'Input',
  },
  {
    label: '商品分类',
    field: 'productCategory',
    component: 'JCheckbox',
    componentProps:{
        dictCode:"product_category"
     },
  },
  {
    label: '是否限购',
    field: 'isLimited',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn"
     },
  },
  {
    label: '限购数量',
    field: 'limitedQuantity',
    component: 'InputNumber',
  },
  {
    label: '是否上架',
    field: 'isListing',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"yn"
     },
  },
  {
    label: '商品主图',
    field: 'mainPicture',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
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
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];
//子表单数据
//子表表格配置
export const productSpecificationColumns: JVxeColumn[] = [
    {
      title: '商品id',
      key: 'productId',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商品单位',
      key: 'productUnit',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商品规格',
      key: 'productSpecification',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商品单价',
      key: 'productPrice',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '是否上架',
      key: 'isListing',
      type: JVxeTypes.inputNumber,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商品附图',
      key: 'otherPicture',
      type: JVxeTypes.image,
      token:true,
      responseName:"message",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商品库存',
      key: 'productInventory',
      type: JVxeTypes.inputNumber,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '商家id',
      key: 'merchantId',
      type: JVxeTypes.select,
      options:[],
      dictCode:"tb_merchant_info,name,id",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '仓库id',
      key: 'warehouseId',
      type: JVxeTypes.select,
      options:[],
      dictCode:"tb_warehouse_info,name,id",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
  ]


// 高级查询数据
export const superQuerySchema = {
  productName: {title: '商品名称',order: 0,view: 'text', type: 'string',},
  productCategory: {title: '商品分类',order: 1,view: 'number', type: 'number',dictCode: 'product_category',},
  isLimited: {title: '是否限购',order: 2,view: 'number', type: 'number',dictCode: 'yn',},
  limitedQuantity: {title: '限购数量',order: 3,view: 'number', type: 'number',},
  isListing: {title: '是否上架',order: 4,view: 'number', type: 'number',dictCode: 'yn',},
  mainPicture: {title: '商品主图',order: 5,view: 'image', type: 'string',},
  merchantId: {title: '商家id',order: 6,view: 'radio', type: 'string',dictTable: "tb_merchant_info", dictCode: 'id', dictText: 'name',},
  //子表高级查询
  productSpecification: {
    title: '商品规格表',
    view: 'table',
    fields: {
        productUnit: {title: '商品单位',order: 1,view: 'text', type: 'string',},
        productSpecification: {title: '商品规格',order: 2,view: 'text', type: 'string',},
        productPrice: {title: '商品单价',order: 3,view: 'text', type: 'string',},
        isListing: {title: '是否上架',order: 4,view: 'number', type: 'number',dictCode: 'yn',},
        otherPicture: {title: '商品附图',order: 5,view: 'image', type: 'string',},
        productInventory: {title: '商品库存',order: 6,view: 'number', type: 'number',},
        merchantId: {title: '商家id',order: 7,view: 'radio', type: 'string',dictTable: "tb_merchant_info", dictCode: 'id', dictText: 'name',},
        warehouseId: {title: '仓库id',order: 8,view: 'radio', type: 'string',dictTable: "tb_warehouse_info", dictCode: 'id', dictText: 'name',},
    }
  },
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
// 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}