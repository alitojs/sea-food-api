<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-row>
						<a-col :span="24">
							<a-form-item label="店铺分类名称" v-bind="validateInfos.storeTypeName">
								<a-input v-model:value="formData.storeTypeName" placeholder="请输入店铺分类名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="店铺分类编码" v-bind="validateInfos.storeTypeCode">
								<a-input v-model:value="formData.storeTypeCode" placeholder="请输入店铺分类编码"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="父级id" v-bind="validateInfos.parentId">
								<j-dict-select-tag type='radio' v-model:value="formData.parentId" dictCode="tb_store_type_info,store_type_name,id" placeholder="请选择父级id"  allow-clear />
							</a-form-item>
						</a-col>
            <a-col :span="24">
              <a-form-item label="父级节点" v-bind="validateInfos.pid">
                <j-tree-select
                  placeholder="请选择父级节点"
                  v-model:value="formData.pid"
                  dict="tb_store_type_info,store_type_name,id"
                  pidField="pid"
                  pidValue="0"
                  hasChildField="has_child"
                  :disabled="disabled">
                </j-tree-select>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </template>
    </JFormContainer>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, unref, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JTreeSelect from '/@/components/Form/src/jeecg/components/JTreeSelect.vue';
  import { getValueType } from '/@/utils';
  import {loadTreeData, saveOrUpdateDict} from '../StoreTypeInfo.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  
  const useForm = Form.useForm;
  const formRef = ref();
  const isUpdate = ref(true);
  const expandedRowKeys = ref([]);
  const treeData = ref([]);
  const pidField = ref<string>('pid');
  const emit = defineEmits(['register', 'ok']);
  let model: Nullable<Recordable> = null;
  const formData = reactive<Record<string, any>>({
    id: '',
    storeTypeName: '',   
    storeTypeCode: '',   
    parentId: '',   
    pid: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    storeTypeName: [{ required: true, message: '请输入店铺分类名称!'},],
    storeTypeCode: [{ required: true, message: '请输入店铺分类编码!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({}) },
    formBpm: { type: Boolean, default: true }
  });

  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
  /**
   * 新增
   */
  function add(obj = {}) {
    edit(obj);
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(async () => {
      resetFields();
      expandedRowKeys.value = [];
      treeData.value = await loadTreeData({ async: false, pcode: '' });
      const tmpData = {};
      Object.keys(formData).forEach((key) => {
        if(record.hasOwnProperty(key)){
          tmpData[key] = record[key]
        }
      })
      //赋值
      Object.assign(formData,tmpData);
      model = tmpData
    });
  }

  /**
   * 根据pid获取展开的节点
   * @param pid
   * @param arr
   */
  function getExpandKeysByPid(pid, arr) {
    if (pid && arr && arr.length > 0) {
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].key == pid && unref(expandedRowKeys).indexOf(pid) < 0) {
          expandedRowKeys.value.push(arr[i].key);
          getExpandKeysByPid(arr[i]['parentId'], unref(treeData));
        } else {
          getExpandKeysByPid(pid, arr[i].children);
        }
      }
    }
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    if (formData.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in formData) {
      //如果该数据是数组并且是字符串类型
      if (formData[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          formData[data] = formData[data].join(',');
        }
      }
    }
    await saveOrUpdateDict(formData, isUpdate.value)
      .then(async (res) => {
        if (res.success) {
          await getExpandKeysByPid(formData['pid'], unref(treeData));
          delete formData['children'];
          emit('ok', {
            isUpdate: unref(isUpdate),
            values: { ...formData },
            expandedArr: unref(expandedRowKeys).reverse(),
            // 是否更改了父级节点
            changeParent: model != null && model['pid'] != formData['pid'],
          });
          createMessage.success(res.message);
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  /**
   * 值改变事件触发
   * @param key
   * @param value
   */
  function handleFormChange(key, value) {
    formData[key] = value;
  }

  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    padding: 14px;
  }
</style>
