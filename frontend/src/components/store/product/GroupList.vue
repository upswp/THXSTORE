<template>
  <aside class="store-product-group-list">
    <div class="menu-group-header">메뉴 그룹 관리</div>
    <ul class="menu-group-items">
      <li
        v-for="(group, index) in menuGroupList"
        :ref="`group-name-${index}`"
        :key="index"
        class="group-name"
        @click="activeItem(index)"
      >
        {{ group }}
      </li>
    </ul>
    <div class="add-group-button">
      <input
        v-model="newGroupName"
        type="text"
        placeholder="그룹 추가하기"
        class="add-group-input"
        @keydown.enter="addNewGroup"
      />
      <awesome icon="plus" @click="addNewGroup"></awesome>
    </div>
  </aside>
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
.store-product-group-list {
  width: 200px;
  padding: 5px;
  // border-right:1px solid $gray400;

  @include lg-pc {
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.3);
  }
  @include pc {
    width: 100%;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  }
  @include mobile {
    width: 100%;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  }
  @include xs-mobile {
    width: 100%;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  }
}

.group-name {
  line-height: 22px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: 4px;
  &:hover {
    background-color: $blue200;
    // color:rgb(90, 86, 119);
    color: white;
  }
  &.active {
    background-color: $blue200 !important;
    color: white;
    &::before {
      background-color: #dbefc2;
    }
  }
  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    margin-right: 5px;
  }
  cursor: pointer;
}
.add-group-button {
  cursor: pointer;
  // text-align: center;
  margin-top: 10px;
  padding: 4px;
  svg:hover {
    -webkit-animation: spin 1s ease;
    -moz-animation: spin 1s ease;
    animation: spin 1s ease;
  }
  @include pc {
    text-align: right;
  }
  @include mobile {
    text-align: right;
  }
  @include xs-mobile {
    text-align: right;
  }
}
@-moz-keyframes spin {
  100% {
    -moz-transform: rotate(180deg);
  }
}
@-webkit-keyframes spin {
  100% {
    -webkit-transform: rotate(180deg);
  }
}
@keyframes spin {
  100% {
    transform: rotate(180deg);
  }
}

.menu-group-header {
  margin: 10px 3px;
  font-weight: bold;
  @include mobile {
    padding-bottom: 10px;
    border-bottom: 1px solid $gray400;
  }
  @include xs-mobile {
    padding-bottom: 10px;
    border-bottom: 1px solid $gray400;
  }
}
.add-group-input {
  background-color: $gray200;
  border: none;
  width: 160px;
  margin-right: 5px;
  padding: 5px;
}
.menu-group-items {
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2) inset;
  padding: 10px 0;
  @include lg-pc {
    box-shadow: none;
  }
  @include pc {
    overflow-y: scroll;
    height: 110px;
  }
  @include mobile {
    overflow-y: scroll;
    height: 110px;
  }
  @include xs-mobile {
    overflow-y: scroll;
    height: 110px;
  }
}
</style>
