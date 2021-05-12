<template>
  <aside class="store-product-group-list">
    <div class="menu-group-header">메뉴 그룹 관리</div>
    <ul class="menu-group-items">
      <li v-for="(group, index) in menuGroupList" :ref="`group-name-${index}`" :key="index" class="group-name">
        <span class="group-name-label" @click="activeItem(index)">
          <div v-if="!group.selected">{{ group.name }}</div>
          <input
            v-else
            v-model="group.name"
            type="text"
            class="group-name-input"
            @keydown.enter="updateGroupName(group)"
          />
        </span>
        <span v-show="!group.selected" class="group-name-revise" @click="group.selected = true">
          <awesome icon="pencil-alt"></awesome>
        </span>
        <span v-show="group.selected" class="group-name-confirm" @click="updateGroupName(group)">
          <awesome :icon="['far', 'check-circle']"></awesome>
        </span>
        <span class="group-name-delete" @click="removeGroup(group.groupId)">
          <awesome icon="eraser"></awesome>
        </span>
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
import { registerMenuGroup, updateMenuGroup, deleteMenuGroup } from '@/api/menuGroup';
import { mapMutations } from 'vuex';
export default {
  props: {
    groupList: {
      type: Array,
      default: () => [],
      require: true,
    },
  },
  data() {
    return {
      menuGroupList: this.groupList.map(x =>
        Object.assign(
          {
            selected: false,
          },
          x,
        ),
      ),
      newGroupName: '',
      active: 0,
    };
  },
  watch: {
    groupList(newValue) {
      this.menuGroupList = this.groupList.map(x =>
        Object.assign(
          {
            selected: false,
          },
          x,
        ),
      );
    },
  },
  mounted() {
    if (this.$refs[`group-name-0`]) {
      this.$refs[`group-name-0`][0].classList.add('active');
    }
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    async addNewGroup() {
      // this.grouList.push(this.newGroupName);
      try {
        this.setSpinnerState(true);
        await registerMenuGroup(this.newGroupName);
        this.newGroupName = '';
        this.$emit('updateGroupList');
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('새로운 그룹 생성에 실패했습니다.');
      }
    },
    async removeGroup(groupId) {
      try {
        this.setSpinnerState(true);
        await deleteMenuGroup(groupId);
        this.$emit('updateGroupList');
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('메뉴 그룹 삭제에 실패하였습니다.');
      }
    },
    activeItem(index) {
      this.$refs[`group-name-${this.active}`][0].classList.toggle('active');
      this.$refs[`group-name-${index}`][0].classList.toggle('active');
      this.active = index;
      this.$emit('pointing', index);
    },
    async updateGroupName(group) {
      try {
        this.setSpinnerState(true);
        await updateMenuGroup({
          groupId: group.groupId,
          name: group.name,
        });
        group.selected = false;
        this.$emit('updateGroupList');
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('그룹 이름 변경에 실패하였습니다.');
      }
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
.group-name-input {
  width: 120px;
  background: inherit;
  border: none;
  border-bottom: 1px solid white;
  color: inherit;
}
.group-name {
  line-height: 22px;

  padding: 4px;
  &:hover {
    .group-name-revise,
    .group-name-delete,
    .group-name-confirm {
      display: inline;
    }
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
}
.group-name-label {
  display: inline-flex;
  vertical-align: center;
  div {
    overflow-x: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  width: 120px;
  @include pc {
    width: 90%;
  }
  @include mobile {
    width: 90%;
  }
  @include xs-mobile {
    width: 90%;
  }
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
.group-name-revise,
.group-name-delete,
.group-name-confirm {
  cursor: pointer;
  margin-left: 2px;
  @include lg-pc {
    display: none;
  }
  @include pc {
    display: none;
  }
  &:hover {
    svg {
      transform: scale(1.1);
    }
  }
}
</style>
