<template>
  <div>
    <el-switch
      v-model="draggable"
      active-text="开启拖拽"
      inactive-text="关闭拖拽"
    />
    <el-button @click="batchSave">批量保存</el-button>
    <div style="height: 20px" />
    <el-tree
      :data="menus"
      :expand-on-click-node="false"
      :props="defaultProps"
      :show-checkbox="true"
      :draggable="draggable"
      :allow-drop="allowDrop"
      node-key="catId"
      :default-expanded-keys="expandedKey"
      @node-drop="nodeDrop"
    >
      <span
        style="
          flex: 1;
          display: flex;
          align-items: center;
          justify-content: space-between;
          font-size: 14px;
          padding-right: 8px;
        "
        slot-scope="{ node, data }"
      >
        <span>
          <span>{{ data.name }}</span>
          <span style="margin-left: 100px">
            <el-button
              @click="() => edit(node, data)"
              style="font-size: 12px"
              type="text"
            >
              编辑
            </el-button>
            <el-button
              @click="() => append(node, data)"
              v-if="node.level <= 2"
              style="font-size: 12px"
              type="text"
            >
              添加
            </el-button>
            <el-button
              v-if="data.children.length == 0"
              style="font-size: 12px"
              type="text"
              @click="() => remove(node, data)"
            >
              删除
            </el-button>
          </span>
        </span>
      </span>
    </el-tree>

    <el-dialog
      :close-on-click-modal="false"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <el-form :model="category" label-position="top">
        <el-form-item label="分类名称" :label-width="formLabelWidth">
          <el-input v-model="category.name" auto-complete="off" />
        </el-form-item>
        <el-form-item label="图标" :label-width="formLabelWidth">
          <el-input v-model="category.icon" auto-complete="off" />
        </el-form-item>
        <el-form-item label="计量单位" :label-width="formLabelWidth">
          <el-input v-model="category.productUnit" auto-complete="off" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="button" @click="submit"> 确 定 </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      draggable: false,
      menus: [],
      dialogVisible: false,
      dialogTitle: "",
      expandedKey: [165],
      formLabelWidth: "120px",
      defaultProps: {
        children: "children",
        label: "name",
      },
      batchList: [],
      category: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        icon: "",
        productUnit: "",
        sort: 0,
      },
      defaultCategoryValue: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        icon: "",
        productUnit: "",
        sort: 0,
      },
      dialogType: "", //edit/add
    };
  },
  methods: {
    // 批量保存
    batchSave() {
      const map = new Map();
      for (const item of this.batchList) {
        map.set(item.catId, item)
      }

      this.$http
        .post(
          this.$http.adornUrl("/product/category/update/sort"),
          Array.from(map.values()).flat()
        )
        .then(({ data: r }) => {
          if (Number(r.code) !== 0) {
            this.$message({ type: "error", message: "修改失败" });
            return;
          }
          this.$message({ type: "success", message: "修改成功" });
          this.getMenus();
          this.$nextTick(() => {
            this.expandedKey = [pCid];
          });
        })
        .catch((e) => {
          console.error("拖拽修改节点：", e);
          this.$message({ type: "error", message: "系统异常" });
        });
    },
    // 表单提交
    async submit() {
      if (this.dialogType === "add") this.addCategory();
      else if (this.dialogType === "edit") this.editCategory();
    },
    // 保存分类编辑信息
    async editCategory() {
      const { name, catId, productUnit, icon } = this.category;
      const category = { name, catId, productUnit, icon };
      this.$http
        .post(this.$http.adornUrl("/product/category/update"), category)
        .then(({ data: result }) => {
          if (result.code != "0") {
            this.$message({ type: "error", message: "保存失败" });
            return;
          }
          this.expandedKey = [this.category.parentCid];
          this.$message({ type: "success", message: "保存成功" });
          this.dialogVisible = false;
          this.getMenus();
        })
        .catch((e) => {
          this.$message({ type: "error", message: "系统异常" });
        });
    },
    // 打开修改分类模态框
    async edit(node, data) {
      this.dialogVisible = true;
      const { data: category } = await this.$http.get(
        this.$http.adornUrl("/product/category/info/" + data.catId)
      );

      this.category = { ...this.category, ...category.data };
      this.dialogType = "edit";
      this.dialogTitle = "修改分类";
    },
    // 打开添加分类模态框
    append(node, data) {
      this.category = JSON.parse(JSON.stringify(this.defaultCategoryValue));
      this.category.parentCid = data.catId;
      this.category.catLevel = Number(data.catLevel + 1);
      this.dialogType = "add";
      this.dialogTitle = "添加分类";
      this.dialogVisible = true;
    },
    // 新增分类
    addCategory() {
      this.$http
        .post(this.$http.adornUrl("/product/category/save"), this.category)
        .then(({ data: result }) => {
          if (result.code != "0") {
            this.$message({ type: "error", message: "保存失败" });
            return;
          }
          this.$message({ type: "success", message: "保存成功" });
          this.getMenus();
          this.dialogVisible = false;
          this.$nextTick(() => (this.expandedKey = [this.category.parentCid]));
        })
        .catch((e) => {
          console.error(e);
          this.$message({ type: "error", message: "系统异常" });
        });
    },
    // 获取所有菜单
    getMenus() {
      this.$http
        .get(this.$http.adornUrl("/product/category/list/tree"))
        .then(({ data }) => {
          this.menus = data.data;
        });
    },
    // 删除指定节点
    remove(node, data) {
      this.$confirm(`是否删除「${data.name}」菜单`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const ids = [data.catId];
          // 删除
          this.$http
            .post(this.$http.adornUrl("/product/category/delete"), ids)
            .then(({ data }) => {
              this.getMenus();
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              // 在页面渲染后打开之前子元素被删除的父节点
              this.$nextTick(() => {
                this.expandedKey = [node.parent.data.catId];
              });
            });
        })
        .catch(() => {});
    },
    allowDrop(draggingNode, dropNode, type) {
      // console.log(draggingNode, dropNode, type);
      // 被拖动的当前节点以及所在的父节点总层数不能大于3
      const maxLevel = this.countNodeLevel(draggingNode.data, 0);
      const deep = Math.abs(maxLevel - draggingNode.data.catLevel + 1);
      if (type === "inner") {
        return dropNode.level != deep && dropNode.level + deep <= 3;
      } else {
        return deep + dropNode.parent.level <= 3;
      }
      // console.log(level);
    },
    // 找到所有子节点并求出最大深度
    countNodeLevel(node, maxLevel = 0) {
      if (node.children != null && node.children.length > 0) {
        for (const child of node.children) {
          if (child.catLevel > maxLevel) {
            maxLevel = child.catLevel;
          }
          const level = this.countNodeLevel(child, maxLevel);
          if (level > maxLevel) {
            maxLevel = level;
          }
        }
      }
      return maxLevel;
    },
    // 节点拖拽成功时触发
    nodeDrop(draggingNode, dropNode, type, event) {
      // 当前节点父节点id
      let pCid = null;
      let childNodes = [];
      let list = [];
      if (type == "before" || type == "after") {
        pCid = dropNode.parent.data.catId;
        childNodes = dropNode.parent.childNodes;
      } else {
        pCid = dropNode.data.catId;
        childNodes = dropNode.childNodes;
      }
      // 找节点信息，顺序、节点、层级
      list = this.findCurrentInTarget(pCid, childNodes);
      this.batchList = [...this.batchList, ...list];
    },
    // 查找当前元素在目标数据集中的位置
    findCurrentInTarget(pCid = 0, list = []) {
      return list.map((data, i) => {
        const newCategory = {
          catId: data.data.catId,
          catLevel: data.level,
          sort: i,
          parentCid: pCid,
        };
        if (data.data.parentCid == pCid) {
          delete newCategory["parentCid"];
        }
        return newCategory;
      });
    },
  },
  created() {
    this.getMenus();
  },
};
</script>

<style scope>
</style>