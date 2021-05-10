<template>
  <div class="store-product-container">
    <aside class="store-product-group-list">
      <ul>
        <li class="menu-group-header">메뉴 그룹 관리</li>
        <li
          v-for="(group, index) in menuGroupList"
          :ref="`group-name-${index}`"
          :key="index"
          class="group-name"
          @click="activeItem(index)"
        >
          {{ group }}
        </li>
        <li class="add-group-button">
          <input
            v-model="newGroupName"
            type="text"
            placeholder="그룹 추가하기"
            class="add-group-input"
            @keydown.enter="addNewGroup"
          />
          <awesome icon="plus" @click="addNewGroup"></awesome>
        </li>
      </ul>
    </aside>
    <main class="store-product-detail">main</main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menuGroupList: ['group1', 'group2', 'group3', 'group4 group4 group4 group4 group4 group4 group4'],
      newGroupName: '',
      active: 0,
    };
  },
  mounted() {
    if (this.$refs[`group-name-0`]) {
      this.$refs[`group-name-0`][0].classList.add('active');
    }
  },
  methods: {
    addNewGroup() {
      this.menuGroupList.push(this.newGroupName);
      this.newGroupName = '';
    },
    activeItem(index) {
      this.$refs[`group-name-${this.active}`][0].classList.toggle('active');
      this.$refs[`group-name-${index}`][0].classList.toggle('active');
      this.active = index;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
