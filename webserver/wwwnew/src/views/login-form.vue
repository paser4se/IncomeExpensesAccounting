<template>
  <dx-validation-group>
    <div class="login-header">
      <div class="title">{{ title }}</div>
      <div>Sign In to your account</div>
    </div>

    <div class="dx-field">
      <dx-text-box placeholder="Username" width="100%" :value.sync="username">
        <dx-validator>
          <dx-required-rule message="Username is required"/>
        </dx-validator>
      </dx-text-box>
    </div>

    <div class="dx-field">
      <dx-text-box placeholder="Password" width="100%" mode="password" :value.sync="password">
        <dx-validator>
          <dx-required-rule message="Password is required"/>
        </dx-validator>
      </dx-text-box>
    </div>

    <div class="dx-field">
      <dx-check-box :value.sync="rememberUser" text="Remember me"/>
    </div>

    <div class="dx-field">
      <dx-button type="default" text="Login" width="100%" @click="onLoginClick"/>
    </div>

    <div class="dx-field">
      <router-link to="/recovery">
        <a>Forgot password ?</a>
      </router-link>
    </div>

    <div class="dx-field">
      <dx-button type="normal" text="Create an account" @click="registerAccount()" width="100%"/>
    </div>
  </dx-validation-group>
</template>

<script>
import DxButton from "devextreme-vue/button";
import DxCheckBox from "devextreme-vue/check-box";
import DxTextBox from "devextreme-vue/text-box";
import DxValidationGroup from "devextreme-vue/validation-group";
import DxValidator, { DxRequiredRule } from "devextreme-vue/validator";
import sha512 from "js-sha512/src/sha512.js";

import auth from "../auth";

export default {
  data() {
    return {
      title: this.$appInfo.title,
      username: localStorage.getItem("username"),
      password: "",
      rememberUser: localStorage.getItem("rememberme") == "true"
    };
  },
  components: {
    DxButton,
    DxCheckBox,
    DxTextBox,
    DxValidator,
    DxRequiredRule,
    DxValidationGroup
  },
  methods: {
    logIn(username, hashedpassword) {
      var user = {
        username: username,
        password: hashedpassword
      };

      fetch("http://localhost:8085/iea/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        credentials: "include",
        body: JSON.stringify(user)
      })
        .then(
          function(response) {
            console.log(response);
            if (response.status == 200) {
              auth.setAuthenticated(true);
              localStorage.setItem("isLoggedIn", "true");
              this.$router.push(this.$route.query.redirect || "/home");
            } else {
              localStorage.setItem("isLoggedIn", "false");
            }
          }.bind(this)
        )
        .catch(error => {
          console.log(error);
        });
    },
    logOut() {
      auth.setAuthenticated(false);
    },
    onLoginClick(e) {
      if (!e.validationGroup.validate().isValid) {
        return;
      }

      this.logIn(this.username, sha512(this.password));
      this.$router.push(this.$route.query.redirect || "/home");

      if (this.rememberUser) {
        localStorage.setItem("username", this.username);
        localStorage.setItem("rememberme", true);
      } else {
        localStorage.setItem("username", "");
        localStorage.setItem("rememberme", false);
      }
      e.validationGroup.reset();
    },
    registerAccount() {
      this.$router.push("/register");
    }
  }
};
</script>

<style lang="scss">
@import "../themes/generated/variables.base.scss";

.login-header {
  text-align: center;
  margin-bottom: 40px;

  .title {
    color: $base-accent;
    font-weight: bold;
    font-size: 30px;
    margin: 0;
  }
}
</style>
