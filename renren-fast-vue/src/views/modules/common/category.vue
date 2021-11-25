<template>
  <el-tree
    :data="menus"
    node-key="catId"
    :props="defaultProps"
    :expand-on-click-node="false"
    ref="menuTree"
    @node-click="nodeClick"
  />
</template>

<script>
export default {
  data() {
    return {
      menus: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },
  activated() {
    this.getMenus()
  },
  methods: {
     // 获取所有菜单
    getMenus() {
      this.$http
        .get(this.$http.adornUrl("/product/category/list/tree"))
        .then(({ data }) => {
          this.menus = data.data;
        });
    },
    nodeClick(node) {
      this.$emit('click', node)
    }
  }
};
</script>

<style>
</style>