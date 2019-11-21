<template>
  <dx-validation-group>
    <div class="register-header">
      <div class="title">IEA</div>
      <div>Register an account</div>
    </div>
    <div class="dx-field">
      <dx-text-box :value.sync="username" placeholder="Username" width="100%">
        <dx-validator>
          <dx-required-rule message="Username is required"></dx-required-rule>
        </dx-validator>
      </dx-text-box>
    </div>
    <div class="dx-field">
      <dx-text-box :value.sync="fullName" placeholder="Name" width="100%">
        <dx-validator>
          <dx-required-rule message="Name is required"></dx-required-rule>
        </dx-validator>
      </dx-text-box>
    </div>
    <div class="dx-field">
      <dx-text-box :value.sync="email" placeholder="Email" width="100%">
        <dx-validator>
          <dx-required-rule type="required" message="Email is required"></dx-required-rule>
          <dx-email-rule type="email" message="Email is invalid"></dx-email-rule>
        </dx-validator>
      </dx-text-box>
    </div>
    <div class="dx-field">
      <dx-text-box mode="password" :value.sync="password" placeholder="Password" width="100%">
        <dx-validator>
          <dx-required-rule message="Password is required"></dx-required-rule>
        </dx-validator>
      </dx-text-box>
    </div>
    <div class="dx-field">
      <dx-text-box
        mode="password"
        :value.sync="confPassword"
        placeholder="Confirm Password"
        width="100%"
      >
        <dx-validator>
          <dx-required-rule message="Conf. Password is required"/>
          <dx-compare-rule
            :comparison-target="passwordComparison"
            message="Password and Conf. Password do not match"
          />
        </dx-validator>
      </dx-text-box>
    </div>
    <div class="dx-field">
      <dx-button type="default" text="Register" @click="onRegisterClick($event)" width="100%"></dx-button>
    </div>

    <div class="dx-field">
      <dx-button type="normal" text="Back" @click="onBackClick()" width="100%"></dx-button>
    </div>
    <md-snackbar
      :md-position="'center'"
      :md-duration="4000"
      :md-active.sync="showSnackbar"
      md-persistent
    >
      <span class="snackbar">Account already exists!</span>
    </md-snackbar>
  </dx-validation-group>
</template>

<script lang="js">
import DxButton from "devextreme-vue/button";
import DxTextBox from "devextreme-vue/text-box";
import DxValidationGroup from "devextreme-vue/validation-group";
import {
  DxValidator,
  DxRequiredRule,
  DxCompareRule,
  DxEmailRule
} from "devextreme-vue/validator";
import sha512 from "js-sha512/src/sha512.js";

export default {
  data() {
    return {
      username: "",
      fullName: "",
      email: "",
      password: "",
      confPassword: "",
      passwordComparison: () => {
        return this.password;
      },
      showSnackbar: false
    };
  },
  components: {
    DxButton,
    DxTextBox,
    DxValidator,
    DxRequiredRule,
    DxCompareRule,
    DxEmailRule,
    DxValidationGroup
  },
  methods: {
    registerAccount(user) {
      fetch("http://localhost:8080/iea/api/auth/register", {
      method: "POST",
      headers: {
          "Content-Type": "text/plain"
      },
      body: JSON.stringify(user)
      })
      .then(
        function(response) {
        if (response.status == 200) {
          this.$router.push("/login");
        } else {
          this.showSnackbar = true;
        }
        }.bind(this)
      )
      .catch(error => {
          console.log(error);
      });
    },
    onRegisterClick(e) {
      if (!e.validationGroup.validate().isValid) {
        return;
      }

      var user = {
        fullName: this.fullName,
        email: this.email,
        username: this.username,
        password: sha512(this.password)
      };
      this.registerAccount(user);
    },
    onBackClick() {
      this.$router.push("/login");
    }
  }
};
</script>

<style lang="scss">
@import "../themes/generated/variables.base.scss";

.register-header {
  text-align: center;
  margin-bottom: 40px;

  .title {
    color: $base-accent;
    font-weight: bold;
    font-size: 30px;
    margin: 0;
  }
}
.snackbar {
  margin-left: auto;
  margin-right: auto;
}
</style>
