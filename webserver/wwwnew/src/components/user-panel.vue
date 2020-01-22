<template>
  <div class="user-panel">
    <div class="user-info">
      <div class="image-container">
        <div class="user-image"/>
      </div>
      <div class="user-name">{{getUsername()}}</div>
    </div>

    <dx-context-menu
      v-if="menuMode === 'context'"
      target=".user-button"
      :items="menuItems"
      :width="170"
      :position="menuPositionConfig"
      show-event="dxclick"
      css-class="user-menu"
    />

    <dx-list v-if="menuMode === 'list'" class="dx-toolbar-menu-action" :items="menuItems"/>
  </div>
</template>

<script>
import DxContextMenu from "devextreme-vue/context-menu";
import DxList from "devextreme-vue/list";

export default {
  props: {
    menuMode: String,
    menuItems: Array
  },
  data() {
    return {
      menuPositionConfig: {
        my: "top center",
        at: "bottom center"
      },
      account: {}
    };
  },
  components: {
    DxContextMenu,
    DxList
  },
  mounted() {
    fetch("http://localhost:8080/auth/account", {
      method: "GET",
      credentials: "include"
    }).then(
      async function(response) {
        let tmp = await response.json();
        this.account = tmp;
      }.bind(this)
    );
  },
  methods: {
    getUsername() {
      return this.account ? this.account.fullName : "";
    }
  }
};
</script>

<style lang="scss">
@import "../themes/generated/variables.base.scss";

.user-info {
  display: flex;
  align-items: center;

  .dx-toolbar-menu-section & {
    padding: 10px 6px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }

  .image-container {
    overflow: hidden;
    border-radius: 50%;
    height: 35px;
    width: 35px;
    margin: 0 4px;
    border: 1px solid rgba(0, 0, 0, 0.1);
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15);

    .user-image {
      width: 100%;
      height: 100%;
      background: url("https://cdn1.iconfinder.com/data/icons/freeline/32/account_friend_human_man_member_person_profile_user_users-512.png")
        no-repeat #fff;
      background-size: cover;
    }
  }

  .user-name {
    font-size: 14px;
    color: $base-text-color;
    margin: 0 9px;
  }
}

.user-panel {
  .dx-list-item .dx-icon {
    vertical-align: middle;
    color: rgba(0, 0, 0, 0.87);
    margin-right: 16px;
  }
  .dx-rtl .dx-list-item .dx-icon {
    margin-right: 0;
    margin-left: 16px;
  }
}

.dx-context-menu.user-menu.dx-menu-base {
  &.dx-rtl {
    .dx-submenu .dx-menu-items-container .dx-icon {
      margin-left: 16px;
    }
  }
  .dx-submenu .dx-menu-items-container .dx-icon {
    margin-right: 16px;
  }
  .dx-menu-item .dx-menu-item-content {
    padding: 3px 15px 4px;
  }
}

.dx-theme-generic .user-menu .dx-menu-item-content .dx-menu-item-text {
  padding-left: 4px;
  padding-right: 4px;
}
</style>
