<template>
  <div>
    <h2 class="content-block">Profile</h2>

    <div class="content-block dx-card responsive-paddings">
      <div class="form-avatar">
        <img :src="imageSrc">
      </div>
      <span>{{ formData.Notes }}</span>
    </div>

    <div class="content-block dx-card responsive-paddings">
      <dx-form
        id="form"
        label-location="top"
        col-count="auto"
        :form-data="formData"
        :min-col-width="233"
      />
      <md-button class="md-raised md-primary right" @click="updateAccount()">Update</md-button>
    </div>
  </div>
</template>

<script>
import DxForm from "devextreme-vue/form";

export default {
  data() {
    return {
      imageSrc: `https://cdn1.iconfinder.com/data/icons/freeline/32/account_friend_human_man_member_person_profile_user_users-512.png`,
      formData: {
        prefix: "",
        fullName: "",
        address: "",
        notes: "",
        email: ""
      },
      account: {}
    };
  },
  mounted() {
    fetch("http://localhost:8085/iea/api/auth/account", {
      method: "GET",
      credentials: "include"
    }).then(
      async function(response) {
        let tmp = await response.json();
        this.account = tmp;
        this.formData.prefix = this.account.prefix;
        this.formData.fullName = this.account.fullName;
        this.formData.address = this.account.address;
        this.formData.notes = this.account.notes;
        this.formData.email = this.account.email;
      }.bind(this)
    );
  },
  components: {
    DxForm
  },
  methods: {
    updateAccount() {
      var obj = {
        prefix: this.formData.prefix ? this.formData.prefix : "",
        fullName: this.formData.fullName ? this.formData.fullName : "",
        address: this.formData.address ? this.formData.address : "",
        notes: this.formData.notes ? this.formData.notes : "",
        email: this.formData.email ? this.formData.email : "",
        id: this.account.id + ""
      };
      fetch("http://localhost:8085/iea/api/auth/account/" + this.account.id, {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        body: JSON.stringify(obj)
      }).then(function(response) {
        console.log(response);
      });
    }
  }
};
</script>

<style lang="scss">
.form-avatar {
  float: left;
  height: 120px;
  width: 120px;
  margin-right: 20px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  background-color: #fff;
  overflow: hidden;

  img {
    height: 120px;
    display: block;
    margin: 0 auto;
  }
}
.right {
  float: right;
}
</style>
